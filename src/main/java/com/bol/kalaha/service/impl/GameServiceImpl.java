package com.bol.kalaha.service.impl;

import com.bol.kalaha.dto.GameCreateRequest;
import com.bol.kalaha.dto.GameJoinRequest;
import com.bol.kalaha.dto.GamePlayRequest;
import com.bol.kalaha.dto.GameResponse;
import com.bol.kalaha.dto.GameScoreResponse;
import com.bol.kalaha.enums.PlayerNo;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.service.PlayService;
import com.bol.kalaha.service.ScoreService;
import com.bol.kalaha.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final PlayService playService;
    private final ScoreService scoreService;
    private final StoreService storeService;

    @Override
    public String create(final GameCreateRequest request) {
        val firstPlayerName = request.playerName();

        val game = new Game(firstPlayerName);

        return storeService.saveGame(game);
    }

    @Override
    public void join(final String id, final GameJoinRequest request) {
        val game = storeService.getGameById(id);
        if (!game.isCreated()) {
            throw new IllegalStateException("You cannot join a game whose status is not 'Created'");
        }

        game.join(request.playerName());

        storeService.saveGame(game);
    }

    @Override
    public void restart(final String id) {
        val game = storeService.getGameById(id);
        if (!game.isInProgress()) {
            throw new IllegalStateException("You cannot restart a game whose status is not 'In Progress'");
        }

        game.restart();

        storeService.saveGame(game);
    }

    @Override
    public void quit(final String id) {
        val game = storeService.getGameById(id);
        if (!game.isInProgress()) {
            throw new IllegalStateException("You cannot quit a game whose status is not 'In Progress'");
        }
        storeService.deleteGameById(id);
    }

    @Override
    public GameResponse get(final String gameId) {
        val game = storeService.getGameById(gameId);
        if (!game.isInProgress()) {
            throw new IllegalStateException("You cannot get the board of a game whose status is not 'In Progress'");
        }

        return new GameResponse(game);
    }

    @Override
    public GameResponse play(final String gameId, final int playerId, final GamePlayRequest request) {
        val game = storeService.getGameById(gameId);
        if (!game.isInProgress()) {
            throw new IllegalStateException("You cannot play the game whose status is not 'In Progress'");
        }

        val player = PlayerNo.of(playerId);
        if (!game.isPlayerTurn(player)) {
            throw new IllegalStateException("You cannot play because you are not the player turn.");
        }

        playService.play(game, request.pitNumber());

        storeService.saveGame(game);

        return new GameResponse(game);
    }

    @Override
    public GameScoreResponse getScore(final String gameId) {
        val game = storeService.getGameById(gameId);
        if (!game.isOver()) {
            throw new IllegalStateException("You cannot get the score of a game whose status is not 'Over'");
        }

        return scoreService.get(game.getBoard());
    }
}