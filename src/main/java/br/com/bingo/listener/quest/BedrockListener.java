package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class BedrockListener implements Listener {

    GameManager gameManager;

    public BedrockListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBedrockSneak(PlayerToggleSneakEvent event){
        if(!gameManager.isGameStarted()){return;}
        if(!gameManager.checkPlayerTeam(event.getPlayer())){return;}
        if(!event.getPlayer().getLocation().subtract(0,1,0).getBlock().getType().equals(Material.BEDROCK)){return;}
        for(Quest quest : gameManager.getAvailableQuests()){
            if(!(quest.getType() == QuestType.BEDROCK)){continue;}
            gameManager.completeQuest(event.getPlayer().getUniqueId(), quest);
            return;
        }
    }
}
