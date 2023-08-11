package br.com.bingo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    
    
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        if(strings.length == 0) return false;
        Player player = (Player) commandSender;
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
}
