package com.bol.kalaha.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Priority {
    HIGH_PRIORITY(10),
    MEDIUM_PRIORITY(20),
    LOW_PRIORITY(30);

    private final int value;
}