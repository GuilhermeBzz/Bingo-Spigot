package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Soulbound extends Kit {

    @Override
    public void giveKit(Player player) {

        ItemStack item = new ItemStack(Material.DRAGON_BREATH);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Portable EnderChest");
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(777);
        item.setItemMeta(itemMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(item);

        ItemStack item2 = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta2 = item.getItemMeta();
        itemMeta2.setDisplayName(ChatColor.GOLD + "Soul Portal");
        itemMeta2.setUnbreakable(true);
        itemMeta2.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item2.setItemMeta(itemMeta2);

        items.add(item2);
        addKitItems(items, player);

    }

    @Override
    public void startKit(Player player) {
        ItemStack item = new ItemStack(Material.DRAGON_BREATH);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Portable EnderChest");
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(777);
        item.setItemMeta(itemMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(item);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {

    }
}
