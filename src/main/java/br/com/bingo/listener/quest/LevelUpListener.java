package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class LevelUpListener implements Listener {

    GameManager gameManager;

    public LevelUpListener(GameManager gameManager) {this.gameManager = gameManager;}


    @EventHandler
    public void onLevelUp(PlayerLevelChangeEvent event){
        if(!gameManager.isGameStarted()){return;}
        if(!gameManager.checkPlayerTeam(event.getPlayer())){return;}

        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.LEVEL_UP)){continue;}
            if((int) quest.getTarget() != event.getNewLevel()){continue;}
            gameManager.completeQuest(event.getPlayer().getUniqueId(), quest);
            return;
        }
    }
}
