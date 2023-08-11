package br.com.bingo.listener;

import br.com.bingo.Bingo;
import br.com.bingo.rank.models.players.PlayersData;
import br.com.bingo.rank.profile.PlayerProfile;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import br.com.bingo.ui.BingoMenu;
import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.UUID;

public class JoinListener implements Listener {

    private GameManager gameManager;

    public JoinListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){

        if(gameManager.isGameStarted()){
            gameManager.paintTAB(event.getPlayer());
            gameManager.updateScoreboard();
            gameManager.barTimer.addPlayerToBarTimer(event.getPlayer());
        }

        World world = event.getPlayer().getWorld();

        if(gameManager.getGameStatus().equals(GameStatus.NONE)){
            event.getPlayer().teleport(Bukkit.getWorlds().get(0).getSpawnLocation().add(0, 1, 0));
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
            for (PotionEffect effect : event.getPlayer().getActivePotionEffects()) {
                event.getPlayer().removePotionEffect(effect.getType());
            }
            if(!PlayersStorageUtil.checkPlayerInstance(event.getPlayer())){
                PlayersStorageUtil.createPlayer(new PlayersData(event.getPlayer().getUniqueId()));
            }
            event.getPlayer().getInventory().clear();
            BingoMenu.giveMenuOpener(event.getPlayer());
            PlayerProfile.givePorfileOpener(event.getPlayer());
            Player player = event.getPlayer();
            HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
            PermissionAttachment attachment = player.addAttachment(Bingo.getInstance());
            perms.put(player.getUniqueId(), attachment);
            PermissionAttachment pperms = perms.get(player.getUniqueId());
            pperms.setPermission("bingo.endgame", false);
            player.recalculatePermissions();
            return;
        }
        if(gameManager.getGameStatus().equals(GameStatus.CREATED)){
            event.getPlayer().teleport(Bukkit.getWorlds().get(0).getSpawnLocation().add(0, 1, 0));
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
            event.getPlayer().getInventory().clear();
            BingoMenu.giveMenuOpener(event.getPlayer());
            PlayerProfile.givePorfileOpener(event.getPlayer());
            gameManager.paintTAB(event.getPlayer());
            return;
        }



    }
}
