package br.com.bingo.web;

public class UpdateQuestDto {

    String gameId;
    String questName;
    String newQuestName;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getNewQuestName() {
        return newQuestName;
    }

    public void setNewQuestName(String newQuestName) {
        this.newQuestName = newQuestName;
    }

    public static String toJson(UpdateQuestDto updateQuest) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"gameId\":\"").append(updateQuest.getGameId()).append("\",");
        json.append("\"questName\":\"").append(updateQuest.getQuestName()).append("\",");
        json.append("\"newQuestName\":\"").append(updateQuest.getNewQuestName()).append("\"");
        json.append("}");
        return json.toString();
    }
}
