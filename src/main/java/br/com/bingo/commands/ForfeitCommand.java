package br.com.bingo.commands;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForfeitCommand implements CommandExecutor {

    GameManager gameManager;

    public ForfeitCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        if(!command.getName().equalsIgnoreCase("ff")) return false;
        if(!gameManager.isGameStarted()) return false;
        //if(gameManager.getGameType().equals(GameType.SOLO)) return false;

        gameManager.forfeitCommand((Player) commandSender);

        return false;
    }
}
