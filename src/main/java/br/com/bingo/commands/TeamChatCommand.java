package br.com.bingo.commands;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameStatus;
import br.com.bingo.game.GameType;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamChatCommand implements CommandExecutor {

    GameManager gameManager;

    public TeamChatCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("teamchat") || command.getName().equalsIgnoreCase("tc")){
            if(commandSender instanceof Player){
                if(!gameManager.getGameStatus().equals(GameStatus.CREATED) && !gameManager.isGameStarted()){
                    commandSender.sendMessage(ChatColor.RED + "Você não pode usar esse comando agora!");
                    return false;
                }
                if(gameManager.getGameType().equals(GameType.SOLO)){
                    commandSender.sendMessage(ChatColor.RED + "Você não pode usar esse comando no modo solo!");
                    return false;
                }
                if(strings.length == 0){
                    commandSender.sendMessage(ChatColor.RED + "Uso correto: /teamchat <mensagem>");
                    return false;
                }
                TeamType senderTeam = gameManager.playerTeam.get(((Player) commandSender).getUniqueId());
                for(UUID uuid: gameManager.playerTeam.keySet()){
                    if(gameManager.playerTeam.get(uuid).equals(senderTeam)){
                        Player player = Bukkit.getPlayer(uuid);
                        String tc = "";
                        if(senderTeam.equals(TeamType.TEAM_RED)){
                            tc = ChatColor.RED + "[TC] ";
                        } else if (senderTeam.equals(TeamType.TEAM_BLUE)) {
                            tc = ChatColor.BLUE + "[TC] ";
                        }
                        player.sendMessage(tc + ChatColor.GOLD + ((Player) commandSender).getName() + ChatColor.WHITE + ": " + String.join(" ", strings));
                    }

                }



            }
            return true;
        }
        return false;
    }
}
