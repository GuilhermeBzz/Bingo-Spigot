package br.com.bingo.listener;

import br.com.bingo.rank.RankList;
import br.com.bingo.rank.Ranks;
import br.com.bingo.rank.profile.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class ProfileListener implements Listener {


    @EventHandler
    public void onProfileClick(InventoryClickEvent event){
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(Material.POISONOUS_POTATO);
        materials.add(Material.COAL);
        materials.add(Material.IRON_INGOT);
        materials.add(Material.GOLD_INGOT);
        materials.add(Material.DIAMOND);
        materials.add(Material.EMERALD);
        materials.add(Material.NETHERITE_INGOT);
        materials.add(Material.NETHER_STAR);
        materials.add(Material.DRAGON_EGG);
        materials.add(Material.COPPER_INGOT);
        if(event.getView().getTitle().contains("Profile")){
            event.setCancelled(true);
            if(event.getClickedInventory() != null){
                if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR){
                    if(event.getCurrentItem().getType().equals(Material.BOOK)){
                        event.getWhoClicked().closeInventory();
                        RankList.openPlayerList((Player) event.getWhoClicked());
                    } else if (materials.contains(event.getCurrentItem().getType())){
                        event.getWhoClicked().closeInventory();
                        Ranks.openRankList((Player) event.getWhoClicked());
                    }
                }
            }
        }


    }


    @EventHandler
    public void onListClick(InventoryClickEvent event){
        if(event.getView().getTitle().contains("Lista de Jogadores")){
            event.setCancelled(true);
            if(event.getClickedInventory() != null){
                if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR){
                    if(event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
                        event.getWhoClicked().closeInventory();
                        SkullMeta meta = (SkullMeta) event.getCurrentItem().getItemMeta();
                        UUID uuid = meta.getOwningPlayer().getUniqueId();
                        PlayerProfile.openProfile(event.getWhoClicked().getUniqueId(),uuid);
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerClickOnProfile(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if(event.getPlayer().getInventory().getItemInMainHand() == null) return;
            if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.PLAYER_HEAD)){
                if(!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
                if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) return;
                if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Profile")) return;
                event.setCancelled(true);
                PlayerProfile.openProfile(event.getPlayer().getUniqueId(),event.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void playerClickonEntity(PlayerInteractAtEntityEvent event){

        if(event.getPlayer().getInventory().getItemInMainHand() != null){
            if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.PLAYER_HEAD)){
                if(!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
                if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) return;
                if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Profile")) return;
                event.setCancelled(true);
                PlayerProfile.openProfile(event.getPlayer().getUniqueId(),event.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void playerClickonRank(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + "Ranks")) {
            event.setCancelled(true);
        }
    }
}
