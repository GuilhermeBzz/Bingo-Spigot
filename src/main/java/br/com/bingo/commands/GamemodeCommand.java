package br.com.bingo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {
    
    
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(!player.hasPermission("bingo.endgame")) return false;
        if(strings.length == 0) return false;
        String gamemode = strings[0];
        if(gamemode.equalsIgnoreCase("0") || gamemode.equalsIgnoreCase("survival")) {
            player.setGameMode(org.bukkit.GameMode.SURVIVAL);
            Bukkit.broadcastMessage("O jogador " + player.getName() + " mudou para o modo Survival" );
            return true;
        } else if (gamemode.equalsIgnoreCase("1") || gamemode.equalsIgnoreCase("creative")) {
            player.setGameMode(org.bukkit.GameMode.CREATIVE);
            Bukkit.broadcastMessage("O jogador " + player.getName() + " mudou para o modo Creative" );
            return true;
        }else if(gamemode.equalsIgnoreCase("2") || gamemode.equalsIgnoreCase("adventure")) {
            player.setGameMode(org.bukkit.GameMode.ADVENTURE);
            Bukkit.broadcastMessage("O jogador " + player.getName() + " mudou para o modo Adventure");
            return true;
        } else if (gamemode.equalsIgnoreCase("spectator")) {
            player.setGameMode(org.bukkit.GameMode.SPECTATOR);
            Bukkit.broadcastMessage("O jogador " + player.getName() + " mudou para o modo Spectator" );
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1){
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("spectator");
            return list;
        }

        return null;
    }
}
