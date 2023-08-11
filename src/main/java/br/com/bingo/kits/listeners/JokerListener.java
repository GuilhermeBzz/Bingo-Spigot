package br.com.bingo.kits.listeners;
import br.com.bingo.game.GameManager;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class JokerListener implements Listener{

    Map<UUID, Long> cooldowns = new HashMap<>();
    GameManager gameManager;

    public JokerListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoker(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "HAHAHAHAHAHAHAHAHA") && !event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Joker"))return;

        if(event.getAction().name().contains("RIGHT")) {
            if (!cooldowns.containsKey(event.getPlayer().getUniqueId())) {
                cooldowns.put(event.getPlayer().getUniqueId(), 0L);
            }
            long currentTime = System.currentTimeMillis();
            long lastUsedTime = cooldowns.getOrDefault(event.getPlayer().getUniqueId(), 0L);
            long cooldown = 60*5 * 1000;
            if (currentTime - lastUsedTime < cooldown) {
                event.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + (60* 5 - (((currentTime - lastUsedTime)) / 1000)) + " segundos para usar novamente");
                return;
            }
            cooldowns.put(event.getPlayer().getUniqueId(), currentTime);
            TeamType jokerTeam = gameManager.getPlayerTeam(event.getPlayer());
            Boolean advanced = null;
            if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Joker")){
                advanced = false;
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "HAHAHAHAHAHAHAHAHA")){
                advanced = true;
            }
            jokerAbility(event.getPlayer(), jokerTeam, advanced);
        }
    }


    @EventHandler
    public void cancelConsume(PlayerItemConsumeEvent event){
        if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Joker")){
            event.setCancelled(true);
            return;
        } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "HAHAHAHAHAHAHAHAHA")){
            event.setCancelled(true);
            return;
        }
    }

    public void jokerAbility(Player joker, TeamType jokerTeam, Boolean advanced){
        if(jokerTeam.equals(TeamType.SOLO)){
            for(UUID uuid : gameManager.playerTeam.keySet()){
                if(uuid.equals(joker.getUniqueId()))continue;
                Player target = Bukkit.getPlayer(uuid);
                if(target == null)continue;
                ItemStack[] items = target.getInventory().getStorageContents();
                List<ItemStack> itemsList = new ArrayList<>();

                for(ItemStack item : items){
                    if(item != null && item.getItemMeta() != null && !item.getItemMeta().isUnbreakable() && !item.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)){
                        target.getInventory().remove(item);
                        itemsList.add(item);
                    }
                }



                Collections.shuffle(itemsList);
                if(advanced){
                    List<ItemStack> dropList;
                    dropList = itemsList.subList(itemsList.size() - 10,itemsList.size());
                    itemsList = itemsList.subList(0, itemsList.size() -  10);


                    for(ItemStack item : dropList){
                        Item drop =target.getWorld().dropItem(target.getLocation(), item);
                        drop.setPickupDelay(200);
                    }
                }


                for(ItemStack item : itemsList){
                    target.getInventory().addItem(item);
                }


            }
        }else{
            for(UUID uuid : gameManager.playerTeam.keySet()) {
                if (gameManager.playerTeam.get(uuid).equals(jokerTeam)) continue;
                Player target = Bukkit.getPlayer(uuid);
                if (target == null) continue;
                ItemStack[] items = target.getInventory().getStorageContents();
                List<ItemStack> itemsList = new ArrayList<>();

                for (ItemStack item : items) {
                    if (item != null && item.getItemMeta() != null && !item.getItemMeta().isUnbreakable() && !item.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)) {
                        target.getInventory().remove(item);
                        itemsList.add(item);
                    }
                }

                Collections.shuffle(itemsList);
                if(advanced){
                    List<ItemStack> dropList;
                    dropList = itemsList.subList(itemsList.size() - 10,itemsList.size());
                    itemsList = itemsList.subList(0, itemsList.size() -  10);


                    for(ItemStack item : dropList){
                        Item drop =target.getWorld().dropItem(target.getLocation(), item);
                        drop.setPickupDelay(200);
                    }
                }
                for (ItemStack item : itemsList) {
                    target.getInventory().addItem(item);
                }
            }
        }
    }

}
