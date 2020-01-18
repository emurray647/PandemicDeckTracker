package com.roguebear.pandemicdecktracker;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Deck {

    public LinkedList<Card> mCards;

    public Deck() {
        mCards = new LinkedList<>();
    }

    public LinkedList<Card> getUnderlyingList() {
        return mCards;
    }

    public boolean contains(Card card)
    {
        return mCards.contains(card);
    }

    public Card get(int pos) {
        return mCards.get(pos);
    }

    public void add(Card card) {
        mCards.add(0, card);
    }

    public Card remove() {
        Card card;
        if (mCards.isEmpty()) {
            return null;
        }
        card = mCards.pop();
        return card;
    }

    public void remove(Card card) {
        Iterator<Card> it = getIterator(card);
        if (it != null)
            it.remove();
    }

    private Iterator<Card> getIterator(Card card) {
        Iterator<Card> it = mCards.iterator();
        while (it.hasNext()) {
            Card testCard = it.next();
            if (testCard.equals(card)) {
                return it;
            }
        }
        return null;
    }

    public Iterator<Card> iterator() {
        return mCards.iterator();
    }

    public int size() {
        return mCards.size();
    }
}
