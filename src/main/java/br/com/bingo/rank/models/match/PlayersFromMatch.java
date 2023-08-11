package br.com.bingo.rank.models.match;

import java.util.UUID;

public class PlayersFromMatch {

    private UUID uuid;
    private String name;
    private Boolean win;
    private int QEasy;
    private int QMedium;
    private int QHard;

    public PlayersFromMatch(UUID uuid, String name, Boolean win, int QEasy, int QMedium, int QHard) {
        this.uuid = uuid;
        this.name = name;
        this.win = win;
        this.QEasy = QEasy;
        this.QMedium = QMedium;
        this.QHard = QHard;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
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
