package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaoListener implements Listener {
    Map<UUID, Long> cooldowns = new HashMap<>();
    GameManager gameManager;

    public PaoListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onAppleUse(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Maça Especial"))return;
        if(event.getAction().name().contains("RIGHT")) {
            if (!cooldowns.containsKey(event.getPlayer().getUniqueId())) {
                cooldowns.put(event.getPlayer().getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = cooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
            long cooldown = 5*60 * 1000;
            if (currentTime - lastUsedTime < cooldown) {
                event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (5*60 - (((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                return;
            }
            cooldowns.put(event.getPlayer().getUniqueId(), currentTime);
            TeamType playerTeam = gameManager.getPlayerTeam(event.getPlayer());
            for(UUID uuid : gameManager.playerTeam.keySet()){
                if(gameManager.playerTeam.get(uuid) == playerTeam){
                    Player target = Bukkit.getPlayer(uuid);
                    if(target == null)continue;
                    if(target.getFoodLevel() >= 15){
                        target.setFoodLevel(20);
                    }
                    else{
                        target.setFoodLevel(target.getFoodLevel() + 5);
                    }
                    target.sendMessage(ChatColor.GREEN + "Seu companheiro te alimentou!");
                }
            }

        }
    }

    @EventHandler
    public void onBreadUse(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Pão Especial"))return;
        if(event.getAction().name().contains("RIGHT")) {
            if (!cooldowns.containsKey(event.getPlayer().getUniqueId())) {
                cooldowns.put(event.getPlayer().getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = cooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
            long cooldown = 5*60 * 1000;
            if (currentTime - lastUsedTime < cooldown) {
                event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (5*60 - (((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                return;
            }
            cooldowns.put(event.getPlayer().getUniqueId(), currentTime);
            TeamType playerTeam = gameManager.getPlayerTeam(event.getPlayer());
            for(UUID uuid : gameManager.playerTeam.keySet()){
                if(gameManager.playerTeam.get(uuid) == playerTeam){
                    Player target = Bukkit.getPlayer(uuid);
                    if(target == null)continue;
                    if(target.getFoodLevel() >= 15){
                        target.setFoodLevel(20);
                    }
                    else{
                        target.setFoodLevel(target.getFoodLevel() + 5);
                    }
                    if(target.getHealth() >= 15){
                        target.setHealth(20);
                    }
                    else{
                        target.setHealth(target.getHealth() + 5);
                    }
                    target.sendMessage(ChatColor.GREEN + "Seu companheiro te alimentou e te curou!");
                }
            }

        }
    }



    @EventHandler
    public void cancelConsume(PlayerItemConsumeEvent event){
        if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Maça Especial")){
            event.setCancelled(true);
            return;
        } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Pão Especial")){
            event.setCancelled(true);
            return;
        }
    }
}
