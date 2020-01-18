package com.roguebear.pandemicdecktracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PandemicActivity extends AppCompatActivity {

    Deck mDrawDeck;
    Deck mDiscardDeck;

    DecksManager mManager;

    DecksManager.DeckType mShowingDeck = DecksManager.DeckType.DRAW;

    CardsAdapter mAdapter;

    TextView mDeckNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandemic);

        mManager = new DecksManager(this);

        // TODO: this is temporary
        RecyclerView recyclerView = findViewById(R.id.card_listview);
//        recyclerView.add
//        List<Card> cards = new ArrayList<>();
//        cards.add(new Card("New York", Card.Color.BLUE));
//        cards.add(new Card("Chicago", Card.Color.BLUE));
//        CardsAdapter adapter = new CardsAdapter(mManager.getActiveDeck().getUnderlyingList());
        mAdapter = new CardsAdapter(mManager, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDeckNameTextView = findViewById(R.id.deckName_textview);
        mDeckNameTextView.setText("Draw Pile");

    }


    public void switchClicked(View view) {

        if (mShowingDeck == DecksManager.DeckType.DISCARD) {
            mShowingDeck = DecksManager.DeckType.DRAW;
            mDeckNameTextView.setText("Draw Pile");
        }
        else {
            mShowingDeck = DecksManager.DeckType.DISCARD;
            mDeckNameTextView.setText("Discard Pile");
        }

        mManager.SetActiveDeckType(mShowingDeck);

        mAdapter.notifyDataSetChanged();



    }

    public void epidemicClicked(View view) {
        mManager.resolveEpidemic();

        mAdapter.notifyDataSetChanged();
    }
}
