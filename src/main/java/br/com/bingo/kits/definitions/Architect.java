package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Architect extends Kit {
    @Override
    public void giveKit(Player player) {
        ItemStack builder = new ItemStack(Material.SHULKER_SHELL);
        ItemMeta builderMeta = builder.getItemMeta();
        builderMeta.setDisplayName(ChatColor.GOLD + "Pro Builder");
        builderMeta.setUnbreakable(true);
        builderMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        builder.setItemMeta(builderMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(builder);

        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack builder = new ItemStack(Material.SHULKER_SHELL);
        ItemMeta builderMeta = builder.getItemMeta();
        builderMeta.setDisplayName(ChatColor.GOLD + "Builder");
        builderMeta.setUnbreakable(true);
        builderMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        builder.setItemMeta(builderMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(builder);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        for(ItemStack item : player.getInventory().getContents()){
            if(item == null) continue;
            if(item.getItemMeta() != null){
                if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Builder")){
                    player.getInventory().remove(item);
                    break;
                }
            }
        }

        giveKit(player);
    }
}
