package com.bol.kalaha.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Status {
    CREATED("Created"),
    IN_PROGRESS("In Progress"),
    OVER("Over");

    private final String value;

    @JsonCreator
    public static Status of(String value) {
        return Arrays.stream(Status.values())
                     .filter(gameStatus -> gameStatus.value.equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Unexpected value [" + value + "]"));
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}