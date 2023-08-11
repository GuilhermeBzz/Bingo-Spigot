package br.com.bingo.kits.definitions;

import br.com.bingo.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Beekeeper extends Kit {

    public Beekeeper(){
        name = "Beekeeper";
    }

    @Override
    public void giveKit(Player player) {

    }

    @Override
    public void startKit(Player player) {
        ItemStack hive = new ItemStack(Material.BEEHIVE, 2);
        ItemStack bee = new ItemStack(Material.BEE_SPAWN_EGG, 2);
        ItemStack flower = new ItemStack(Material.POPPY, 8);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(hive);
        items.add(bee);
        items.add(flower);
        addKitItems(items, player);
    }

    @Override
    public void completeKit(Player player) {
        ItemStack hive = new ItemStack(Material.BEEHIVE, 2);
        ItemStack bee = new ItemStack(Material.BEE_SPAWN_EGG, 2);
        ItemStack flower = new ItemStack(Material.POPPY, 8);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(hive);
        items.add(bee);
        items.add(flower);
        addKitItems(items, player);
    }

    @Override
    public void onRespawn(Player player){
        player.sendMessage("Voce ja recebeu seus itens!");
    }

    @Override
    public void onStartRespawn(Player player){
        player.sendMessage("Voce recebera seus itens em 5 segundos!");
    }
}
