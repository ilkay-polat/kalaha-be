package com.bol.kalaha.mapper;

import com.bol.kalaha.document.GameDocument;
import com.bol.kalaha.enums.PlayerNo;
import com.bol.kalaha.enums.Status;
import com.bol.kalaha.model.BigPit;
import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.SmallPit;
import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.bol.kalaha.enums.PlayerNo.PLAYER_1;
import static com.bol.kalaha.enums.PlayerNo.PLAYER_2;
import static com.bol.kalaha.util.GameConstants.FIRST_PLAYER_BIG_PIT_INDEX;
import static com.bol.kalaha.util.GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER;
import static com.bol.kalaha.util.GameConstants.SECOND_PLAYER_BIG_PIT_INDEX;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameMapper {
    int NUMBER_OF_PLAYERS = 2;
    int FIRST_PLAYER_FIRST_SMALL_PIT_INDEX = 0;
    int SECOND_PLAYER_FIRST_SMALL_PIT_INDEX = 7;

    @Mapping(target = "status", source = "status", qualifiedByName = "mapToGameStatusAsString")
    @Mapping(target = "playerTurn", source = "playerTurn", qualifiedByName = "mapToPlayerNoAsInt")
    @Mapping(target = "pits", source = "game.board.pits", qualifiedByName = "mapToPits")
    @Mapping(target = "players", source = "players", qualifiedByName = "mapToPlayersAsString")
    GameDocument mapToGameDocument(Game game);

    @Mapping(target = "status", source = "status", qualifiedByName = "mapToGameStatus")
    @Mapping(target = "playerTurn", source = "playerTurn", qualifiedByName = "mapToPlayerNo")
    @Mapping(target = "board", source = "pits", qualifiedByName = "mapToBoard")
    @Mapping(target = "players", source = "players", qualifiedByName = "mapToPlayers")
    Game mapToGame(GameDocument gameDocument);

    @Named("mapToGameStatusAsString")
    default String mapToGameStatusAsString(final Status status) {
        return status.getValue();
    }

    @Named("mapToPlayerNoAsInt")
    default int mapToPlayerNoAsInt(final PlayerNo playerTurn) {
        return playerTurn.getValue();
    }

    @Named("mapToPits")
    default List<Integer> mapToPits(final List<Pit> pits) {
        return pits.stream()
                   .map(Pit::getStones)
                   .toList();
    }

    @Named("mapToPlayersAsString")
    default List<String> mapToPlayersAsString(final List<Player> players) {
        return players.stream()
                      .map(Player::playerName)
                      .toList();
    }

    @Named("mapToGameStatus")
    default Status mapToGameStatus(final String status) {
        return Status.of(status);
    }

    @Named("mapToPlayerNo")
    default PlayerNo mapToPlayerNo(final int playerTurn) {
        return PlayerNo.of(playerTurn);
    }

    @Named("mapToBoard")
    default Board mapToBoard(final List<Integer> pits) {
        var pitList = new ArrayList<Pit>(createSmallPits(PLAYER_1, pits));
        pitList.add(new BigPit(PLAYER_1, pits.get(FIRST_PLAYER_BIG_PIT_INDEX)));
        pitList.addAll(createSmallPits(PLAYER_2, pits));
        pitList.add(new BigPit(PLAYER_2, pits.get(SECOND_PLAYER_BIG_PIT_INDEX)));
        return new Board(pitList);
    }

    @Named("mapToPlayers")
    default List<Player> mapToPlayers(final List<String> playerNames) {
        var players = new ArrayList<Player>();

        players.add(new Player(PLAYER_1, playerNames.get(0)));

        if (playerNames.size() == NUMBER_OF_PLAYERS) {
            players.add(new Player(PLAYER_2, playerNames.get(1)));
        }

        return players;
    }

    private List<SmallPit> createSmallPits(final PlayerNo player, final List<Integer> pits) {
        val startIndex = player == PLAYER_1 ? FIRST_PLAYER_FIRST_SMALL_PIT_INDEX : SECOND_PLAYER_FIRST_SMALL_PIT_INDEX;
        return IntStream.range(startIndex, startIndex + NUMBER_OF_SMALL_PITS_PER_PLAYER)
                        .mapToObj(i -> new SmallPit(player, pits.get(i)))
                        .toList();
    }
}