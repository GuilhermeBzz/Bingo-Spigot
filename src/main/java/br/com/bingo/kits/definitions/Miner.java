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

public class Miner extends Kit {


    public Miner(){
        this.name = "Miner";
    }

    @Override
    public void giveKit(Player player) {
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.setDisplayName(ChatColor.GOLD + "Miner's Pickaxe");
        pickaxeMeta.setUnbreakable(true);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxeMeta.setCustomModelData(777);
        pickaxe.setItemMeta(pickaxeMeta);
        pickaxe.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5);
        pickaxe.addUnsafeEnchantment(Enchantment.FORTUNE, 1);

        ItemStack rope = new ItemStack(org.bukkit.Material.LEAD);
        ItemMeta ropeMeta = rope.getItemMeta();
        ropeMeta.setDisplayName(ChatColor.GOLD + "Escape Rope");
        ropeMeta.setUnbreakable(true);
        ropeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        ropeMeta.setCustomModelData(777);
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
        pickaxeMeta.setDisplayName(ChatColor.GOLD + "Miner's Pickaxe");
        pickaxeMeta.setUnbreakable(true);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxeMeta.setCustomModelData(777);
        pickaxe.setItemMeta(pickaxeMeta);
        pickaxe.addUnsafeEnchantment(Enchantment.EFFICIENCY, 1);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(pickaxe);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {

        for(ItemStack item : player.getInventory().getContents()){
            if(item == null) continue;
            if(item.getItemMeta() != null){
                if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Miner's Pickaxe")){
                    player.getInventory().remove(item);
                    break;
                }
            }
        }

        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.setDisplayName(ChatColor.GOLD + "Miner's Pickaxe");
        pickaxeMeta.setUnbreakable(true);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxeMeta.setCustomModelData(777);
        pickaxe.setItemMeta(pickaxeMeta);
        pickaxe.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5);
        pickaxe.addUnsafeEnchantment(Enchantment.FORTUNE, 1);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(pickaxe);

        ItemStack rope = new ItemStack(org.bukkit.Material.LEAD);
        ItemMeta ropeMeta = rope.getItemMeta();
        ropeMeta.setDisplayName(ChatColor.GOLD + "Escape Rope");
        ropeMeta.setUnbreakable(true);
        ropeMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        ropeMeta.setCustomModelData(777);
        rope.setItemMeta(ropeMeta);

        items.add(rope);

        addKitItems(items, player);
    }
}
