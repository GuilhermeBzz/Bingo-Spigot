package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class MaterialListener implements Listener {

    private GameManager gameManager;

    public MaterialListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(gameManager.isGameStarted() && gameManager.checkPlayerTeam(player)){
                ItemStack item = event.getItem().getItemStack();
                for(Quest quest : gameManager.getAvailableQuests()){
                    if(quest.getType() == QuestType.COLLECT_ITEM){
                        if(item.getType() == quest.getTarget() && !item.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)){
                            gameManager.completeQuest(player.getUniqueId(), quest);
                            return;
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(gameManager.isGameStarted() && gameManager.checkPlayerTeam(player)){
            Inventory clickedInventory = event.getClickedInventory();
            ItemStack clickedItem = event.getCurrentItem();
            ItemStack outroItem = event.getCursor();
            String clickedItemName = null;
            if(clickedItem != null) clickedItemName = clickedItem.getType().name();
            String outroItemName = null;
            if(outroItem != null) outroItemName = outroItem.getType().name();
            for(Quest quest : gameManager.getAvailableQuests()){
                if(quest.getType() == QuestType.COLLECT_ITEM){
                    if(clickedInventory != null){
                        if(event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Cartela do Bingo") || event.getView().getTitle().equals(ChatColor.DARK_RED + "Menu") || event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Kits") || event.getView().getTitle().equals(ChatColor.DARK_RED + "Criar Partida")) continue;
                        if(clickedItem != null){
                            if(clickedItem.getType() == quest.getTarget() && !clickedItem.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)){
                                gameManager.completeQuest(player.getUniqueId(), quest);
                                return;
                            }
                        } else if (outroItem != null){
                            if(outroItem.getType() == quest.getTarget() && !outroItem.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)){
                                gameManager.completeQuest(player.getUniqueId(), quest);
                                return;
                            }
                        }
                    }
                }
            }
            return;
        }
        return;
    }
}
