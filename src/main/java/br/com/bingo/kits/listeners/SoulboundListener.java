package br.com.bingo.kits.listeners;

import br.com.bingo.Bingo;
import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameStatus;
import br.com.bingo.kits.KitType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class SoulboundListener implements Listener {

    Map<UUID, Location> lastDiePoint = new HashMap<>();

    public SoulboundListener (GameManager gameManager){
        this.gameManager = gameManager;
    }

    GameManager gameManager;

    @EventHandler
    public void onPortableChestUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() == null){return;}
        if(!player.getInventory().getItemInMainHand().hasItemMeta()) return;
        if(!Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.GOLD + "Portable EnderChest"))return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR))return;

        if(event.getAction().name().contains("RIGHT")){
            event.getPlayer().openInventory(event.getPlayer().getEnderChest());
        }
    }

    @EventHandler
    public void onSoulboundUse(PlayerInteractEvent event){
        //Teleport player to lastDiePoint after 30 seconds of use and remove item from inventory
        Player player = event.getPlayer();
        if(event.getItem() == null){return;}
        if(!player.getInventory().getItemInMainHand().hasItemMeta()) return;
        if(!Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.GOLD + "Soul Portal"))return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR))return;

        if(!lastDiePoint.containsKey(player.getUniqueId())) return;
        player.sendMessage(ChatColor.GREEN +"Teleportando em 30 segundos");

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.GREEN +"Teleportando em 15 segundos");
            }
        }, 300L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.GREEN +"Teleportando em 5 segundos");
            }
        }, 500L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.GREEN +"Teleportando em 4 segundos");
            }
        }, 520L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.GREEN +"Teleportando em 3 segundos");
            }
        }, 540L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.GREEN +"Teleportando em 2 segundos");
            }
        }, 560L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.GREEN +"Teleportando em 1 segundos");
            }
        }, 580L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.GREEN +"Teleportando...");
                player.teleport(lastDiePoint.get(player.getUniqueId()).add(0,2,0));
                player.getInventory().remove(event.getItem());
                lastDiePoint.remove(player.getUniqueId());
            }
        }, 600L);

    }


    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(!gameManager.getGameStatus().equals(GameStatus.STARTED)) return;
        if(!gameManager.playerKit.get(player.getUniqueId()).equals(KitType.SOULBOUND)) return;
        lastDiePoint.put(player.getUniqueId(), player.getLocation());
    }
}
