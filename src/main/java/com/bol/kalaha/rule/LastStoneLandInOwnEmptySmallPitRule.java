package com.bol.kalaha.rule;

import com.bol.kalaha.enums.PlayerNo;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import lombok.val;
import org.springframework.stereotype.Component;

import static com.bol.kalaha.enums.Priority.MEDIUM_PRIORITY;
import static com.bol.kalaha.util.GameConstants.LAST_PIT_INDEX;

@Component
public class LastStoneLandInOwnEmptySmallPitRule extends Rule {

    private static final int STONE_COUNT_1 = 1;

    public LastStoneLandInOwnEmptySmallPitRule() {
        super(MEDIUM_PRIORITY);
    }

    @Override
    public boolean matches(final Game game) {
        val board = game.getBoard();
        val player = game.getPlayerTurn();

        var lastSowPit = getLastSowPit(board);

        return isLastStoneLandInOwnEmptySmallPit(lastSowPit, player);
    }

    @Override
    public void apply(final Game game) {
        val board = game.getBoard();
        val player = game.getPlayerTurn();

        val bigBit = board.getBigPit(player);

        val lastSowPit = getLastSowPit(board);
        lastSowPit.moveStones(bigBit);

        val oppositeOfLastSowPit = getOppositeOfLastSowPit(board);
        oppositeOfLastSowPit.moveStones(bigBit);
    }

    private boolean isLastStoneLandInOwnEmptySmallPit(final Pit pit, final PlayerNo player) {
        return pit.isOwnedBy(player) && pit.isPlayable() && pit.hasStones(STONE_COUNT_1);
    }

    private Pit getLastSowPit(final Board board) {
        return board.getPits()
                    .get(board.getLastSowPitIndex());
    }

    private Pit getOppositeOfLastSowPit(final Board board) {
        val oppositeOfLastSowPit = LAST_PIT_INDEX - board.getLastSowPitIndex() - 1;
        return board.getPits()
                    .get(oppositeOfLastSowPit);
    }
}