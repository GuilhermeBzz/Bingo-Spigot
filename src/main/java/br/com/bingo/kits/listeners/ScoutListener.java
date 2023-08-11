package br.com.bingo.kits.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoutListener implements Listener {

    Map<UUID, Long> cooldowns = new HashMap<>();


    @EventHandler
    public void onBoostUse(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Boost") && !event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Ultra Boost"))return;

        if(event.getAction().name().contains("RIGHT")) {
            if (!cooldowns.containsKey(event.getPlayer().getUniqueId())) {
                cooldowns.put(event.getPlayer().getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = cooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
            long cooldown = 40 * 1000;
            if (currentTime - lastUsedTime < cooldown) {
                event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (40 - (((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                return;
            }
            cooldowns.put(event.getPlayer().getUniqueId(), currentTime);
            if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Boost")){
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1));
            } else{
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 3));
            }
            event.getPlayer().sendMessage(ChatColor.GREEN + "Você usou o boost!");
        }
    }
}
