package com.roguebear.pandemicdecktracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cardNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardNameTextView = itemView.findViewById(R.id.card_name);
        }
    }

    private List<Card> mCards;
    private DecksManager mManager;
    private Activity mParentActivity;

    int mBlueColorCode;
    int mRedColorCode;
    int mYellowColorCode;
    int mBlackColorCode;
    int mEpidemicColorCode;
    
    public CardsAdapter(DecksManager manager, Activity parentActivity) {
        mManager = manager;
        mParentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_row, parent, false);

        mBlueColorCode = parent.getResources().getColor(R.color.cardBlue);
        mRedColorCode = parent.getResources().getColor(R.color.cardRed);
        mYellowColorCode = parent.getResources().getColor(R.color.cardYellow);
        mBlackColorCode = parent.getResources().getColor(R.color.cardBlack);
        mEpidemicColorCode = parent.getResources().getColor(R.color.epidemicGreen);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

//        Card card = mCards.get(position);
        Card card = mManager.getActiveDeck().get(position);
        TextView tv = holder.cardNameTextView;

        if (card.isSeparator() == false) {

            tv.setText(card.getName());
            tv.setTextColor(Color.BLACK);
            switch (card.getColor()) {
                case BLUE:
                    tv.setBackgroundColor(mBlueColorCode);
                    break;
                case RED:
                    tv.setBackgroundColor(mRedColorCode);
                    break;
                case YELLOW:
                    tv.setBackgroundColor(mYellowColorCode);
                    break;
                case BLACK:
                    tv.setBackgroundColor(mBlackColorCode);
                    tv.setTextColor(Color.WHITE);
                    break;

            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Card card = CardsAdapter.this.mManager.getActiveDeck().get(position);

                    if (!card.isSeparator() && card.getDeckType() != DecksManager.DeckType.DISCARD) {
                        card.getManager().discard(card);

                        CardsAdapter.this.notifyDataSetChanged();
                    }

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final Card card = CardsAdapter.this.mManager.getActiveDeck().get(position);
                    Log.d("AAAA", "Long click: " + card.getName());

                    AlertDialog.Builder builder = new AlertDialog.Builder(mParentActivity);
                    builder.setTitle(card.getName());
                    builder.setItems(new CharSequence[]
                            {"Remove from Game"},
                            new Dialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    switch (which) {
                                        case 0:
                                            mManager.removeFromGame(card);
                                            CardsAdapter.this.notifyDataSetChanged();
                                            Toast.makeText(mParentActivity,
                                                    "Removed " + card.getName() + " from game", 0).show();
                                            break;
                                    }
                                }
                            });
                    builder.show();
                    return true;
                }
            });
        }
        else {
            tv.setText("-----Break-----");
            tv.setBackgroundColor(mEpidemicColorCode);

        }

    }

    @Override
    public int getItemCount() {
        return mManager.getActiveDeck().size();
//        return mCards.size();
    }



}
