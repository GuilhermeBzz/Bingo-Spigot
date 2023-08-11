package br.com.bingo.commands;

import br.com.bingo.rank.LeaderBoard;
import br.com.bingo.ui.BingoMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpdataLeaderBoardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("updateleaderboard")){
            LeaderBoard.createLeaderBoard();
        }
        return false;
    }
}
