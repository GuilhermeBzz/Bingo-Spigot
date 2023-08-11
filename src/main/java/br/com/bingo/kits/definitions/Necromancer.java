package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Necromancer extends Kit {
    @Override
    public void giveKit(Player player) {
        ItemStack hoe = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta hoeMeta = hoe.getItemMeta();
        hoeMeta.setDisplayName(ChatColor.GOLD + "Necromancer's Hoe");
        hoeMeta.setUnbreakable(true);
        hoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        hoe.setItemMeta(hoeMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(hoe);
        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack hoe = new ItemStack(Material.WOODEN_HOE);
        ItemMeta hoeMeta = hoe.getItemMeta();
        hoeMeta.setDisplayName(ChatColor.GOLD + "Necromancer's Hoe");
        hoeMeta.setUnbreakable(true);
        hoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        hoe.setItemMeta(hoeMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(hoe);
        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {

        ItemStack hoe = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta hoeMeta = hoe.getItemMeta();
        hoeMeta.setDisplayName(ChatColor.GOLD + "Necromancer's Hoe");
        hoeMeta.setUnbreakable(true);
        hoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        hoe.setItemMeta(hoeMeta);

        for(ItemStack item : player.getInventory().getContents()){
            if(item != null){
                if(!item.hasItemMeta()) continue;
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Necromancer's Hoe")){
                    player.getInventory().remove(item);
                    player.getInventory().addItem(hoe);
                }
            }
        }
    }
}
