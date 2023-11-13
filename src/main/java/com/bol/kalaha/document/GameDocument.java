package com.bol.kalaha.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("games")
public class GameDocument {
    @Id
    private String id;

    private String status;

    private List<String> players;

    private List<Integer> pits;

    private int playerTurn;
}