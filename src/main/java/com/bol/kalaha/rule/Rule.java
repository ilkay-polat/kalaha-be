package com.bol.kalaha.rule;

import com.bol.kalaha.enums.Priority;
import com.bol.kalaha.model.Game;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Rule {
    private final Priority priority;

    protected abstract boolean matches(Game game);

    protected abstract void apply(Game game);

    int getPriority() {
        return priority.getValue();
    }
}