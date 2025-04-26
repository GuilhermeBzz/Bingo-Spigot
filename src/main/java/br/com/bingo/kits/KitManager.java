package br.com.bingo.kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KitManager {


    public static void openKitMenu(Player player){
        Inventory kitMenu = Bukkit.createInventory(null, 3 * 9, ChatColor.DARK_PURPLE + "Kits");

        List<KitType> allKits = new ArrayList<>();
        Collections.addAll(allKits, KitType.values());

        for(KitType kitType : allKits){
            ItemStack kitItem = new ItemStack(kitType.getIcon());
            ItemMeta kitItemMeta = kitItem.getItemMeta();
            kitItemMeta.addItemFlags(ItemFlag.HIDE_BEES);
            kitItemMeta.addItemFlags(ItemFlag.HIDE_BLOCK_STATE);
            kitItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            kitItemMeta.addItemFlags(ItemFlag.HIDE_FOOD);
            kitItemMeta.setDisplayName(ChatColor.AQUA + kitType.getName());
            kitItemMeta.setCustomModelData(777);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + kitType.getDescription());
            lore.add("");
            lore.add(ChatColor.YELLOW + "Clique para selecionar!");
            kitItemMeta.setLore(lore);
            kitItem.setItemMeta(kitItemMeta);
            kitMenu.addItem(kitItem);
        }


        player.openInventory(kitMenu);
    }

}
