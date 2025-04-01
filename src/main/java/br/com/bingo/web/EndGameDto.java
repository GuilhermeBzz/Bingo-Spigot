package br.com.bingo.web;

public class EndGameDto {

    private String gameId;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public static String toJson(EndGameDto endGame) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"gameId\":\"").append(endGame.getGameId()).append("\"");
        json.append("}");
        return json.toString();
    }
}
