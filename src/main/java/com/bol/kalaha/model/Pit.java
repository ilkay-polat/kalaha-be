package com.bol.kalaha.model;

import com.bol.kalaha.enums.PlayerNo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Pit {
    protected final PlayerNo owner;
    protected int stones;

    public abstract int pickUpStones();

    public abstract boolean sowStone(final PlayerNo player);

    public abstract boolean isPlayable();

    public boolean isEmpty() {
        return stones == 0;
    }

    public boolean hasStones(final int stones) {
        return this.stones == stones;
    }

    public boolean isOwnedBy(final PlayerNo player) {
        return owner == player;
    }

    public boolean isItsOwnPlayablePit(final PlayerNo player) {
        return isOwnedBy(player) && isPlayable();
    }

    public void moveStones(final Pit pit) {
        pit.stones += pickUpStones();
    }
}