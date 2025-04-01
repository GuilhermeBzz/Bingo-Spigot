package br.com.bingo.listener;

import br.com.bingo.Bingo;
import br.com.bingo.rank.Ranks;
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

    private final GameManager gameManager;

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
            setPlayerDefaultState(event.getPlayer());
        } else if(gameManager.getGameStatus().equals(GameStatus.CREATED)){
            event.getPlayer().teleport(Bukkit.getWorlds().get(0).getSpawnLocation().add(0, 2, 0));
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
            event.getPlayer().getInventory().clear();
            BingoMenu.giveMenuOpener(event.getPlayer());
            PlayerProfile.givePorfileOpener(event.getPlayer());
            gameManager.paintTAB(event.getPlayer());
        } else if(gameManager.getGameStatus().equals(GameStatus.STARTED) && !gameManager.checkPlayerTeam(event.getPlayer())){
            setPlayerDefaultState(event.getPlayer());
        }
        Ranks.setPrefixAndDisplayName(event.getPlayer(), gameManager.playerTeam);
        return;
    }

    public void setPlayerDefaultState(Player player){
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation().add(0, 2, 0));
        player.setGameMode(GameMode.ADVENTURE);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        if(!PlayersStorageUtil.checkPlayerInstance(player)){
            PlayersStorageUtil.createPlayer(new PlayersData(player.getUniqueId()));
        }
        player.setFireTicks(0);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        player.setSaturation(20);
        player.getInventory().clear();
        BingoMenu.giveMenuOpener(player);
        PlayerProfile.givePorfileOpener(player);
        HashMap<UUID, PermissionAttachment> perms = new HashMap<>();
        PermissionAttachment attachment = player.addAttachment(Bingo.getInstance());
        perms.put(player.getUniqueId(), attachment);
        PermissionAttachment pperms = perms.get(player.getUniqueId());
        pperms.setPermission("bingo.endgame", false);
        player.recalculatePermissions();
    }
}
