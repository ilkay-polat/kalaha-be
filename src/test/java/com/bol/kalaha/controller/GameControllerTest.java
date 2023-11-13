package com.bol.kalaha.controller;

import com.bol.kalaha.document.GameDocument;
import com.bol.kalaha.dto.GameCreateRequest;
import com.bol.kalaha.dto.GameJoinRequest;
import com.bol.kalaha.dto.GamePlayRequest;
import com.bol.kalaha.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.bol.kalaha.enums.PlayerNo.PLAYER_1;
import static com.bol.kalaha.enums.PlayerNo.PLAYER_2;
import static com.bol.kalaha.enums.Status.CREATED;
import static com.bol.kalaha.enums.Status.IN_PROGRESS;
import static com.bol.kalaha.enums.Status.OVER;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {
    private static final List<Integer> PITS_AS_INITIAL = List.of(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0);
    private static final List<Integer> PITS_AS_OVER = List.of(0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 42);


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        gameRepository.deleteAll();
    }

    @Test
    void givenValidRequest_whenCreateGame_thenReturnCreatedResponse() throws Exception {
        mockMvc.perform(post("/games")
                   .contentType(APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(getGameCreateRequest())))
               .andExpect(status().isCreated())
               .andExpect(header().exists("Location"));
    }

    @Test
    void givenValidRequest_whenJoinGame_thenReturnNoContentResponse() throws Exception {
        var gameDocument = getGameDocument();
        var savedGameDocument = gameRepository.save(gameDocument);

        mockMvc.perform(patch("/games/{id}", savedGameDocument.getId())
                   .contentType(APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(getGameJoinRequest())))
               .andExpect(status().isNoContent());
    }

    @Test
    void givenValidRequest_whenRestartGame_thenReturnNoContentResponse() throws Exception {
        var gameDocument = getGameDocument();
        gameDocument.setStatus(IN_PROGRESS.getValue());
        var savedGameDocument = gameRepository.save(gameDocument);

        mockMvc.perform(put("/games/{id}", savedGameDocument.getId())
                   .contentType(APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void givenValidRequest_whenQuitGame_thenReturnNoContentResponse() throws Exception {
        var gameDocument = getGameDocument();
        gameDocument.setStatus(IN_PROGRESS.getValue());
        var savedGameDocument = gameRepository.save(gameDocument);

        mockMvc.perform(delete("/games/{id}", savedGameDocument.getId())
                   .contentType(APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void givenValidRequest_whenGetGame_thenReturnGameResponse() throws Exception {
        var gameDocument = getGameDocument();
        gameDocument.setStatus(IN_PROGRESS.getValue());
        var savedGameDocument = gameRepository.save(gameDocument);

        mockMvc.perform(get("/games/{id}", savedGameDocument.getId())
                   .contentType(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value(IN_PROGRESS.getValue()))
               .andExpect(jsonPath("$.playerTurn").value(PLAYER_1.getValue()))
               .andExpect(jsonPath("$.pits").isArray())
               .andExpect(jsonPath("$.pits", hasItems(PITS_AS_INITIAL.toArray())));
    }

    @Test
    void givenValidRequest_whenPlayGame_thenReturnGamePlayResponse() throws Exception {
        var gameDocument = getGameDocumentWithStatusInProgress();
        var savedGameDocument = gameRepository.save(gameDocument);

        mockMvc.perform(put("/games/{id}/players/{playerId}", savedGameDocument.getId(), PLAYER_1.getValue())
                   .contentType(APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(getBoardPlayRequest())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value(IN_PROGRESS.getValue()))
               .andExpect(jsonPath("$.playerTurn").value(PLAYER_2.getValue()))
               .andExpect(jsonPath("$.pits").isArray())
               .andExpect(jsonPath("$.pits", hasItems(6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0)));
    }

    @Test
    void givenValidRequest_whenGetGameScore_thenReturnGameScoreResponse() throws Exception {
        var gameDocument = getGameDocumentWithStatusOver();
        var savedGameDocument = gameRepository.save(gameDocument);

        mockMvc.perform(get("/games/{id}/score", savedGameDocument.getId())
                   .contentType(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.winnerPlayer").value(PLAYER_2.getValue()))
               .andExpect(jsonPath("$.scores").isArray())
               .andExpect(jsonPath("$.scores", hasItems(30, 42)));
    }

    private GameDocument getGameDocument() {
        var gameDocument = new GameDocument();
        gameDocument.setStatus(CREATED.getValue());
        gameDocument.setPlayers(List.of("Jack Daniel"));
        gameDocument.setPlayerTurn(1);
        gameDocument.setPits(PITS_AS_INITIAL);
        return gameDocument;
    }

    private GameCreateRequest getGameCreateRequest() {
        return new GameCreateRequest("Jack Daniel");
    }

    private GameJoinRequest getGameJoinRequest() {
        return new GameJoinRequest("George Dickel");
    }

    private GameDocument getGameDocumentWithStatusInProgress() {
        var gameDocument = new GameDocument();
        gameDocument.setStatus(IN_PROGRESS.getValue());
        gameDocument.setPlayerTurn(1);
        gameDocument.setPlayers(List.of("Jack Daniel", "George Dickel"));
        gameDocument.setPits(PITS_AS_INITIAL);
        return gameDocument;
    }

    private GamePlayRequest getBoardPlayRequest() {
        return new GamePlayRequest(3);
    }

    private GameDocument getGameDocumentWithStatusOver() {
        var gameDocument = new GameDocument();
        gameDocument.setStatus(OVER.getValue());
        gameDocument.setPlayerTurn(1);
        gameDocument.setPlayers(List.of("Jack Daniel", "George Dickel"));
        gameDocument.setPits(PITS_AS_OVER);
        return gameDocument;
    }
}