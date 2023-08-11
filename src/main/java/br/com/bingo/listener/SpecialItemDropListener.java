package br.com.bingo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpecialItemDropListener implements Listener {


    @EventHandler
    public void onDropItem(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();
        ItemMeta itemMeta = item.getItemMeta();

        if(itemMeta.isUnbreakable()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        List<ItemStack> itemstoRemove = new ArrayList<>();
        for(ItemStack item: event.getDrops()){
            if(item.getItemMeta().isUnbreakable()){
                itemstoRemove.add(item);
            }
        }
        event.getDrops().removeAll(itemstoRemove);
    }
}
