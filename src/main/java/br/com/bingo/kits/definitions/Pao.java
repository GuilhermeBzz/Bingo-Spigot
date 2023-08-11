package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Pao extends Kit {
    @Override
    public void giveKit(Player player) {
        ItemStack pao = new ItemStack(Material.BREAD);
        ItemMeta paoMeta = pao.getItemMeta();
        paoMeta.setDisplayName(ChatColor.GOLD + "Pão Especial");
        paoMeta.setUnbreakable(true);
        paoMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        pao.setItemMeta(paoMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(pao);

        addKitItems(items, player);
    }

    @Override
    public void startKit(Player player) {
        ItemStack apple = new ItemStack(Material.BREAD);
        ItemMeta appleMeta = apple.getItemMeta();
        appleMeta.setDisplayName(ChatColor.GOLD + "Maça Especial");
        appleMeta.setUnbreakable(true);
        appleMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        apple.setItemMeta(appleMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(apple);

        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        for(ItemStack item : player.getInventory().getContents()){
            if(item == null) continue;
            if(item.getItemMeta() != null){
                if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Maça Especial")){
                    player.getInventory().remove(item);
                    break;
                }
            }
        }

        giveKit(player);
    }
}
