package br.com.bingo.kits.listeners;

import br.com.bingo.game.GameManager;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SedexListener implements Listener {

    Map<UUID, Long> cooldowns = new HashMap<>();
    GameManager gameManager;
    ArrayList<UUID> onKit = new ArrayList<>();

    public SedexListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onSedexUse(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "PAC") && !event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "SEDEX 10"))return;

        if(event.getAction().name().contains("RIGHT")){
            openSedexMenu(event.getPlayer());
        }
    }


    public void openSedexMenu(Player player){
        TeamType sedexTeam = gameManager.getPlayerTeam(player);
        Inventory sedexMenu = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Lista de Endereços");

        for(UUID uuid : gameManager.playerTeam.keySet()){
            if(Bukkit.getPlayer(uuid) == null)continue;
            if(uuid.equals(player.getUniqueId()))continue;
            if(gameManager.playerTeam.get(uuid).equals(sedexTeam)){
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                headMeta.setDisplayName(ChatColor.GOLD + Bukkit.getPlayer(uuid).getName());
                headMeta.setOwningPlayer(Bukkit.getPlayer(uuid));
                head.setItemMeta(headMeta);
                sedexMenu.addItem(head);
            }
        }

        player.openInventory(sedexMenu);
    }


    @EventHandler
    public void openPlayerInventory(InventoryClickEvent event){
        if(event.getView().equals(null)) return;
        if(!event.getView().getTitle().equals(ChatColor.DARK_RED + "Lista de Endereços")){
            return;
        }
        event.setCancelled(true);
        if(event.getCurrentItem() == null) return;
        if(!event.getCurrentItem().hasItemMeta()) return;
        if(!event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) return;
        SkullMeta meta = (SkullMeta) event.getCurrentItem().getItemMeta();
        UUID uuid = meta.getOwningPlayer().getUniqueId();
        event.getWhoClicked().closeInventory();

        if (!cooldowns.containsKey(event.getWhoClicked().getUniqueId())) {
            cooldowns.put(event.getWhoClicked().getUniqueId(), 0L);
        }
        long currentTime = System.currentTimeMillis();
        long lastUsedTime = cooldowns.getOrDefault(event.getWhoClicked().getUniqueId(), 0L);
        long cooldown = 0;
        if(gameManager.getAvailableQuests().size() >= gameManager.questLeftWhenChange){
            cooldown = 1000 * 60 * 8;
        } else{
            cooldown = 1000 * 60 * 5;
        }


        if (currentTime - lastUsedTime < cooldown) {
            event.getWhoClicked().sendMessage(ChatColor.RED + "Aguarde " + ((cooldown/1000) - (((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
            return;
        }
        cooldowns.put(event.getWhoClicked().getUniqueId(), currentTime);
        onKit.add(event.getWhoClicked().getUniqueId());
        event.getWhoClicked().openInventory(Bukkit.getPlayer(uuid).getInventory());

    }

    @EventHandler
    public void disableKitItem(InventoryClickEvent event){
        if(event.getView().equals(null)) return;
        if(event.getView().getTitle().equals("Player")){
            if(event.getCurrentItem() == null) return;
            if(!event.getCurrentItem().hasItemMeta()) return;
            if(event.getCurrentItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE) || event.getCurrentItem().getItemMeta().isUnbreakable()){
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void disableKitItem2(InventoryClickEvent event){
        if(event.getView().equals(null)) return;
        if(event.getView().getTitle().equals("Player")){
            if(event.getCursor() == null) return;
            if(!event.getCursor().hasItemMeta()) return;
            if(event.getCursor().getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE) || event.getCursor().getItemMeta().isUnbreakable()){
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void disableKitItem3(InventoryDragEvent event){
        if(event.getView().equals(null)) return;
        if(event.getView().getTitle().equals("Player")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void disableKitItem4(InventoryClickEvent event){
        if(event.getView().equals(null)) return;
        if(event.getView().getTitle().equals("Player")){
            if(event.getInventory().getType().equals(InventoryType.PLAYER)){
                if(event.getCurrentItem() == null) return;
                if(!event.getCurrentItem().hasItemMeta()) return;
                if(event.getCurrentItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE) || event.getCurrentItem().getItemMeta().isUnbreakable()) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void removeFromOnKit(InventoryCloseEvent event){
        if(event.getView().equals(null)) return;
        if(event.getView().getTitle().equals("Player")){
            onKit.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void disableSedexItem(InventoryClickEvent event){
        if(event.getView().equals(null)) return;
        if(onKit.contains(event.getWhoClicked().getUniqueId())){
            if(event.getCurrentItem() != null){
                if(event.getCurrentItem().hasItemMeta()){
                    if(event.getCurrentItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE) || event.getCurrentItem().getItemMeta().isUnbreakable()){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }



}
