package br.com.bingo.feast;

import br.com.bingo.SchematicManager;
import br.com.bingo.game.GameManager;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class MiniFeast {

    GameManager gameManager;
    static ArrayList<Location> miniFeastLocations;

    public MiniFeast(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void generateMiniFeast(World world){
        Random random = new Random();
        int min = -1000;
        int max = 1000;
        int x = random.nextInt(max - min + 1) + min;
        int z = random.nextInt(max - min + 1) + min;
        int y = world.getHighestBlockYAt(x, z);
        if(y < 64) y = 64;
        if(y > 200) y = 200;
        Location location = new Location(world, x, y, z);
        if(miniFeastLocations == null){
            miniFeastLocations = new ArrayList<>();
        }else{
            for(Location location1 : miniFeastLocations){
                if(location1.distance(location) < 200){
                    location.add(location.getX()*-1, 0,0);
                    y = world.getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
                    location.setY(y);
                    return;
                }
            }
        }
        miniFeastLocations.add(location);
        generateMiniFeast(location);
        fillChests(location);

        int finalX = location.getBlockX();
        int finalZ = location.getBlockZ();
        int maxX = (finalX/100) * 100;
        int minX = maxX - 100;
        int maxZ = (finalZ/100) * 100;
        int minZ = maxZ - 100;
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Um MiniFeast apareceu entre " + ChatColor.GOLD + "X: " + minX +  " e " + maxX +  ChatColor.LIGHT_PURPLE + " e " + ChatColor.GOLD + "Z: " + minZ + " e " + maxZ + "!");

    }

    public void eraseMiniFeast(){
        miniFeastLocations = null;
    }

    public void generateMiniFeast(Location location){
        try{
            SchematicManager.buildSchematic( "mini_feast.schem", location);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void fillChests(Location location){
        ArrayList<Location> chests = new ArrayList<>();
        location.add(0,0,-2);
        chests.add(location.clone().add(1,0,0));
        chests.add(location.clone().add(-1,0,0));
        chests.add(location.clone().add(0,0,1));
        chests.add(location.clone().add(0,0,-1));

        for(Location chest : chests){
            fillChest(chest);
        }
    }

    public void fillChest(Location chest){
        Chest chestState = (Chest) chest.getBlock().getState();
        Inventory chestInv = chestState.getInventory();
        chestInv.clear();
        ArrayList<MiniFeastLootTable> items = new ArrayList<>(Arrays.asList(MiniFeastLootTable.values()));
        Set<MiniFeastLootTable> used = new HashSet<>();
        Random random = new Random();
        for(int slot = 0; slot < chestInv.getSize(); slot++){
            MiniFeastLootTable item = items.get(random.nextInt(items.size()));
            if(used.contains(item)) continue;
            ItemStack newItem = MiniFeastLootTable.generateItem(item);
            if(!newItem.getType().equals(Material.AIR)) used.add(item);
            chestInv.setItem(slot, newItem);
        }
    }
}
