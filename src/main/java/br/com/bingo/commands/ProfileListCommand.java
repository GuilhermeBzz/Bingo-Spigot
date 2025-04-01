package br.com.bingo.commands;

import br.com.bingo.rank.RankList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("profiles")){
            if(commandSender instanceof Player){
                Player player = (Player) commandSender;
                RankList.openPlayerList(player);
            }
            return true;
        }
        return false;
    }
}
