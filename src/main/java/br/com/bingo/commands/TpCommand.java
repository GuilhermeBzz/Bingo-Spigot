package br.com.bingo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(!player.hasPermission("bingo.endgame")) return false;
        if(strings.length == 0) return false;
        Player target = player.getServer().getPlayer(strings[0]);
        if(target == null) return false;
        player.teleport(target);


        return false;
    }
}
