package com.bol.kalaha.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum PlayerNo {
    PLAYER_1(1),
    PLAYER_2(2);

    private final int value;

    @JsonCreator
    public static PlayerNo of(final int value) {
        return Arrays.stream(PlayerNo.values())
                     .filter(playerNo -> playerNo.value == value)
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Unexpected value [" + value + "]"));
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public PlayerNo nextPlayer() {
        return this == PLAYER_1 ? PLAYER_2 : PLAYER_1;
    }

    @Override
    public String toString() {
        return "PlayerNo{" +
            "value=" + value +
            '}';
    }
}