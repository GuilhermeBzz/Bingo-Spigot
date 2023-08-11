package br.com.bingo.commands;

import br.com.bingo.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ConsoleCommand implements CommandExecutor {

    GameManager gameManager;

    public ConsoleCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) return false;
        if(!command.getName().equalsIgnoreCase("adm"))return false;

        if(strings.length == 0) return false;
        if(strings[0].equalsIgnoreCase("kitList")){
            commandSender.sendMessage("");
            commandSender.sendMessage("Lista de kits:");
            for(UUID uuid :gameManager.playerKit.keySet()){
                commandSender.sendMessage(ChatColor.GOLD + Bukkit.getPlayer(uuid).getName() + " - " + gameManager.playerKit.get(uuid));
            }
            commandSender.sendMessage("");
            return true;
        }
        return false;
    }
}
