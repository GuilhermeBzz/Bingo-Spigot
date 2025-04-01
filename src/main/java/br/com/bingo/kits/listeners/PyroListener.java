package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameStatus;
import br.com.bingo.kits.KitType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PyroListener implements Listener {

    GameManager gameManager;
    Map<UUID, Long> cooldowns = new HashMap<>();

    public PyroListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(!gameManager.getGameStatus().equals(GameStatus.STARTED)) return;
        Player player = event.getPlayer();
        if (!isPyro(player)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        player.setFireTicks(0);
        //take damage when in water
        if (player.getLocation().getBlock().getType() == Material.WATER && !(player.getVehicle() instanceof Boat)) {
            player.damage(2);
        }

        if(gameManager.getAvailableQuests().size() >gameManager.questLeftWhenChange) return;
        //get player held item
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if(heldItem == null || heldItem.getItemMeta() == null || !heldItem.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Botafogo")) return;

        Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
        if (block.getType().isSolid()) {
            Block fireBlock = player.getLocation().getBlock();
            if (fireBlock.getType() == Material.AIR) {
                fireBlock.setType(Material.FIRE);
            }
        }

    }

    public boolean isPyro(Player player) {
        return (gameManager.isGameStarted() && gameManager.playerKit.containsKey(player.getUniqueId()) &&  gameManager.playerKit.get(player.getUniqueId()).equals(KitType.PYRO));
    }

    //33% of setting entity on fire after hit
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if (!isPyro(player)) return;
        if (Math.random() < 0.33) {
            event.getEntity().setFireTicks(100);
        }
    }

    //use Botafogo to create fire circle
    @EventHandler
    public void onBotafogoUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!isPyro(player)) return;
        if (event.getItem() == null) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Botafogo")) return;

        if(!cooldowns.containsKey(player.getUniqueId())){
            cooldowns.put(player.getUniqueId(), 0L);
        }
        long currentTime = System.currentTimeMillis();
        long lastUsedTime = cooldowns.getOrDefault(player.getUniqueId(), 0L);
        long cooldown = 2*60*1000;
        if(currentTime - lastUsedTime < cooldown){
            player.sendMessage(ChatColor.RED + "Aguarde " + ((cooldown/1000)-(((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
            return;
        }
        cooldowns.put(player.getUniqueId(), currentTime);

        World world = player.getWorld();
        int px = player.getLocation().getBlockX();
        int py = player.getLocation().getBlockY();
        int pz = player.getLocation().getBlockZ();

        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                if (Math.abs(x) + Math.abs(z) <= 4) {
                    Block fireBlock = world.getBlockAt(px + x, py, pz + z);
                    if (fireBlock.getType() == Material.AIR) {
                        fireBlock.setType(Material.FIRE);
                    }
                }
            }
        }
        player.sendMessage(ChatColor.DARK_RED + "Você ativou sua Explosão de Chamas!");
    }

}
