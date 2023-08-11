package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Scout extends Kit {

    @Override
    public void giveKit(Player player) {
        ItemStack feather = new ItemStack(Material.FEATHER);
        ItemMeta featherMeta = feather.getItemMeta();
        featherMeta.setDisplayName(ChatColor.GOLD + "Ultra Boost");
        featherMeta.setUnbreakable(true);
        featherMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        feather.setItemMeta(featherMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(feather);

        player.getInventory().addItem(feather);
    }

    @Override
    public void startKit(Player player) {
        ItemStack feather = new ItemStack(Material.FEATHER);
        ItemMeta featherMeta = feather.getItemMeta();
        featherMeta.setDisplayName(ChatColor.GOLD + "Boost");
        featherMeta.setUnbreakable(true);
        featherMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        feather.setItemMeta(featherMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(feather);

        player.getInventory().addItem(feather);
    }

    @Override
    public void completeKit(Player player) {
        for(ItemStack item : player.getInventory().getContents()){
            if(item == null) continue;
            if(item.getItemMeta() != null){
                if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Boost")){
                    player.getInventory().remove(item);
                    break;
                }
            }
        }

        giveKit(player);
    }
}
