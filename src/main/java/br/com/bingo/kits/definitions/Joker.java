package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Joker extends Kit {
    @Override
    public void giveKit(Player player) {
        ItemStack joker = new ItemStack(Material.TROPICAL_FISH);
        ItemMeta jokerMeta = joker.getItemMeta();
        jokerMeta.setDisplayName(ChatColor.GOLD + "HAHAHAHAHAHAHAHAHA");
        jokerMeta.setUnbreakable(true);
        jokerMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        joker.setItemMeta(jokerMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(joker);

        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack joker = new ItemStack(Material.TROPICAL_FISH);
        ItemMeta jokerMeta = joker.getItemMeta();
        jokerMeta.setDisplayName(ChatColor.GOLD + "Joker");
        jokerMeta.setUnbreakable(true);
        jokerMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        joker.setItemMeta(jokerMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(joker);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        for(ItemStack item : player.getInventory().getContents()){
            if(item == null) continue;
            if(item.getItemMeta() != null){
                if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Joker")){
                    player.getInventory().remove(item);
                    break;
                }
            }
        }

        giveKit(player);
    }
}
