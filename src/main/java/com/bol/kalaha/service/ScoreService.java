package com.bol.kalaha.service;

import com.bol.kalaha.dto.GameScoreResponse;
import com.bol.kalaha.model.Board;

public interface ScoreService {
    GameScoreResponse get(Board board);
}