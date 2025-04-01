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

public class Gambler extends Kit {
    @Override
    public void giveKit(Player player) {

    }

    @Override
    public void startKit(Player player) {
        Bingo plugin = Bingo.getInstance();
        player.sendMessage(ChatColor.GREEN + "Você tem ira escolher seu Kit em 5 minutos!");
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
            @Override
            public void run() {
                ItemStack item = new ItemStack(Material.GOLD_INGOT);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GOLD + "Escolher Kit");
                itemMeta.setUnbreakable(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                itemMeta.setCustomModelData(777);
                item.setItemMeta(itemMeta);

                ArrayList<ItemStack> items = new ArrayList<>();
                items.add(item);

                addKitItems(items, player);
                //selectKit(player);
                player.sendMessage(ChatColor.GREEN + "Escolha seu Kit!");
            }
        }, 6000L);
    }

    @Override
    public void completeKit(Player player) {
        return;
    }

    @Override
    public void onStartRespawn(Player player){
        if(player.getWorld().getPVP()){
            ItemStack item = new ItemStack(Material.GOLD_INGOT);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD + "Escolher Kit");
            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemMeta.setCustomModelData(777);
            item.setItemMeta(itemMeta);

            ArrayList<ItemStack> items = new ArrayList<>();
            items.add(item);

            addKitItems(items, player);
            //selectKit(player);
            player.sendMessage(ChatColor.GREEN + "Escolha seu Kit!");
        }

        return;
    }

    @Override
    public void onRespawn(Player player){
        if(player.getWorld().getPVP()){
            ItemStack item = new ItemStack(Material.GOLD_INGOT);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD + "Escolher Kit");
            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemMeta.setCustomModelData(777);
            item.setItemMeta(itemMeta);

            ArrayList<ItemStack> items = new ArrayList<>();
            items.add(item);

            addKitItems(items, player);
            //selectKit(player);
            player.sendMessage(ChatColor.GREEN + "Escolha seu Kit!");
        }

        return;
    }

}
