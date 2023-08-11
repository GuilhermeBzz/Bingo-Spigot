package br.com.bingo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(!player.isOp()) return false;
        if(!command.getName().equalsIgnoreCase("invsee")) return false;
        if(args.length != 1) return false;
        Player target = player.getServer().getPlayer(args[0]);
        if(target == null) return false;
        if(!target.isOnline()) return false;
        player.openInventory(target.getInventory());
        return false;
    }
}
