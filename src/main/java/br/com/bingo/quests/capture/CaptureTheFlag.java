package br.com.bingo.quests.capture;

import br.com.bingo.SchematicManager;
import br.com.bingo.team.TeamType;
import org.bukkit.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class CaptureTheFlag {

    public static HashMap<TeamType, Location> startCaptureTheFlag(World world){
        Random random = new Random();
        int min = -300;
        int max = 300;

        int xBlue = random.nextInt(max - min + 1) + min;
        int zBlue = random.nextInt(max - min + 1) + min;
        int yBlue = world.getHighestBlockYAt(xBlue, zBlue);
        if(yBlue < 64) yBlue = 64;
        if(yBlue > 200) yBlue = 200;

        int xRed = xBlue * -1;
        int zRed = zBlue * -1;
        int yRed = world.getHighestBlockYAt(xRed, zRed);
        if(yRed < 64) yRed = 64;
        if(yRed > 200) yRed = 200;

        yBlue += 4;
        yRed += 4;

        Location locationBlue = new Location(world, xBlue, yBlue, zBlue);
        Location locationRed = new Location(world, xRed, yRed, zRed);

        generateBase(locationBlue, TeamType.TEAM_BLUE);
        generateBase(locationRed, TeamType.TEAM_RED);

        generateFlag(locationBlue, TeamType.TEAM_BLUE);
        generateFlag(locationRed, TeamType.TEAM_RED);

        Bukkit.broadcastMessage(ChatColor.BLUE +  "O ponto de captura Azul está em: " + ChatColor.GOLD + "X: " + xBlue + " Y: " + yBlue + " Z: " + zBlue + "!");
        Bukkit.broadcastMessage(ChatColor.RED +  "O ponto de captura Vermelho está em: " + ChatColor.GOLD + "X: " + xRed + " Y: " + yRed + " Z: " + zRed + "!");
        Bukkit.broadcastMessage(ChatColor.GREEN + "Para roubar a bandeira inimiga, aperte com o botão direito com a mão vazia nela.");
        Bukkit.broadcastMessage(ChatColor.GREEN + "Para capturar ela, aperte com o botão direito na base da sua bandeira com a bandeira inimiga na mão.");

        HashMap<TeamType, Location> baseLocations = new HashMap<>();
        baseLocations.put(TeamType.TEAM_BLUE, locationBlue.clone());
        baseLocations.put(TeamType.TEAM_RED, locationRed.clone());

        return baseLocations; //retornar map
    }

    public static void generateBase(Location location, TeamType teamType){
        String fileName = "";
        if(teamType.equals(TeamType.TEAM_RED)){
            fileName = "red_base.schem";
        } else if(teamType.equals(TeamType.TEAM_BLUE)){
            fileName = "blue_base.schem";
        }

        try{
            SchematicManager.buildSchematic( fileName, location, true);
        } catch (IOException e){
            br.com.bingo.Bingo.getInstance().getLogger().log(java.util.logging.Level.SEVERE, "Erro inesperado no plugin Bingo (veja o stacktrace)", e);
        }
    }

    public static void generateFlag(Location location, TeamType teamType){
        Material flagMaterial = Material.RED_BANNER;
        if(teamType.equals(TeamType.TEAM_BLUE)){
            flagMaterial = Material.BLUE_BANNER;
        }
        location.getBlock().setType(flagMaterial);
    }
}
