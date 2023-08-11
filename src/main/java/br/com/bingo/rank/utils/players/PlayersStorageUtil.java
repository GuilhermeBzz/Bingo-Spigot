package br.com.bingo.rank.utils.players;

import br.com.bingo.Bingo;
import br.com.bingo.rank.models.match.MatchesData;
import br.com.bingo.rank.models.players.PlayersData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayersStorageUtil {

    private static ArrayList<PlayersData> players = new ArrayList<>();


    public static void createPlayer(PlayersData player) {
        player.setPoints(player.getPoints() + 50);
        players.add(player);
    }

    public static void updatePlayer(PlayersData newPlayer) {
        for(PlayersData player : players) {
            if(player.getUuid().equals(newPlayer.getUuid())) {
                player.setWins(player.getWins() + newPlayer.getWins());
                player.setMatches(player.getMatches() + newPlayer.getMatches());
                player.setQEasy(player.getQEasy() + newPlayer.getQEasy());
                player.setQMedium(player.getQMedium() + newPlayer.getQMedium());
                player.setQHard(player.getQHard() + newPlayer.getQHard());
                player.setPoints(Math.max(0, player.getPoints() + newPlayer.getPoints()));

                int currentStreakSignal = player.getStreak() > 0 ? 1 : -1;
                int newStreakSignal = newPlayer.getStreak() > 0 ? 1 : -1;
                if(currentStreakSignal == newStreakSignal) {
                    player.setStreak(player.getStreak() + newPlayer.getStreak());
                } else {
                    player.setStreak(newPlayer.getStreak());
                }
            }
        }
    }
    public static boolean checkPlayer(PlayersData newPlayer) {
        for(PlayersData player : players) {
            if(player.getUuid().equals(newPlayer.getUuid())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkPlayerInstance(Player player){
        for(PlayersData playersData : players){
            if(playersData.getUuid().equals(player.getUniqueId())){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<PlayersData> getPlayers() {
        return players;
    }

    public static PlayersData getPlayer(String uuid) {
        for(PlayersData player : players) {
            if(player.getUuid().toString().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    public static void savePlayers() throws IOException {
        Gson gson = new Gson();
        File file = new File(Bingo.getInstance().getDataFolder().getAbsolutePath() + "/players.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(players, writer);
        writer.flush();
        writer.close();
        Bukkit.getLogger().info("Jogadores Atualizados!");
    }

    public static void loadPlayers() throws IOException {
        Gson gson = new Gson();
        File file = new File(Bingo.getInstance().getDataFolder().getAbsolutePath() + "/players.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            PlayersData[] loadPlayers = gson.fromJson(reader, PlayersData[].class);
            players = new ArrayList<>(Arrays.asList(loadPlayers));
            Bukkit.getLogger().info("Jogadores Carregados!");
        }
    }
}
