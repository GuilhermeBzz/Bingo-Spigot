package br.com.bingo.rank.models.quests;

public class QuestsData {

    String name;
    int difficulty;
    int done;
    int notDone;

    public QuestsData(String name, int difficulty, int done, int notDone) {
        this.name = name;
        this.difficulty = difficulty;
        this.done = done;
        this.notDone = notDone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getNotDone() {
        return notDone;
    }

    public void setNotDone(int notDone) {
        this.notDone = notDone;
    }
}
