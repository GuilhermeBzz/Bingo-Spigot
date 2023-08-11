package br.com.bingo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpecialItemContainersListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        ClickType clickType = event.getClick();
        if(isSpecialItem(event.getCurrentItem()) || isSpecialItem(event.getCursor())){
            if(clickType.equals(ClickType.LEFT) || clickType.equals(ClickType.RIGHT)){
                if(event.getClickedInventory() != null){
                    if(!event.getClickedInventory().getType().equals(InventoryType.PLAYER)){
                        event.setCancelled(true);
                    }
                }
            } else if (clickType.name().equals(ClickType.SHIFT_LEFT.name()) || clickType.name().equals(ClickType.SHIFT_RIGHT.name())) {

                if(!event.getInventory().getType().equals(InventoryType.PLAYER)){
                    if(event.getInventory().getType() == InventoryType.CRAFTING && event.getClickedInventory().getType() == InventoryType.PLAYER){
                    }else{
                        event.setCancelled(true);
                    }
                }
            }

        } else if (clickType.equals(ClickType.NUMBER_KEY)) {
            ItemStack itemHotBar = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
            if(isSpecialItem(itemHotBar)){

                if(!event.getInventory().getType().equals(InventoryType.PLAYER)){
                    if(event.getInventory().getType() == InventoryType.CRAFTING && event.getClickedInventory().getType() == InventoryType.PLAYER){
                    }else{
                        event.setCancelled(true);
                    }
                }
            }

        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        if(isSpecialItem(event.getOldCursor())){
            if(event.getInventory() != null){
                if(!event.getInventory().getType().equals(InventoryType.PLAYER)){
                    event.setCancelled(true);
                }
            }
        }
    }


    private boolean isSpecialItem(ItemStack item){
        if(item != null && item.hasItemMeta()){
            ItemMeta itemMeta = item.getItemMeta();
            return itemMeta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE);
        }
        return false;
    }
    
}
