package br.com.bingo.rank;

import br.com.bingo.rank.models.players.PlayersData;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class RankList {


    public static void openPlayerList(Player player){
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor. GOLD + "Lista de Jogadores");

        for(int i = 0; i < 27; i++){
            inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
        int position = 0;

        for(PlayersData playerData : PlayersStorageUtil.getPlayers()){
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setDisplayName(ChatColor.GOLD + Bukkit.getOfflinePlayer(playerData.getUuid()).getName());
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerData.getUuid()));
            skull.setItemMeta(skullMeta);
            inventory.setItem(position, skull);
            position++;
        }

        player.openInventory(inventory);
    }
}
