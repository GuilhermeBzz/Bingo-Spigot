package br.com.bingo.kits.definitions;

import br.com.bingo.Bingo;
import br.com.bingo.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class Beastmaster extends Kit {

    public Beastmaster(){
        this.name = "Beastmaster";
    }

    @Override
    public void giveKit(Player player){
        ItemStack item = new ItemStack(Material.QUARTZ);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Egg Generator");
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(item);

        addKitItems(items, player);

    }

    @Override
    public void startKit(Player player) {
        ItemStack item = new ItemStack(Material.QUARTZ);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Egg Generator");
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(item);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        ItemStack wheat = new ItemStack(Material.WHEAT, 16);
        ItemStack seeds = new ItemStack(Material.WHEAT_SEEDS, 16);
        ItemStack bamboo = new ItemStack(Material.BAMBOO, 16);

        ArrayList<ItemStack> items = new ArrayList<>();
        Collections.addAll(items, wheat, seeds, bamboo);

        addKitItems(items, player);


    }

}

