package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Map;
import java.util.UUID;

public class ExplorerListener implements Listener {

    GameManager gameManager;

    public ExplorerListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onExplorerUse(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Biome's Compass"))return;

        if(event.getAction().name().contains("RIGHT")){
            openExplorerMenu(event.getPlayer());
        }
    }

    public void openExplorerMenu(Player player){

        Inventory explorerMenu = Bukkit.createInventory(null, 9*3, ChatColor.DARK_RED + "Lista de Biomas");
        Map<Biome, Location> playerBiomes = gameManager.getPlayerBiomes(player.getUniqueId());
        Map<Biome, Material> biomeIcons = gameManager.getBiomeIconsMap();
        if(playerBiomes == null || playerBiomes.isEmpty()){
            player.sendMessage(ChatColor.RED + "Você ainda não descobriu nenhum bioma!");
            player.openInventory(explorerMenu);
            return;
        }
        for(Biome biome : playerBiomes.keySet()){
            ItemStack icon = new ItemStack(biomeIcons.get(biome));
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.setDisplayName(gameManager.biomeNameMap.get(biome));
            iconMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            icon.setItemMeta(iconMeta);
            explorerMenu.addItem(icon);
        }

        player.openInventory(explorerMenu);
    }

    @EventHandler
    public void openPlayerInventory(InventoryClickEvent event){
        if(event.getView().equals(null)) return;
        if(!event.getView().getTitle().equals(ChatColor.DARK_RED + "Lista de Biomas")){
            return;
        }

        Player player = (Player) event.getWhoClicked();

        Map<Biome, Location> playerBiomes = gameManager.getPlayerBiomes(player.getUniqueId());
        Map<Biome, Material> biomeIcons = gameManager.getBiomeIconsMap();

        event.setCancelled(true);
        if(event.getCurrentItem() == null) return;
        if(!event.getCurrentItem().hasItemMeta()) return;
        ItemStack item = event.getCurrentItem();
        if(!biomeIcons.containsValue(item.getType())) return;
        Biome biome = null;
        for(Biome b : biomeIcons.keySet()){
            if(biomeIcons.get(b).equals(item.getType())){
                biome = b;
                break;
            }
        }
        if(biome == null) return;
        Location location = playerBiomes.get(biome);

        //find compass in player inventory
        ItemStack compass = null;
        for(ItemStack i : player.getInventory().getContents()){
            if(i == null) continue;
            if(i.getType().equals(Material.COMPASS)){
                compass = i;
                break;
            }
        }
        if(compass == null) return;
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        compassMeta.setLodestone(location);
        compassMeta.setLodestoneTracked(false);
        compass.setItemMeta(compassMeta);
        player.setCompassTarget(location);

        player.sendMessage(ChatColor.GREEN + "A bússola foi atualizada para o bioma " + ChatColor.GOLD + gameManager.biomeNameMap.get(biome));
        player.closeInventory();
    }
}
