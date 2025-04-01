package br.com.bingo.quests;

public enum SpecialCollectQuantity {

    SPECIAL_FURNACE(Quest.COLLECT_37_FURNACES, 37),
    SPECIAL_WOOL(Quest.COLLECT_64_BLUE_WOOL, 64),;

    private Quest quest;
    private int quantity;

    SpecialCollectQuantity(Quest quest, int quantity) {
        this.quest = quest;
        this.quantity = quantity;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
