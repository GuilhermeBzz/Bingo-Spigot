package br.com.bingo.kits.listeners;


import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SurvivorListener implements Listener {

    Map<UUID, Long> cooldowns = new HashMap<>();
    Map<UUID, Long> cooldowns2 = new HashMap<>();


    @EventHandler
    public void onAppleUse(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Special Apple"))return;
        if(event.getAction().name().contains("RIGHT")){
            if(!cooldowns.containsKey(event.getPlayer().getUniqueId())){
                cooldowns.put(event.getPlayer().getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = cooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
            long cooldown = 3*60 *1000;
            if(currentTime - lastUsedTime < cooldown){
                event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (180-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                return;
            }
            cooldowns.put(event.getPlayer().getUniqueId(), currentTime);
            event.getPlayer().setHealth(20);
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setSaturation(20);
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20*15, 0));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*10, 5));
            event.getPlayer().sendMessage(ChatColor.GREEN + "Você usou sua maçã especial!");
        }
    }

    @EventHandler
    public void onBeefUse(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Special Beef")) return;
        if(event.getAction().name().contains("RIGHT")){
            if(!cooldowns2.containsKey(event.getPlayer().getUniqueId())){
                cooldowns2.put(event.getPlayer().getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = cooldowns2.getOrDefault(event.getPlayer().getUniqueId(), 0L);
            long cooldown = 60 *1000;
            if(currentTime - lastUsedTime < cooldown){
                event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (60-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                return;
            }
            cooldowns2.put(event.getPlayer().getUniqueId(), currentTime);
            if(event.getPlayer().getHealth() + 5 <= 20){
                event.getPlayer().setHealth(event.getPlayer().getHealth() + 5);
            } else{
                event.getPlayer().setHealth(20);
            }
            if(event.getPlayer().getFoodLevel() + 5 <= 20){
                event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 5);
            } else{
                event.getPlayer().setFoodLevel(20);
            }
            if(event.getPlayer().getSaturation() + 5 <=20){
                event.getPlayer().setSaturation(event.getPlayer().getSaturation() + 5);
            } else{
                event.getPlayer().setSaturation(20);
            }

            event.getPlayer().sendMessage(ChatColor.GREEN + "Você usou sua carne especial!");
        }
    }


    @EventHandler
    public void cancelConsume(PlayerItemConsumeEvent event){
        if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Special Apple")){
            event.setCancelled(true);
            return;
        } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Special Beef")){
            event.setCancelled(true);
            return;
        }
    }
}
