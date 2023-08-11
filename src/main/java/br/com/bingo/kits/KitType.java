package br.com.bingo.kits;


import br.com.bingo.kits.definitions.*;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementDisplay;

public enum KitType {

    HUNTER("Hunter", Material.COMPASS, "Use a bússola para encontrar inimigos!", new Hunter()),
    AQUAMAN("Aquaman", Material.TURTLE_HELMET, "Respire, nade rapido e quebre blocos rapidamente embaixo d'agua!", new Aquaman()),
    MINER("Miner", Material.IRON_PICKAXE, "Quebre blocos e saia rapidamente de cavernas!", new Miner()),
    SURVIVOR("Survivor", Material.GOLDEN_APPLE, "Se projeta com sua armadura e coma suas maças para sobreviver!", new Survivor()),
    BEASTMASTER("Beastmaster", Material.CHICKEN_SPAWN_EGG, "Use seus ovos para invocar animais!", new Beastmaster()),
    ENDERMAGE("Endermage", Material.ENDER_EYE, "Use seus olhos e sua perola para se encontrar o Fim!", new Endermage()),
    RAIDER("Raider", Material.EMERALD, "Inicie Raids automaticamente ao entrar nas vilas!", new Raider()),
    BEEKEEPER("Beekeeper", Material.BEE_NEST, "Crie seu próprio Apiário!", new Beekeeper()),
    NETHER_EXPLORER("Nether Explorer", Material.NETHERRACK, "Crie portais do Nether e encontre estruturas do nether rapidamente!", new NetherExplorer()),
    SURPRISE("Surprise", Material.NAME_TAG, "Receba um Kit aleatório no início da partida!", new Surprise()),
    GAMBLER("Gambler", Material.GOLD_INGOT, "Apos 5 minutos de partida, escolha entre 3 Kits aleatórios!", new Gambler()),

    NECROMANCER("Necromancer", Material.NETHERITE_HOE, "Use sua foice para invocar nos seus inimigos!", new Necromancer()),
    FISHERMAN("Fisherman", Material.FISHING_ROD, "Pesque rápido e recolha tesouros da Água!", new Fisherman()),
    PAO("Pão (Suporte)", Material.BREAD, "Ajude sua equipe alimentando e curando eles!", new Pao()),
    SCOUT("Scout", Material.FEATHER, "Use seu boost para correr Rapido!", new Scout()),
    CHECKPOINT("Checkpoint", Material.NETHER_BRICK_FENCE, "Use seu checkpoint para voltar para algum lugar", new Checkpoint()),
    JOKER("Joker", Material.TROPICAL_FISH, "Use seu Joker para embaralhar o inventario de seus inimigos!", new Joker()),
    ARCHITECT("Architect", Material.SHULKER_SHELL, "Use seu Builder para construir rapidamente!", new Architect()),
    SEDEX("Sedex", Material.CHEST, "Use seu Sedex para enviar pegar itens dos seus aliados!", new Sedex()),
    CULTIVATOR("Cultivator", Material.WHEAT, "Segure sua enxada para que as plantas cresçam rapidamente!", new Cultivator()),
    ;


    private final String name;
    private final Material icon;
    private final String description;
    private final Kit kit;



    KitType(String name, Material icon, String description, Kit kit1){
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.kit = kit1;
    }

    public String getName(){
        return name;
    }
    public Material getIcon(){
        return icon;
    }
    public String getDescription(){
        return description;
    }
    public Kit getKit(){
        return kit;
    }



}
