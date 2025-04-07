package br.com.bingo.rank;

import br.com.bingo.rank.models.players.PlayersData;
import br.com.bingo.rank.utils.players.PlayersStorageUtil;
import br.com.bingo.team.TeamType;
import br.com.bingo.quests.Quest;
import org.bukkit.Bukkit;

import java.util.*;

public class RankCalculator {
    
    public static class RankResult {
        public final UUID playerUUID;
        public final String playerName;
        public final int points;
        public final int easyQuests;
        public final int mediumQuests;
        public final int hardQuests;
        public final boolean isWinner;
        
        public RankResult(UUID playerUUID, String playerName, int points, int easyQuests, int mediumQuests, int hardQuests, boolean isWinner) {
            this.playerUUID = playerUUID;
            this.playerName = playerName;
            this.points = points;
            this.easyQuests = easyQuests;
            this.mediumQuests = mediumQuests;
            this.hardQuests = hardQuests;
            this.isWinner = isWinner;
        }
    }

    public static List<RankResult> calculateRankResults(Map<UUID, TeamType> playerTeam, Map<Quest, UUID> playerQuests, TeamType teamWinner) {
        List<RankResult> results = new ArrayList<>();
        
        // Count quests completed by each player
        Map<UUID, Integer> easyQuestCompleted = new HashMap<>();
        Map<UUID, Integer> mediumQuestCompleted = new HashMap<>();
        Map<UUID, Integer> hardQuestCompleted = new HashMap<>();
        
        for(Quest quest : playerQuests.keySet()) {
            if(playerQuests.get(quest) != null) {
                UUID playerUUID = playerQuests.get(quest);
                switch (quest.getDifficulty()) {
                    case 1:
                        easyQuestCompleted.merge(playerUUID, 1, Integer::sum);
                        break;
                    case 2:
                        mediumQuestCompleted.merge(playerUUID, 1, Integer::sum);
                        break;
                    case 3:
                        hardQuestCompleted.merge(playerUUID, 1, Integer::sum);
                        break;
                }
            }
        }
        
        // Calculate results for each player
        for(UUID uuid : playerTeam.keySet()) {
            String playerName = Bukkit.getOfflinePlayer(uuid).getName();
            int easy = easyQuestCompleted.getOrDefault(uuid, 0);
            int medium = mediumQuestCompleted.getOrDefault(uuid, 0);
            int hard = hardQuestCompleted.getOrDefault(uuid, 0);
            boolean isWinner = playerTeam.get(uuid) == teamWinner;
            
            // Calculate points based on quests and win status
            int points = calculatePoints(easy, medium, hard, isWinner);
            
            results.add(new RankResult(uuid, playerName, points, easy, medium, hard, isWinner));
        }
        
        return results;
    }
    
    private static int calculatePoints(int easyQuests, int mediumQuests, int hardQuests, boolean isWinner) {
        int points = 0;
        
        // Base points for quests
        points += easyQuests; // 1 point per easy quest
        points += mediumQuests * 3; // 3 points per medium quest
        points += hardQuests * 5; // 5 points per hard quest
        
        // Win/lose points
        if(isWinner) {
            points += 25; // Win bonus
        } else {
            points -= 15; // Lose penalty
        }
        
        return Math.max(0, points); // Ensure points don't go below 0
    }
    
    public static void updatePlayerRanks(List<RankResult> results) {
        for(RankResult result : results) {
            PlayersData playerData = new PlayersData(
                result.playerUUID,
                result.isWinner ? 1 : 0,
                1, // One match played
                result.easyQuests,
                result.mediumQuests,
                result.hardQuests
            );
            
            if(!PlayersStorageUtil.checkPlayer(playerData)) {
                PlayersStorageUtil.createPlayer(playerData);
            } else {
                PlayersStorageUtil.updatePlayer(playerData);
            }
        }
    }
} 