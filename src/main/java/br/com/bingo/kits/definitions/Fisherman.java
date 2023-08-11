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

public class Fisherman extends Kit {
    @Override
    public void giveKit(Player player) {
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        ItemMeta rodMeta = rod.getItemMeta();
        rodMeta.setDisplayName(ChatColor.GOLD + "Fisherman's Rod");
        rodMeta.setUnbreakable(true);
        rodMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        rodMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rod.setItemMeta(rodMeta);

        rod.addUnsafeEnchantment(Enchantment.LURE ,10);
        rod.addUnsafeEnchantment(Enchantment.LUCK, 10);

        ItemStack net = new ItemStack(Material.LEAD);
        ItemMeta netMeta = net.getItemMeta();
        netMeta.setDisplayName(ChatColor.GOLD + "Fishing Net");
        netMeta.setUnbreakable(true);
        netMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        net.setItemMeta(netMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(net);
        items.add(rod);
        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        ItemMeta rodMeta = rod.getItemMeta();
        rodMeta.setDisplayName(ChatColor.GOLD + "Fisherman's Rod");
        rodMeta.setUnbreakable(true);
        rodMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        rodMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rod.setItemMeta(rodMeta);

        rod.addEnchantment(Enchantment.LURE ,3);
        rod.addEnchantment(Enchantment.LUCK, 3);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(rod);
        addKitItems(items, player);

    }

    @Override
    public void completeKit(Player player) {
        ItemStack net = new ItemStack(Material.LEAD);
        ItemMeta netMeta = net.getItemMeta();
        netMeta.setDisplayName(ChatColor.GOLD + "Fishing Net");
        netMeta.setUnbreakable(true);
        netMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        net.setItemMeta(netMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(net);
        addKitItems(items, player);



        for(ItemStack item : player.getInventory().getContents()){
            if(item != null){
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Fisherman's Rod")){
                    item.addUnsafeEnchantment(Enchantment.LURE, 10);
                    item.addUnsafeEnchantment(Enchantment.LUCK, 10);
                }
            }
        }
    }
}
