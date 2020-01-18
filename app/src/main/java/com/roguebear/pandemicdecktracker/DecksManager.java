package com.roguebear.pandemicdecktracker;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class DecksManager {

    private Deck mDiscard;
    private Deck mDraw;

    private DeckType mActiveDeckType;

    public enum DeckType {
        DISCARD, DRAW;
    }

    public DecksManager(Activity activity) {
        mDiscard = new Deck();
        mDraw = new Deck();

        mActiveDeckType = DeckType.DRAW;

        readCardFile(activity);
        
        // temporary
//        mDraw.add(new Card("New York", Card.Color.BLUE, mDraw, this));
//        mDraw.add(new Card("Chicago", Card.Color.BLUE, mDraw, this));
//        mDraw.add(new Card("Atlanta", Card.Color.BLUE, mDraw, this));
    }

    private void readCardFile(Activity activity) {

        try {

            String[] f = activity.getAssets().list("");
//            for (String a : f) {
//                System.out.println(a);
//                Log.d("AAAA",a);
//            }

            InputStreamReader is = new InputStreamReader(activity.getAssets()
                    .open("cities.csv"));

            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//                Log.d("AAAA", line);

                String[] array = line.split(",");
                String name = array[0];
                Card.Color color = Card.Color.BLACK;
                switch (array[1].trim().toLowerCase()) {
                    case "blue":
                        color = Card.Color.BLUE;
                        break;
                    case "red":
                        color = Card.Color.RED;
                        break;
                    case "yellow":
                        color = Card.Color.YELLOW;
                        break;
                    case "black":
                        color = Card.Color.BLACK;
                        break;
                }

                mDraw.add(new Card(name, color, mDraw, this));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetActiveDeckType(DeckType type) {
        mActiveDeckType = type;
    }

    public Deck getActiveDeck() {
        if (mActiveDeckType == DeckType.DISCARD) {
            return mDiscard;
        }
        return mDraw;
    }

    public void discard(Card card) {
        // remove the card from the draw pile
        mDraw.remove(card);

        // add the card to discard
        mDiscard.add(card);
        card.setDeckType(DeckType.DISCARD);

        removeSeparators();
    }

    public void removeFromGame(Card card) {
        if (card.getDeckType() == DeckType.DISCARD) {
            mDiscard.remove(card);
        }
        else {
            mDraw.remove(card);
        }

    }

    public void resolveEpidemic() {

        // add a break to top of draw
        SeparatorCard separatorCard = new SeparatorCard(this);
        mDraw.add(separatorCard);

        // put all of discard on top of draw
        Card card;
        while ((card = mDiscard.remove()) != null) {
            mDraw.add(card);
            card.setDeckType(DeckType.DRAW);
        }

        removeSeparators();
    }

    private void removeSeparators() {
        while (mDraw.get(0) != null && mDraw.get(0).isSeparator()) {
            mDraw.remove();
        }

        Card previous = null;
        Card current = null;
        Iterator<Card> it = mDraw.iterator();

        while (it.hasNext()) {
            current = it.next();

            if (previous != null && previous.isSeparator() && current.isSeparator()) {
                //
                it.remove();
            }

            previous = current;
        }
    }
}
