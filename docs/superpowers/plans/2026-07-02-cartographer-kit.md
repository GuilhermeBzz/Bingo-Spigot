# Kit Cartógrafo — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Adicionar o kit Cartógrafo, que localiza estruturas do overworld (análogo ao Explorer) com mecânica de draft e buff de fase 2.

**Architecture:** Estado e lógica isolados num `CartographerManager` (novo por partida, como o `ScoreboardBingo`). Um enum `MappedStructure` mapeia cada estrutura lógica a variantes `Structure` + ícone + nome. A localização roda async (espelha `searchBiomes`). O kit e o listener delegam ao manager via `gameManager.getCartographerManager()`.

**Tech Stack:** Java 21 (source), Spigot 26.1.2 API (Mojang-mapped), Maven. **Build exige JDK 25.**

**Nota sobre testes:** este é um plugin Spigot sem testes automatizados (Bukkit exige servidor rodando). A verificação de cada tarefa é **compilação com JDK 25**; a validação funcional é o checklist in-game da Task 8.

**Comando de compilação (reutilizado em todas as tasks):**
```powershell
$env:JAVA_HOME="C:\Users\Bz\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.1.8-hotspot"; & "C:\Program Files\Apache\Maven\apache-maven-3.9.14\bin\mvn.cmd" -q -DskipTests clean compile
```
Esperado: sem saída de erro e `EXIT/LASTEXITCODE = 0` (BUILD SUCCESS).

---

### Task 1: Enum `MappedStructure`

**Files:**
- Create: `src/main/java/br/com/bingo/kits/MappedStructure.java`

- [ ] **Step 1: Criar o enum com as estruturas overworld, ícones e variantes**

```java
package br.com.bingo.kits;

import org.bukkit.Material;
import org.bukkit.generator.structure.Structure;

import java.util.Arrays;
import java.util.List;

public enum MappedStructure {
    VILLAGE("Vila", Material.BELL, Structure.VILLAGE_PLAINS, Structure.VILLAGE_DESERT, Structure.VILLAGE_SAVANNA, Structure.VILLAGE_SNOWY, Structure.VILLAGE_TAIGA),
    PILLAGER_OUTPOST("Posto Avancado", Material.CROSSBOW, Structure.PILLAGER_OUTPOST),
    OCEAN_MONUMENT("Monumento Oceanico", Material.SEA_LANTERN, Structure.MONUMENT),
    MANSION("Mansao", Material.DARK_OAK_LOG, Structure.MANSION),
    DESERT_PYRAMID("Piramide do Deserto", Material.SANDSTONE, Structure.DESERT_PYRAMID),
    JUNGLE_TEMPLE("Templo da Selva", Material.MOSSY_COBBLESTONE, Structure.JUNGLE_PYRAMID),
    IGLOO("Iglu", Material.SNOW_BLOCK, Structure.IGLOO),
    SWAMP_HUT("Cabana do Pantano", Material.CAULDRON, Structure.SWAMP_HUT),
    SHIPWRECK("Naufragio", Material.OAK_BOAT, Structure.SHIPWRECK, Structure.SHIPWRECK_BEACHED),
    BURIED_TREASURE("Tesouro Enterrado", Material.CHEST, Structure.BURIED_TREASURE),
    RUINED_PORTAL("Portal em Ruinas", Material.CRYING_OBSIDIAN, Structure.RUINED_PORTAL, Structure.RUINED_PORTAL_DESERT, Structure.RUINED_PORTAL_JUNGLE, Structure.RUINED_PORTAL_SWAMP, Structure.RUINED_PORTAL_MOUNTAIN, Structure.RUINED_PORTAL_OCEAN),
    ANCIENT_CITY("Cidade Antiga", Material.SCULK_CATALYST, Structure.ANCIENT_CITY),
    TRAIL_RUINS("Ruinas de Trilha", Material.SUSPICIOUS_GRAVEL, Structure.TRAIL_RUINS),
    TRIAL_CHAMBERS("Camaras de Provacao", Material.TRIAL_KEY, Structure.TRIAL_CHAMBERS),
    MINESHAFT("Mina Abandonada", Material.RAIL, Structure.MINESHAFT, Structure.MINESHAFT_MESA),
    STRONGHOLD("Fortaleza", Material.END_PORTAL_FRAME, Structure.STRONGHOLD),
    OCEAN_RUIN("Ruina Oceanica", Material.PRISMARINE_BRICKS, Structure.OCEAN_RUIN_COLD, Structure.OCEAN_RUIN_WARM),
    ;

    private final String displayName;
    private final Material icon;
    private final List<Structure> variants;

    MappedStructure(String displayName, Material icon, Structure... variants) {
        this.displayName = displayName;
        this.icon = icon;
        this.variants = Arrays.asList(variants);
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getIcon() {
        return icon;
    }

    public List<Structure> getVariants() {
        return variants;
    }
}
```

Nota: os nomes usam ASCII (sem acento) de propósito, para servirem de chave estável de comparação por `ChatColor.stripColor` no listener. Os acentos ficam por conta da futura tradução visual, se desejado.

- [ ] **Step 2: Compilar**

Run: (comando de compilação acima)
Expected: BUILD SUCCESS (EXIT 0).

- [ ] **Step 3: Commit**

```bash
git add src/main/java/br/com/bingo/kits/MappedStructure.java
git commit -m "feat(cartografo): enum MappedStructure com estruturas overworld"
```

---

### Task 2: `CartographerManager`

**Files:**
- Create: `src/main/java/br/com/bingo/game/CartographerManager.java`

- [ ] **Step 1: Criar o manager com estado, busca async e lógica de draft/cooldown**

```java
package br.com.bingo.game;

import br.com.bingo.Bingo;
import br.com.bingo.kits.MappedStructure;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.structure.Structure;
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

    private static final int SEARCH_RADIUS = 5000;
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

    /** Localiza (async) a variante mais proxima de cada MappedStructure no mundo dado. Espelha searchBiomes. */
    public void searchStructures(World world) {
        Bukkit.getScheduler().runTaskAsynchronously(Bingo.getInstance(), () -> {
            Location origin = world.getSpawnLocation();
            for (MappedStructure structure : MappedStructure.values()) {
                Location nearest = null;
                double nearestDist = Double.MAX_VALUE;
                for (Structure variant : structure.getVariants()) {
                    StructureSearchResult result = world.locateNearestStructure(origin, variant, SEARCH_RADIUS, true);
                    if (result != null) {
                        double dist = result.getLocation().distanceSquared(origin);
                        if (dist < nearestDist) {
                            nearestDist = dist;
                            nearest = result.getLocation();
                        }
                    }
                }
                if (nearest != null) {
                    located.put(structure, nearest);
                }
            }
            Bukkit.getLogger().info("[Cartographer] Estruturas localizadas: " + located.size());
        });
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
```

- [ ] **Step 2: Compilar**

Run: (comando de compilação)
Expected: BUILD SUCCESS.

- [ ] **Step 3: Commit**

```bash
git add src/main/java/br/com/bingo/game/CartographerManager.java
git commit -m "feat(cartografo): CartographerManager (busca async, draft, cooldown)"
```

---

### Task 3: Integrar o `CartographerManager` no `GameManager` (estado + busca)

**Files:**
- Modify: `src/main/java/br/com/bingo/game/GameManager.java`

- [ ] **Step 1: Declarar o campo do manager**

Localize a declaração do campo do scoreboard (procure por `ScoreboardBingo scoreboardBingo;`) e adicione **logo abaixo dela**:

```java
    public CartographerManager cartographerManager = new CartographerManager();
```

- [ ] **Step 2: Adicionar o getter**

Adicione este método na classe (por exemplo, logo abaixo do construtor):

```java
    public CartographerManager getCartographerManager() {
        return cartographerManager;
    }
```

- [ ] **Step 3: Resetar o manager a cada partida**

Localize a linha de reset do scoreboard (procure por `if(scoreboardBingo != null) this.scoreboardBingo = new ScoreboardBingo(this);`) e adicione **logo abaixo dela**:

```java
        this.cartographerManager = new CartographerManager();
```

- [ ] **Step 4: Disparar a busca de estruturas junto com a de biomas**

Localize a chamada `searchBiomes(overworld);` e adicione **logo abaixo dela**:

```java
        cartographerManager.searchStructures(overworld);
```

- [ ] **Step 5: Compilar**

Run: (comando de compilação)
Expected: BUILD SUCCESS.

- [ ] **Step 6: Commit**

```bash
git add src/main/java/br/com/bingo/game/GameManager.java
git commit -m "feat(cartografo): integra CartographerManager no GameManager (estado + busca)"
```

---

### Task 4: Kit `Cartographer`

**Files:**
- Create: `src/main/java/br/com/bingo/kits/definitions/Cartographer.java`

- [ ] **Step 1: Criar o kit (bussola + buff de fase 2)**

```java
package br.com.bingo.kits.definitions;

import br.com.bingo.Bingo;
import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Cartographer extends Kit {

    public static final String COMPASS_NAME = ChatColor.GOLD + "Cartographer's Compass";

    private ItemStack buildCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(COMPASS_NAME);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        compass.setItemMeta(meta);
        return compass;
    }

    @Override
    public void giveKit(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(buildCompass());
        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(buildCompass());
        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        Bingo.getInstance().gameManager.getCartographerManager().onPhase2(player.getUniqueId());
        player.sendMessage(ChatColor.GOLD + "Cartografo evoluido: draft mais rapido (1 min) e com mais opcoes (5)!");
    }
}
```

- [ ] **Step 2: Compilar**

Run: (comando de compilação)
Expected: BUILD SUCCESS.

- [ ] **Step 3: Commit**

```bash
git add src/main/java/br/com/bingo/kits/definitions/Cartographer.java
git commit -m "feat(cartografo): definicao do kit Cartographer"
```

---

### Task 5: `CartographerListener` (bússola + menus + draft)

**Files:**
- Create: `src/main/java/br/com/bingo/kits/listeners/CartographerListener.java`

- [ ] **Step 1: Criar o listener**

```java
package br.com.bingo.kits.listeners;

import br.com.bingo.game.CartographerManager;
import br.com.bingo.game.GameManager;
import br.com.bingo.kits.MappedStructure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class CartographerListener implements Listener {

    private static final String MAP_TITLE = ChatColor.DARK_RED + "Mapa de Estruturas";
    private static final String DRAFT_TITLE = ChatColor.DARK_RED + "Draft de Estruturas";
    private static final int DRAFT_BUTTON_SLOT = 26;
    private static final String COMPASS_NAME = ChatColor.GOLD + "Cartographer's Compass";

    private final GameManager gameManager;

    public CartographerListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onCompassUse(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        ItemMeta meta = event.getItem().getItemMeta();
        if (meta == null || !COMPASS_NAME.equals(meta.getDisplayName())) return;
        if (event.getAction().name().contains("RIGHT")) {
            openMap(event.getPlayer());
        }
    }

    public void openMap(Player player) {
        CartographerManager manager = gameManager.getCartographerManager();
        Inventory menu = Bukkit.createInventory(null, 9 * 3, MAP_TITLE);

        for (MappedStructure structure : manager.getOwned(player.getUniqueId())) {
            ItemStack icon = new ItemStack(structure.getIcon());
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.setDisplayName(ChatColor.GREEN + structure.getDisplayName());
            iconMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            icon.setItemMeta(iconMeta);
            menu.addItem(icon);
        }

        menu.setItem(DRAFT_BUTTON_SLOT, buildDraftButton(manager, player.getUniqueId()));
        player.openInventory(menu);
    }

    private ItemStack buildDraftButton(CartographerManager manager, UUID uuid) {
        boolean available = manager.isDraftAvailable(uuid);
        ItemStack button = new ItemStack(available ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta meta = button.getItemMeta();
        if (available) {
            meta.setDisplayName(ChatColor.GREEN + "Draftar estrutura");
        } else {
            meta.setDisplayName(ChatColor.RED + "Aguarde " + manager.remainingCooldownSeconds(uuid) + "s");
        }
        button.setItemMeta(meta);
        return button;
    }

    private void openDraft(Player player) {
        CartographerManager manager = gameManager.getCartographerManager();
        List<MappedStructure> options = manager.rollDraft(player.getUniqueId());
        if (options.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Nenhuma nova estrutura disponivel para draft.");
            return;
        }
        Inventory draft = Bukkit.createInventory(null, 9, DRAFT_TITLE);
        int slot = 2;
        for (MappedStructure structure : options) {
            ItemStack icon = new ItemStack(structure.getIcon());
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + structure.getDisplayName());
            icon.setItemMeta(meta);
            draft.setItem(slot, icon);
            slot++;
        }
        player.openInventory(draft);
    }

    private MappedStructure matchStructure(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        if (name == null) return null;
        for (MappedStructure s : MappedStructure.values()) {
            if (s.getDisplayName().equals(name)) return s;
        }
        return null;
    }

    private void pointCompass(Player player, Location location, String name) {
        if (location == null) return;
        ItemStack compass = null;
        for (ItemStack i : player.getInventory().getContents()) {
            if (i != null && i.getType() == Material.COMPASS) {
                compass = i;
                break;
            }
        }
        if (compass == null) return;
        CompassMeta meta = (CompassMeta) compass.getItemMeta();
        meta.setLodestone(location);
        meta.setLodestoneTracked(false);
        compass.setItemMeta(meta);
        player.setCompassTarget(location);
        player.sendMessage(ChatColor.GREEN + "Bussola apontada para: " + ChatColor.GOLD + name);
        player.closeInventory();
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(MAP_TITLE) && !title.equals(DRAFT_TITLE)) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        Player player = (Player) event.getWhoClicked();
        CartographerManager manager = gameManager.getCartographerManager();

        if (title.equals(MAP_TITLE)) {
            if (event.getRawSlot() == DRAFT_BUTTON_SLOT) {
                if (manager.isDraftAvailable(player.getUniqueId())) {
                    openDraft(player);
                } else {
                    player.sendMessage(ChatColor.RED + "Draft em cooldown: " + manager.remainingCooldownSeconds(player.getUniqueId()) + "s restantes.");
                }
                return;
            }
            MappedStructure clicked = matchStructure(event.getCurrentItem());
            if (clicked == null || !manager.getOwned(player.getUniqueId()).contains(clicked)) return;
            pointCompass(player, manager.getLocation(clicked), clicked.getDisplayName());
        } else {
            MappedStructure chosen = matchStructure(event.getCurrentItem());
            if (chosen == null) return;
            if (manager.chooseFromDraft(player.getUniqueId(), chosen)) {
                player.sendMessage(ChatColor.GREEN + "Estrutura adicionada: " + ChatColor.GOLD + chosen.getDisplayName());
                openMap(player);
            }
        }
    }
}
```

- [ ] **Step 2: Compilar**

Run: (comando de compilação)
Expected: BUILD SUCCESS.

- [ ] **Step 3: Commit**

```bash
git add src/main/java/br/com/bingo/kits/listeners/CartographerListener.java
git commit -m "feat(cartografo): CartographerListener (bussola, mapa, draft)"
```

---

### Task 6: Registrar o `KitType.CARTOGRAPHER`

**Files:**
- Modify: `src/main/java/br/com/bingo/kits/KitType.java`

- [ ] **Step 1: Adicionar a entrada do kit**

Localize a última entrada do enum (procure por `ALCHEMIST("Alchemist", Material.EXPERIENCE_BOTTLE, "Crie itens com receitas especiais!", new Alchemist()),`) e adicione **logo abaixo dela** (antes do `;`):

```java
    CARTOGRAPHER("Cartographer", Material.MAP, "Localize estruturas do mundo e rastreie-as com sua bussola!", new Cartographer()),
```

- [ ] **Step 2: Compilar**

Run: (comando de compilação)
Expected: BUILD SUCCESS.

- [ ] **Step 3: Commit**

```bash
git add src/main/java/br/com/bingo/kits/KitType.java
git commit -m "feat(cartografo): registra KitType.CARTOGRAPHER"
```

---

### Task 7: Wiring final (init do jogador + registro do listener)

**Files:**
- Modify: `src/main/java/br/com/bingo/game/GameManager.java`
- Modify: `src/main/java/br/com/bingo/Bingo.java`

- [ ] **Step 1: Inicializar o jogador Cartógrafo no início da partida**

Em `GameManager.java`, localize o bloco no loop de start dos jogadores:

```java
            if(kit && playerKit.get(player.getUniqueId()).equals(KitType.EXPLORER)){
                giveNewBiome(player);
            }
```

Substitua-o por:

```java
            if(kit && playerKit.get(player.getUniqueId()).equals(KitType.EXPLORER)){
                giveNewBiome(player);
            }
            if(kit && playerKit.get(player.getUniqueId()).equals(KitType.CARTOGRAPHER)){
                cartographerManager.initPlayer(player.getUniqueId());
            }
```

- [ ] **Step 2: Registrar o listener no onEnable**

Em `Bingo.java`, localize a linha `getServer().getPluginManager().registerEvents(new ExplorerListener(gameManager), this);` e adicione **logo abaixo dela**:

```java
        getServer().getPluginManager().registerEvents(new CartographerListener(gameManager), this);
```

Confirme que o import existe (adicione no topo se necessário): `import br.com.bingo.kits.listeners.CartographerListener;` — nota: `Bingo.java` já usa `import br.com.bingo.kits.listeners.*;`, então nenhum import novo é necessário.

- [ ] **Step 3: Compilar**

Run: (comando de compilação)
Expected: BUILD SUCCESS.

- [ ] **Step 4: Commit**

```bash
git add src/main/java/br/com/bingo/game/GameManager.java src/main/java/br/com/bingo/Bingo.java
git commit -m "feat(cartografo): wiring final (init do jogador + registro do listener)"
```

---

### Task 8: Build final e verificação in-game

**Files:** nenhum (verificação)

- [ ] **Step 1: Build completo (package) com JDK 25**

Run:
```powershell
$env:JAVA_HOME="C:\Users\Bz\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.1.8-hotspot"; & "C:\Program Files\Apache\Maven\apache-maven-3.9.14\bin\mvn.cmd" -q -DskipTests clean package
```
Expected: BUILD SUCCESS; jar gerado em `C:\Spigot\Servidor Spigot 26.2\plugins\Bingo-1.0.jar`.

- [ ] **Step 2: Checklist in-game (reiniciar o servidor primeiro)**

Verificar manualmente:
1. Criar partida com kit **Cartographer** → jogador recebe a bússola no start; o console loga `[Cartographer] Estruturas localizadas: N` (N > 0) após alguns segundos.
2. Clique direito na bússola → abre "Mapa de Estruturas" com o botão de draft no canto inferior direito (verde/disponível).
3. Clicar no botão → abre draft com **3** estruturas; escolher 1 → volta ao mapa com a estrutura adicionada; botão fica vermelho (cooldown).
4. Reabrir o draft antes de 3 min → mensagem de cooldown. As estruturas recusadas podem reaparecer; a escolhida não.
5. Clicar numa estrutura do mapa → bússola aponta pra ela (ir até o local e confirmar que existe).
6. Entrar na **fase 2** (completar quests até `questLeftWhenChange`) → mensagem de buff; draft libera na hora, mostra **5** opções e cooldown vira 1 min.
7. Estruturas não encontradas no mundo não aparecem no draft; sem candidatos → mensagem "nenhuma nova estrutura".

---

## Self-Review (feita pelo autor do plano)

**Cobertura do spec:** núcleo bússola/menu (Tasks 4,5), draft + cooldown + fase 1/2 (Tasks 2,5), buff fase 2 (Tasks 2,4,7), busca async (Tasks 2,3), lista de estruturas (Task 1), integrações KitType/Bingo/GameManager (Tasks 3,6,7), casos de borda (Task 5 + checklist Task 8). ✔ Sem lacunas.

**Placeholders:** nenhum — todo código está completo.

**Consistência de tipos:** `getCartographerManager()` (Task 3) usado em Tasks 4,5; `initPlayer/searchStructures/onPhase2/rollDraft/chooseFromDraft/getOwned/getLocation/isDraftAvailable/remainingCooldownSeconds` definidos na Task 2 e usados com as mesmas assinaturas nas Tasks 3,4,5,7; `MappedStructure.getIcon/getDisplayName/getVariants` (Task 1) usados em Tasks 2,5. ✔ Consistente.
