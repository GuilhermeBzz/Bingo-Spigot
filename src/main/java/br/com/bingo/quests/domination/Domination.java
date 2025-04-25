package br.com.bingo.quests.domination;

import br.com.bingo.SchematicManager;
import br.com.bingo.team.TeamType;
import org.bukkit.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Domination {

    public static Location startDomination(World world){
        Random random = new Random();
        int min = -300;
        int max = 300;
        int x = random.nextInt(max - min + 1) + min;
        int z = random.nextInt(max - min + 1) + min;
        int y = world.getHighestBlockYAt(x, z);
        if(y < 64) y = 64;
        if(y > 200) y = 200;
        y = y + 4;

        Location location = new Location(world, x, y, z);
        generateArena(location);
        generateTotem(location);
        Bukkit.broadcastMessage(ChatColor.AQUA +  "O ponto de dominação surgiu em " + ChatColor.GOLD + "X: " + x + " Y: " + y + " Z: " + z + "!");

        return location;
    }

    public static void generateArena(Location location){
        try{
            SchematicManager.buildSchematic( "domination.schem", location, true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void generateTotem(Location location){
        Location totemLocation = location.clone();

        totemLocation.getBlock().setType(Material.GRAY_CONCRETE);
        totemLocation.add(0,1,0).getBlock().setType(Material.GRAY_CONCRETE);
        totemLocation.add(0,1,0).getBlock().setType(Material.GRAY_CONCRETE);
        totemLocation.add(0,1,0).getBlock().setType(Material.GRAY_CONCRETE);
    }
}
