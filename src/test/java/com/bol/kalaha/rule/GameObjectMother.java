package com.bol.kalaha.rule;

import com.bol.kalaha.model.BigPit;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.SmallPit;

import java.util.ArrayList;
import java.util.List;

import static com.bol.kalaha.enums.PlayerNo.PLAYER_1;
import static com.bol.kalaha.enums.PlayerNo.PLAYER_2;

public class GameObjectMother {
    private static final int FIRST_PLAYER_BIG_PIT_INDEX = 6;
    private static final int SECOND_PLAYER_PIT_INDEX = 12;

    public static Game createGameAsLastStoneLandInOwnBigPit() {
        var game = new Game("Player1");
        game.join("Player2");

        var pits = getPits();
        var board = new Board(pits);
        board.setLastSowPitIndex(FIRST_PLAYER_BIG_PIT_INDEX);
        game.setBoard(board);

        return game;
    }

    public static Game createGameAsLastStoneLandInOwnEmptySmallPit() {
        var game = new Game("Player1");
        game.join("Player2");
        game.setPlayerTurn(PLAYER_2);

        var pits = getPits();
        var board = new Board(pits);
        board.setLastSowPitIndex(SECOND_PLAYER_PIT_INDEX);
        game.setBoard(board);

        return game;
    }

    public static Game createGameAsLastStoneLandInOwnEmptySmallPitAfterRuleExecution() {
        var game = new Game("Player1");
        game.join("Player2");
        game.setPlayerTurn(PLAYER_2);

        var pits = getPitsAsLastStoneLandInOwnEmptySmallPitAfterRuleExecution();
        var board = new Board(pits);
        board.setLastSowPitIndex(SECOND_PLAYER_PIT_INDEX);
        game.setBoard(board);

        return game;
    }

    public static Game createGameWithOverStatus() {
        var game = new Game("Player1");
        game.join("Player2");

        var pits = getPitsByGameOver();
        game.setBoard(new Board(pits));
        return game;
    }

    private static List<Pit> getPits() {
        var pits = new ArrayList<Pit>();
        pits.add(new SmallPit(PLAYER_1, 3));
        pits.add(new SmallPit(PLAYER_1, 1));
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new SmallPit(PLAYER_1, 9));
        pits.add(new SmallPit(PLAYER_1, 10));
        pits.add(new SmallPit(PLAYER_1, 10));
        pits.add(new BigPit(PLAYER_1, 4));
        pits.add(new SmallPit(PLAYER_2, 10));
        pits.add(new SmallPit(PLAYER_2, 10));
        pits.add(new SmallPit(PLAYER_2, 9));
        pits.add(new SmallPit(PLAYER_2, 0));
        pits.add(new SmallPit(PLAYER_2, 2));
        pits.add(new SmallPit(PLAYER_2, 1));
        pits.add(new BigPit(PLAYER_2, 3));
        return pits;
    }

    private static List<Pit> getPitsAsLastStoneLandInOwnEmptySmallPitAfterRuleExecution() {
        var pits = new ArrayList<Pit>();
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new SmallPit(PLAYER_1, 1));
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new SmallPit(PLAYER_1, 9));
        pits.add(new SmallPit(PLAYER_1, 10));
        pits.add(new SmallPit(PLAYER_1, 10));
        pits.add(new BigPit(PLAYER_1, 4));
        pits.add(new SmallPit(PLAYER_2, 10));
        pits.add(new SmallPit(PLAYER_2, 10));
        pits.add(new SmallPit(PLAYER_2, 9));
        pits.add(new SmallPit(PLAYER_2, 0));
        pits.add(new SmallPit(PLAYER_2, 2));
        pits.add(new SmallPit(PLAYER_2, 0));
        pits.add(new BigPit(PLAYER_2, 7));
        return pits;
    }

    private static List<Pit> getPitsByGameOver() {
        var pits = new ArrayList<Pit>();
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new SmallPit(PLAYER_1, 0));
        pits.add(new BigPit(PLAYER_1, 12));
        pits.add(new SmallPit(PLAYER_2, 2));
        pits.add(new SmallPit(PLAYER_2, 2));
        pits.add(new SmallPit(PLAYER_2, 2));
        pits.add(new SmallPit(PLAYER_2, 2));
        pits.add(new SmallPit(PLAYER_2, 2));
        pits.add(new SmallPit(PLAYER_2, 2));
        pits.add(new BigPit(PLAYER_2, 48));
        return pits;
    }
}
