package com.bol.kalaha.model;

import com.bol.kalaha.enums.PlayerNo;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.bol.kalaha.enums.PlayerNo.PLAYER_1;
import static com.bol.kalaha.enums.PlayerNo.PLAYER_2;
import static com.bol.kalaha.util.GameConstants.FIRST_PLAYER_BIG_PIT_INDEX;
import static com.bol.kalaha.util.GameConstants.LAST_PIT_INDEX;
import static com.bol.kalaha.util.GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER;
import static com.bol.kalaha.util.GameConstants.SECOND_PLAYER_BIG_PIT_INDEX;

@Getter
@Setter
public class Board {
    private final List<Pit> pits;
    private int lastSowPitIndex;

    public Board() {
        pits = new ArrayList<>(createSmallPits(PLAYER_1));
        pits.add(new BigPit(PLAYER_1));

        pits.addAll(createSmallPits(PLAYER_2));
        pits.add(new BigPit(PLAYER_2));
    }

    public Board(final List<Pit> pits) {
        this.pits = pits;
    }

    public Pit getBigPit(final PlayerNo playerNo) {
        return playerNo == PLAYER_1 ? pits.get(FIRST_PLAYER_BIG_PIT_INDEX) : pits.get(SECOND_PLAYER_BIG_PIT_INDEX);
    }

    public List<Integer> getBigPitStones() {
        return List.of(pits.get(FIRST_PLAYER_BIG_PIT_INDEX).stones, pits.get(SECOND_PLAYER_BIG_PIT_INDEX).stones);
    }

    public boolean areSmallPitsEmpty(final PlayerNo player) {
        return pits.stream()
                   .filter(pit -> pit.isItsOwnPlayablePit(player))
                   .allMatch(Pit::isEmpty);
    }

    public void moveSmallPitsStones(final PlayerNo player) {
        val bigPit = getBigPit(player);

        pits.stream()
            .filter(pit -> pit.isItsOwnPlayablePit(player))
            .forEach(pit -> pit.moveStones(bigPit));
    }

    public boolean isEmptyPit(final PlayerNo player, final int pitNumber) {
        val pitIndex = getPitIndex(player, pitNumber);
        val pit = pits.get(pitIndex);

        return pit.isEmpty();
    }

    public void sowStones(final PlayerNo player, final int pitNumber) {
        val pitIndex = getPitIndex(player, pitNumber);
        val pit = pits.get(pitIndex);

        val stones = pit.pickUpStones();

        sowStones(player, pitIndex, stones);
    }

    private int getPitIndex(final PlayerNo player, final int pitNumber) {
        if (player == PLAYER_1) {
            return pitNumber - 1;
        }
        return NUMBER_OF_SMALL_PITS_PER_PLAYER + pitNumber;
    }

    private void sowStones(final PlayerNo player, final int pitIndex, final int stones) {
        var remainingStones = stones;
        var nextPitIndex = pitIndex;
        while (remainingStones > 0) {
            nextPitIndex = getNextPitIndex(nextPitIndex);
            if (pits.get(nextPitIndex).sowStone(player)) {
                remainingStones--;
            }

            if (remainingStones == 0) {
                lastSowPitIndex = nextPitIndex;
            }
        }
    }

    private int getNextPitIndex(int pitIndex) {
        return pitIndex < LAST_PIT_INDEX ? pitIndex + 1 : 0;
    }

    private List<SmallPit> createSmallPits(final PlayerNo player) {
        return IntStream.range(0, 6)
                        .mapToObj(i -> new SmallPit(player))
                        .toList();
    }
}