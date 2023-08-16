package br.com.bingo.commands;

import br.com.bingo.game.GameManager;
import br.com.bingo.rank.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class SkinCommand implements CommandExecutor, TabCompleter {

    GameManager gameManager;

    public SkinCommand(GameManager gameManager) {
        this.gameManager = gameManager;

    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(gameManager.isGameStarted()) return false;

        if(strings.length == 0) return false;
        String skin = strings[0];
        if(skin.equalsIgnoreCase("reset")){
            player.sendMessage("Resetando skin...");
            NickAPI.resetSkin(player);

        } else{
            player.sendMessage("Mudando skin para " + skin);
            NickAPI.setSkin(player, skin);
        }
        NickAPI.refreshPlayer(player);
        for(Player online : Bukkit.getOnlinePlayers()){
            online.hidePlayer(player);
            online.showPlayer(player);
        }
        Ranks.setPrefixAndDisplayName(player, gameManager.playerTeam);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1){
            list.add("reset");
            return list;
        }
        return null;
    }
}
