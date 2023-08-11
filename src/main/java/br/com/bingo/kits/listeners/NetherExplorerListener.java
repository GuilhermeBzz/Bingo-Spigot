package br.com.bingo.kits.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.util.StructureSearchResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class NetherExplorerListener implements Listener {

    Map<UUID, Long> compassCooldown = new HashMap<>();
    Map<UUID, Long> creatorCooldown = new HashMap<>();

    @EventHandler
    public void onCompassInteract(PlayerInteractEvent event){
        if(event.getItem() == null){return;}
        if(!event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Nether Compass")){return;}
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR) && event.getAction().equals(Action.LEFT_CLICK_AIR) && event.getAction().equals(Action.LEFT_CLICK_BLOCK)){return;}
        Player player = event.getPlayer();
        if(player.getWorld().getEnvironment().equals(World.Environment.NETHER)){

            if(!compassCooldown.containsKey(player.getUniqueId())){
                compassCooldown.put(player.getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = compassCooldown.getOrDefault(player.getUniqueId(), 0L);
            long cooldown = 60*8*1000;
            if(currentTime - lastUsedTime < cooldown){
                player.sendMessage(ChatColor.RED + "Aguarde " + (60*8-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente o seu kit");
                return;
            }
            compassCooldown.put(player.getUniqueId(), currentTime);

            Location structureLocation = null;
            String structureName = "";
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
                StructureSearchResult fortress = player.getWorld().locateNearestStructure(player.getLocation(), Structure.FORTRESS, 500, false);
                structureName = "a Fortaleza mais proxima!";
                if(fortress == null){
                    player.sendMessage(ChatColor.RED + "Nao foi possivel encontrar uma Fortaleza");
                    return;
                }
                Location fortressLocation = fortress.getLocation();
                fortressLocation.setY(1);
                structureLocation = fortressLocation;
            } else{
                StructureSearchResult bastion = player.getWorld().locateNearestStructure(player.getLocation(), Structure.BASTION_REMNANT, 500, false);
                structureName = "o Bastion mais proximo!";
                if(bastion == null){
                    player.sendMessage(ChatColor.RED + "Nao foi possivel encontrar um Bastion");
                    return;
                }
                Location bastionLocation = bastion.getLocation();
                bastionLocation.setY(1);
                structureLocation = bastionLocation;
            }

            CompassMeta compassMeta = (CompassMeta) event.getItem().getItemMeta();
            compassMeta.setLodestone(structureLocation);
            compassMeta.setLodestoneTracked(false);
            event.getItem().setItemMeta(compassMeta);
            player.setCompassTarget(structureLocation);
            player.sendMessage(ChatColor.GREEN + "Voce esta sendo guiado para " +structureName);
            return;
        }else {
            player.sendMessage(ChatColor.RED + "Voce precisa estar no Nether para procurar uma Estrutura");
        }


    }

    @EventHandler
    public void onCreatorInteract(PlayerInteractEvent event){
        if(event.getItem() == null){return;}
        if(!event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Portal Creator")){return;}



        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){return;}

        Player player = event.getPlayer();

        if(!creatorCooldown.containsKey(player.getUniqueId())){
            creatorCooldown.put(player.getUniqueId(), 0L);
        }
        long currentTime = System.currentTimeMillis();
        long lastUsedTime = creatorCooldown.getOrDefault(player.getUniqueId(), 0L);
        long cooldown = 5*60*1000;
        if(currentTime - lastUsedTime < cooldown){
            player.sendMessage(ChatColor.RED + "Aguarde " + (5*60-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente o seu kit");
            return;
        }
        creatorCooldown.put(player.getUniqueId(), currentTime);

        Location location = event.getClickedBlock().getLocation();
        Material obsidian = Material.OBSIDIAN;

        location.getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);

        location.add(1, 0, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(1, 1, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(-3, 0, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(0, 1, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(3, 0, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(0, 1, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(-3, 0, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(1, 1, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(1, 0, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);

        location.add(1, 0, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(-3, 0, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(0, -4, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.add(3, 0, 0).getBlock().setType(obsidian);
        location.getWorld().spawnParticle(Particle.PORTAL, location, 10);
        location.getWorld().playSound(location, Sound.BLOCK_STONE_PLACE, 1.0f, 1.0f);
    }

}
