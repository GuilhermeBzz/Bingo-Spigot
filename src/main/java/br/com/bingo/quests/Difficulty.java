package br.com.bingo.quests;

public enum Difficulty {

    D1(1,25,0,0),
    D2(2,20,5,0),
    D3(3,16,7,2),
    D4(4,11,11,3),
    D5(5,8,12,5),
    D6(6,5,12,8),
    D7(7,2,13,10),
    D8(8,0,11,14),
    D9(9,0,6,19),
    D10(10,0,0,25);


    private final int difficulty;
    private final int easy;
    private final int medium;

    public int getDifficulty() {
        return difficulty;
    }

    public int getEasy() {
        return easy;
    }

    public int getMedium() {
        return medium;
    }

    public int getHard() {
        return hard;
    }

    private final int hard;

    Difficulty(int difficulty, int easy, int medium, int hard) {
        this.difficulty = difficulty;
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
    }
}
