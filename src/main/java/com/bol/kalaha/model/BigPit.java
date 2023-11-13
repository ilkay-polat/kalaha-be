package com.bol.kalaha.model;

import com.bol.kalaha.enums.PlayerNo;

public class BigPit extends Pit {
    private static final int ZERO_STONES = 0;
    private static final int NUMBER_OF_STONES_IN_BIG_PIT = 0;

    public BigPit(final PlayerNo ownerPlayer) {
        super(ownerPlayer, NUMBER_OF_STONES_IN_BIG_PIT);
    }

    public BigPit(final PlayerNo ownerPlayer, final int numberOfStones) {
        super(ownerPlayer, numberOfStones);
    }

    @Override
    public int pickUpStones() {
        return ZERO_STONES;
    }

    @Override
    public boolean sowStone(final PlayerNo player) {
        if (owner != player) {
            return false;
        }
        stones++;

        return true;
    }

    @Override
    public boolean isPlayable() {
        return false;
    }
}