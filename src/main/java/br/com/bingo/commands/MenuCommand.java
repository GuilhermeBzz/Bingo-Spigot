package br.com.bingo.commands;

import br.com.bingo.Bingo;
import br.com.bingo.rank.LeaderBoard;
import br.com.bingo.ui.BingoMenu;
import br.com.bingo.game.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class MenuCommand implements CommandExecutor {

    GameManager gameManager;

    public MenuCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("menu")){
            if(commandSender instanceof Player){
                Player player = (Player) commandSender;
                BingoMenu.openMenu(player);
            }
            return true;
        }
        return false;
    }

}


