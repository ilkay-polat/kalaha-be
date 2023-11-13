package com.bol.kalaha.service;

import com.bol.kalaha.dto.GameCreateRequest;
import com.bol.kalaha.dto.GameJoinRequest;
import com.bol.kalaha.dto.GamePlayRequest;
import com.bol.kalaha.dto.GameResponse;
import com.bol.kalaha.dto.GameScoreResponse;

public interface GameService {
    String create(GameCreateRequest request);

    void join(String id, GameJoinRequest request);

    void restart(String id);

    void quit(String id);

    GameResponse get(String gameId);

    GameResponse play(String gameId, int playerId, GamePlayRequest request);

    GameScoreResponse getScore(String gameId);
}