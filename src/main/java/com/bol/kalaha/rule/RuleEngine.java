package com.bol.kalaha.rule;

import com.bol.kalaha.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RuleEngine {
    private final List<Rule> rules;

    public void execute(final Game game) {
        rules.stream()
             .sorted(Comparator.comparing(Rule::getPriority))
             .filter(rule -> rule.matches(game))
             .forEach(rule -> rule.apply(game));
    }
}