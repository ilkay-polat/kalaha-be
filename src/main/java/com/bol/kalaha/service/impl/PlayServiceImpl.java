package com.bol.kalaha.service.impl;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.rule.RuleEngine;
import com.bol.kalaha.service.PlayService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayServiceImpl implements PlayService {
    private final RuleEngine ruleEngine;

    @Override
    public void play(final Game game, final int pitNumber) {
        val board = game.getBoard();
        val player = game.getPlayerTurn();

        if (board.isEmptyPit(player, pitNumber)) {
            throw new IllegalStateException("You cannot play with a pit who has no stones");
        }

        board.sowStones(player, pitNumber);

        ruleEngine.execute(game);

        if (!game.isAnotherTurn()) {
            game.setPlayerTurn(player.nextPlayer());
        }
    }
}