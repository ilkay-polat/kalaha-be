package com.bol.kalaha.controller;

import com.bol.kalaha.dto.GameCreateRequest;
import com.bol.kalaha.dto.GameJoinRequest;
import com.bol.kalaha.dto.GamePlayRequest;
import com.bol.kalaha.dto.GameResponse;
import com.bol.kalaha.dto.GameScoreResponse;
import com.bol.kalaha.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@Slf4j
public class GameController {
    private final GameService gameService;

    @PostMapping
    public ResponseEntity<Void> createGame(@RequestBody @Valid final GameCreateRequest request) {
        log.info("Create a new game with a first player [{}]", request);

        val gameId = gameService.create(request);

        return getResponseEntityWithCreated(gameId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> joinGame(@PathVariable final String id,
                                         @RequestBody @Valid final GameJoinRequest request) {
        log.info("Join the game with a second player [{}]", request);

        gameService.join(id, request);

        return getResponseEntityWithNoContent();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> restartGame(@PathVariable final String id) {
        log.info("Restart the game by resetting the board [{}]", id);

        gameService.restart(id);

        return getResponseEntityWithNoContent();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> quitGame(@PathVariable final String id) {
        log.info("Quit the game [{}]", id);

        gameService.quit(id);

        return getResponseEntityWithNoContent();
    }

    @PutMapping("/{id}/players/{playerId}")
    public ResponseEntity<GameResponse> play(@PathVariable("id") final String gameId,
                                             @PathVariable final int playerId,
                                             @RequestBody @Valid final GamePlayRequest request) {
        log.info("Play the game [{}] for the player [{}] [{}]", gameId, playerId, request);

        val response = gameService.play(gameId, playerId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> get(@PathVariable("id") final String gameId) {
        log.info("Get the game [{}]", gameId);

        val response = gameService.get(gameId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/score")
    public ResponseEntity<GameScoreResponse> getScore(@PathVariable("id") final String gameId) {
        log.info("Get the score of a given game [{}]", gameId);

        val response = gameService.getScore(gameId);

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Void> getResponseEntityWithCreated(final String gameId) {
        return ResponseEntity.created(getLocation(gameId))
                             .build();
    }

    private ResponseEntity<Void> getResponseEntityWithNoContent() {
        return ResponseEntity.noContent()
                             .build();
    }

    private URI getLocation(final String gameId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                                          .path("/{id}")
                                          .buildAndExpand(gameId)
                                          .toUri();
    }
}