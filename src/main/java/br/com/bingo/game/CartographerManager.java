package br.com.bingo.game;

import br.com.bingo.Bingo;
import br.com.bingo.kits.MappedStructure;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StructureSearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class CartographerManager {

    private static final int SEARCH_RADIUS_CHUNKS = 100; // em CHUNKS (~1600 blocos). locateNearestStructure usa raio em chunks
    private static final long SEARCH_START_DELAY_TICKS = 20L; // 1s apos o inicio
    private static final long SEARCH_PERIOD_TICKS = 2L;       // uma busca a cada 2 ticks (espalha o custo)
    private static final long COOLDOWN_PHASE1_MS = 180_000L; // 3 min
    private static final long COOLDOWN_PHASE2_MS = 60_000L;  // 1 min
    private static final int DRAFT_SIZE_PHASE1 = 3;
    private static final int DRAFT_SIZE_PHASE2 = 5;

    private final Map<MappedStructure, Location> located = new HashMap<>();
    private final Map<UUID, Set<MappedStructure>> owned = new HashMap<>();
    private final Map<UUID, Long> cooldownUntil = new HashMap<>();
    private final Map<UUID, List<MappedStructure>> currentDraft = new HashMap<>();
    private boolean phase2 = false;
    private final Random random = new Random();

    /**
     * Localiza a variante mais proxima de cada MappedStructure no mundo dado.
     *
     * IMPORTANTE: roda na MAIN THREAD (uma busca por vez, espalhada em ticks), NAO async.
     * locateNearestStructure com findUnexplored=true MUTA o cache interno de estruturas do NMS
     * (StructureCheck / Long2ObjectOpenHashMap), que NAO e thread-safe; chama-lo de outra thread
     * corrompe o mapa e derruba o servidor (ArrayIndexOutOfBoundsException no rehash). Espalhar
     * uma busca por tick na main thread evita tanto a corrupcao quanto um unico congelamento longo.
     *
     * Como tudo (busca + leitura via rollDraft/getLocation) roda na main thread, os mapas de estado
     * nao precisam ser concorrentes.
     */
    public void searchStructures(World world) {
        final Location origin = world.getSpawnLocation();
        final List<MappedStructure> structureOf = new ArrayList<>();
        final List<Structure> variantOf = new ArrayList<>();
        for (MappedStructure ms : MappedStructure.values()) {
            for (Structure variant : ms.getVariants()) {
                structureOf.add(ms);
                variantOf.add(variant);
            }
        }

        new BukkitRunnable() {
            private int index = 0;

            @Override
            public void run() {
                if (index >= variantOf.size()) {
                    Bukkit.getLogger().info("[Cartographer] Estruturas localizadas: " + located.size());
                    cancel();
                    return;
                }
                MappedStructure structure = structureOf.get(index);
                Structure variant = variantOf.get(index);
                index++;

                StructureSearchResult result = world.locateNearestStructure(origin, variant, SEARCH_RADIUS_CHUNKS, true);
                if (result != null) {
                    Location found = result.getLocation();
                    Location existing = located.get(structure);
                    if (existing == null || found.distanceSquared(origin) < existing.distanceSquared(origin)) {
                        located.put(structure, found);
                    }
                }
            }
        }.runTaskTimer(Bingo.getInstance(), SEARCH_START_DELAY_TICKS, SEARCH_PERIOD_TICKS);
    }

    /** Inicia o jogador com um conjunto vazio e o draft ja disponivel. */
    public void initPlayer(UUID uuid) {
        owned.putIfAbsent(uuid, new HashSet<>());
        cooldownUntil.put(uuid, System.currentTimeMillis());
    }

    public boolean isDraftAvailable(UUID uuid) {
        return System.currentTimeMillis() >= cooldownUntil.getOrDefault(uuid, 0L);
    }

    public long remainingCooldownSeconds(UUID uuid) {
        long remaining = cooldownUntil.getOrDefault(uuid, 0L) - System.currentTimeMillis();
        return Math.max(0, remaining / 1000);
    }

    public int draftSize() {
        return phase2 ? DRAFT_SIZE_PHASE2 : DRAFT_SIZE_PHASE1;
    }

    /** Sorteia ate draftSize() estruturas de (localizadas AND nao-possuidas) e guarda como o draft atual do jogador. */
    public List<MappedStructure> rollDraft(UUID uuid) {
        Set<MappedStructure> playerOwned = owned.getOrDefault(uuid, Collections.emptySet());
        List<MappedStructure> pool = new ArrayList<>();
        for (MappedStructure s : located.keySet()) {
            if (!playerOwned.contains(s)) {
                pool.add(s);
            }
        }
        Collections.shuffle(pool, random);
        List<MappedStructure> options = new ArrayList<>(pool.subList(0, Math.min(draftSize(), pool.size())));
        currentDraft.put(uuid, options);
        return options;
    }

    /** Confirma a escolha se ela estava no draft atual. Retorna false se invalida. */
    public boolean chooseFromDraft(UUID uuid, MappedStructure structure) {
        List<MappedStructure> options = currentDraft.get(uuid);
        if (options == null || !options.contains(structure)) {
            return false;
        }
        owned.computeIfAbsent(uuid, k -> new HashSet<>()).add(structure);
        currentDraft.remove(uuid);
        long cooldown = phase2 ? COOLDOWN_PHASE2_MS : COOLDOWN_PHASE1_MS;
        cooldownUntil.put(uuid, System.currentTimeMillis() + cooldown);
        return true;
    }

    public Set<MappedStructure> getOwned(UUID uuid) {
        return owned.getOrDefault(uuid, Collections.emptySet());
    }

    public Location getLocation(MappedStructure structure) {
        return located.get(structure);
    }

    /** Buff de fase 2: liga o modo buffado e libera o draft do jogador imediatamente. */
    public void onPhase2(UUID uuid) {
        phase2 = true;
        cooldownUntil.put(uuid, System.currentTimeMillis());
    }
}
