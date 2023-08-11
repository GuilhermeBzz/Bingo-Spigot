package br.com.bingo.rank.models.players;

import java.util.UUID;

public class PlayersData {
    private UUID uuid;
    private int wins;
    private int matches;
    private int points;
    private int QEasy;
    private int QMedium;
    private int QHard;

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    private int streak;

    public PlayersData(UUID uuid, int wins, int matches, int QEasy, int QMedium, int QHard) {
        this.uuid = uuid;
        this.wins = wins;
        this.matches = matches;
        int lose = 0;
        if(wins == 0) lose = 1;
        this.points = (wins * 25) + (QEasy) + (QMedium * 3) + (QHard * 5) - (lose * 15);
        this.QEasy = QEasy;
        this.QMedium = QMedium;
        this.QHard = QHard;
        if(wins == 1){
            this.streak = 1;
        } else{
            this.streak = -1;
        }
    }

    public PlayersData(UUID uuid){
        this.uuid = uuid;
        this.wins = 0;
        this.matches = 0;
        this.points = 0;
        this.QEasy = 0;
        this.QMedium = 0;
        this.QHard = 0;
        this.streak = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getQEasy() {
        return QEasy;
    }

    public void setQEasy(int QEasy) {
        this.QEasy = QEasy;
    }

    public int getQMedium() {
        return QMedium;
    }

    public void setQMedium(int QMedium) {
        this.QMedium = QMedium;
    }

    public int getQHard() {
        return QHard;
    }

    public void setQHard(int QHard) {
        this.QHard = QHard;
    }
}
