package br.com.bingo.commands;

import br.com.bingo.rank.profile.PlayerProfile;
import br.com.bingo.ui.BingoMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ProfileCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("profile")){
            if(commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (strings.length == 0) {
                    PlayerProfile.openProfile(player.getUniqueId(), player.getUniqueId());
                } else {
                    OfflinePlayer player1 = Bukkit.getOfflinePlayer(strings[0]);
                    if (player1 != null) {
                        PlayerProfile.openProfile(player.getUniqueId(), player1.getUniqueId());
                    } else {
                        player.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                    }
                }
                return true;
            }
        }
        return false;
    }
}
