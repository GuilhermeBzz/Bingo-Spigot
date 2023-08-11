package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class ShearListener implements Listener {

    GameManager gameManager;

    public ShearListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onShearEvent(PlayerShearEntityEvent event){
        if(!gameManager.isGameStarted()){return;}
        if(!gameManager.checkPlayerTeam(event.getPlayer())){return;}
        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.SHEAR)){continue;}
            if(event.getEntity().getType().equals(quest.getTarget()) ){
                gameManager.completeQuest(event.getPlayer().getUniqueId(), quest);
                return;
            }
        }

    }

}
