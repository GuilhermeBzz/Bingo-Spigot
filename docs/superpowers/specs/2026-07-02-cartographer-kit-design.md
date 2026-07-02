# Kit Cartógrafo — Design

**Data:** 2026-07-02
**Alvo:** Bingo-Spigot (Spigot 26.1.2)

## Objetivo

Adicionar um novo kit, o **Cartógrafo**, que ajuda o jogador a completar o bingo localizando
**estruturas** do overworld. É o análogo do kit **Explorer** (que localiza biomas), mas com uma
mecânica de **draft** para o jogador escolher quais estruturas quer rastrear, e um buff de fase 2.

## Contexto (padrões existentes que espelhamos)

- **Explorer** dá uma bússola; clique direito abre um menu de biomas descobertos; clicar num bioma
  aponta a bússola (`CompassMeta.setLodestone` + `player.setCompassTarget`).
- Biomas são localizados **assíncronamente** no início da partida por `GameManager.searchBiomes(world)`
  (`runTaskAsynchronously`), guardados num mapa compartilhado; localizar na main thread trava o servidor.
- O **buff de fase 2** de um kit é o método `Kit.completeKit(Player)`, chamado para cada jogador em
  `GameManager.completeQuest` quando `getAvailableQuests().size() == questLeftWhenChange`. O padrão é
  **fortalecer a habilidade central** do kit (ex.: Miner troca a picareta por uma melhor).
- Observação: o `completeKit` do Explorer é **vazio** — o Explorer não tem buff de fase 2. O Cartógrafo
  será mais elaborado.

## Comportamento

### Núcleo (bússola + menu)
- O kit dá uma **bússola** "Cartographer's Compass" (unbreakable, `HIDE_UNBREAKABLE`), nos métodos
  `giveKit`/`startKit` (mesmo padrão do Explorer).
- **Clique direito** na bússola → abre o inventário **"Mapa de Estruturas"** (9×3) com as estruturas
  que o jogador **já draftou** (ícone + nome PT).
- **Clicar numa estrutura própria** → aponta a bússola para a localização dela
  (`CompassMeta.setLodestone(loc)` + `setLodestoneTracked(false)` + `player.setCompassTarget(loc)`),
  fecha o inventário e envia mensagem de confirmação. Idêntico ao Explorer.

### Draft
- No **slot 26** (canto inferior direito) do "Mapa de Estruturas" há um botão **"Draftar estrutura"**.
- O botão está disponível **no início da partida** e, depois de usado, entra em **cooldown**.
- Clicar no botão **quando disponível** → abre a tela de **Draft**: mostra **N estruturas** aleatórias
  do conjunto `(localizadas ∧ não-possuídas)`.
- Clicar em 1 das N → adiciona ao mapa do jogador, fecha o draft e **inicia o cooldown**.
- As estruturas **recusadas** (as não escolhidas) voltam ao pool e podem reaparecer em drafts futuros.
  As **escolhidas** nunca reaparecem.
- Clicar no botão **em cooldown** → não abre draft; informa o tempo restante (ex.: lore/mensagem).
- Se não houver estruturas restantes para oferecer → informa "nenhuma nova estrutura disponível".

### Parâmetros por fase
| Parâmetro           | Fase 1 | Fase 2 (buff) |
|---------------------|--------|---------------|
| Opções por draft (N)| 3      | 5             |
| Cooldown do draft   | 3 min  | 1 min         |

O **buff de fase 2** (`Cartographer.completeKit(player)`) liga a flag de fase 2 e **reseta o cooldown**
do jogador (draft disponível na hora).

## Estruturas (overworld)

`StructureType` (agrupador) não cobre Village/Pillager Outpost/Ancient City/Trail Ruins/Trial Chambers,
então usamos o registro específico `org.bukkit.generator.structure.Structure`. Cada estrutura lógica
mapeia **uma ou mais variantes**; a busca localiza a **variante mais próxima**.

| Estrutura lógica | Variantes (`Structure`) | Ícone sugerido |
|---|---|---|
| Vila | VILLAGE_PLAINS, VILLAGE_DESERT, VILLAGE_SAVANNA, VILLAGE_SNOWY, VILLAGE_TAIGA | BELL |
| Posto Avançado | PILLAGER_OUTPOST | CROSSBOW |
| Monumento Oceânico | MONUMENT | SEA_LANTERN |
| Mansão | MANSION | DARK_OAK_LOG |
| Pirâmide do Deserto | DESERT_PYRAMID | SANDSTONE |
| Templo da Selva | JUNGLE_PYRAMID | MOSSY_COBBLESTONE |
| Iglu | IGLOO | SNOW_BLOCK |
| Cabana do Pântano | SWAMP_HUT | CAULDRON |
| Naufrágio | SHIPWRECK, SHIPWRECK_BEACHED | OAK_BOAT |
| Tesouro Enterrado | BURIED_TREASURE | CHEST |
| Portal em Ruínas | RUINED_PORTAL, RUINED_PORTAL_DESERT, RUINED_PORTAL_JUNGLE, RUINED_PORTAL_SWAMP, RUINED_PORTAL_MOUNTAIN, RUINED_PORTAL_OCEAN | CRYING_OBSIDIAN |
| Cidade Antiga | ANCIENT_CITY | SCULK_CATALYST |
| Ruínas de Trilha | TRAIL_RUINS | SUSPICIOUS_GRAVEL |
| Câmaras de Provação | TRIAL_CHAMBERS | TRIAL_KEY |
| Mina Abandonada | MINESHAFT, MINESHAFT_MESA | RAIL |
| Fortaleza (Stronghold) | STRONGHOLD | END_PORTAL_FRAME |
| Ruína Oceânica | OCEAN_RUIN_COLD, OCEAN_RUIN_WARM | PRISMARINE_BRICKS |

(RUINED_PORTAL_NETHER, FORTRESS, BASTION_REMNANT, END_CITY, NETHER_FOSSIL ficam de fora — não são overworld.)

## Arquitetura / Componentes

Para não inflar ainda mais o `GameManager` (já >1200 linhas), o estado e a lógica do Cartógrafo ficam
numa classe dedicada **`CartographerManager`**, criada e reiniciada pelo `GameManager` a cada partida
(um novo por jogo, como o `ScoreboardBingo`). Isso mantém a feature isolada e testável.

### 1. `br.com.bingo.kits.MappedStructure` (enum)
Lista curada acima. Campos: `List<Structure> variants`, `String displayName`, `Material icon`.

### 2. `br.com.bingo.game.CartographerManager`
Detém todo o estado (resetado por partida):
- `Map<MappedStructure, Location> located` — variante mais próxima localizada (compartilhado).
- `Map<UUID, Set<MappedStructure>> owned` — estruturas draftadas por jogador.
- `Map<UUID, Long> cooldownUntil` — instante (ms) em que o draft volta a ficar disponível.
- `boolean phase2` — flag global de buff.

Métodos:
- `searchStructures(World world)` — **async** (`runTaskAsynchronously`), espelha `searchBiomes`: para cada
  `MappedStructure`, `world.locateNearestStructure(spawn, variant, raio, findUnexplored=true)` para cada
  variante e guarda a mais próxima em `located`. Estruturas não encontradas ficam ausentes de `located`.
- `initPlayer(UUID)` — inicia `owned` vazio e deixa o draft disponível (cooldownUntil = agora).
- `isDraftAvailable(UUID)` / `remainingCooldownSeconds(UUID)`.
- `rollDraft(UUID)` → `List<MappedStructure>` de tamanho `draftSize()` sorteado de `(located ∧ !owned)`.
- `chooseFromDraft(UUID, MappedStructure)` → adiciona a `owned`, seta `cooldownUntil = agora + cooldownMillis()`.
- `getOwned(UUID)`, `getLocation(MappedStructure)`.
- `onPhase2(UUID)` → `phase2 = true`, `cooldownUntil.put(uuid, agora)` (disponível já).
- `draftSize()` → `phase2 ? 5 : 3`; `cooldownMillis()` → `phase2 ? 60_000 : 180_000`.

### 3. `br.com.bingo.kits.definitions.Cartographer` (extends `Kit`)
- `giveKit`/`startKit`: dão a bússola (padrão Explorer). No `startKit`, chamam
  `Bingo.getInstance().gameManager.getCartographerManager().initPlayer(player.getUniqueId())`.
- `completeKit(player)`: chama `...getCartographerManager().onPhase2(player.getUniqueId())` + mensagem de buff.

### 4. `br.com.bingo.kits.listeners.CartographerListener`
Espelha `ExplorerListener`, recebe `GameManager`. Trata:
- `PlayerInteractEvent` (clique direito na bússola) → abre "Mapa de Estruturas".
- `InventoryClickEvent` no "Mapa de Estruturas":
  - clique no slot 26 → se disponível, abre "Draft de Estruturas"; senão, mensagem de cooldown.
  - clique num ícone de estrutura própria → aponta a bússola.
- `InventoryClickEvent` no "Draft de Estruturas": clique numa das N opções → `chooseFromDraft` + reabre o mapa.
- Todos os menus com `event.setCancelled(true)` e checagem de título (mesmo padrão do ExplorerListener).

### 5. Integrações
- `KitType`: nova entrada `CARTOGRAPHER("Cartographer", Material.MAP, "Localize estruturas e rastreie-as com sua bússola!", new Cartographer())`.
- `Bingo.java`: registrar `new CartographerListener(gameManager)`.
- `GameManager`: campo `cartographerManager` (novo por partida, reset junto com o resto do estado);
  `getCartographerManager()`; chamar `cartographerManager.searchStructures(gameWorld)` no início (onde
  `searchBiomes` é chamado); chamar `initPlayer` no start dos jogadores com kit CARTOGRAPHER (espelhando
  o `giveNewBiome` condicional em `KitType.EXPLORER`).

## Casos de borda
- Estrutura não localizada → ausente de `located` → nunca aparece no draft.
- Draft sem candidatos (todas possuídas ou nenhuma localizada) → mensagem "nenhuma nova estrutura".
- Localização ainda rodando (async) no primeiro draft → mostra só o que já foi encontrado; se nada,
  mensagem "aguarde, localizando estruturas".
- Bússola perdida/removida → clicar numa estrutura sem bússola no inventário: não faz nada (como Explorer).
- Jogador sem `owned` inicial → tratado por `initPlayer`.

## Considerações técnicas
- **`locateNearestStructure` é caro e roda async** (espelha `searchBiomes`); `findUnexplored=true` para
  garantir que encontra mesmo em mundo novo. Raio grande (ex.: 5000, igual ao biome search).
- Risco conhecido: worldgen assíncrono no Spigot — é o **mesmo risco** do `searchBiomes` já existente e
  funcionando; se der problema, a mitigação (localizar em lotes / na main thread com spread) vale para ambos.
- Cooldown por tempo real (millis), independente de TPS.

## Verificação (in-game, sem testes automatizados — é um plugin)
1. Criar partida com kit Cartographer; confirmar bússola no start e primeiro draft disponível.
2. Draftar → estrutura entra no mapa; cooldown inicia; recusadas reaparecem, escolhida não.
3. Clicar numa estrutura → bússola aponta corretamente (ir até lá e confirmar).
4. Entrar na fase 2 → cooldown 1 min e draft com 5 opções; cooldown resetado na virada.
5. Estruturas não encontradas não aparecem; sem candidatos → mensagem correta.
