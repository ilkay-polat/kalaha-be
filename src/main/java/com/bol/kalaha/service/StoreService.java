package com.bol.kalaha.service;

import com.bol.kalaha.model.Game;

public interface StoreService {
    String saveGame(Game game);

    Game getGameById(String gameId);

    void deleteGameById(String gameId);
}