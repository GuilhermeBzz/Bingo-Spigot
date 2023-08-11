package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Checkpoint extends Kit {
    @Override
    public void giveKit(Player player) {
        ItemStack checkpoint = new ItemStack(Material.NETHER_BRICK_FENCE);
        ItemMeta checkpointMeta = checkpoint.getItemMeta();
        checkpointMeta.setDisplayName(ChatColor.GOLD + "Checkpoint Pro");
        checkpointMeta.setUnbreakable(true);
        checkpointMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        checkpoint.setItemMeta(checkpointMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(checkpoint);

        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack checkpoint = new ItemStack(Material.NETHER_BRICK_FENCE);
        ItemMeta checkpointMeta = checkpoint.getItemMeta();
        checkpointMeta.setDisplayName(ChatColor.GOLD + "Checkpoint");
        checkpointMeta.setUnbreakable(true);
        checkpointMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        checkpoint.setItemMeta(checkpointMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(checkpoint);
        
        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        for(ItemStack item : player.getInventory().getContents()){
            if(item == null) continue;
            if(item.getItemMeta() != null){
                if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Checkpoint")){
                    player.getInventory().remove(item);
                    break;
                }
            }
        }

        giveKit(player);
    }
}
