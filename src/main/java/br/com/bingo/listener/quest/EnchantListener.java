package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantListener implements Listener {

    private GameManager gameManager;


    public EnchantListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onEnchantEvent(EnchantItemEvent event){
        if(!gameManager.isGameStarted()){return;}
        if(!gameManager.checkPlayerTeam(event.getEnchanter())){return;}

        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.ENCHANT)){continue;}
            gameManager.completeQuest(event.getEnchanter().getUniqueId(), quest);
            return;
        }
    }
}

