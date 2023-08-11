package br.com.bingo.kits.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MinerListener implements Listener {

    Map<UUID, Long> compassCooldown = new HashMap<>();

    @EventHandler
    public void onLeadEvent(PlayerLeashEntityEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand() == null){return;}
        if(!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
        String name = Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
        if(name.equals(ChatColor.GOLD + "Escape Rope")){
            event.setCancelled(true);
            return;
        }
    }
    @EventHandler
    public void onLeadToo(PlayerInteractAtEntityEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand() == null){return;}
        if(!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
        String name = Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getDisplayName();
        if(name.equals(ChatColor.GOLD + "Escape Rope")){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onEscapeEvent(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getItem().getItemMeta() == null) return;
        String name = event.getItem().getItemMeta().getDisplayName();
        if(event.getAction().name().contains("RIGHT") && event.getItem().getType().equals(Material.LEAD)){
            event.setCancelled(true);
            if(name.equals(ChatColor.GOLD + "Escape Rope")){
                Player player = event.getPlayer();
                if(player.getWorld().getEnvironment().equals(World.Environment.NETHER)){
                    player.sendMessage(ChatColor.RED + "Você não pode usar a corda de escape no nether!");
                    return;
                }

                if(!compassCooldown.containsKey(player.getUniqueId())){
                    compassCooldown.put(player.getUniqueId(), 0L);
                }
                long currentTime = System.currentTimeMillis();
                long lastUsedTime = compassCooldown.getOrDefault(player.getUniqueId(), 0L);
                long cooldown = 60000;
                if(currentTime - lastUsedTime < cooldown){
                    player.sendMessage(ChatColor.RED + "Aguarde " + (60-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                    return;
                }
                compassCooldown.put(player.getUniqueId(), currentTime);


                int x = event.getPlayer().getLocation().getBlockX();
                int z = event.getPlayer().getLocation().getBlockZ();
                int y = event.getPlayer().getWorld().getHighestBlockYAt(x, z) + 1;
                event.getPlayer().teleport(event.getPlayer().getWorld().getBlockAt(x, y, z).getLocation());
                event.getPlayer().sendMessage(ChatColor.GREEN + "Você usou sua corda de escape!");
            }
        }

    }
}
