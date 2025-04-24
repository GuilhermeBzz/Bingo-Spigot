package br.com.bingo.quests.domination.listener;

import br.com.bingo.Bingo;
import br.com.bingo.game.GameManager;
import br.com.bingo.quests.Quest;
import br.com.bingo.quests.QuestType;
import br.com.bingo.quests.domination.Domination;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class DominationQuestListener implements Listener {

    GameManager gameManager;
    private boolean isDominating = false;
    private final List<BukkitTask> scheduledTasks = new ArrayList<>();
    private final List<BukkitTask> particleTasks = new ArrayList<>();


    TeamType dominatingTeam = null;

    public DominationQuestListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @EventHandler
    public void onClickTotemEvent(PlayerInteractEvent event){
        if(!gameManager.isGameStarted()) return;
        if(gameManager.dominationEnded) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(event.getClickedBlock() == null) return;
        if(!event.getClickedBlock().getType().equals(Material.BLUE_CONCRETE)
                && !event.getClickedBlock().getType().equals(Material.RED_CONCRETE)
                && !event.getClickedBlock().getType().equals(Material.GRAY_CONCRETE)
        ) return;
        Location blockLocation = event.getClickedBlock().getLocation();
        Location dominationLocation = gameManager.dominationLocation;

        if(!isTotemInDomination(blockLocation.clone(), dominationLocation.clone())) return;
        Player player = event.getPlayer();
        TeamType playerTeam = gameManager.getPlayerTeam(player);
        if(dominatingTeam != null && dominatingTeam.equals(playerTeam)) return;

        dominate(player, playerTeam);

    }

    public void dominate(Player player, TeamType playerTeam){

        if(isDominating){
            cancelScheduledTasks();
        }

        Location baseLocation = gameManager.dominationLocation;
        isDominating = true;
        dominatingTeam = playerTeam;

        Domination.generateTotem(gameManager.dominationLocation);

        Material blockType = Material.GRAY_CONCRETE;
        Particle particle = Particle.PORTAL;
        if(dominatingTeam.equals(TeamType.TEAM_RED)){
            blockType = Material.RED_CONCRETE;
            particle = Particle.FLAME;
        } else if(dominatingTeam.equals(TeamType.TEAM_BLUE)){
            blockType = Material.BLUE_CONCRETE;
            particle = Particle.SOUL_FIRE_FLAME;
        }

        baseLocation.getBlock().setType(blockType);
        registerScheduledTasks(baseLocation, blockType, player, particle);
    }

    private boolean isTotemInDomination(Location blockLocation, Location dominationLocation){
        return blockLocation.getBlockX() == dominationLocation.getBlockX()
                && blockLocation.getBlockZ() == dominationLocation.getBlockZ()
                && (blockLocation.getBlockY() == dominationLocation.getBlockY()
                || blockLocation.getBlockY() == dominationLocation.getBlockY() + 1
                || blockLocation.getBlockY() == dominationLocation.getBlockY() + 2
                || blockLocation.getBlockY() == dominationLocation.getBlockY() + 3
        );
    }

    public void registerScheduledTasks(Location location, Material blockType, Player player, Particle particle){

        for (int i = 1; i <= 3; i++) {
            int finalI = i;

            Location targetLocation = location.clone().add(0, finalI, 0);

            BukkitTask particleTask = Bukkit.getScheduler().runTaskTimer(Bingo.getInstance(), () -> {
                targetLocation.getWorld().spawnParticle(
                        particle,
                        targetLocation.clone().add(0.5, 0.5, 0.5), // centro do bloco
                        25, // número de partículas
                        1.0, 1.0, 1.0, // offset X, Y, Z — quanto elas se espalham
                        0.02 // velocidade (opcional)
                );
            }, 0, 10);
            particleTasks.add(particleTask);

            int delay = i * 20 * 20;
            scheduledTasks.add(Bukkit.getScheduler().runTaskLater(Bingo.getInstance(), () -> {
                targetLocation.getBlock().setType(blockType);
                particleTask.cancel(); // Para as partículas quando o bloco for trocado
                particleTasks.remove(particleTask);
                if (finalI == 3) {
                    gameManager.dominationEnded = true;
                    completeQuest(player);
                }
            }, delay));
        }

    }


    public void completeQuest(Player player){
        for(Quest quest : gameManager.getAvailableQuests()){
            if(quest.getType() != QuestType.DOMINATION) continue;
            gameManager.completeQuest(player.getUniqueId(), quest);
            break;
        }
    }


    public void cancelScheduledTasks() {
        for (BukkitTask task : scheduledTasks) {
            task.cancel();
        }
        scheduledTasks.clear();

        for (BukkitTask task : particleTasks) {
            task.cancel();
        }
        particleTasks.clear();
    }

}
