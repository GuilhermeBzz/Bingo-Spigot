package br.com.bingo.quests;

import br.com.bingo.game.GameType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.bingo.quests.Difficulty;

public class QuestManager {

    public List<Quest> availableQuests = new ArrayList<>();

    public void initializeQuests(int difficulty, int specialQuests){
        availableQuests = new ArrayList<>();

        List<Quest> allQuests = new ArrayList<>();
        Collections.addAll(allQuests, Quest.values());
        allQuests.removeIf(quest -> quest.getDifficulty() == 4);
        allQuests.removeIf(quest -> quest.getDifficulty() == 0);
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

        if(specialQuests != 0) setSpecialQuests(specialQuests);
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

    public void setSpecialQuests (int specialQuests){
        Collections.shuffle(availableQuests);

        Quest quest = Quest.QUESTION;

        for(int i = 0; i < specialQuests; i++){
            if(availableQuests.size() > 0){
                Quest questToRemove = availableQuests.get(0);
                availableQuests.remove(questToRemove);
                availableQuests.add(quest);
            }
        }
        Collections.shuffle(availableQuests);
    }

    public void replaceQuest(Quest questOld, Quest questNew){
        for(Quest quest : availableQuests){
            if(quest.equals(questOld)){
                availableQuests.remove(questOld);
                availableQuests.add(questNew);
                return;
            }
        }


        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE + "NEW: " + availableQuests.toString());

    }


    public List<Quest> getSpecialQuest(GameType gameType, int specialQuestNumber){

        ArrayList<Quest> allQuests = new ArrayList<>();
        Collections.addAll(allQuests, Quest.values());
        List<Quest> specialQuests =  allQuests.stream().filter(quest ->
                quest.getDifficulty() == 4).collect(Collectors.toList());

        if(gameType.equals(GameType.SOLO)){
            specialQuests.removeIf(quest -> quest.getType().equals(QuestType.DOMINATION));
            specialQuests.removeIf(quest -> quest.getType().equals(QuestType.CAPTURE));
        }

        specialQuests.removeIf(quest-> availableQuests.contains(quest));

        List<Quest> armorQuests =  allQuests.stream().filter(quest ->
                quest.getType() == QuestType.FULL_SET).collect(Collectors.toList());

        Collections.shuffle(armorQuests);
        Quest armorQuest = armorQuests.get(0);

        specialQuests.removeIf(quest -> quest.getTarget() == QuestType.FULL_SET && quest.equals(armorQuest));

        Collections.shuffle(specialQuests);

        //return allQuests.stream().filter(q -> q.getType().equals(QuestType.CAPTURE)).collect(Collectors.toList()).get(0);

        return specialQuests.subList(0, specialQuestNumber);
    }
}