package br.com.bingo.quests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.com.bingo.quests.Difficulty;

public class QuestManager {

    public List<Quest> availableQuests = new ArrayList<>();
    public void initializeQuests(int difficulty){
        availableQuests = new ArrayList<>();

        List<Quest> allQuests = new ArrayList<>();
        Collections.addAll(allQuests, Quest.values());
        allQuests.remove(Quest.KILL_PLAYER);
        List<Quest> easyQuests = new ArrayList<>();
        List<Quest> mediumQuests = new ArrayList<>();
        List<Quest> hardQuests = new ArrayList<>();
        Collections.shuffle(allQuests);
        for(Quest quest : allQuests){
            if(quest.getDifficulty() == 1) easyQuests.add(quest);
            if(quest.getDifficulty() == 2) mediumQuests.add(quest);
            if(quest.getDifficulty() == 3) hardQuests.add(quest);
        }
        if(difficulty > 10){
            this.availableQuests.addAll(allQuests.subList(0, 25));
        }else{
            List<Difficulty> difficulties = new ArrayList<>();
            Collections.addAll(difficulties, Difficulty.values());
            for(Difficulty diff : difficulties){
                if(diff.getDifficulty() == difficulty){
                    this.availableQuests.addAll(easyQuests.subList(0, diff.getEasy()));
                    this.availableQuests.addAll(mediumQuests.subList(0, diff.getMedium()));
                    this.availableQuests.addAll(hardQuests.subList(0, diff.getHard()));
                    Collections.shuffle(this.availableQuests);
                }
            }
        }

        ensureQuest(Quest.QUESTION);
    }

    public void removeQuests(Quest quest){
        this.availableQuests.remove(quest);
    }

    public boolean containsQuestType(QuestType questType){
        for(Quest quest : availableQuests){
            if(quest.getType() == questType) return true;
        }
        return false;
    }

    public void ensureQuest (Quest quest){
        if(!availableQuests.contains(quest)){
            availableQuests.remove(0);
            availableQuests.add(quest);
            Collections.shuffle(availableQuests);
        }
    }

    public void replaceQuest(Quest questOld, Quest questNew){
        availableQuests.remove(questOld);
        availableQuests.add(questNew);

        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE + "NEW: " + availableQuests.toString());

    }
}