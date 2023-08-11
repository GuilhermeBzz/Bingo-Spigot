package br.com.bingo.kits.listeners;

import br.com.bingo.Bingo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArchitectListener implements Listener {

    Map<UUID, Long> cooldowns = new HashMap<>();
    ArrayList<Location> locations = new ArrayList<>();

    @EventHandler
    public void onPlaceBlock(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Builder") && !event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Pro Builder"))return;
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Location location = event.getClickedBlock().getLocation();
            BlockFace face = event.getBlockFace();
            int remaining = 3;
            if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Pro Builder")){
                remaining = 5;
            }
            event.setCancelled(true);

            if (!cooldowns.containsKey(event.getPlayer().getUniqueId())) {
                cooldowns.put(event.getPlayer().getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = cooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
            long cooldown = 100;
            if (currentTime - lastUsedTime < cooldown) {
                return;
            }
            cooldowns.put(event.getPlayer().getUniqueId(), currentTime);

            blockLogic(location, face, remaining);
            return;
        }
    }

    @EventHandler
    public void preventBreak(BlockBreakEvent event){
        if(locations.contains(event.getBlock().getLocation())){
            event.setCancelled(true);
        }
        return;
    }


    public void blockLogic(Location location, BlockFace face, int remaining){
        Material blockMaterial = Material.DIRT;
        switch (remaining){
            case 5:
                blockMaterial = Material.NETHERITE_BLOCK;
                break;
            case 4:
                blockMaterial = Material.DIAMOND_BLOCK;
                break;
            case 3:
                blockMaterial = Material.LIME_CONCRETE;
                break;
            case 2:
                blockMaterial = Material.YELLOW_CONCRETE;
                break;
            case 1:
                blockMaterial = Material.RED_CONCRETE;
                break;
            case 0:
                blockMaterial = Material.AIR;
        }

        int newRemaining = remaining - 1;


        Block block = location.getBlock();
        block = block.getRelative(face);
        BlockData blockData = block.getBlockData();
        if(!locations.contains(block.getLocation())){
            locations.add(block.getLocation());
        }

        block.setType(blockMaterial);
        if(newRemaining == -1){
            locations.remove(block.getLocation());
            return;
        }


        Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(),  new Runnable() {
            @Override
            public void run() {
                blockLogic(location, face, newRemaining);
            }
        }, 60L);

    }
}
