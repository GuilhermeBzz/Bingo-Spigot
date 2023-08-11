package br.com.bingo.rank.utils.quests;

import br.com.bingo.Bingo;
import br.com.bingo.rank.models.quests.QuestsData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestsStorageUtil {

    private static ArrayList<QuestsData> quests = new ArrayList<>();

    public static void addQuest(QuestsData quest) {
        quests.add(quest);
    }


    public static void updateQuest(QuestsData quest){
        for(QuestsData q : quests) {
            if(q.getName().equalsIgnoreCase(quest.getName())) {
                q.setDone(q.getDone() + quest.getDone());
                q.setNotDone(q.getNotDone() + quest.getNotDone());
                break;
            }
        }
    }

    public static boolean checkQuest(QuestsData newQuest) {
        for(QuestsData quest : quests) {
            if(quest.getName().equalsIgnoreCase(newQuest.getName())) {
                return true;
            }
        }
        return false;
    }


    public static void saveQuests() throws IOException {
        Gson gson = new Gson();
        File file = new File(Bingo.getInstance().getDataFolder().getAbsolutePath() + "/quests.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(quests, writer);
        writer.flush();
        writer.close();
        Bukkit.getLogger().info("Quests Atualizados!");
    }

    public static void loadQuests() throws IOException {
        Gson gson = new Gson();
        File file = new File(Bingo.getInstance().getDataFolder().getAbsolutePath() + "/quests.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            QuestsData[] loadQuests = gson.fromJson(reader, QuestsData[].class);
            quests = new ArrayList<>(Arrays.asList(loadQuests));
            Bukkit.getLogger().info("Quests Carregadas!");
        }
    }


}
