package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Miner extends Kit {


    public Miner(){
        this.name = "Miner";
    }

    @Override
    public void giveKit(Player player) {
        ItemStack pickaxe = new ItemStack(org.bukkit.Material.IRON_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.setDisplayName(ChatColor.GOLD + "Pickaxe's Miner");
        pickaxeMeta.setUnbreakable(true);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxe.setItemMeta(pickaxeMeta);
        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);

        ItemStack rope = new ItemStack(org.bukkit.Material.LEAD);
        ItemMeta ropeMeta = rope.getItemMeta();
        ropeMeta.setDisplayName(ChatColor.GOLD + "Escape Rope");
        ropeMeta.setUnbreakable(true);
        ropeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        rope.setItemMeta(ropeMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(rope);
        items.add(pickaxe);

        addKitItems(items, player);

    }

    @Override
    public void startKit(Player player) {
        ItemStack pickaxe = new ItemStack(org.bukkit.Material.IRON_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.setDisplayName(ChatColor.GOLD + "Pickaxe's Miner");
        pickaxeMeta.setUnbreakable(true);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxe.setItemMeta(pickaxeMeta);
        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(pickaxe);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {

        for(ItemStack item : player.getInventory().getContents()){
            if(item != null){
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Pickaxe's Miner")){
                    item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 7);
                }
            }
        }


        ItemStack rope = new ItemStack(org.bukkit.Material.LEAD);
        ItemMeta ropeMeta = rope.getItemMeta();
        ropeMeta.setDisplayName(ChatColor.GOLD + "Escape Rope");
        ropeMeta.setUnbreakable(true);
        ropeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        rope.setItemMeta(ropeMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(rope);

        addKitItems(items, player);
    }
}
