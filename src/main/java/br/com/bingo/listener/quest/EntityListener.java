package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.util.UUID;

public class EntityListener implements Listener {

    GameManager gameManager;
    public EntityListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event){
        if(!gameManager.isGameStarted()){return;}
        Entity entity = event.getEntity();
        if(!(entity instanceof Mob)){return;}
        Player player = event.getEntity().getKiller();

        if(player == null){return;}
        if(!gameManager.checkPlayerTeam(player)){return;}

        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.KILL_MOB)){continue;}
            if(entity.getType().equals(quest.getTarget()) ){
                gameManager.completeQuest(player.getUniqueId(), quest);
                return;
            }
        }
    }

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event){
        if(!gameManager.isGameStarted()){return;}
        Player player = (Player) event.getBreeder();
        if(player == null){return;}
        if(!gameManager.checkPlayerTeam(player)){return;}
        Entity entity = event.getEntity();
        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.BREED_MOB)){continue;}
            if(entity.getType().equals(quest.getTarget())){
                gameManager.completeQuest(player.getUniqueId(), quest);
                return;
            }
        }
    }

    @EventHandler
    public void onEntityTame(EntityTameEvent event){
        if(!gameManager.isGameStarted()){return;}
        Player player = (Player) event.getOwner();
        if(!gameManager.checkPlayerTeam(player)){return;}
        Entity entity = event.getEntity();
        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.TAME_MOB)){continue;}
            if(entity.getType().equals(quest.getTarget())){
                gameManager.completeQuest(player.getUniqueId(), quest);
                return;
            }
        }
    }

    @EventHandler
    public void onSpawnEvent(EntitySpawnEvent event){
        if(!event.getEntity().getType().equals(EntityType.WARDEN)){return;}
        if(!gameManager.isGameStarted()){return;}
        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.WARDEN)){continue;}
            Location location = event.getLocation();
            Player nearestPlayer = null;
            double nearestDistance = 9999999;
            for(UUID uuid : gameManager.playerTeam.keySet()){
                Location playerLocation = Bukkit.getPlayer(uuid).getLocation();
                if(playerLocation.distance(location) < nearestDistance){
                    nearestPlayer = Bukkit.getPlayer(uuid);
                    nearestDistance = playerLocation.distance(location);
                }
            }
            gameManager.completeQuest(nearestPlayer.getUniqueId(), quest);
            return;
        }
    }

}
