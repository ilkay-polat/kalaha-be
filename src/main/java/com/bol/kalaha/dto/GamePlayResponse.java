package com.bol.kalaha.dto;

import com.bol.kalaha.enums.PlayerNo;
import com.bol.kalaha.enums.Status;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import lombok.Getter;

import java.util.List;

@Getter
public class GamePlayResponse {
    private final Status status;
    private final PlayerNo playerTurn;
    private final List<Integer> pits;

    public GamePlayResponse(final Game game) {
        status = game.getStatus();
        playerTurn = game.getPlayerTurn();
        pits = getPits(game);
    }

    private List<Integer> getPits(final Game game) {
        return game.getBoard()
                   .getPits()
                   .stream()
                   .map(Pit::getStones)
                   .toList();
    }
}