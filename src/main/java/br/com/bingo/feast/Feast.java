package br.com.bingo.feast;

import br.com.bingo.Bingo;
import br.com.bingo.SchematicManager;
import br.com.bingo.game.GameManager;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class Feast {

    GameManager gameManager;
    static Location feastLocation;
    World world;

    public Feast(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void startFeast(World worldNew) {
        world = worldNew;
        Random random = new Random();
        int min = -1000;
        int max = 1000;
        int x = random.nextInt(max - min + 1) + min;
        int z = random.nextInt(max - min + 1) + min;
        int y = world.getHighestBlockYAt(x, z);
        if(y < 64) y = 64;
        if(y > 200) y = 200;
        y = y + 10;
        feastLocation = new Location(world, x, y, z);
        generateFeast(feastLocation);
        Bukkit.broadcastMessage(ChatColor.GREEN + "Feast vai aparecer em 10 minutos em X: " + x + " Y: " + y + " Z: " + z + "!");
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                fillFeast(feastLocation);

            }
        }, 12000L);
    }


    public void eraseFeast() {
     feastLocation = null;
     world = null;
    }

    public void generateFeast(Location location){
        try{
            SchematicManager.buildSchematic( "feast.schem", location);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static Location getFeastLocation() {
        return feastLocation;
    }
    public void fillFeast(Location feast){
        Bukkit.broadcastMessage(ChatColor.GREEN + "Feast apareceu em X: " + feast.getBlockX() + " Y: " + feast.getBlockY() + " Z: " + feast.getBlockZ() + "!");
        ArrayList<Location> chests = new ArrayList<>();
        feast.getBlock().setType(Material.ENCHANTING_TABLE);
        chests.add(feast.clone().add(1,0,1));
        chests.add(feast.clone().add(-1,0,-1));
        chests.add(feast.clone().add(-1,0,1));
        chests.add(feast.clone().add(1,0,-1));
        chests.add(feast.clone().add(2,0,2));
        chests.add(feast.clone().add(-2,0,2));
        chests.add(feast.clone().add(-2,0,-2));
        chests.add(feast.clone().add(2,0,-2));
        chests.add(feast.clone().add(2,0,0));
        chests.add(feast.clone().add(-2,0,0));
        chests.add(feast.clone().add(0,0,2));
        chests.add(feast.clone().add(0,0,-2));

        for(Location chest : chests){
            chest.getBlock().setType(Material.CHEST);
            chest.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, chest, 1);
            chest.getWorld().playSound(chest, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            fillChest(chest);
        }

    }

    public void fillChest(Location chest){
        Chest chestState = (Chest) chest.getBlock().getState();
        Inventory chestInv = chestState.getInventory();
        chestInv.clear();
        ArrayList<FeastLootTable> items = new ArrayList<>(Arrays.asList(FeastLootTable.values()));
        Set<FeastLootTable> used = new HashSet<>();
        Random random = new Random();
        for(int slot = 0; slot < chestInv.getSize(); slot++){
            FeastLootTable item = items.get(random.nextInt(items.size()));
            if(used.contains(item)) continue;
            ItemStack newItem = FeastLootTable.generateItem(item);
            if(!newItem.getType().equals(Material.AIR)) used.add(item);
            chestInv.setItem(slot, newItem);
        }

    }


}
