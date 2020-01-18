package com.roguebear.pandemicdecktracker;

public class SeparatorCard extends Card {

    public SeparatorCard(DecksManager manager) {
        super(manager);
    }

    @Override
    public boolean isSeparator() {
        return true;
    }
}
