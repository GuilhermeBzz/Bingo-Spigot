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
import java.util.Collections;

public class Beastmaster extends Kit {

    public Beastmaster(){
        this.name = "Beastmaster";
    }

    @Override
    public void giveKit(Player player){
    }

    @Override
    public void startKit(Player player) {
        giveEgg(player);
    }

    @Override
    public void completeKit(Player player) {
        ItemStack wheat = new ItemStack(Material.WHEAT, 16);
        ItemStack seeds = new ItemStack(Material.WHEAT_SEEDS, 16);
        ItemStack bamboo = new ItemStack(Material.BAMBOO, 16);

        ArrayList<ItemStack> items = new ArrayList<>();
        Collections.addAll(items, wheat, seeds, bamboo);

        addKitItems(items, player);


    }

    @Override
    public void onRespawn(Player player){

    }

    @Override
    public void onStartRespawn(Player player){

    }

    public void giveEgg(Player player){
        ArrayList<ItemStack> eggs = new ArrayList<>();
        eggs.add(new ItemStack(Material.CHICKEN_SPAWN_EGG));
        eggs.add(new ItemStack(Material.COW_SPAWN_EGG));
        eggs.add(new ItemStack(Material.PIG_SPAWN_EGG));
        eggs.add(new ItemStack(Material.SHEEP_SPAWN_EGG));
        eggs.add(new ItemStack(Material.WOLF_SPAWN_EGG));
        eggs.add(new ItemStack(Material.PANDA_SPAWN_EGG));
        eggs.add(new ItemStack(Material.OCELOT_SPAWN_EGG));
        eggs.add(new ItemStack(Material.RABBIT_SPAWN_EGG));
        eggs.add(new ItemStack(Material.HORSE_SPAWN_EGG));
        eggs.add(new ItemStack(Material.LLAMA_SPAWN_EGG));
        eggs.add(new ItemStack(Material.PARROT_SPAWN_EGG));
        eggs.add(new ItemStack(Material.CAT_SPAWN_EGG));
        eggs.add(new ItemStack(Material.TURTLE_SPAWN_EGG));
        eggs.add(new ItemStack(Material.FOX_SPAWN_EGG));
        eggs.add(new ItemStack(Material.BEE_SPAWN_EGG));

        Collections.shuffle(eggs);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.addAll(eggs.subList(0, 1));
        addKitItems(items, player);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                giveEgg(player);
            }
        }, 6000L);
    }
}

