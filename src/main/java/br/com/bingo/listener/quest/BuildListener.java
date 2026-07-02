package br.com.bingo.listener.quest;

import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BuildListener implements Listener {

    GameManager gameManager;

    public BuildListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onFlintAndSteelUse(PlayerInteractEvent event){
        if(!gameManager.isGameStarted()){return;}
        if(!gameManager.checkPlayerTeam(event.getPlayer())){return;}
        if(event.getItem() == null){return;}
        if(event.getClickedBlock() == null){return;}
        if(!event.getItem().getType().equals(Material.FLINT_AND_STEEL)) return;
        for(Quest quest : gameManager.getAvailableQuests()){
            if((quest.getType() != QuestType.BUILD_STRUCTURE)){continue;}
            if(quest.equals(Quest.BUILD_HEROBRINE_TOTEM) && checkTotem(event)){
                Location location = event.getClickedBlock().getLocation();
                location.getWorld().strikeLightning(location);
                location.getWorld().playSound(location, org.bukkit.Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                gameManager.completeQuest(event.getPlayer().getUniqueId(), quest);
                return;
            }
        }
    }

    public boolean checkTotem(PlayerInteractEvent event){
        Bukkit.getLogger().info("check totem");
        Block block = event.getClickedBlock();
        if(block == null) return false;

        if(!block.getType().equals(Material.NETHERRACK)) return false;
        Location location = block.getLocation();
        if(!location.add(0,-1,0).getBlock().getType().equals(Material.NETHERRACK)) return false;
        if(!location.add(0,-1,0).getBlock().getType().equals(Material.GOLD_BLOCK)) return false;
        return location.add(0, -1, 0).getBlock().getType().equals(Material.GOLD_BLOCK);
    }
}
