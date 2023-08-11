package br.com.bingo.rank;

import br.com.bingo.rank.models.players.PlayersData;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoard {


    private static ArrayList<ArmorStand> entities = new ArrayList<>();
    private static final World world = Bukkit.getWorld("world");
    private static final Location location = new Location(world, -19, 63, 99);


    public static void addEntity(ArmorStand entity){
        entities.add(entity);
    }

    public static void removeEntity(ArmorStand entity){
        entities.remove(entity);
    }

    public static ArrayList<ArmorStand> getEntities(){
        return entities;
    }

    public static void clearEntities(){
        for(ArmorStand entity : entities){
            entity.damage(99999);
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill " + entity.getUniqueId());
            entity.remove();
        }
        entities.clear();
    }

    public static void createLeaderBoard(){
        if(!entities.isEmpty()){
                clearEntities();
        }


        Location locationToUse = location.clone();
        ArmorStand top5 = (ArmorStand) world.spawnEntity(locationToUse.add(0,-0.3,0), EntityType.ARMOR_STAND);
        ArmorStand top4 = (ArmorStand) world.spawnEntity(locationToUse.add(0,0.3,0), EntityType.ARMOR_STAND);
        ArmorStand top3 = (ArmorStand) world.spawnEntity(locationToUse.add(0,0.3,0), EntityType.ARMOR_STAND);
        ArmorStand top2 = (ArmorStand) world.spawnEntity(locationToUse.add(0,0.3,0), EntityType.ARMOR_STAND);
        ArmorStand top1 = (ArmorStand) world.spawnEntity(locationToUse.add(0,0.3,0), EntityType.ARMOR_STAND);
        ArmorStand title = (ArmorStand) world.spawnEntity(locationToUse.add(0,0.5,0), EntityType.ARMOR_STAND);
        entities.add(top5);
        entities.add(top4);
        entities.add(top3);
        entities.add(top2);
        entities.add(top1);
        entities.add(title);

        for(ArmorStand entity : entities){
            entity.setGravity(false);
            entity.setCustomNameVisible(true);
            entity.setVisible(false);
            entity.setInvulnerable(true);
            entity.setSmall(true);
        }

        title.setCustomName("§e§lTOP 5");

        ArrayList<PlayersData> auxList = PlayersStorageUtil.getPlayers();
        Comparator<PlayersData> comparator = Comparator.comparing(PlayersData::getPoints);
        Collections.sort(auxList, comparator.reversed());

        if(!auxList.isEmpty()){
            top1.setCustomName(ChatColor.YELLOW + "1º " + Ranks.getRank(auxList.get(0).getPoints()).getColor() + Bukkit.getOfflinePlayer(auxList.get(0).getUuid()).getName() + " §f" + auxList.get(0).getPoints() + " §7pontos");
        }else{
            top1.setCustomName(ChatColor.GRAY + "-");
        }
        if(auxList.size() > 1){
            top2.setCustomName(ChatColor.YELLOW + "2º " + Ranks.getRank(auxList.get(1).getPoints()).getColor() + Bukkit.getOfflinePlayer(auxList.get(1).getUuid()).getName() + " §f" + auxList.get(1).getPoints() + " §7pontos");
        }else{
            top2.setCustomName(ChatColor.GRAY + "-");
        }
        if(auxList.size() > 2){
            top3.setCustomName(ChatColor.YELLOW + "3º " + Ranks.getRank(auxList.get(2).getPoints()).getColor() + Bukkit.getOfflinePlayer(auxList.get(2).getUuid()).getName() + " §f" + auxList.get(2).getPoints() + " §7pontos");
        }else{
            top3.setCustomName(ChatColor.GRAY + "-");
        }
        if(auxList.size() > 3){
            top4.setCustomName(ChatColor.YELLOW + "4º " + Ranks.getRank(auxList.get(3).getPoints()).getColor() + Bukkit.getOfflinePlayer(auxList.get(3).getUuid()).getName() + " §f" + auxList.get(3).getPoints() + " §7pontos");
        }else{
            top4.setCustomName(ChatColor.GRAY + "-");
        }
        if(auxList.size() > 4){
            top5.setCustomName(ChatColor.YELLOW + "5º " + Ranks.getRank(auxList.get(4).getPoints()).getColor() + Bukkit.getOfflinePlayer(auxList.get(4).getUuid()).getName() + " §f" + auxList.get(4).getPoints() + " §7pontos");
        }else{
            top5.setCustomName(ChatColor.GRAY + "-");
        }
    }
}
