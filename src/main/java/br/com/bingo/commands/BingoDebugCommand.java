package br.com.bingo.commands;

import br.com.bingo.game.GameManager;
import br.com.bingo.ui.BingoMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BingoDebugCommand implements CommandExecutor {

    GameManager gameManager;

    public BingoDebugCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("bingoDebug")){
            if(commandSender instanceof Player){
                Player player = (Player) commandSender;
                if(!player.isOp()) return false;
            }
            commandSender.sendMessage(ChatColor.BLUE + gameManager.playerPoints.toString());
            commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "Player Kits: " + gameManager.playerKit.toString());
            commandSender.sendMessage(ChatColor.BLUE + "Player Team: " + gameManager.playerTeam.toString());
            commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "Player Quests: " + gameManager.playerQuests.toString());
            commandSender.sendMessage(ChatColor.BLUE + "Available Quests: " + gameManager.getAvailableQuests().toString());
            commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "Team Points: " + gameManager.teamPoints.toString());
            commandSender.sendMessage(ChatColor.BLUE + "Team Quests: "+ gameManager.teamQuests.toString());
            commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "Biome Locations: " + gameManager.biomeLocationMap.toString());
            commandSender.sendMessage(ChatColor.BLUE + "Game Status: " + gameManager.getGameStatus().toString());
            commandSender.sendMessage(gameManager.isGameStarted() ? ChatColor.LIGHT_PURPLE + "Game Started" : ChatColor.LIGHT_PURPLE + "Game Not Started");

            return true;
        }
        return false;
    }
}
