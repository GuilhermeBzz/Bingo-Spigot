package br.com.bingo.kits;

import br.com.bingo.Bingo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Kit {
    public String name;
    public abstract void giveKit(Player player);
    public abstract void startKit(Player player);
    public abstract void completeKit(Player player);

    public String getName(){
        return name;
    }

    public void addKitItems(ArrayList<ItemStack> items, Player player){
        int emptySlots = 0;
        for(ItemStack item : player.getInventory().getStorageContents()){
            if(item == null || item.getType().isAir()){
                emptySlots++;
            }
        }

        if(emptySlots < items.size()){

            for(ItemStack item : player.getInventory().getContents()){
                if(item != null && !Objects.requireNonNull(item.getItemMeta()).hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)){
                    player.getInventory().remove(item);
                    player.getWorld().dropItem(player.getLocation(), item);
                    emptySlots++;
                }
                if(emptySlots == items.size()){
                    player.sendMessage(ChatColor.GREEN + "Seu inventário estava cheio, seus itens foram jogados no chão!");
                    break;
                }
            }

        }
        for(ItemStack item : items){
            player.getInventory().addItem(item);
        }
    }

    public void onStartRespawn(Player player){
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                startKit(player);
            }
        }, 20L);
    }

    public void onRespawn(Player player){

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                giveKit(player);
            }
        }, 20L);
    }
}

