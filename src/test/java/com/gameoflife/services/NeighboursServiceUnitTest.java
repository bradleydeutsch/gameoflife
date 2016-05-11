package com.gameoflife.services;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NeighboursServiceUnitTest {

    private NeighboursService sut;

    @Before
    public void before() {
        sut = new NeighboursService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNeighbourValuesWillThrowAnExceptionIfGameStateIsNull() {

        // test fixtures
        final GameState gameState = null;
        final Coord coord = new Coord(0, 0);

        // when
        sut.getNeighbourValues(gameState, coord);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNeighbourValuesWillThrowAnExceptionIfCoordIsNull() {

        // test fixtures
        final GameState gameState = GameState.Builder.create()
                .withWidth(0)
                .withHeight(0)
                .build();
        final Coord coord = null;

        // when
        sut.getNeighbourValues(gameState, coord);
    }

    @Test
    public void getNeighbourValuesReturnsListOfNeighbourValues() {

        // test fixtures
        // 0 1 1
        // 1 x 0
        // 0 0 0
        final GameState gameState = GameState.Builder.create()
                .withWidth(3)
                .withHeight(3)
                .withCurrentValue(1, 0, Boolean.TRUE)
                .withCurrentValue(2, 0, Boolean.TRUE)
                .withCurrentValue(0, 1, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(1, 1);

        // when
        final List<Boolean> result = sut.getNeighbourValues(gameState, coord);

        // then
        assertThat(result).hasSize(8);
        assertThat(trueCount(result)).isEqualTo(3);
    }

    @Test
    public void getNeighbourValuesWillOnlyAddValuesForNeighboursThatExist() {

        // test fixtures
        // x 0
        // 0 1
        final GameState gameState = GameState.Builder.create()
                .withWidth(2)
                .withHeight(2)
                .withCurrentValue(1, 1, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(0, 0);

        // when
        final List<Boolean> result = sut.getNeighbourValues(gameState, coord);

        // then
        assertThat(result).hasSize(3);
        assertThat(trueCount(result)).isEqualTo(1);
    }

    private int trueCount(@Nonnull final List<Boolean> result) {

        int trueCount = 0;
        for (Boolean value : result) {
            if (value) {
                trueCount++;
            }
        }

        return trueCount;
    }
}