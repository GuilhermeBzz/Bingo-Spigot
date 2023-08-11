package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Endermage extends Kit {

    public Endermage(){
        this.name = "Endermage";
    }

    @Override
    public void giveKit(Player player){

    }

    @Override
    public void startKit(Player player) {
        ItemStack pearl = new ItemStack(org.bukkit.Material.ENDER_PEARL, 1);
        ItemMeta pearlMeta = pearl.getItemMeta();
        pearlMeta.setDisplayName(org.bukkit.ChatColor.GOLD + "Endermage Pearl");
        pearlMeta.setUnbreakable(true);
        pearlMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        pearl.setItemMeta(pearlMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(pearl);
        addKitItems(items, player);

    }

    @Override
    public void completeKit(Player player) {
        ItemStack eyes = new ItemStack(org.bukkit.Material.ENDER_EYE, 12);
        ItemMeta eyesMeta = eyes.getItemMeta();
        eyesMeta.setDisplayName(org.bukkit.ChatColor.GOLD + "Endermage Eyes");
        eyesMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        eyes.setItemMeta(eyesMeta);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(eyes);
        addKitItems(items, player);
    }

    @Override
    public void onRespawn(Player player){
        player.sendMessage(org.bukkit.ChatColor.GREEN + "Seus olhos estão no seu drop");
        startKit(player);
    }

    @Override
    public void onStartRespawn(Player player){
        startKit(player);
    }
}
