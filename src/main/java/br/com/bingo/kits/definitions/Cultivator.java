package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Cultivator extends Kit {

    @Override
    public void giveKit(Player player) {
        ItemStack soup = new ItemStack(Material.MUSHROOM_STEW);
        ItemMeta soupMeta = soup.getItemMeta();
        soupMeta.setDisplayName(ChatColor.GOLD + "Sopa do HG");
        soupMeta.setUnbreakable(true);
        soupMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        soup.setItemMeta(soupMeta);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(soup);

        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta hoeMeta = hoe.getItemMeta();
        hoeMeta.setDisplayName(ChatColor.GOLD + "Cultivator's Hoe");
        hoeMeta.setUnbreakable(true);
        hoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        hoe.setItemMeta(hoeMeta);

        items.add(hoe);

        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta hoeMeta = hoe.getItemMeta();
        hoeMeta.setDisplayName(ChatColor.GOLD + "Cultivator's Hoe");
        hoeMeta.setUnbreakable(true);
        hoeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        hoe.setItemMeta(hoeMeta);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(hoe);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        ItemStack soup = new ItemStack(Material.MUSHROOM_STEW);
        ItemMeta soupMeta = soup.getItemMeta();
        soupMeta.setDisplayName(ChatColor.GOLD + "Sopa do HG");
        soupMeta.setUnbreakable(true);
        soupMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        soup.setItemMeta(soupMeta);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(soup);

        addKitItems(items, player);
    }
}
