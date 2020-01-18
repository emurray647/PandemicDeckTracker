package com.roguebear.pandemicdecktracker;

public class Card {

    public Card(DecksManager manager) {
        this(null, null, null, manager);
    }

    public Card(String name, Color color, Deck deck, DecksManager manager) {
        mName = name;
        mColor = color;
        mDeck = deck;
        mManager = manager;
    }

    public boolean isSeparator() {
        return false;
    }

    public DecksManager getManager() { return mManager; }

    public String getName() {
        return mName;
    }

    public Color getColor() {
        return mColor;
    }

    public void setDeckType(DecksManager.DeckType deckType) {
        mDeckType = deckType;
    }

    public DecksManager.DeckType getDeckType() {
        return mDeckType;
    }

    public enum Color {
        BLUE, RED, YELLOW, BLACK
    }

    private Deck mDeck;
    private DecksManager.DeckType mDeckType = DecksManager.DeckType.DRAW;
    private DecksManager mManager;
    private String mName;
    private Color mColor;

}
