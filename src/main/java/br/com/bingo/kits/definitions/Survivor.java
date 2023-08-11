package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Survivor extends Kit {

    public Survivor(){
        this.name = "Survivor";
    }


    @Override
    public void giveKit(Player player) {
        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.setDisplayName(ChatColor.GOLD + "Survivor's Chestplate");
        chestplateMeta.setUnbreakable(true);
        chestplateMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        chestplate.setItemMeta(chestplateMeta);
        chestplateMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);

        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta goldenAppleMeta = goldenApple.getItemMeta();
        goldenAppleMeta.setDisplayName(ChatColor.GOLD + "Special Apple");
        goldenAppleMeta.setUnbreakable(true);
        goldenAppleMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        goldenApple.setItemMeta(goldenAppleMeta);

        ItemStack beef = new ItemStack(Material.COOKED_BEEF);
        ItemMeta beefMeta = beef.getItemMeta();
        beefMeta.setDisplayName(ChatColor.GOLD + "Special Beef");
        beefMeta.setUnbreakable(true);
        beefMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        beef.setItemMeta(beefMeta);


        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(goldenApple);
        items.add(beef);
        player.getInventory().setChestplate(chestplate);
        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.setDisplayName(ChatColor.GOLD + "Survivor's Chestplate");
        chestplateMeta.setUnbreakable(true);
        chestplateMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        chestplate.setItemMeta(chestplateMeta);
        chestplateMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);

        ItemStack beef = new ItemStack(Material.COOKED_BEEF);
        ItemMeta beefMeta = beef.getItemMeta();
        beefMeta.setDisplayName(ChatColor.GOLD + "Special Beef");
        beefMeta.setUnbreakable(true);
        beefMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        beef.setItemMeta(beefMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(beef);
        player.getInventory().setChestplate(chestplate);
        addKitItems(items, player);

    }

    @Override
    public void completeKit(Player player) {
        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.setDisplayName(ChatColor.GOLD + "Survivor's Chestplate");
        chestplateMeta.setUnbreakable(true);
        chestplateMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        chestplate.setItemMeta(chestplateMeta);
        chestplateMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);

        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta goldenAppleMeta = goldenApple.getItemMeta();
        goldenAppleMeta.setDisplayName(ChatColor.GOLD + "Special Apple");
        goldenAppleMeta.setUnbreakable(true);
        goldenAppleMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        goldenApple.setItemMeta(goldenAppleMeta);



        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(goldenApple);

        player.getInventory().setChestplate(chestplate);
        addKitItems(items, player);

    }
}
