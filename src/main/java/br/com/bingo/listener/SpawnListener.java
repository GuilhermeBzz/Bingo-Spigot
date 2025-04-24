package br.com.bingo.listener;

import br.com.bingo.rank.profile.PlayerProfile;
import br.com.bingo.ui.BingoMenu;
import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;

public class SpawnListener implements Listener{

    GameManager gameManager;

    public SpawnListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void  onSpawnEvent(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        if(!gameManager.isGameStarted()){
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            BingoMenu.giveMenuOpener(player);
            PlayerProfile.givePorfileOpener(player);
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());

        }
        if(gameManager.getGameStatus().equals(GameStatus.CREATED)){return;}
        if(!gameManager.checkPlayerTeam(player)){return;}


        Location spawnLocation = player.getRespawnLocation();
        Location respawnLocation = Objects.requireNonNull(Bukkit.getWorld("gameWorld")).getSpawnLocation();

        if(spawnLocation == null){
            player.setRespawnLocation(respawnLocation, true);
            event.setRespawnLocation(respawnLocation);
        }


        if(respawnLocation.add(0,-1,0).getBlock().getType().equals(Material.AIR)){
            respawnLocation.add(0,-1,0).getBlock().setType(Material.STONE);
        }

        ItemStack item = new ItemStack(Material.PAPER);
        gameManager.giveStarterKit(player);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Cartela do Bingo");
        meta.setCustomModelData(777);
        item.setItemMeta(meta);
        player.getInventory().addItem(item);

        if(!gameManager.kit) return;


        if(!(gameManager.getAvailableQuests().size() > gameManager.questLeftWhenChange)){
            gameManager.playerKit.get(player.getUniqueId()).getKit().onRespawn(player);
            return;
        }
        gameManager.playerKit.get(player.getUniqueId()).getKit().onStartRespawn(player);
        return;
    }
}
