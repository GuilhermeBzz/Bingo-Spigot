package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DieListener implements Listener {

    GameManager gameManager;

    public DieListener(GameManager gameManager) {this.gameManager = gameManager;}

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(!gameManager.isGameStarted()){return;}
        Player player = event.getEntity();
        if(!gameManager.checkPlayerTeam(player)){return;}
        if(player.getBedSpawnLocation() == null){
            player.setBedSpawnLocation(player.getWorld().getSpawnLocation(), true);
        }

        EntityDamageEvent.DamageCause deathCause = null;
        try{
            deathCause = player.getLastDamageCause().getCause();
        }
        catch (Exception e){
            return;
        }

        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.DIE)){continue;}
            if(quest.getTarget() == EntityDamageEvent.DamageCause.FIRE){
                if(deathCause.equals(EntityDamageEvent.DamageCause.FIRE) || deathCause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)){
                    gameManager.completeQuest(player.getUniqueId(), quest);
                    return;
                }
            }
            if(deathCause.equals(quest.getTarget())){
                gameManager.completeQuest(player.getUniqueId(), quest);
                return;
            }
        }

    }
}
