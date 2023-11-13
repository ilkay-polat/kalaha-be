package com.bol.kalaha.rule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GameOverRuleTest {
    @Autowired
    private GameOverRule gameOverRule;

    @Test
    void givenOverGame_whenMatches_thenReturnsTrue() {
        // given
        var game = GameObjectMother.createGameWithOverStatus();

        // when
        var matched = gameOverRule.matches(game);

        // Then
        assertTrue(matched);
    }

    @Test
    void givenOverGame_whenApply_thenSetStatusOver() {
        // given
        var game = GameObjectMother.createGameWithOverStatus();

        // when
        gameOverRule.apply(game);

        // Then
        assertTrue(game.isOver());
    }
}