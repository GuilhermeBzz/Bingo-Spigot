package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.kits.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;


public class BlacksmithListener implements Listener {

    GameManager gameManager;

    public BlacksmithListener (GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        Player player = (Player) event.getView().getPlayer();
        Map<Material, Material> smeltingMap = Map.of(
                Material.IRON_ORE, Material.IRON_INGOT,
                Material.GOLD_ORE, Material.GOLD_INGOT,
                Material.COPPER_ORE, Material.COPPER_INGOT,
                Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT,
                Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT,
                Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT,
                Material.RAW_IRON, Material.IRON_INGOT,
                Material.RAW_COPPER, Material.COPPER_INGOT,
                Material.RAW_GOLD, Material.GOLD_INGOT
        );

        ItemStack[] matrix = event.getInventory().getMatrix();
        if(isBlackSmith(player)){
            for (ItemStack itemStack : matrix) {
                if (itemStack == null) continue;
                if (smeltingMap.containsKey(itemStack.getType())) {
                    if (gameManager.getAvailableQuests().size() <= gameManager.questLeftWhenChange) {
                        event.getInventory().setResult(new ItemStack(smeltingMap.get(itemStack.getType()), 3));
                    }
                }
            }
        } else{
            for (ItemStack itemStack : matrix) {
                if (itemStack == null) continue;
                if (smeltingMap.containsKey(itemStack.getType())) {
                    event.getInventory().setResult(null);
                    return;
                }
            }

        }




    }


    private boolean isBlackSmith(Player player){
        if(gameManager.playerKit.get(player.getUniqueId()) == null) return false;
        return gameManager.playerKit.get(player.getUniqueId()).equals(KitType.BLACKSMITH);
    }
}
