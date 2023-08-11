package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Hunter extends Kit {

    public Hunter(){
        this.name = "Hunter";
    }
    @Override
    public void giveKit(Player player) {

        ArrayList<ItemStack> items = new ArrayList<>();

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setUnbreakable(true);
        compassMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        compassMeta.setDisplayName(ChatColor.GOLD + "Hunter's Compass");
        compass.setItemMeta(compassMeta);
        items.add(compass);

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setUnbreakable(true);
        swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        swordMeta.setDisplayName(ChatColor.GOLD + "Hunter's Sword");
        sword.setItemMeta(swordMeta);
        sword.addEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 5);
        items.add(sword);

        player.sendMessage(ChatColor.GREEN + "Voce recebeu o seu kit!");
        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setUnbreakable(true);
        swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        swordMeta.setDisplayName(ChatColor.GOLD + "Hunter's Sword");
        sword.setItemMeta(swordMeta);
        sword.addEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 2);
        items.add(sword);

        player.sendMessage(ChatColor.GREEN + "Voce recebeu o seu kit!");
        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {

        for(ItemStack item : player.getInventory().getContents()){
            if(item != null){
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Hunter's Sword")){
                    item.addEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 5);
                }
            }
        }


        ArrayList<ItemStack> items = new ArrayList<>();

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setUnbreakable(true);
        compassMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        compassMeta.setDisplayName(ChatColor.GOLD + "Hunter's Compass");
        compass.setItemMeta(compassMeta);
        items.add(compass);

        player.sendMessage(ChatColor.GREEN + "Voce recebeu o seu kit!");
        addKitItems(items, player);
    }
}
