package br.com.bingo.commands;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BingoCommand implements CommandExecutor, TabCompleter {

    GameManager gameManager;
    CommandSender console = Bukkit.getServer().getConsoleSender();

    public BingoCommand(GameManager gameManager) {
        Bukkit.getLogger().info("Iniciando BingoCommand...");
        this.gameManager = gameManager;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            if(args.length > 0 && args[0].equalsIgnoreCase("cancel")){
                gameManager.cancelCommand();
                return true;
            }
            sender.sendMessage("Este comando só pode ser executado por jogadores.");
            return true;
        }
        Player player = (Player) sender;

        if(args.length > 0){
            if (args[0].equalsIgnoreCase("cancel")) {
                gameManager.cancelCommand(player);
                return true;
            } else if (args[0].equalsIgnoreCase("leader")) {
                //Logica de dar leader para alguem
                if(!gameManager.isGameStarted() && gameManager.existGame() && player.getUniqueId() == gameManager.getGameMaster() && gameManager.getGameType() == GameType.TEAM_MANUAL){
                    if(args.length > 1){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target == null){
                            player.sendMessage(ChatColor.RED + "Jogador não encontrado");
                            return false;
                        }
                        if(target.equals(player)){
                            player.sendMessage(ChatColor.RED + "Nao foi possivel adicionar um jogador como lider.");
                            return false;
                        }
                        gameManager.setTeamLeader(target, player);
                        gameManager.addPlayerToTeam(target, TeamType.TEAM_BLUE);
                        player.sendMessage(ChatColor.GREEN + target.getName() + " foi definido como Líder do Time" + ChatColor.BLUE + " Azul");
                        target.sendMessage(ChatColor.GREEN + "Agora você é líder do Time" + ChatColor.BLUE + " Azul");
                        return false;
                    }
                }
                player.sendMessage(ChatColor.RED + "Nao foi possivel adicionar um jogador como lider.");
                return false;

            } else if (args[0].equalsIgnoreCase("add")) {
                //Logica de adicao de jogador para o time
                if(!gameManager.isGameStarted() && gameManager.existGame() && gameManager.leaderOrMaster(player)){
                    if(args.length > 1){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target == null){
                            player.sendMessage(ChatColor.RED + "Jogador não encontrado");
                            return false;
                        }
                        if(!gameManager.checkPlayerTeam(target)){
                            TeamType playerTeam = gameManager.getPlayerTeam(player);
                            gameManager.addPlayerToTeam(target, playerTeam);
                            player.sendMessage(ChatColor.GREEN + "Jogador adicionado com sucesso!");
                            return false;
                        }
                        player.sendMessage(ChatColor.RED + "Esse jogador ja esta em um time!");

                    }
                } else{
                    player.sendMessage(ChatColor.RED + "Erro. Ou a partida não está no estado de criação ou você não tem essa permissão.");
                    return false;
                }
            }else if (args[0].equalsIgnoreCase("help")){
                player.sendMessage(ChatColor.WHITE + "-=-=-=-=-=-=-" + ChatColor.LIGHT_PURPLE + "Bingo" + ChatColor.WHITE + "-=-=-=-=-=-=-");
                player.sendMessage(ChatColor.AQUA + "/bingo cancel" + ChatColor.WHITE + " - Cancela a partida de bingo");
                player.sendMessage(ChatColor.AQUA + "/bingo leader <player> " + ChatColor.WHITE + "- Define um jogador como líder da equipe 2 (TEAM MANUAL)");
                player.sendMessage(ChatColor.AQUA + "/bingo add <player> " + ChatColor.WHITE + "- Adiciona um jogador ao seu time (TEAM MANUAL)");
                player.sendMessage(ChatColor.AQUA + "/bingo" + ChatColor.WHITE + " - Recebe uma Cartela de Bingo");
                player.sendMessage(ChatColor.AQUA + "/teamchat ou /tc" + ChatColor.WHITE + " - Envie uma mensagem para o seu time");
                player.sendMessage(ChatColor.AQUA + "/ff" + ChatColor.WHITE + " - Desista de uma partida de Time");
            } else{
                player.sendMessage(ChatColor.RED + "Essa opção não existe.");
            }
        } else{
            if(gameManager.isGameStarted()){
                ItemStack item = new ItemStack(Material.PAPER);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + "Cartela do Bingo");
                item.setItemMeta(meta);
                player.getInventory().addItem(item);
            }else {
                player.sendMessage(ChatColor.RED + "Nenhum partida iniciada. Crie e inicie uma nova partida para abrir o inventario.");
            }
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1){
            list.add("cancel");
            list.add("leader");
            list.add("add");
            list.add("help");
        } else if(strings.length == 2){
             if(strings[0].equalsIgnoreCase("leader")){
                for(Player player : Bukkit.getOnlinePlayers()){
                    list.add(player.getName());
                }
            } else if(strings[0].equalsIgnoreCase("add")){
                for(Player player : Bukkit.getOnlinePlayers()){
                    list.add(player.getName());
                }
            }
        }
        return list;
    }
}
