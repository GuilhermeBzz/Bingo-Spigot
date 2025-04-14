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
    private boolean isFill = false;
    private final GameManager gameManager;
    private final HashMap<UUID, Objective> scoreboardHashMap = new HashMap<>();
    private final HashMap<UUID, Scoreboard> playerScoreboards = new HashMap<>();

    public ScoreboardBingo(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void startScoreboard() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if (scoreboardManager == null) return;

        scoreboardHashMap.clear();
        playerScoreboards.clear();

        for (UUID uuid : gameManager.playerTeam.keySet()) {
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("bingo", Criteria.DUMMY, ChatColor.GOLD + "" + ChatColor.BOLD + "Placar do Bingo");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            scoreboardHashMap.put(uuid, objective);
            playerScoreboards.put(uuid, scoreboard);
        }

        isFill = false;
        updateScoreboard();
    }

    public void updateScoreboard() {
        if (isFill) {
            deleteScoreboard();
            reStartScoreboard();
        }

        isFill = true;

        for (UUID uuid : scoreboardHashMap.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;

            Objective objective = scoreboardHashMap.get(uuid);
            Scoreboard scoreboard = playerScoreboards.get(uuid);
            if (objective == null || scoreboard == null) continue;

            int score = 0;
            objective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "BzCraft.me").setScore(score++);
            objective.getScore(ChatColor.WHITE + "-------------------").setScore(score++);
            objective.getScore(" ").setScore(score++);
            objective.getScore(ChatColor.GREEN + "Quests disponíveis: " + ChatColor.WHITE + gameManager.getAvailableQuests().size()).setScore(score++);

            String mode;

            if (gameManager.getGameType().equals(GameType.SOLO)) {
                for (UUID uuid1 : gameManager.playerPoints.keySet()) {
                    Player player1 = Bukkit.getPlayer(uuid1);
                    if (player1 != null) {
                        objective.getScore(ChatColor.LIGHT_PURPLE + player1.getName() + ": " + ChatColor.WHITE + gameManager.playerPoints.get(uuid1)).setScore(score++);
                    }
                }
                mode = "Solo";
            } else {
                objective.getScore(ChatColor.RED + "Time Vermelho: " + ChatColor.WHITE + gameManager.teamPoints.get(TeamType.TEAM_RED)).setScore(score++);
                objective.getScore(ChatColor.BLUE + "Time Azul: " + ChatColor.WHITE + gameManager.teamPoints.get(TeamType.TEAM_BLUE)).setScore(score++);
                mode = "Times";
            }

            objective.getScore("  ").setScore(score++);
            if (!gameManager.getGameType().equals(GameType.SOLO)) {
                if (gameManager.getPlayerTeam(player).equals(TeamType.TEAM_RED)) {
                    objective.getScore(ChatColor.WHITE + "Seu Time: " + ChatColor.RED + "Vermelho").setScore(score++);
                } else if (gameManager.getPlayerTeam(player).equals(TeamType.TEAM_BLUE)) {
                    objective.getScore(ChatColor.WHITE + "Seu Time: " + ChatColor.BLUE + "Azul").setScore(score++);
                }
            }

            if (gameManager.kit) {
                objective.getScore(ChatColor.WHITE + "Kit: " + ChatColor.GREEN + getPlayerKit(player)).setScore(score++);
            }

            objective.getScore(ChatColor.WHITE + "Modo: " + ChatColor.GOLD + mode).setScore(score++);
            objective.getScore("      ").setScore(score++);
            objective.getScore(ChatColor.WHITE + "-=-=-=-=-=-=-=-=-=-").setScore(score++);

            player.setScoreboard(scoreboard);
        }
    }

    public void deleteScoreboard() {
        for (UUID uuid : scoreboardHashMap.keySet()) {
            Scoreboard scoreboard = playerScoreboards.get(uuid);
            if (scoreboard == null) continue;

            scoreboard.getObjectives().forEach(Objective::unregister);
        }
    }

    public void reStartScoreboard() {
        for (UUID uuid : scoreboardHashMap.keySet()) {
            Scoreboard scoreboard = playerScoreboards.get(uuid);
            if (scoreboard == null) continue;

            Objective objective = scoreboard.registerNewObjective("bingo", Criteria.DUMMY, ChatColor.GOLD + "" + ChatColor.BOLD + "Placar do Bingo");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            scoreboardHashMap.put(uuid, objective);
        }
    }

    public String getPlayerKit(Player player) {
        return gameManager.playerKit.get(player.getUniqueId()).getName();
    }

    public Scoreboard getPlayerScoreboard(Player player) {
        return playerScoreboards.get(player.getUniqueId());
    }
}
