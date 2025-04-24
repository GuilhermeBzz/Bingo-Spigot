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

public class BonusFeast {

    GameManager gameManager;
    static Location feastLocation;
    World world;

    public BonusFeast(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void startBonusFeast(World worldNew) {
        world = worldNew;
        Random random = new Random();
        int min = -1500;
        int max = 1500;
        int x = random.nextInt(max - min + 1) + min;
        int z = random.nextInt(max - min + 1) + min;
        int y = world.getHighestBlockYAt(x, z);
        if(y < 64) y = 64;
        if(y > 200) y = 200;
        y = y + 30;
        feastLocation = new Location(world, x, y, z);
        generateBonusFeast(feastLocation);
        Bukkit.broadcastMessage(ChatColor.RED + "Bonus Feast apareceu em " +ChatColor.GOLD + " X: " + x + " Y: " + y + " Z: " + z + ChatColor.RED + "!");
        fillBonusFeast(feastLocation);

    }

    public void eraseBonusFeast() {
        feastLocation = null;
        world = null;
    }


    public void generateBonusFeast(Location location){
        try{
            SchematicManager.buildSchematic( "bonus_feast.schem", location, false);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static Location getBonusFeastLocation() {
        return feastLocation;
    }

    public void fillBonusFeast(Location feast){
        ArrayList<Location> chests = new ArrayList<>();
        feast.getBlock().setType(Material.ENCHANTING_TABLE);
        chests.add(feast.clone().add(1,0,0));
        chests.add(feast.clone().add(-1,0,0));
        chests.add(feast.clone().add(0,0,1));
        chests.add(feast.clone().add(0,0,-1));
        chests.add(feast.clone().add(3,0,0));
        chests.add(feast.clone().add(-3,0,0));
        chests.add(feast.clone().add(0,0,3));
        chests.add(feast.clone().add(0,0,-3));
        chests.add(feast.clone().add(2,0,-1));
        chests.add(feast.clone().add(2,0,1));
        chests.add(feast.clone().add(-2,0,-1));
        chests.add(feast.clone().add(-2,0,1));
        chests.add(feast.clone().add(1,0,2));
        chests.add(feast.clone().add(-1,0,2));
        chests.add(feast.clone().add(1,0,-2));
        chests.add(feast.clone().add(-1,0,-2));




        for(Location chest : chests){
            chest.getBlock().setType(Material.CHEST);
            chest.getWorld().spawnParticle(Particle.EXPLOSION, chest, 1);
            chest.getWorld().playSound(chest, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            fillChest(chest);
        }

    }

    public void fillChest(Location chest){
        Chest chestState = (Chest) chest.getBlock().getState();
        Inventory chestInv = chestState.getInventory();
        chestInv.clear();
        ArrayList<BonusFeastLootTable> items = new ArrayList<>(Arrays.asList(BonusFeastLootTable.values()));
        Set<BonusFeastLootTable> used = new HashSet<>();
        Random random = new Random();
        for(int slot = 0; slot < chestInv.getSize(); slot++){
            BonusFeastLootTable item = items.get(random.nextInt(items.size()));
            if(used.contains(item)) continue;
            ItemStack newItem = BonusFeastLootTable.generateItem(item);
            if(!newItem.getType().equals(Material.AIR)) used.add(item);
            chestInv.setItem(slot, newItem);
        }

    }
}
