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
