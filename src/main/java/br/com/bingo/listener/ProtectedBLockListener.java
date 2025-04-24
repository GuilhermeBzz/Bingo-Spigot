package br.com.bingo.listener;

import br.com.bingo.Bingo;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class ProtectedBLockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (Bingo.getInstance().gameManager.blocosIndestrutiveis.contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Você não pode quebrar esse bloco!");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Location loc = event.getBlock().getLocation();
        if (Bingo.getInstance().gameManager.blocosIndestrutiveis.contains(loc)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Você não pode colocar blocos aqui!");
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block ->
                Bingo.getInstance().gameManager.blocosIndestrutiveis.contains(block.getLocation())
        );
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().removeIf(block ->
                Bingo.getInstance().gameManager.blocosIndestrutiveis.contains(block.getLocation())
        );
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (Bingo.getInstance().gameManager.blocosIndestrutiveis.contains(block.getLocation())) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        for (Block block : event.getBlocks()) {
            if (Bingo.getInstance().gameManager.blocosIndestrutiveis.contains(block.getLocation())) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Location targetLocation = event.getBlockClicked().getRelative(event.getBlockFace()).getLocation();
        if (Bingo.getInstance().gameManager.blocosIndestrutiveis.contains(targetLocation)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Você não pode colocar líquidos nesse local protegido!");
        }
    }

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        if (Bingo.getInstance().gameManager.blocosIndestrutiveis.contains(event.getToBlock().getLocation())) {
            event.setCancelled(true);
        }
    }
}
