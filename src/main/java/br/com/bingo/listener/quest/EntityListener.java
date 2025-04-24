package br.com.bingo.listener.quest;

import br.com.bingo.quests.boss.entities.EntityBoss;
import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_21_R4.entity.CraftLivingEntity;
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
        if(entity.getCustomName() != null && entity.getCustomName().equalsIgnoreCase("necro")){
            event.setDroppedExp(0);
            event.getDrops().clear();
        }


        if(player == null){return;}
        if(!gameManager.checkPlayerTeam(player)){return;}

        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.KILL_MOB || quest.getType() == QuestType.KILL_BOSS)){continue;}

            if(quest.getType().equals(QuestType.KILL_MOB)){
                if(entity.getType().equals(quest.getTarget()) ){
                    if(entity.getCustomName() != null && entity.getCustomName().equalsIgnoreCase("necro")) continue;
                    gameManager.completeQuest(player.getUniqueId(), quest);
                    return;
                }
            } else {
                if(entity.getType() == EntityType.ZOMBIE && entity instanceof CraftLivingEntity craftEntity){
                    if(craftEntity.getHandle() instanceof EntityBoss){
                        gameManager.completeQuest(player.getUniqueId(), quest);
                        return;
                    }
                }
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
    public void onSpawnEvent(CreatureSpawnEvent event){
        if(!gameManager.isGameStarted()){return;}
        EntityType eventEntityType = event.getEntity().getType();
        if(!eventEntityType.equals(EntityType.WARDEN) && !eventEntityType.equals(EntityType.ALLAY)){return;}
        for(Quest quest : gameManager.getAvailableQuests()){
            if(quest.getType() != QuestType.SPAWN){continue;}
            Location location = event.getLocation();
            Player nearestPlayer = getNearestPlayer(location);
            if(nearestPlayer == null){return;}
            if(quest.getTarget().equals(EntityType.WARDEN)){
                gameManager.completeQuest(nearestPlayer.getUniqueId(), quest);
                return;
            } else if(quest.getTarget().equals(EntityType.ALLAY)){
                if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.DUPLICATION)){
                    gameManager.completeQuest(nearestPlayer.getUniqueId(), quest);
                    return;
                }
            }
            return;
        }
    }

    @EventHandler
    public void dyeSheepEvent(SheepDyeWoolEvent event){
        if(!gameManager.isGameStarted()){return;}
        Player player = event.getPlayer();
        if(!gameManager.checkPlayerTeam(player)){return;}
        for(Quest quest : gameManager.getAvailableQuests()){
            if(quest.getType() != QuestType.DYE){continue;}
            gameManager.completeQuest(player.getUniqueId(), quest);
            return;
        }
    }

    public Player getNearestPlayer(Location location){
        Player nearestPlayer = null;
        double nearestDistance = 9999999;
        for(UUID uuid : gameManager.playerTeam.keySet()){
            Location playerLocation = Bukkit.getPlayer(uuid).getLocation();
            if(playerLocation.distance(location) < nearestDistance){
                nearestPlayer = Bukkit.getPlayer(uuid);
                nearestDistance = playerLocation.distance(location);
            }
        }
        return nearestPlayer;
    }


}
