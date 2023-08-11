package br.com.bingo.kits.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CultivatorListener implements Listener {

    Map<UUID, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void growPlants(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(!event.getItem().hasItemMeta()) return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Cultivator's Hoe")) return;
        if(!event.getAction().name().contains("RIGHT")) return;
        Player player = event.getPlayer();
        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        int radius = 5;

        for (int x = playerX - radius; x <= playerX + radius; x++) {
            for (int y = playerY - radius; y <= playerY + radius; y++) {
                for (int z = playerZ - radius; z <= playerZ + radius; z++) {
                    Block block = player.getWorld().getBlockAt(x, y, z);
                    Material material = block.getType();

                    if (isGrowablePlant(material)) {
                        applyBoneMeal(block);
                    }
                }
            }
        }

        return;
    }

    @EventHandler
    public void onSoupUse(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().hasItemMeta())return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Sopa do HG"))return;
        if(!event.getAction().name().contains("RIGHT")) return;
        Player player = event.getPlayer();
        if (!cooldowns.containsKey(event.getPlayer().getUniqueId())) {
            cooldowns.put(event.getPlayer().getUniqueId(), 0L);
        }
        long currentTime = System.currentTimeMillis();
        long lastUsedTime = cooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
        long cooldown = 60 * 1000;
        if (currentTime - lastUsedTime < cooldown) {
            event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (60 - (((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
            return;
        }
        cooldowns.put(event.getPlayer().getUniqueId(), currentTime);

        if(player.getFoodLevel() >= 15){
            player.setFoodLevel(20);
        }
        else{
            player.setFoodLevel(player.getFoodLevel() + 5);
        }
        if(player.getHealth() >= 15){
            player.setHealth(20);
        } else{
            player.setHealth(player.getHealth() + 5);
        }
        if(player.getSaturation() >= 15){
            player.setSaturation(20);
        } else{
            player.setSaturation(player.getSaturation() + 5);
        }
    }

    @EventHandler
    public void cancelConsume(PlayerItemConsumeEvent event){
        if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Sopa do HG")){
            event.setCancelled(true);
            return;
        }
    }



    private boolean isGrowablePlant(Material material) {
        return material == Material.WHEAT
                || material == Material.CARROTS
                || material == Material.POTATOES
                || material == Material.BEETROOTS
                || material == Material.NETHER_WART
                || material == Material.COCOA
                || material == Material.BAMBOO //
                || material == Material.OAK_SAPLING //
                || material == Material.SPRUCE_SAPLING //
                || material == Material.BIRCH_SAPLING //
                || material == Material.JUNGLE_SAPLING  //
                || material == Material.ACACIA_SAPLING //
                || material == Material.DARK_OAK_SAPLING //
                || material == Material.BROWN_MUSHROOM //
                || material == Material.RED_MUSHROOM //
                || material == Material.KELP //
                || material == Material.KELP_PLANT //
                || material == Material.MELON_STEM
                || material == Material.PUMPKIN_STEM
                || material == Material.SWEET_BERRY_BUSH
                || material == Material.WARPED_FUNGUS //
                || material == Material.CRIMSON_FUNGUS //
                || material == Material.TWISTING_VINES
                || material == Material.WEEPING_VINES;


    }

    private void applyBoneMeal(Block block) {
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Ageable) {
            Ageable ageable = (Ageable) blockData;
            if (!(ageable.getAge() == ageable.getMaximumAge())) {
                ageable.setAge(Math.min(ageable.getAge() + 1, ageable.getMaximumAge()));
                block.setBlockData(ageable);
                block.getWorld().playSound(block.getLocation(), Sound.ITEM_BONE_MEAL_USE, 1.0f, 1.0f);
                block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5), 10);
            }
        } else if(block instanceof Sapling){
            Sapling sapling = (Sapling) blockData;
            if(sapling.getStage() != sapling.getMaximumStage()){
                sapling.setStage(sapling.getMaximumStage());
                block.setBlockData(sapling);
                block.getWorld().playSound(block.getLocation(), Sound.ITEM_BONE_MEAL_USE, 1.0f, 1.0f);
                block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5), 10);
            }
        }
    }
}
