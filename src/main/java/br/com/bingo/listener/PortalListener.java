package br.com.bingo.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {

    @EventHandler
    public void onPLayerPortal(PlayerPortalEvent event){
        World fromWorld = event.getFrom().getWorld();
        World toWorld;
        if(fromWorld.getName().equals("gameWorld")){
            if(event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
                toWorld = Bukkit.getWorld("gameWorld_nether");
            }
            else if (event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
                toWorld = Bukkit.getWorld("gameWorld_the_end");
            }
            else return;
        }
        else if (fromWorld.getName().equals("gameWorld_nether")) {
            toWorld = Bukkit.getWorld("gameWorld");
        }
        else if (fromWorld.getName().equals("gameWorld_the_end")) {
            toWorld = Bukkit.getWorld("gameWorld");
        }
        else{return;}

        Location destination = event.getTo();
        destination.setWorld(toWorld);
        event.setTo(destination);

    }
    @EventHandler
    public void test(EntityPortalEvent event){

        World fromWorld = event.getFrom().getWorld();
        World toWorld;
        if(fromWorld.getName().equals("gameWorld")){
            if(event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
                toWorld = Bukkit.getWorld("gameWorld_nether");
            }
            else if (event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
                toWorld = Bukkit.getWorld("gameWorld_the_end");
            }
            else return;
        }
        else if (fromWorld.getName().equals("gameWorld_nether")) {
            toWorld = Bukkit.getWorld("gameWorld");
        }
        else if (fromWorld.getName().equals("gameWorld_the_end")) {
            toWorld = Bukkit.getWorld("gameWorld");
        }
        else{return;}

        Location destination = event.getTo();
        destination.setWorld(toWorld);
        event.setTo(destination);
    }


}
