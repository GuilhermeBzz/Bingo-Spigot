package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {

    GameManager gameManager;
    public AdvancementListener(GameManager gameManager) {this.gameManager = gameManager;}


    @EventHandler
    public void onGetEvent (PlayerAdvancementDoneEvent event){
        if(event.getAdvancement().getDisplay() == null){return;}
        if(!gameManager.isGameStarted()){return;}
        Player player = event.getPlayer();
        if(!gameManager.checkPlayerTeam(player)){return;}

        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.ADVANCEMENT)){continue;}
            if(event.getAdvancement().getDisplay().getTitle().equalsIgnoreCase((String) quest.getTarget())){
                gameManager.completeQuest(player.getUniqueId(), quest);
                return;

            }
        }

    }
}
