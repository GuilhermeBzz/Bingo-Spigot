package br.com.bingo.quests.boss;

import br.com.bingo.SchematicManager;
import org.bukkit.*;

import java.io.IOException;
import java.util.Random;

public class BossFight {

    public static Location startBossFight(World world) {
        Random random = new Random();
        int min = -300;
        int max = 300;
        int x = random.nextInt(max - min + 1) + min;
        int z = random.nextInt(max - min + 1) + min;
        int y = world.getHighestBlockYAt(x, z);
        if(y < 64) y = 64;
        if(y > 200) y = 200;
        y = y + 2;

        Location location = new Location(world, x, y, z);
        generateArena(location);
        generateTotem(location);
        Bukkit.broadcastMessage(ChatColor.DARK_RED +  "O Boss apareceu em " + ChatColor.GOLD + "X: " + x + " Y: " + y + " Z: " + z + "!");

        return location;
    }

    public static void generateArena(Location location){
        try{
            SchematicManager.buildSchematic( "boss_arena.schem", location, true);
        } catch (IOException e){
            br.com.bingo.Bingo.getInstance().getLogger().log(java.util.logging.Level.SEVERE, "Erro inesperado no plugin Bingo (veja o stacktrace)", e);
        }

    }

    public static void generateTotem(Location location){
        Location totemLocation = location.clone();
        totemLocation.getBlock().setType(Material.REINFORCED_DEEPSLATE);
        totemLocation.add(0,1,0).getBlock().setType(Material.REINFORCED_DEEPSLATE);
        totemLocation.add(0,1,0).getBlock().setType(Material.REINFORCED_DEEPSLATE);

    }

}
