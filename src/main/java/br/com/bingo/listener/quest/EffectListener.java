package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class EffectListener implements Listener {

    private GameManager gameManager;

    public EffectListener(GameManager gameManager) {this.gameManager = gameManager;}


    @EventHandler
    public void playerGetEffect(EntityPotionEffectEvent event){
        if(!gameManager.isGameStarted()){return;}
        if(!(event.getEntity() instanceof Player)){return;}
        Player player = (Player) event.getEntity();
        if(!gameManager.checkPlayerTeam(player)){return;}
        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.GET_EFFECT)){continue;}
            if(event.getModifiedType().equals(quest.getTarget())){
                gameManager.completeQuest(player.getUniqueId(), quest);
                return;
            }
        }

    }
}
