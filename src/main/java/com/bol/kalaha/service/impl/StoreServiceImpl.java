package com.bol.kalaha.service.impl;

import com.bol.kalaha.mapper.GameMapper;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final GameMapper gameMapper;
    private final GameRepository gameRepository;

    @Override
    public String saveGame(final Game game) {
        val gameDocument = gameMapper.mapToGameDocument(game);

        val savedGameDocument = gameRepository.save(gameDocument);

        return savedGameDocument.getId();
    }

    @Override
    public Game getGameById(final String gameId) {
        val gameDocument = gameRepository.findById(gameId)
                                         .orElseThrow(() -> new NoSuchElementException("Game not found by a given id:" + gameId));

        return gameMapper.mapToGame(gameDocument);
    }

    @Override
    public void deleteGameById(final String gameId) {
        gameRepository.deleteById(gameId);
    }
}