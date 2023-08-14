package br.com.bingo.quests;

import br.com.bingo.EntityHead;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public enum Quest {
    COLLECT_OBSIDIAN(QuestType.COLLECT_ITEM, Material.OBSIDIAN, Material.OBSIDIAN, "Colete uma Obsidian", 2),
    COLLECT_MOSSY_COBBLE_WALL(QuestType.COLLECT_ITEM, Material.MOSSY_COBBLESTONE_WALL, Material.MOSSY_COBBLESTONE_WALL, "Colete uma Mossy Cobblestone Wall", 2),
    COLLECT_COBBLED_DEEPSLATE(QuestType.COLLECT_ITEM, Material.COBBLED_DEEPSLATE, Material.COBBLED_DEEPSLATE, "Colete uma Cobbled Deepslate", 1),
    COLLECT_DIAMOND(QuestType.COLLECT_ITEM, Material.DIAMOND, Material.DIAMOND, "Colete um Diamante", 2),
    COLLECT_JUNGLE_LOG(QuestType.COLLECT_ITEM, Material.JUNGLE_LOG, Material.JUNGLE_LOG, "Colete uma Jungle Log", 2),
    COLLECT_ACACIA_DOOR(QuestType.COLLECT_ITEM, Material.ACACIA_DOOR, Material.ACACIA_DOOR, "Colete uma Porta de Acacia", 2),
    COLLECT_STRIPPED_BIRCH_LOG(QuestType.COLLECT_ITEM, Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_BIRCH_LOG, "Colete uma Stripped Birch Log", 1),
    COLLECT_SPRUCE_FENCE_GATE(QuestType.COLLECT_ITEM, Material.SPRUCE_FENCE_GATE, Material.SPRUCE_FENCE_GATE, "Colete uma Porteira de Spruce", 2),
    COLLECT_DARK_OAK_TRAPDOOR(QuestType.COLLECT_ITEM, Material.DARK_OAK_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, "Colete uma Trapdoor de Dark Oak", 2),
    COLLECT_CHERRY_STAIRS(QuestType.COLLECT_ITEM, Material.CHERRY_STAIRS, Material.CHERRY_STAIRS, "Colete uma Escada de Cherry", 3),
    COLLECT_MAGENTA_GLAZED_TERRACOTTA(QuestType.COLLECT_ITEM, Material.MAGENTA_GLAZED_TERRACOTTA, Material.MAGENTA_GLAZED_TERRACOTTA, "Colete uma Magenta Glazed Terracota", 2),
    COLLECT_SPONGE(QuestType.COLLECT_ITEM, Material.SPONGE, Material.SPONGE, "Colete uma Esponja", 3),
    COLLECT_SMOKER(QuestType.COLLECT_ITEM, Material.SMOKER, Material.SMOKER, "Colete um Smoker", 1),
    COLLECT_COMPOSTER(QuestType.COLLECT_ITEM, Material.COMPOSTER, Material.COMPOSTER, "Colete uma Composteira", 1),
    COLLECT_ARMOR_STAND(QuestType.COLLECT_ITEM, Material.ARMOR_STAND, Material.ARMOR_STAND, "Colete um Armor Stand", 1),
    COLLECT_CRACKED_STONE_BRICKS(QuestType.COLLECT_ITEM, Material.CRACKED_STONE_BRICKS, Material.CRACKED_STONE_BRICKS, "Colete um Cracked Stone Bricks", 2),
    COLLECT_CHISELED_STONE_BRICKS(QuestType.COLLECT_ITEM, Material.CHISELED_STONE_BRICKS, Material.CHISELED_STONE_BRICKS, "Colete um Chiseled Stone Bricks", 2),
    COLLECT_RESPAWN_ANCHOR(QuestType.COLLECT_ITEM, Material.RESPAWN_ANCHOR, Material.RESPAWN_ANCHOR, "Colete um Respawn Anchor", 3),
    COLLECT_SHROOMLIGHT(QuestType.COLLECT_ITEM, Material.SHROOMLIGHT, Material.SHROOMLIGHT, "Colete um Shroomlight", 2),
    COLLECT_LOOM(QuestType.COLLECT_ITEM, Material.LOOM, Material.LOOM, "Colete um Loom", 1),
    COLLECT_BLAST_FURNACE(QuestType.COLLECT_ITEM, Material.BLAST_FURNACE, Material.BLAST_FURNACE, "Colete uma Blast Furnace", 2),
    COLLECT_STICKY_PISTON(QuestType.COLLECT_ITEM, Material.STICKY_PISTON, Material.STICKY_PISTON, "Colete um Pistão grudento", 3),
    COLLECT_CACTUS(QuestType.COLLECT_ITEM, Material.CACTUS, Material.CACTUS, "Colete um Cactus", 2),
    COLLECT_WHEAT_SEEDS(QuestType.COLLECT_ITEM, Material.WHEAT_SEEDS, Material.WHEAT_SEEDS, "Colete uma Semente de Trigo", 1),
    COLLECT_FLINT(QuestType.COLLECT_ITEM, Material.FLINT, Material.FLINT, "Colete um Flint", 1),
    COLLECT_VINE(QuestType.COLLECT_ITEM, Material.VINE, Material.VINE, "Colete um Vinha", 2),
    COLLECT_NETHER_BRICK(QuestType.COLLECT_ITEM, Material.NETHER_BRICK, Material.NETHER_BRICK, "Colete Nether Brick", 2),
    COLLECT_TRAPPED_CHEST(QuestType.COLLECT_ITEM, Material.TRAPPED_CHEST, Material.TRAPPED_CHEST, "Colete um Baú Armadilhado", 1),
    COLLECT_END_STONE(QuestType.COLLECT_ITEM, Material.END_STONE, Material.END_STONE, "Colete uma EndStone", 3),
    COLLECT_ANVIL(QuestType.COLLECT_ITEM, Material.ANVIL, Material.ANVIL, "Colete uma Anvil", 2),
    COLLECT_ACTIVATOR_RAIL(QuestType.COLLECT_ITEM, Material.ACTIVATOR_RAIL, Material.ACTIVATOR_RAIL, "Colete um Activator Rail", 2),
    COLLECT_ARROW(QuestType.COLLECT_ITEM, Material.ARROW, Material.ARROW, "Colete uma Flecha", 1),
    COLLECT_SADDLE(QuestType.COLLECT_ITEM, Material.SADDLE, Material.SADDLE, "Colete uma Sela", 3),
    COLLECT_CAKE(QuestType.COLLECT_ITEM, Material.CAKE, Material.CAKE, "Colete um Bolo", 3),
    COLLECT_PUFFERFISH(QuestType.COLLECT_ITEM, Material.PUFFERFISH, Material.PUFFERFISH, "Colete um Baiacu", 2),
    COLLECT_HAY_BALE(QuestType.COLLECT_ITEM, Material.HAY_BLOCK, Material.HAY_BLOCK, "Colete um Bloco de Feno", 1),
    COLLECT_FERMENTED_SPIDER_EYE(QuestType.COLLECT_ITEM, Material.FERMENTED_SPIDER_EYE, Material.FERMENTED_SPIDER_EYE, "Colete um Olho de Aranha Fermentado", 3),
    COLLECT_BELL(QuestType.COLLECT_ITEM, Material.BELL, Material.BELL, "Colete um Sino", 2),
    COLLECT_AMETHYST_SHARD(QuestType.COLLECT_ITEM, Material.AMETHYST_SHARD, Material.AMETHYST_SHARD, "Colete uma Amethyst Shard", 2),
    COLLECT_FISHING_ROD(QuestType.COLLECT_ITEM, Material.FISHING_ROD, Material.FISHING_ROD, "Colete uma Vara de Pesca", 1),
    COLLECT_ENDERCHEST(QuestType.COLLECT_ITEM, Material.ENDER_CHEST, Material.ENDER_CHEST, "Colete um EnderChest", 3),
    COLLECT_ENDER_EYE(QuestType.COLLECT_ITEM, Material.ENDER_EYE, Material.ENDER_EYE, "Colete um Olho do Fim", 3),
    COLLECT_BRICK_STAIR(QuestType.COLLECT_ITEM, Material.BRICK_STAIRS, Material.BRICK_STAIRS, "Colete uma Escada de Tijolos", 1),
    COLLECT_CANDLE(QuestType.COLLECT_ITEM, Material.CANDLE, Material.CANDLE, "Colete uma Vela", 3),
    COLLECT_CARROTS(QuestType.COLLECT_ITEM, Material.CARROT, Material.CARROT, "Colete uma Cenoura", 1),
    COLLECT_COARSE_DIRT(QuestType.COLLECT_ITEM, Material.COARSE_DIRT, Material.COARSE_DIRT, "Colete uma Coarse Dirt", 1),
    COLLECT_CLOCK(QuestType.COLLECT_ITEM, Material.CLOCK, Material.CLOCK, "Colete um Relogio", 1),
    COLLECT_LAPIS_BLOCK(QuestType.COLLECT_ITEM, Material.LAPIS_BLOCK, Material.LAPIS_BLOCK, "Colete um Bloco de Lapis Lazuli", 1),
    COLLECT_BAMBOO_CHEST_RAFT(QuestType.COLLECT_ITEM, Material.BAMBOO_CHEST_RAFT, Material.BAMBOO_CHEST_RAFT, "Colete uma Jangada de Bambu com Bau", 3),
    COLLECT_HONEY_BLOCK(QuestType.COLLECT_ITEM, Material.HONEY_BLOCK, Material.HONEY_BLOCK, "Colete um Bloco de Mel", 3),
    COLLECT_HONEYCOMB_BLOCK(QuestType.COLLECT_ITEM, Material.HONEYCOMB_BLOCK, Material.HONEYCOMB_BLOCK, "Colete um Bloco de Colmeia", 3),
    COLLECT_JACK_O_LANTERN(QuestType.COLLECT_ITEM, Material.JACK_O_LANTERN, Material.JACK_O_LANTERN, "Colete um Jack o'Lantern", 2),
    COLLECT_END_CRYSTAL(QuestType.COLLECT_ITEM, Material.END_CRYSTAL, Material.END_CRYSTAL, "Colete um Cristal Do Fim", 3),
    COLLECT_COBWEB(QuestType.COLLECT_ITEM, Material.COBWEB, Material.COBWEB, "Colete uma Teia", 2),
    COLLECT_COOKED_MUTTON(QuestType.COLLECT_ITEM, Material.COOKED_MUTTON, Material.COOKED_MUTTON, "Colete uma Carne de Ovelha Cozida", 1),
    COLLECT_AXOLOTL_BUCKET(QuestType.COLLECT_ITEM, Material.AXOLOTL_BUCKET, Material.AXOLOTL_BUCKET, "Colete um Axolot no Balde", 2),
    COLLECT_GLOW_BERRIES(QuestType.COLLECT_ITEM, Material.GLOW_BERRIES, Material.GLOW_BERRIES, "Colete uma Glow Berry", 2),
    COLLECT_CRYING_OBSIDIAN(QuestType.COLLECT_ITEM, Material.CRYING_OBSIDIAN, Material.CRYING_OBSIDIAN, "Colete uma Crying Obsidian", 2),
    COLLECT_REPEATER(QuestType.COLLECT_ITEM, Material.REPEATER, Material.REPEATER, "Colete um Repetidor", 2),
    COLLECT_ROOTED_DIRT(QuestType.COLLECT_ITEM, Material.ROOTED_DIRT, Material.ROOTED_DIRT, "Colete uma Rooted Dirt", 3),
    COLLECT_BLACK_WOOL(QuestType.COLLECT_ITEM, Material.BLACK_WOOL, Material.BLACK_WOOL, "Colete uma La Preta", 1),
    COLLECT_EGG(QuestType.COLLECT_ITEM, Material.EGG, Material.EGG, "Colete um Ovo", 2),
    COLLECT_DRIED_KELP_BLOCK(QuestType.COLLECT_ITEM, Material.DRIED_KELP_BLOCK, Material.DRIED_KELP_BLOCK, "Colete um Bloco de Algas Marinhas Secas", 1),
    COLLECT_EMERALD_BLOCK(QuestType.COLLECT_ITEM, Material.EMERALD_BLOCK, Material.EMERALD_BLOCK, "Colete um Bloco de Esmeralda", 3),
    COLLECT_GRANITE_STAIRS(QuestType.COLLECT_ITEM, Material.GRANITE_STAIRS, Material.GRANITE_STAIRS, "Colete uma Escada de Granito", 1),
    COLLECT_MAGMA_CREAM(QuestType.COLLECT_ITEM, Material.MAGMA_CREAM, Material.MAGMA_CREAM, "Colete um Magma Cream", 2),
    COLLECT_POLISHED_ANDESITE_STAIRS(QuestType.COLLECT_ITEM, Material.POLISHED_ANDESITE_STAIRS, Material.POLISHED_ANDESITE_STAIRS, "Colete uma Escada de Andesito Polido", 1),
    COLLECT_CHISELED_QUARTZ_BLOCK(QuestType.COLLECT_ITEM, Material.CHISELED_QUARTZ_BLOCK, Material.CHISELED_QUARTZ_BLOCK, "Colete um Bloco de Quartzo Entalhada", 2),
    COLLECT_POLISHED_BASALT(QuestType.COLLECT_ITEM, Material.POLISHED_BASALT, Material.POLISHED_BASALT, "Colete um Basalto Polido", 2),
    COLLECT_POLISHED_BLACKSTONE_BRICK_WALL(QuestType.COLLECT_ITEM, Material.POLISHED_BLACKSTONE_BRICK_WALL, Material.POLISHED_BLACKSTONE_BRICK_WALL, "Colete um Tijolo de Blackstone Polido", 2),
    COLLECT_POTATO(QuestType.COLLECT_ITEM, Material.POTATO, Material.POTATO, "Colete uma Batata", 1),
    COLLECT_MAGMA_BLOCK(QuestType.COLLECT_ITEM, Material.MAGMA_BLOCK, Material.MAGMA_BLOCK, "Colete um Bloco de Magma", 1),
    COLLECT_NETHER_WART_BLOCK(QuestType.COLLECT_ITEM, Material.NETHER_WART_BLOCK, Material.NETHER_WART_BLOCK, "Colete um Bloco de Fungo do Nether", 2),
    COLLECT_APPLE(QuestType.COLLECT_ITEM, Material.APPLE, Material.APPLE, "Colete uma Maça", 1),
    COLLECT_BONE_BLOCK(QuestType.COLLECT_ITEM, Material.BONE_BLOCK, Material.BONE_BLOCK, "Colete um Bloco de Osso", 1),
    COLLECT_CALCITE(QuestType.COLLECT_ITEM, Material.CALCITE, Material.CALCITE, "Colete um Calcite", 2),
    COLLECT_CRIMSON_HANGING_SIGN(QuestType.COLLECT_ITEM, Material.CRIMSON_HANGING_SIGN, Material.CRIMSON_HANGING_SIGN, "Colete uma Placa Pendurada de Crimson", 2),
    COLLECT_DEAD_BUSH(QuestType.COLLECT_ITEM, Material.DEAD_BUSH, Material.DEAD_BUSH, "Colete um DeadBush", 2),
    COLLECT_NOTE_BLOCK(QuestType.COLLECT_ITEM, Material.NOTE_BLOCK, Material.NOTE_BLOCK, "Colete um Note Block", 1),
    COLLECT_PINK_WOOL(QuestType.COLLECT_ITEM, Material.PINK_WOOL, Material.PINK_WOOL, "Colete uma La Rosa", 1),
    COLLECT_RABBIT_HIDE(QuestType.COLLECT_ITEM, Material.RABBIT_HIDE, Material.RABBIT_HIDE, "Colete Pele de Coelho", 0),
    COLLECT_SNOWBALL(QuestType.COLLECT_ITEM, Material.SNOWBALL, Material.SNOWBALL, "Colete uma Bola de Neve", 1),
    COLLECT_TARGET(QuestType.COLLECT_ITEM, Material.TARGET, Material.TARGET, "Colete um Alvo", 2),
    COLLECT_TNT(QuestType.COLLECT_ITEM, Material.TNT, Material.TNT, "Colete uma TNT", 2),
    COLLECT_WARPED_DOOR(QuestType.COLLECT_ITEM, Material.WARPED_DOOR, Material.WARPED_DOOR, "Colete uma Warped Door", 2),
    COLLECT_WARPED_FUNGUS(QuestType.COLLECT_ITEM, Material.WARPED_FUNGUS, Material.WARPED_FUNGUS, "Colete um Warped Fungus", 2),
    COLLECT_WRITABLE_BOOK(QuestType.COLLECT_ITEM, Material.WRITABLE_BOOK, Material.WRITABLE_BOOK, "Colete um Book and Quill", 1),
    COLLECT_DANDELION(QuestType.COLLECT_ITEM, Material.DANDELION, Material.DANDELION, "Colete um Dandelion", 1),
    COLLECT_ENDER_PEARL(QuestType.COLLECT_ITEM, Material.ENDER_PEARL, Material.ENDER_PEARL, "Colete uma Perola do Fim", 2),
    COLLECT_GLOW_INK_SAC(QuestType.COLLECT_ITEM, Material.GLOW_INK_SAC, Material.GLOW_INK_SAC, "Colete uma Glow Ink Sac", 2),
    COLLECT_GOLDEN_CARROT(QuestType.COLLECT_ITEM, Material.GOLDEN_CARROT, Material.GOLDEN_CARROT, "Colete uma Cenoura Dourada", 2),
    COLLECT_REDSAND(QuestType.COLLECT_ITEM, Material.RED_SAND, Material.RED_SAND, "Colete uma Red Sand", 3),
    KILL_HORSE(QuestType.KILL_MOB, EntityType.HORSE, EntityHead.HORSE,"Mate um Cavalo", 1),
    KILL_DOLPHIN(QuestType.KILL_MOB, EntityType.DOLPHIN, EntityHead.DOLPHIN,"Mate um Golfinho", 1),
    KILL_VILLAGER(QuestType.KILL_MOB, EntityType.VILLAGER, EntityHead.VILLAGER,"Mate um Villager", 1),
    KILL_SNOWMAN(QuestType.KILL_MOB, EntityType.SNOWMAN, EntityHead.SNOWMAN,"Mate um Snowman", 2),
    KILL_WITCH(QuestType.KILL_MOB, EntityType.WITCH, EntityHead.WITCH,"Mate uma Bruxa", 2),
    KILL_BAT(QuestType.KILL_MOB, EntityType.BAT, EntityHead.BAT,"Mate um Morcego", 1),
    KILL_WITHER_SKELETON(QuestType.KILL_MOB, EntityType.WITHER_SKELETON, EntityHead.WITHER_SKELETON,"Mate um Wither Skeleton", 2),
    KILL_BLAZE(QuestType.KILL_MOB, EntityType.BLAZE, EntityHead.BLAZE,"Mate um Blaze", 2),
    KILL_DROWNED(QuestType.KILL_MOB, EntityType.DROWNED, EntityHead.DROWNED,"Mate um Afogado", 2),
    KILL_SPIDER(QuestType.KILL_MOB, EntityType.SPIDER, EntityHead.SPIDER,"Mate uma Aranha", 1),
    KILL_CAVE_SPIDER(QuestType.KILL_MOB, EntityType.CAVE_SPIDER, EntityHead.CAVE_SPIDER,"Mate uma Aranha da Caverna", 2),
    KILL_SKELETON(QuestType.KILL_MOB, EntityType.SKELETON, EntityHead.SKELETON,"Mate um Esqueleto", 1),
    KILL_CREEPER(QuestType.KILL_MOB, EntityType.CREEPER, EntityHead.CREEPER,"Mate um Creeper", 1),
    KILL_BEE(QuestType.KILL_MOB, EntityType.BEE, EntityHead.BEE,"Mate uma Abelha", 1),
    KILL_STRIDER(QuestType.KILL_MOB, EntityType.STRIDER, EntityHead.STRIDER,"Mate um Strider", 2),
    KILL_SILVERFISH(QuestType.KILL_MOB, EntityType.SILVERFISH, EntityHead.SILVERFISH,"Mate um Silverfish", 1),
    KILL_GUARDIAN(QuestType.KILL_MOB, EntityType.GUARDIAN, EntityHead.GUARDIAN,"Mate um Guardian", 2),
    KILL_ZOMBIE(QuestType.KILL_MOB, EntityType.ZOMBIE, EntityHead.ZOMBIE,"Mate um Zumbi", 1),
    KILL_ZOGLIN(QuestType.KILL_MOB, EntityType.ZOGLIN, EntityHead.ZOGLIN,"Mate um Zoglin", 3),
    KILL_ZOMBIFIED_PIGLIN(QuestType.KILL_MOB, EntityType.ZOMBIFIED_PIGLIN, EntityHead.ZOMBIFIED_PIGLIN,"Mate um Zombified Piglin", 2),
    BREED_PIG(QuestType.BREED_MOB, EntityType.PIG, EntityHead.PIG, "Reproduza Porcos", 1),
    BREED_COW(QuestType.BREED_MOB, EntityType.COW, EntityHead.COW, "Reproduza Vacas", 1),
    BREED_SHEEP(QuestType.BREED_MOB, EntityType.SHEEP, EntityHead.SHEEP, "Reproduza Ovelhas", 1),
    BREED_CHICKEN(QuestType.BREED_MOB, EntityType.CHICKEN, EntityHead.CHICKEN, "Reproduza Galinhas", 1),
    BREED_PANDA(QuestType.BREED_MOB, EntityType.PANDA, EntityHead.PANDA, "Reproduza Pandas", 3),
    KILL_TURTLE(QuestType.KILL_MOB, EntityType.TURTLE, EntityHead.TURTLE, "Mate Tartarugas", 2),
    TAME_WOLF(QuestType.TAME_MOB, EntityType.WOLF, EntityHead.WOLF, "Domestique um Lobo", 2),
    DIE_DROWNING(QuestType.DIE, EntityDamageEvent.DamageCause.DROWNING,EntityHead.DIE,"Morra Afogado", 1),
    DIE_FALL(QuestType.DIE, EntityDamageEvent.DamageCause.FALL,EntityHead.DIE,"Morra de Queda", 1),
    DIE_SUFFOCATION(QuestType.DIE, EntityDamageEvent.DamageCause.SUFFOCATION,EntityHead.DIE,"Morra Sufocado", 1),
    DIE_FIRE(QuestType.DIE, EntityDamageEvent.DamageCause.FIRE,EntityHead.DIE,"Morra pegando Fogo", 1),
    ADV_IRON_GOLEM(QuestType.ADVANCEMENT, "Hired Help", EntityHead.IRON_GOLEM,"Construa um Iron Golem", 2),
    ADV_VISIT_NETHER(QuestType.ADVANCEMENT, "We Need to Go Deeper", EntityHead.NETHER,"Visite o Nether", 1),
    ADV_VISIT_FORTRESS(QuestType.ADVANCEMENT, "A Terrible Fortress", Material.NETHER_BRICKS,"Encontre uma Fortaleza do Nether", 2),
    ADV_OH_SHINY(QuestType.ADVANCEMENT, "Oh Shiny", Material.GOLD_INGOT,"Troque com Piglin", 2),
    EFFECT_POISON(QuestType.GET_EFFECT, PotionEffectType.POISON, EntityHead.POISON,"Consiga o Efeito Poison", 1),
    EFFECT_HUNGER(QuestType.GET_EFFECT, PotionEffectType.HUNGER, EntityHead.HUNGER,"Consiga o Efeito Hunger", 1),
    EFFECT_HERO(QuestType.GET_EFFECT, PotionEffectType.HERO_OF_THE_VILLAGE, EntityHead.HERO,"Complete uma Raid", 3),
    ADV_POTION(QuestType.ADVANCEMENT, "Local Brewery", Material.BREWING_STAND,"Faça uma Poção", 2),
    ADV_SLEEP(QuestType.ADVANCEMENT, "Sweet Dreams", Material.RED_BED,"Deite em uma Cama", 1),
    ADV_TRIM(QuestType.ADVANCEMENT, "Crafting a New Look", Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE,"Customize uma Armadura", 2),
    ADV_TRADE(QuestType.ADVANCEMENT, "What a Deal!", Material.EMERALD,"Faça uma Day Trade", 2),
    ADV_BRUSH(QuestType.ADVANCEMENT, "Respecting the Remnants", Material.BRUSH,"Escove uma Areia e um Gravel Suspeitos", 3),
    ADV_SHIELD(QuestType.ADVANCEMENT, "Not Today, Thank You", Material.SHIELD,"Se defenda de qualquer projetil usando o Escudo", 1),
    ADV_GHAST(QuestType.ADVANCEMENT, "Return to Sender", Material.FIRE_CHARGE,"Mate um Ghast devolvendo sua Bola de Fogo", 3),
    ADV_BASTION(QuestType.ADVANCEMENT, "War Pigs", Material.CHEST ,"Abra um Baú de um Bastion", 3),
    ADV_BOW(QuestType.ADVANCEMENT, "Take Aim", Material.BOW,"Atinja qualquer Mob com uma Flecha", 1),
    ADV_FISH(QuestType.ADVANCEMENT, "Tactical Fishing", Material.PUFFERFISH_BUCKET ,"Pegue qualquer peixe com um balde", 1),
    ENCHANT(QuestType.ENCHANT, "NADA", Material.ENCHANTING_TABLE,"Encante um item", 3),
    WARDEN_SUMMON(QuestType.WARDEN, "NADA", EntityHead.WARDEN,"Spawne o Warden", 3),
    SHEAR(QuestType.SHEAR, EntityType.SHEEP, Material.SHEARS,"Corte a Lã de uma Ovelha", 1),
    BEDROCK(QuestType.BEDROCK, "Nada", Material.BEDROCK,"Agache pisando na Bedrock", 1),
    QUESTION(QuestType.NONE, "Nada", EntityHead.QUESTION,"???", 0),
    KILL_PLAYER(QuestType.KILL_PLAYER, "Nada", EntityHead.STEVE,"Mate um Jogador de outro time", 2),
    COLLECT_SPYGLASS(QuestType.COLLECT_ITEM, Material.SPYGLASS, Material.SPYGLASS, "Faça uma Luneta", 2),
    COLLECT_LIGHTNING_ROD(QuestType.COLLECT_ITEM, Material.LIGHTNING_ROD, Material.LIGHTNING_ROD, "Faça um Para-Raios", 1),
    KILL_CAMEL(QuestType.KILL_MOB, EntityType.CAMEL, EntityHead.CAMEL, "Mate um Camelo", 2),
    KILL_ENDERMITE(QuestType.KILL_MOB, EntityType.ENDERMITE, EntityHead.ENDERMITE, "Mate um Endermite", 3),
    COLLECT_ENDER_EGG(QuestType.COLLECT_ITEM, Material.DRAGON_EGG, Material.DRAGON_EGG, "Pegue um Ovo de Dragão", 3),
    COLLECT_WITHER_SKULL(QuestType.COLLECT_ITEM, Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_SKULL, "Pegue uma Caveira de Wither", 3),
    COLLECT_PIG_STEP(QuestType.COLLECT_ITEM, Material.MUSIC_DISC_PIGSTEP, Material.MUSIC_DISC_PIGSTEP, "Pegue um Disco de Pigstep", 3),
    COLLECT_ECHO_SHARD(QuestType.COLLECT_ITEM, Material.ECHO_SHARD, Material.ECHO_SHARD, "Pegue uma Echo Shell", 3),
    COLLECT_CALYBRATED(QuestType.COLLECT_ITEM, Material.CALIBRATED_SCULK_SENSOR, Material.CALIBRATED_SCULK_SENSOR, "Pegue um Calibrated Sculk Sensor", 3),
    COLLECT_DRAGON_BREATH(QuestType.COLLECT_ITEM, Material.DRAGON_BREATH, Material.DRAGON_BREATH, "Pegue um Dragon Breath", 3),
    COLLECT_ENCHANTED_GOLDEN(QuestType.COLLECT_ITEM, Material.ENCHANTED_GOLDEN_APPLE, Material.ENCHANTED_GOLDEN_APPLE, "Pegue uma Maçã Dourada Encantada", 3),
    COLLECT_CONDUIT(QuestType.COLLECT_ITEM, Material.CONDUIT, Material.CONDUIT, "Pegue um Conduit", 3),
    COLLECT_ANCIENT(QuestType.COLLECT_ITEM, Material.ANCIENT_DEBRIS, Material.ANCIENT_DEBRIS, "Pegue um Ancient Debris", 3),
    LEVEL_30(QuestType.LEVEL_UP, 30, Material.EXPERIENCE_BOTTLE, "Alcance o Nível 30", 3),
    LEVEL_20(QuestType.LEVEL_UP, 20, Material.EXPERIENCE_BOTTLE, "Alcance o Nível 20", 2),
    LEVEL_10(QuestType.LEVEL_UP, 10, Material.EXPERIENCE_BOTTLE, "Alcance o Nível 10", 1),
    COLLECT_MUD_BRICKS(QuestType.COLLECT_ITEM, Material.MUD_BRICKS, Material.MUD_BRICKS, "Pegue um Tijolo de Lama", 2),
    COLLECT_HEART_OF_THE_SEA(QuestType.COLLECT_ITEM, Material.HEART_OF_THE_SEA, Material.HEART_OF_THE_SEA, "Pegue um Coração do Mar", 3),
    COLLECT_NETHERITE(QuestType.COLLECT_ITEM, Material.NETHERITE_INGOT, Material.NETHERITE_INGOT, "Pegue um Netherite", 3),
    COLLECT_NAUTILUS(QuestType.COLLECT_ITEM, Material.NAUTILUS_SHELL, Material.NAUTILUS_SHELL, "Pegue uma Concha de Nautilus", 2),
    COLLECT_POWDER_SNOW(QuestType.COLLECT_ITEM, Material.POWDER_SNOW_BUCKET, Material.POWDER_SNOW_BUCKET, "Pegue um Balde de Neve em Pó", 2),
    KILL_POLAR_BEAR(QuestType.KILL_MOB, EntityType.POLAR_BEAR, EntityHead.POLAR_BEAR, "Mate um Urso Polar", 3),
    KILL_PILLAGER(QuestType.KILL_MOB, EntityType.PILLAGER, EntityHead.PILLAGER, "Mate um Pillager", 3),

    ;


    private final QuestType type;
    private final Object target;
    private final Object icon;
    private final String name;
    private final int difficulty;

    Quest(QuestType type, Object target, Object icon, String name, int difficulty){
        this.type = type;
        this.target = target;
        this.icon = icon;
        this.name = name;
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
    public QuestType getType() {
        return type;
    }
    public Object getTarget() {
        return target;
    }
    public Object getIcon(){
        return icon;
    }
    public String getName() {
        return name;
    }
}
