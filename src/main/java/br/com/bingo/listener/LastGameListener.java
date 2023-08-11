package br.com.bingo.listener;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.game.LastGame;
import br.com.bingo.quests.Quest;
import br.com.bingo.team.TeamType;
import br.com.bingo.ui.BingoMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class LastGameListener implements Listener {

    GameManager gameManager;


    public LastGameListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onInventoryClickInOldGame(InventoryClickEvent event){
        InventoryView inventory = event.getView();
        if(inventory != null && inventory.getTitle().contains("Última Partida")){
            event.setCancelled(true);
            if(event.getClickedInventory() == null) return;
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().hasItemMeta()){
                ItemMeta meta = event.getCurrentItem().getItemMeta();
                if(meta.getDisplayName().equals(ChatColor.GREEN + "Placar")){
                    event.getWhoClicked().closeInventory();
                    openPlacar((Player) event.getWhoClicked());
                }
            }
        }
    }

    @EventHandler
    public void onLastGameClick(InventoryClickEvent event){
        InventoryView inventory = event.getView();
        if(inventory != null && inventory.getTitle().contains("Placar")){
            event.setCancelled(true);
            return;
        }
    }

    public void openPlacar(Player player){
        LastGame lastGame = gameManager.lastGame;
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Placar");
        if(lastGame.gameType.equals(GameType.SOLO)){
            UUID bestPlayer = null;
            ItemStack divider = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta dividerMeta = divider.getItemMeta();
            dividerMeta.setDisplayName(" ");
            divider.setItemMeta(dividerMeta);
            inventory.setItem(0, divider);
            inventory.setItem(1, divider);
            inventory.setItem(2, divider);
            inventory.setItem(3, divider);
            inventory.setItem(5, divider);
            inventory.setItem(6, divider);
            inventory.setItem(7, divider);
            inventory.setItem(8, divider);
            for(UUID uuid : lastGame.playerPoints.keySet()){
                if(bestPlayer == null){
                    bestPlayer = uuid;
                    continue;
                }
                if(lastGame.playerPoints.get(uuid) > lastGame.playerPoints.get(bestPlayer)){
                    bestPlayer = uuid;
                }
            }
            int position = 9;
            for(UUID uuid : lastGame.playerPoints.keySet()){
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                ItemMeta playerHeadMeta = playerHead.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Pontos: " + ChatColor.GOLD + lastGame.playerPoints.get(uuid));
                if(lastGame.kit) lore.add(ChatColor.GRAY + "Kit: " + ChatColor.GOLD + lastGame.playerKit.get(uuid).getName());
                if(uuid.equals(bestPlayer)){
                    lore.add(ChatColor.GOLD + "Vencedor");
                }
                playerHeadMeta.setLore(lore);
                playerHeadMeta.setDisplayName(ChatColor.GOLD + Bukkit.getOfflinePlayer(uuid).getName());
                playerHead.setItemMeta(playerHeadMeta);
                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
                playerHead.setItemMeta(skullMeta);
                if(bestPlayer.equals(uuid)){
                    inventory.setItem(4, playerHead);
                } else{
                    inventory.setItem(position, playerHead);
                    position++;
                }
            }


        } else{
            //TEAM
            ItemStack divider = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta dividerMeta = divider.getItemMeta();
            dividerMeta.setDisplayName(" ");
            divider.setItemMeta(dividerMeta);
            inventory.setItem(4, divider);
            inventory.setItem(13, divider);
            inventory.setItem(22, divider);

            ItemStack teamRed = new ItemStack(Material.RED_CONCRETE);
            ItemStack teamBlue = new ItemStack(Material.BLUE_CONCRETE);
            ItemMeta redMeta = teamRed.getItemMeta();
            ItemMeta blueMeta = teamBlue.getItemMeta();

            for(TeamType team :lastGame.teamPoints.keySet()){
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Pontos: " + ChatColor.GOLD + lastGame.teamPoints.get(team));
                if(team.equals(TeamType.TEAM_RED)){
                    redMeta.setDisplayName(ChatColor.RED + "Time Vermelho");
                    redMeta.setLore(lore);
                    teamRed.setItemMeta(redMeta);
                    inventory.setItem(3, teamRed);
                } else{
                    blueMeta.setDisplayName(ChatColor.BLUE + "Time Azul");
                    blueMeta.setLore(lore);
                    teamBlue.setItemMeta(blueMeta);
                    inventory.setItem(5, teamBlue);
                }
            }
            int bluePosition = 14;
            int redPosition = 12;
            Map<UUID, Integer> eachPlayerPoints = new HashMap<>();
            for(Quest quest : lastGame.playerQuests.keySet()){
                if(lastGame.playerQuests.get(quest) == null) continue;
                if(eachPlayerPoints.containsKey(lastGame.playerQuests.get(quest))){
                    eachPlayerPoints.put(lastGame.playerQuests.get(quest), eachPlayerPoints.get(lastGame.playerQuests.get(quest)) + 1);
                }else{
                    eachPlayerPoints.put(lastGame.playerQuests.get(quest), 1);
                }
                for(UUID uuid : lastGame.playerTeam.keySet()){
                    if(eachPlayerPoints.containsKey(uuid)) continue;
                    eachPlayerPoints.put(uuid, 0);
                }
            }
            for(UUID uuid : lastGame.playerTeam.keySet()){
                TeamType playerTeam = lastGame.playerTeam.get(uuid);
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                ItemMeta playerHeadMeta = playerHead.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Pontos: " + ChatColor.GOLD + eachPlayerPoints.get(uuid));
                if(lastGame.kit) lore.add(ChatColor.GRAY + "Kit: " + ChatColor.GOLD + lastGame.playerKit.get(uuid).getName());

                int postion = 0;
                if(playerTeam.equals(TeamType.TEAM_RED)){
                    playerHeadMeta.setDisplayName(ChatColor.RED + Bukkit.getOfflinePlayer(uuid).getName());
                    playerHeadMeta.setLore(lore);
                    postion = redPosition;
                    redPosition--;
                    if(redPosition == 8) redPosition = 21;
                }else{
                    playerHeadMeta.setDisplayName(ChatColor.BLUE + Bukkit.getOfflinePlayer(uuid).getName());
                    playerHeadMeta.setLore(lore);
                    postion = bluePosition;
                    bluePosition++;
                    if(bluePosition == 18) bluePosition = 23;
                }
                playerHead.setItemMeta(playerHeadMeta);
                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
                playerHead.setItemMeta(skullMeta);
                inventory.setItem(postion, playerHead);
            }

        }
        player.openInventory(inventory);
    }

}
