package br.com.bingo.listener;

import br.com.bingo.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezePlayersListener implements Listener {

    private final GameManager gameManager;

    public FreezePlayersListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerFreeze(PlayerMoveEvent event) {
        if (!gameManager.freezePlayers) return;
        if(event.getFrom().equals(event.getTo())) return;
        if(event.getFrom().getX() == event.getTo().getX()
                && event.getFrom().getZ() == event.getTo().getZ()) return;
        //Bukkit.getLogger().info("Player " + event.getPlayer().getName() + " is frozen.");
        Location start = event.getFrom();
        Location end = event.getTo();
        end.setX(start.getX());
        end.setZ(start.getZ());
        event.setTo(end);
    }
}
