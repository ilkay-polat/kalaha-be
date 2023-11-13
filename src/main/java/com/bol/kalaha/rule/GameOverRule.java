package com.bol.kalaha.rule;

import com.bol.kalaha.enums.PlayerNo;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import lombok.val;
import org.springframework.stereotype.Component;

import static com.bol.kalaha.enums.PlayerNo.PLAYER_1;
import static com.bol.kalaha.enums.PlayerNo.PLAYER_2;
import static com.bol.kalaha.enums.Priority.LOW_PRIORITY;
import static com.bol.kalaha.enums.Status.OVER;

@Component
public class GameOverRule extends Rule {

    public GameOverRule() {
        super(LOW_PRIORITY);
    }

    @Override
    public boolean matches(final Game game) {
        val board = game.getBoard();

        return board.areSmallPitsEmpty(PLAYER_1) || board.areSmallPitsEmpty(PLAYER_2);
    }

    @Override
    public void apply(final Game game) {
        val board = game.getBoard();
        val player = getPlayerHavingNonEmptySmallPits(board);

        board.moveSmallPitsStones(player);

        game.setStatus(OVER);
    }

    private PlayerNo getPlayerHavingNonEmptySmallPits(final Board board) {
        return board.areSmallPitsEmpty(PLAYER_1) ? PLAYER_1 : PLAYER_2;
    }
}