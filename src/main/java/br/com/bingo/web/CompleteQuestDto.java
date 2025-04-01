package br.com.bingo.web;

public class CompleteQuestDto {

    private String gameId;
    private String teamName;
    private String playerName;
    private String questName;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }


    public static String toJson(CompleteQuestDto completeQuest) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"gameId\":\"").append(completeQuest.getGameId()).append("\",");
        json.append("\"teamName\":\"").append(completeQuest.getTeamName()).append("\",");
        json.append("\"playerName\":\"").append(completeQuest.getPlayerName()).append("\",");
        json.append("\"questName\":\"").append(completeQuest.getQuestName()).append("\"");
        json.append("}");
        return json.toString();
    }
}
