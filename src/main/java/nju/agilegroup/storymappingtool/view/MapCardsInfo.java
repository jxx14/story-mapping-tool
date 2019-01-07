package nju.agilegroup.storymappingtool.view;


import java.util.List;

public class MapCardsInfo {
    private int id;
    private List<CardInfo> cards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CardInfo> getCards() {
        return cards;
    }

    public void setCards(List<CardInfo> cards) {
        this.cards = cards;
    }
}
