package br.com.bingo.listener;

import br.com.bingo.rank.profile.PlayerProfile;
import br.com.bingo.ui.BingoMenu;
import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class SpawnListener implements Listener{

    GameManager gameManager;

    public SpawnListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void  onSpawnEvent(PlayerRespawnEvent event){
        if(!gameManager.isGameStarted()){
            for (PotionEffect effect : event.getPlayer().getActivePotionEffects()) {
                event.getPlayer().removePotionEffect(effect.getType());
            }
            BingoMenu.giveMenuOpener(event.getPlayer());
            PlayerProfile.givePorfileOpener(event.getPlayer());
        }
        if(gameManager.getGameStatus().equals(GameStatus.CREATED)){return;}
        if(!gameManager.checkPlayerTeam(event.getPlayer())){return;}


        if(event.getPlayer().getBedSpawnLocation() == null){
            event.getPlayer().setBedSpawnLocation(Bukkit.getWorld("gameWorld").getSpawnLocation(), true);
            event.setRespawnLocation(event.getPlayer().getBedSpawnLocation());
        }


        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Cartela do Bingo");
        item.setItemMeta(meta);
        event.getPlayer().getInventory().addItem(item);

        if(!gameManager.kit) return;


        if(!(gameManager.getAvailableQuests().size() > gameManager.questLeftWhenChange)){
            gameManager.playerKit.get(event.getPlayer().getUniqueId()).getKit().onRespawn(event.getPlayer());
            return;
        }
        gameManager.playerKit.get(event.getPlayer().getUniqueId()).getKit().onStartRespawn(event.getPlayer());
        return;
    }
}
