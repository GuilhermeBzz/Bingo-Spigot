package br.com.bingo.rank.profile;

import br.com.bingo.rank.Ranks;
import br.com.bingo.rank.models.players.PlayersData;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class PlayerProfile {

    public static void openProfile(UUID opener, UUID uuid) {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Profile");
        for(int i = 0; i < 27; i++) {
            inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        //13 cabeca
        //15 rank
        ItemStack list = new ItemStack(Material.BOOK);
        ItemMeta listMeta = list.getItemMeta();
        listMeta.setDisplayName(ChatColor.GREEN + "Lista de Jogadores");
        list.setItemMeta(listMeta);
        inventory.setItem(11, list);

        PlayersData playerData = PlayersStorageUtil.getPlayer(uuid.toString());
        int matches = 0;
        int wins = 0;
        int points = 50;
        int QEasy = 0;
        int QMedium = 0;
        int QHard = 0;
        int Streak = 0;
        if(playerData != null) {
            matches = playerData.getMatches();
            wins = playerData.getWins();
            points = playerData.getPoints();
            QEasy = playerData.getQEasy();
            QMedium = playerData.getQMedium();
            QHard = playerData.getQHard();
            Streak = playerData.getStreak();
        }

        String stringStreak = "";
        if(Streak > 0) {
            stringStreak = ChatColor.GREEN + "Streak de Vitorias: " + ChatColor.GREEN + Streak;
        } else if(Streak < 0) {
            stringStreak = ChatColor.RED + "Streak de Derrotas: " + ChatColor.RED + Streak*-1;
        } else {
            stringStreak = ChatColor.GRAY + "Streak de Vitorias: " + ChatColor.GRAY + Streak;
        }

        ArrayList<PlayersData> auxList = PlayersStorageUtil.getPlayers();
        Comparator<PlayersData> comparator = Comparator.comparing(PlayersData::getPoints);
        Collections.sort(auxList, comparator.reversed());
        int rankPosition = 0;

        for(int i = 0; i < auxList.size(); i++) {
            if(auxList.get(i).getUuid().equals(uuid)) {
                rankPosition = i+1;
                break;
            }
        }


        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName(ChatColor.GREEN + Bukkit.getOfflinePlayer(uuid).getName());
        headMeta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Posição: " + ChatColor.GREEN + "#" + rankPosition, "" ,ChatColor.GRAY + "Partidas: " + ChatColor.GREEN + matches, ChatColor.GRAY + "Vitorias: " + ChatColor.GREEN + wins, ChatColor.GRAY + "Pontos: " + ChatColor.GREEN + points, ChatColor.GRAY + "Quests Faceis: " + ChatColor.GREEN + QEasy, ChatColor.GRAY + "Quests Medias: " + ChatColor.GREEN + QMedium, ChatColor.GRAY + "Quests Dificeis: " + ChatColor.GREEN + QHard, stringStreak));
        head.setItemMeta(headMeta);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        head.setItemMeta(skullMeta);
        inventory.setItem(13, head);

        Ranks rank = Ranks.getRank(points);
        int quantity = 0;
        if(rank.getTier() == 0){
            quantity = 1;
        } else{
            quantity = rank.getTier();
        }

        ItemStack rankItem = new ItemStack(rank.getIcon(), quantity);
        ItemMeta rankMeta = rankItem.getItemMeta();
        rankMeta.setDisplayName(rank.getColor() + rank.getName());
        List<String> lore = new ArrayList<>();
        lore.add("");
        Ranks nextRank = Ranks.getNextRank(rank);
        if(nextRank != null) {
            int rankPercentage = (int) (((points-rank.getMin()) * 100)/(rank.getMax()-rank.getMin()));
            int decimal = rankPercentage/10;
            String bar = "";
            for(int i = 0; i < decimal; i++){
                bar = bar + "| ";
            }
            String emptyBar = "";
            for(int i = 0; i < 10-decimal; i++){
                emptyBar = emptyBar + "| ";
            }
            lore.add(ChatColor.GRAY + "Proximo Rank: " + nextRank.getColor() + nextRank.getName() + ChatColor.GRAY + " (" + rankPercentage + "%)");
            lore.add(ChatColor.GRAY + "Progresso: " + ChatColor.GREEN + bar + ChatColor.GRAY + emptyBar);
        } else{
            lore.add(ChatColor.GOLD + "Rank Máximo!");
            lore.add(ChatColor.GRAY + "Progresso: " + ChatColor.GREEN + "| | | | | | | | | |");
        }
        rankMeta.setLore(lore);
        rankItem.setItemMeta(rankMeta);
        inventory.setItem(15, rankItem);

        Bukkit.getPlayer(opener).openInventory(inventory);
    }



    public static void givePorfileOpener(Player player){
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatColor.GREEN + "Profile");
        item.setItemMeta(itemMeta);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwningPlayer(player);
        item.setItemMeta(skullMeta);
        player.getInventory().setItem(3, item);
    }
}
