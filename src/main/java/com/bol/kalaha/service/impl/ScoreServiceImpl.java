package com.bol.kalaha.service.impl;

import com.bol.kalaha.dto.GameScoreResponse;
import com.bol.kalaha.enums.PlayerNo;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.service.ScoreService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bol.kalaha.enums.PlayerNo.PLAYER_1;
import static com.bol.kalaha.enums.PlayerNo.PLAYER_2;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Override
    public GameScoreResponse get(final Board board) {
        val scores = board.getBigPitStones();
        val winnerPlayer = getWinnerPlayer(scores);

        return new GameScoreResponse(winnerPlayer, scores);
    }

    private PlayerNo getWinnerPlayer(final List<Integer> scores) {
        val firstPlayerScore = scores.get(0);
        val secondPlayerScore = scores.get(1);

        return firstPlayerScore > secondPlayerScore ? PLAYER_1 : PLAYER_2;
    }
}