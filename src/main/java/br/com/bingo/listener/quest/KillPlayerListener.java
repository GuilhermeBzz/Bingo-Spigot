package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import br.com.bingo.team.TeamType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillPlayerListener implements Listener {


    GameManager gameManager;

    public KillPlayerListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event){
        if(event.getEntity().getKiller() == null){return;}
        if(!gameManager.isGameStarted()){return;}
        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.KILL_PLAYER)){continue;}
            if(gameManager.getGameType().equals(GameType.SOLO)){
                gameManager.completeQuest(event.getEntity().getKiller().getUniqueId(), quest);
                return;
            }
            else {
                TeamType killerTeam = gameManager.getPlayerTeam(event.getEntity().getKiller());
                TeamType killedTeam = gameManager.getPlayerTeam(event.getEntity());
                if(killedTeam.equals(killerTeam)){return;}
                gameManager.completeQuest(event.getEntity().getKiller().getUniqueId(), quest);
                return;
            }
        }
    }


}
