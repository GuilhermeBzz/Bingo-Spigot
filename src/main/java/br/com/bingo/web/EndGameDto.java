package br.com.bingo.web;

import java.util.List;

public class EndGameDto {

    private String gameId;

    private boolean ranked;

    private List<PlayerUpdateDto> playerUpdates;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean isRanked() {
        return ranked;
    }

    public List<PlayerUpdateDto> getPlayerUpdates() {
        return playerUpdates;
    }

    public void setPlayerUpdates(List<PlayerUpdateDto> playerUpdates) {
        this.playerUpdates = playerUpdates;
    }

    public void setRanked(boolean isRanked) {
        this.ranked = isRanked;
    }

    public static String toJson(EndGameDto endGame) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"gameId\":\"").append(endGame.getGameId()).append("\",");
        json.append("\"ranked\":").append(endGame.isRanked()).append(",");

        json.append("\"playerUpdates\":[");
        List<PlayerUpdateDto> playerUpdates = endGame.getPlayerUpdates();
        for (int i = 0; i < playerUpdates.size(); i++) {
            PlayerUpdateDto player = playerUpdates.get(i);
            json.append("{");
            json.append("\"playerName\":\"").append(player.getPlayerName()).append("\",");
            json.append("\"playerId\":\"").append(player.getPlayerId()).append("\",");
            json.append("\"mmr\":").append(player.getMmr());
            json.append("}");
            if (i < playerUpdates.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        json.append("}");
        return json.toString();
    }

}
