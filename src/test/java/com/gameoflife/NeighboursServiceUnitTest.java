package com.gameoflife;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import org.junit.Before;
import org.junit.Test;

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
        final GameState gameState = new GameState(0, 0);
        final Coord coord = null;

        // when
        sut.getNeighbourValues(gameState, coord);
    }

    @Test
    public void getNeighbourValuesReturnsListOfNeighbourValues() {

        // test fixtures
        final GameState gameState = new GameState(3, 3);
        // 0 1 1
        // 1 x 0
        // 0 0 0
        gameState.setState(1, 0, true);
        gameState.setState(2, 0, true);
        gameState.setState(0, 1, true);
        final Coord coord = new Coord(1, 1);

        // when
        List<Boolean> result = sut.getNeighbourValues(gameState, coord);

        // then
        assertThat(result).hasSize(8);
        assertThat(trueCount(result)).isEqualTo(3);
    }

    @Test
    public void getNeighbourValuesWillOnlyAddValuesForNeighboursThatExist() {

        // test fixtures
        final GameState gameState = new GameState(2, 2);
        // x 0
        // 0 1
        gameState.setState(1, 1, true);
        final Coord coord = new Coord(0, 0);

        // when
        List<Boolean> result = sut.getNeighbourValues(gameState, coord);

        // then
        assertThat(result).hasSize(3);
        assertThat(trueCount(result)).isEqualTo(1);
    }

    private int trueCount(List<Boolean> result) {

        int trueCount = 0;
        for (Boolean value : result) {
            if (value) {
                trueCount++;
            }
        }

        return trueCount;
    }
}