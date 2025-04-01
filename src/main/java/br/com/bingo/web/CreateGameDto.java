package br.com.bingo.web;

import java.util.List;

public class CreateGameDto {

    private String team1Name;
    private String team2Name;
    List<QuestDto> quests;

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public List<QuestDto> getQuests() {
        return quests;
    }

    public void setQuests(List<QuestDto> quests) {
        this.quests = quests;
    }


    public static String toJson(CreateGameDto game) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"gameType\":\"").append("Teams").append("\",");
        json.append("\"team1Name\":\"").append(game.getTeam1Name()).append("\",");
        json.append("\"team2Name\":\"").append(game.getTeam2Name()).append("\",");
        json.append("\"quests\":[");

        List<QuestDto> quests = game.getQuests();
        for (int i = 0; i < quests.size(); i++) {
            QuestDto quest = quests.get(i);
            json.append("{");
            json.append("\"name\":\"").append(quest.name).append("\"");
            json.append("}");
            if (i < quests.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        json.append("}");
        return json.toString();
    }
}
