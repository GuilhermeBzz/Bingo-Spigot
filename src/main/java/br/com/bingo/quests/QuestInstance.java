package br.com.bingo.quests;

import java.util.UUID;

public record QuestInstance(Quest quest, UUID uuid) {

    public QuestInstance(Quest quest){
       this(quest, quest.equals(Quest.QUESTION) ? UUID.randomUUID() : null);
    }

}
