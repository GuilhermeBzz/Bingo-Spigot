package br.com.bingo.kits.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EndermageListener implements Listener {

    Map<UUID, Long> cooldowns = new HashMap<>();
    ArrayList<UUID> endermage = new ArrayList<>();

    @EventHandler
    public void onThrow(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() == null) return;
        if(event.getItem().getItemMeta() == null) return;
        if(event.getItem().getItemMeta().getDisplayName() == null) return;
        if(!event.getAction().name().contains("RIGHT")) return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(org.bukkit.ChatColor.GOLD + "Endermage Pearl")) return;
        event.setCancelled(true);

        if(!cooldowns.containsKey(player.getUniqueId())){
            cooldowns.put(player.getUniqueId(), 0L);
        }
        long currentTime = System.currentTimeMillis();
        long lastUsedTime = cooldowns.getOrDefault(player.getUniqueId(), 0L);
        long cooldown = 60*1000;
        if(currentTime - lastUsedTime < cooldown){
            player.sendMessage(ChatColor.RED + "Aguarde " + ((cooldown/1000)-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
            return;
        }
        if(!endermage.contains(player.getUniqueId())) endermage.add(player.getUniqueId());
        cooldowns.put(player.getUniqueId(), currentTime);
        event.getPlayer().launchProjectile(org.bukkit.entity.EnderPearl.class);

    }

    @EventHandler
    public void cancelDamage(EntityDamageEvent event){

        if(!(event.getEntity() instanceof Player)) return;
        if(!endermage.contains(event.getEntity().getUniqueId())) return;
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) event.setDamage(0);
        endermage.remove(event.getEntity().getUniqueId());
        return;
    }

}
