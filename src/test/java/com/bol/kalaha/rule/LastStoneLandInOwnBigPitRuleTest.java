package com.bol.kalaha.rule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LastStoneLandInOwnBigPitRuleTest {
    @Autowired
    private LastStoneLandInOwnBigPitRule lastStoneLandInOwnBigPitRule;

    @Test
    void givenGameAsLastStoneLandInOwnBigPit_whenMatches_thenReturnsTrue() {
        // given
        var game = GameObjectMother.createGameAsLastStoneLandInOwnBigPit();

        // when
        var matched = lastStoneLandInOwnBigPitRule.matches(game);

        // Then
        assertTrue(matched);
    }

    @Test
    void givenOverGame_whenApply_thenPlayerTurnRepeated() {
        // given
        var game = GameObjectMother.createGameAsLastStoneLandInOwnBigPit();

        // when
        lastStoneLandInOwnBigPitRule.apply(game);

        // Then
        assertTrue(game.isAnotherTurn());
    }
}