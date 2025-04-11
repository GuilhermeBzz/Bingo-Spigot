package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.kits.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;


import java.util.Map;
import java.util.Objects;

public class AlchemistListener implements Listener {

    GameManager gameManager;

    public AlchemistListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        Material result = null;
        if(event.getInventory().getResult() != null && event.getInventory().getResult().getType() != Material.AIR) {
            result = event.getInventory().getResult().getType();
        }
        if(result == null) return;


        Player player = (Player) event.getView().getPlayer();

        Map<Material, Material> recipeMap = Map.ofEntries(
                Map.entry(Material.IRON_INGOT, Material.GOLD_INGOT),
                Map.entry(Material.GOLD_INGOT, Material.DIAMOND),
                Map.entry(Material.ANDESITE, Material.GRANITE),
                Map.entry(Material.GRANITE, Material.DIORITE),
                Map.entry(Material.DIORITE, Material.ANDESITE),
                Map.entry(Material.DIAMOND, Material.NETHERITE_SCRAP),
                Map.entry(Material.ROTTEN_FLESH, Material.LEATHER),
                Map.entry(Material.STRING, Material.LEAD),
                Map.entry(Material.WHEAT, Material.POTATO),
                Map.entry(Material.POTATO, Material.CARROT),
                Map.entry(Material.GRAVEL, Material.FLINT),
                Map.entry(Material.SLIME_BALL, Material.MAGMA_CREAM),
                Map.entry(Material.MAGMA_CREAM, Material.SLIME_BALL),
                Map.entry(Material.OAK_SAPLING, Material.BIRCH_SAPLING),
                Map.entry(Material.BIRCH_SAPLING, Material.SPRUCE_SAPLING),
                Map.entry(Material.SPRUCE_SAPLING, Material.JUNGLE_SAPLING),
                Map.entry(Material.JUNGLE_SAPLING, Material.ACACIA_SAPLING),
                Map.entry(Material.ACACIA_SAPLING, Material.DARK_OAK_SAPLING),
                Map.entry(Material.LAPIS_LAZULI, Material.EMERALD),
                Map.entry(Material.REDSTONE, Material.LAPIS_LAZULI),
                Map.entry(Material.QUARTZ, Material.AMETHYST_SHARD),
                Map.entry(Material.COBBLESTONE, Material.STONE),
                Map.entry(Material.STONE, Material.SMOOTH_STONE)
                );

        for(Material ingredient : recipeMap.keySet()){
            if(!result.equals(recipeMap.get(ingredient))) continue;
            ItemStack[] matrix = event.getInventory().getMatrix();
            boolean isAlchemistRecipe = true;
            for(ItemStack itemStack : matrix) {
                if(itemStack == null) continue;
                if(itemStack.getType() == Material.AIR) continue;
                if (itemStack.getType() != ingredient) {
                    isAlchemistRecipe = false;
                    break;
                }
            }
            if(isAlchemistRecipe && !isAlchemist(player)){
                event.getInventory().setResult(null);
                return;
            }
            if(gameManager.getAvailableQuests().size() <= gameManager.questLeftWhenChange){
                ItemStack resultItem = Objects.requireNonNull(event.getRecipe()).getResult();
                event.getInventory().setResult(new ItemStack(resultItem.getType(), resultItem.getAmount() * 2));
            }
        }
    }




    private boolean isAlchemist(Player player){
        if(gameManager.playerKit.get(player.getUniqueId()) == null) return false;
        return gameManager.playerKit.get(player.getUniqueId()).equals(KitType.ALCHEMIST);
    }
}
