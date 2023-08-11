package br.com.bingo.rank.utils.match;

import br.com.bingo.Bingo;
import br.com.bingo.rank.models.match.MatchesData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MatchesStorageUtil {

    private static ArrayList<MatchesData> matches = new ArrayList<>();

    public static void addMatch(MatchesData match) {
        matches.add(match);
    }



    public static void saveMatches() throws IOException {
        Gson gson = new Gson();
        File file = new File(Bingo.getInstance().getDataFolder().getAbsolutePath() + "/matches.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(matches, writer);
        writer.flush();
        writer.close();
        Bukkit.getLogger().info("Partidas Atualizadas!");
    }

    public static void loadMatches() throws IOException {
        Gson gson = new Gson();
        File file = new File(Bingo.getInstance().getDataFolder().getAbsolutePath() + "/matches.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            MatchesData[] loadMatches = gson.fromJson(reader, MatchesData[].class);
            matches = new ArrayList<>(Arrays.asList(loadMatches));
            Bukkit.getLogger().info("Partidas Carregadas!");
        }
    }

}
