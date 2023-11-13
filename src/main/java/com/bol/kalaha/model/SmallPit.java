package com.bol.kalaha.model;

import com.bol.kalaha.enums.PlayerNo;
import lombok.val;

public class SmallPit extends Pit {
    private static final int NUMBER_OF_STONES_IN_SMALL_PIT = 6;

    public SmallPit(final PlayerNo owner) {
        super(owner, NUMBER_OF_STONES_IN_SMALL_PIT);
    }

    public SmallPit(final PlayerNo owner, final int stones) {
        super(owner, stones);
    }

    @Override
    public int pickUpStones() {
        val stonesToPickUp = stones;

        stones = 0;

        return stonesToPickUp;
    }

    @Override
    public boolean sowStone(final PlayerNo player) {
        stones++;

        return true;
    }

    @Override
    public boolean isPlayable() {
        return true;
    }
}