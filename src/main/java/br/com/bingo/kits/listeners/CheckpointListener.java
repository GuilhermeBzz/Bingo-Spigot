package br.com.bingo.kits.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CheckpointListener implements Listener {

    Map<UUID, Long> placecooldowns = new HashMap<>();
    Map<UUID, Location> checkpoints = new HashMap<>();


    @EventHandler
    public void onPlace(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Checkpoint")){
            event.setCancelled(true);
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (!placecooldowns.containsKey(event.getPlayer().getUniqueId())) {
                    placecooldowns.put(event.getPlayer().getUniqueId(), 0L);
                }
                long currentTime = System.currentTimeMillis();
                long lastUsedTime = placecooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
                long cooldown = 2*60 * 1000;
                if (currentTime - lastUsedTime < cooldown) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (2*60 - (((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                    return;
                }
                placecooldowns.put(event.getPlayer().getUniqueId(), currentTime);
                Location blockLocation =  event.getClickedBlock().getLocation();
                blockLocation.add(0,1,0);
                blockLocation.getBlock().setType(Material.NETHER_BRICK_FENCE);
                if(checkpoints.containsKey(event.getPlayer().getUniqueId())){
                    checkpoints.get(event.getPlayer().getUniqueId()).getBlock().setType(Material.AIR);
                    checkpoints.remove(event.getPlayer().getUniqueId());
                }
                checkpoints.put(event.getPlayer().getUniqueId(), blockLocation);

            }
        } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Checkpoint Pro")){
            event.setCancelled(true);
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (!placecooldowns.containsKey(event.getPlayer().getUniqueId())) {
                    placecooldowns.put(event.getPlayer().getUniqueId(), 0L);
                }
                long currentTime = System.currentTimeMillis();
                long lastUsedTime = placecooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
                long cooldown = 60 * 1000;
                if (currentTime - lastUsedTime < cooldown) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (60 - (((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                    return;
                }
                placecooldowns.put(event.getPlayer().getUniqueId(), currentTime);
                Location blockLocation =  event.getClickedBlock().getLocation();
                blockLocation.add(0,1,0);
                blockLocation.getBlock().setType(Material.NETHER_BRICK_FENCE);
                if(checkpoints.containsKey(event.getPlayer().getUniqueId())){
                    checkpoints.get(event.getPlayer().getUniqueId()).getBlock().setType(Material.AIR);
                    checkpoints.remove(event.getPlayer().getUniqueId());
                }
                checkpoints.put(event.getPlayer().getUniqueId(), blockLocation);

            }
        }

    }

    @EventHandler
    public void CancelPlace(BlockPlaceEvent event){
        if(event.getBlock().getType().equals(Material.NETHER_BRICK_FENCE)){
            if(!event.getPlayer().getItemInUse().hasItemMeta()) return;
            if(!event.getPlayer().getItemInUse().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Checkpoint") && !event.getPlayer().getItemInUse().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Checkpoint Pro")) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void teleportToCheckpoint(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Checkpoint") && !event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Checkpoint Pro")) return;
        if(event.getAction().name().contains("LEFT")){
            if(checkpoints.containsKey(event.getPlayer().getUniqueId())){
                event.getPlayer().teleport(checkpoints.get(event.getPlayer().getUniqueId()));
                checkpoints.get(event.getPlayer().getUniqueId()).getBlock().setType(Material.AIR);
                checkpoints.remove(event.getPlayer().getUniqueId());
            } else{
                event.getPlayer().sendMessage(ChatColor.RED + "Você não tem nenhum checkpoint");
            }
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(event.getBlock().getType().equals(Material.NETHER_BRICK_FENCE)){
            for(UUID uuid : checkpoints.keySet()){
                if(checkpoints.get(uuid).equals(event.getBlock().getLocation())){
                    checkpoints.remove(uuid);
                    Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED + "Seu checkpoint foi destruido");
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    break;
                }
            }
        }
    }
}
