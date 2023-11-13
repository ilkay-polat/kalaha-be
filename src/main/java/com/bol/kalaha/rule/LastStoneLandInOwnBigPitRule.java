package com.bol.kalaha.rule;

import com.bol.kalaha.enums.PlayerNo;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import lombok.val;
import org.springframework.stereotype.Component;

import static com.bol.kalaha.enums.PlayerNo.PLAYER_1;
import static com.bol.kalaha.enums.Priority.HIGH_PRIORITY;
import static com.bol.kalaha.util.GameConstants.FIRST_PLAYER_BIG_PIT_INDEX;
import static com.bol.kalaha.util.GameConstants.SECOND_PLAYER_BIG_PIT_INDEX;

@Component
public class LastStoneLandInOwnBigPitRule extends Rule {

    public LastStoneLandInOwnBigPitRule() {
        super(HIGH_PRIORITY);
    }

    @Override
    public boolean matches(final Game game) {
        val board = game.getBoard();
        var player = game.getPlayerTurn();

        game.setAnotherTurn(false);

        return isLastStoneLandInOwnBigPit(board, player);
    }

    @Override
    public void apply(final Game game) {
        game.setAnotherTurn(true);
    }

    private boolean isLastStoneLandInOwnBigPit(final Board board, final PlayerNo player) {
        return player == PLAYER_1 ?
            board.getLastSowPitIndex() == FIRST_PLAYER_BIG_PIT_INDEX :
            board.getLastSowPitIndex() == SECOND_PLAYER_BIG_PIT_INDEX;
    }
}