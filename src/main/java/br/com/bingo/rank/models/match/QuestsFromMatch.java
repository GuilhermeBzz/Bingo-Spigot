package br.com.bingo.rank.models.match;

import java.util.UUID;

public class QuestsFromMatch {

    private String questName;
    private int difficulty;
    private UUID doneBy;

    public QuestsFromMatch(String questName, int difficulty, UUID doneBy) {
        this.questName = questName;
        this.difficulty = difficulty;
        this.doneBy = doneBy;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isDone() {
        return (doneBy != null);
    }

    public UUID getDoneBy() {
        return doneBy;
    }

    public void setDone(UUID doneBy) {
        this.doneBy = doneBy;
    }
}
