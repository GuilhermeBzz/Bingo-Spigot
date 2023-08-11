package br.com.bingo.kits.listeners;


import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HunterListener implements Listener {

    GameManager gameManager;
    Map<UUID, Long> compassCooldown = new HashMap<>();
    public HunterListener(GameManager gameManager){
        this.gameManager = gameManager;
    }



    @EventHandler
    public void onCompassClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)){return;}
        if(!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Hunter's Compass")){return;}

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            event.setCancelled(true);

            Player target = null;
            double distance = 0;
            if(gameManager.getGameType().equals(GameType.SOLO)){
                for(UUID uuid : gameManager.playerTeam.keySet()){
                    Player p = Bukkit.getPlayer(uuid);
                    if(p == null){continue;}
                    if(!(p.getWorld().getName().equals(player.getWorld().getName()))){continue;}
                    if(p.equals(player)){continue;}
                    if(target == null){
                        target = p;
                        distance = p.getLocation().distance(player.getLocation());
                    } else{
                        if(p.getLocation().distance(player.getLocation()) < distance){
                            target = p;
                            distance = p.getLocation().distance(player.getLocation());
                        }
                    }
                }
            }
            else {
                TeamType team = gameManager.playerTeam.get(player.getUniqueId());
                for(UUID uuid : gameManager.playerTeam.keySet()){
                    Player p = Bukkit.getPlayer(uuid);
                    if(p == null){continue;}
                    if(!(p.getWorld().getName().equals(player.getWorld().getName()))){continue;}
                    if(p.equals(player)){continue;}
                    if(gameManager.getPlayerTeam(p).equals(team)){continue;}
                    if(target == null){
                        target = p;
                        distance = p.getLocation().distance(player.getLocation());
                    } else{
                        if(p.getLocation().distance(player.getLocation()) < distance){
                            target = p;
                            distance = p.getLocation().distance(player.getLocation());
                        }
                    }
                }
            }
            if(target == null){
                player.sendMessage(ChatColor.RED + "Nenhum jogador encontrado!");
                return;
            }

            if(!compassCooldown.containsKey(player.getUniqueId())){
                compassCooldown.put(player.getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = compassCooldown.getOrDefault(player.getUniqueId(), 0L);
            long cooldown = 20*1000;
            if(currentTime - lastUsedTime < cooldown){
                player.sendMessage(ChatColor.RED + "Aguarde " + ((cooldown/1000)-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                return;
            }
            compassCooldown.put(player.getUniqueId(), currentTime);
            player.sendMessage(ChatColor.GREEN + "Bussola apontando para um jogador");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 2));
            player.setCompassTarget(target.getLocation());
            target.sendMessage(ChatColor.RED + "Um jogador está te caçando! Ele está a " + (int)distance + " blocos de distância");
            return;
        }
    }
}
