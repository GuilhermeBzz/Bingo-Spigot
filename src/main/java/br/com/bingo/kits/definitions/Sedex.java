package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Sedex extends Kit {
    @Override
    public void giveKit(Player player) {
        ItemStack item = new ItemStack(Material.HONEYCOMB);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "SEDEX 10");
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setLore(new ArrayList<String>(){{add(ChatColor.RED + "NAO CLIQUE NESSE ITEM QUANDO USANDO O KIT");}});
        item.setItemMeta(itemMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(item);

        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack item = new ItemStack(Material.HONEYCOMB);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "PAC");
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setLore(new ArrayList<String>(){{add(ChatColor.RED + "NAO CLIQUE NESSE ITEM QUANDO USANDO O KIT");}});
        item.setItemMeta(itemMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(item);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        for(ItemStack item : player.getInventory().getContents()){
            if(item == null) continue;
            if(item.getItemMeta() != null){
                if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "PAC")){
                    player.getInventory().remove(item);
                    break;
                }
            }
        }

        giveKit(player);
    }

}
