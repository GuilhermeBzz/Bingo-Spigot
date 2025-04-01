package br.com.bingo.web;

import java.util.List;

public class CreateGameSoloDto {

    private List<String> players;
    private List<QuestDto> quests;

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<QuestDto> getQuests() {
        return quests;
    }

    public void setQuests(List<QuestDto> quests) {
        this.quests = quests;
    }

    public static String toJson(CreateGameSoloDto gameSolo) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"gameType\":\"").append("Solo").append("\",");
        json.append("\"team1Name\":\"").append("Red").append("\",");
        json.append("\"team2Name\":\"").append("Blue").append("\",");
        json.append("\"players\":[");
        List<String> players = gameSolo.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            json.append("\"").append(players.get(i)).append("\"");
            if (i < players.size() - 1) {
                json.append(",");
            }
        }
        json.append("],");

        json.append("\"quests\":[");
        List<QuestDto> quests = gameSolo.getQuests();
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
