package com.bol.kalaha.model;

import com.bol.kalaha.enums.PlayerNo;
import com.bol.kalaha.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.bol.kalaha.enums.PlayerNo.PLAYER_1;
import static com.bol.kalaha.enums.PlayerNo.PLAYER_2;
import static com.bol.kalaha.enums.Status.CREATED;
import static com.bol.kalaha.enums.Status.IN_PROGRESS;
import static com.bol.kalaha.enums.Status.OVER;

@Getter
@Setter
public class Game {
    private String id;
    private Status status;
    private PlayerNo playerTurn;
    private Board board;
    private List<Player> players;
    private boolean anotherTurn;

    public Game(final String playerName) {
        status = CREATED;
        playerTurn = PLAYER_1;
        board = new Board();
        addFirstPlayer(playerName);
    }

    public void join(final String playerName) {
        status = IN_PROGRESS;
        players.add(new Player(PLAYER_2, playerName));
    }

    public void restart() {
        playerTurn = PLAYER_1;
        board = new Board();
    }

    private void addFirstPlayer(final String playerName) {
        players = new ArrayList<>();
        players.add(new Player(PLAYER_1, playerName));
    }

    public boolean isPlayerTurn(PlayerNo player) {
        return playerTurn == player;
    }

    public boolean isCreated() {
        return status == CREATED;
    }

    public boolean isInProgress() {
        return status == IN_PROGRESS;
    }

    public boolean isOver() {
        return status == OVER;
    }
}