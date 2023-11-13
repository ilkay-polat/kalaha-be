package com.bol.kalaha.rule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LastStoneLandInOwnEmptySmallPitRuleTest {

    @Autowired
    private LastStoneLandInOwnEmptySmallPitRule lastStoneLandInOwnEmptySmallPitRule;

    @Test
    void givenGameAsLastStoneLandInOwnEmptySmallPit_whenMatches_thenReturnsTrue() {
        // given
        var game = GameObjectMother.createGameAsLastStoneLandInOwnEmptySmallPit();

        // when
        var matched = lastStoneLandInOwnEmptySmallPitRule.matches(game);

        // Then
        assertTrue(matched);
    }

    @Test
    void givenGameAsLastStoneLandInOwnEmptySmallPit_whenApply_thenMoveStones() {
        // given
        var game = GameObjectMother.createGameAsLastStoneLandInOwnEmptySmallPit();

        // when
        lastStoneLandInOwnEmptySmallPitRule.apply(game);

        // Then
        var expectedGame = GameObjectMother.createGameAsLastStoneLandInOwnEmptySmallPitAfterRuleExecution();
        assertThat(game)
            .usingRecursiveComparison()
            .isEqualTo(expectedGame);
    }
}