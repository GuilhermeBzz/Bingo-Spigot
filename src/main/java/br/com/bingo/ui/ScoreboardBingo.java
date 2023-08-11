package br.com.bingo.ui;

import br.com.bingo.game.GameManager;
import br.com.bingo.game.GameType;
import br.com.bingo.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;


public class ScoreboardBingo {
    Boolean isFill = false;
    GameManager gameManager;

    public ScoreboardBingo(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    HashMap<UUID, Objective> scoreboardHashMap;
    public void startScoreboard(){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if(scoreboardManager == null) return;
        scoreboardHashMap = new HashMap<>();
        for(UUID uuid: gameManager.playerTeam.keySet()){
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("bingo", Criteria.DUMMY, ChatColor.GOLD + String.valueOf(ChatColor.BOLD) + "Placar do Bingo");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            scoreboardHashMap.put(uuid, objective);
        }
        isFill = false;
        updateScoreboard();
    }

    public void updateScoreboard() {
        if(isFill){
            deleteScoreboard();
            reStartScoreboard();
        }
        isFill = true;

        for(UUID uuid : scoreboardHashMap.keySet()){
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) continue;
            int score = 0;
            scoreboardHashMap.get(uuid).getScore(ChatColor.AQUA + String.valueOf(ChatColor.BOLD) + "BzCraft.me").setScore(score++);
            scoreboardHashMap.get(uuid).getScore(ChatColor.WHITE + "-------------------").setScore(score++);
            scoreboardHashMap.get(uuid).getScore(" ").setScore(score++);
            String mode = null;
            scoreboardHashMap.get(uuid).getScore(ChatColor.GREEN + "Quests disponíveis: " + ChatColor.WHITE + gameManager.getAvailableQuests().size()).setScore(score++);
            if(gameManager.getGameType().equals(GameType.SOLO)){
                for(UUID uuid1 : gameManager.playerPoints.keySet()){
                    Player player1 = Bukkit.getPlayer(uuid1);
                    if(player1 != null){
                        scoreboardHashMap.get(uuid).getScore(ChatColor.LIGHT_PURPLE + player1.getName() + ": " + ChatColor.WHITE + gameManager.playerPoints.get(uuid1)).setScore(score++);
                    }
                }
                mode = "Solo";
            }else{
                scoreboardHashMap.get(uuid).getScore(ChatColor.RED + "Time Vermelho: " + ChatColor.WHITE + gameManager.teamPoints.get(TeamType.TEAM_RED)).setScore(score++);
                scoreboardHashMap.get(uuid).getScore(ChatColor.BLUE + "Time Azul: " + ChatColor.WHITE + gameManager.teamPoints.get(TeamType.TEAM_BLUE)).setScore(score++);
                mode = "Times";
            }

            scoreboardHashMap.get(uuid).getScore("  ").setScore(score++);
            if(!gameManager.getGameType().equals(GameType.SOLO)){
                if (gameManager.getPlayerTeam(player).equals(TeamType.TEAM_RED)) {
                    scoreboardHashMap.get(uuid).getScore(ChatColor.WHITE + "Seu Time: " + ChatColor.RED + "Vermelho").setScore(score++);
                } else if (gameManager.getPlayerTeam(player).equals(TeamType.TEAM_BLUE)) {
                    scoreboardHashMap.get(uuid).getScore(ChatColor.WHITE + "Seu Time: " + ChatColor.BLUE + "Azul").setScore(score++);
                }
            }
            if(gameManager.kit){
                scoreboardHashMap.get(uuid).getScore(ChatColor.WHITE + "Kit: " + ChatColor.GREEN + getPlayerKit(player)).setScore(score++);
            }
            scoreboardHashMap.get(uuid).getScore(ChatColor.WHITE + "Modo: " + ChatColor.GOLD+ mode).setScore(score++);
            scoreboardHashMap.get(uuid).getScore("      ").setScore(score++);
            scoreboardHashMap.get(uuid).getScore(ChatColor.WHITE + "-=-=-=-=-=-=-=-=-=-").setScore(score++);
            Scoreboard scoreboard = scoreboardHashMap.get(uuid).getScoreboard();
            if(scoreboard == null) continue;
            player.setScoreboard(scoreboard);

        }

    }

    public void deleteScoreboard() {
        for(UUID uuid : scoreboardHashMap.keySet()){
            Scoreboard scoreboard = scoreboardHashMap.get(uuid).getScoreboard();
            if(scoreboard == null) continue;
            scoreboard.getObjectives().forEach(Objective::unregister);
        }
    }

    public void reStartScoreboard(){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if(scoreboardManager == null) return;

        for(UUID uuid : scoreboardHashMap.keySet()){
            Scoreboard scoreboard = scoreboardHashMap.get(uuid).getScoreboard();
            if(scoreboard == null) continue;
            Objective objective = scoreboard.registerNewObjective("bingo", Criteria.DUMMY, ChatColor.GOLD + String.valueOf(ChatColor.BOLD) + "Placar do Bingo");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            scoreboardHashMap.put(uuid, objective);
        }
    }

    public String getPlayerKit(Player player){
        return gameManager.playerKit.get(player.getUniqueId()).getName();
    }

}
