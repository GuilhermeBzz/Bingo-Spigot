package br.com.bingo.rank.models.match;

import java.util.ArrayList;

public class MatchesData {

    private String id;
    private ArrayList<QuestsFromMatch> quests;
    private ArrayList<PlayersFromMatch> players;


    public MatchesData(String id, ArrayList<QuestsFromMatch> quests, ArrayList<PlayersFromMatch> players) {
        this.id = id;
        this.quests = quests;
        this.players = players;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<QuestsFromMatch> getQuests() {
        return quests;
    }

    public void setQuests(ArrayList<QuestsFromMatch> quests) {
        this.quests = quests;
    }

    public ArrayList<PlayersFromMatch> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayersFromMatch> players) {
        this.players = players;
    }
}
