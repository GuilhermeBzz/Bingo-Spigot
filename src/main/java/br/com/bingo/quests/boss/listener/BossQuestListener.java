package br.com.bingo.quests.boss.listener;

import br.com.bingo.Bingo;
import br.com.bingo.quests.boss.entities.EntityBoss;
import br.com.bingo.game.GameManager;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BossQuestListener implements Listener {

    GameManager gameManager;
    public BossQuestListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onClickTotemEvent(PlayerInteractEvent event){
        if(!gameManager.isGameStarted()) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(event.getClickedBlock() == null) return;
        if(!event.getClickedBlock().getType().equals(Material.REINFORCED_DEEPSLATE)) return;
        Location blockLocation = event.getClickedBlock().getLocation();
        Location bossFightLocation = gameManager.bossFightLocation.clone();

        boolean clickedTotem = blockLocation.getBlockX() == bossFightLocation.getBlockX()
                && blockLocation.getBlockZ() == bossFightLocation.getBlockZ()
                && (blockLocation.getBlockY() == bossFightLocation.getBlockY()
                || blockLocation.getBlockY() == bossFightLocation.getBlockY() + 1
                || blockLocation.getBlockY() == bossFightLocation.getBlockY() + 2
        );

        if(clickedTotem){
            int seconds = 3;

            Location totemLocation = bossFightLocation.clone();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
                @Override
                public void run(){
                    countDownAndSpawn(totemLocation, seconds);
                }
            }, 20L);

        }
    }

    public void countDownAndSpawn(Location location, int seconds){
        World world = location.getWorld();
        if(seconds == 0 && !gameManager.bossSpawned){
            gameManager.bossSpawned = true;
            location.add(0,-1,0);
            EntityBoss boss = new EntityBoss(location);
            boss.spawnBoss(location);
            world.spawnParticle(Particle.EXPLOSION, location, 1);
            world.playSound(location, Sound.ENTITY_WITHER_SPAWN, 1, 1);
        } else{
            location.getBlock().setType(Material.AIR);
            world.spawnParticle(Particle.PORTAL, location, 1);
            world.playSound(location, Sound.BLOCK_STONE_BREAK, 1, 1);

            Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
                @Override
                public void run(){
                    countDownAndSpawn(location.add(0,1,0),seconds -1);
                }
            }, 20L);
        }
    }
}
