package com.gameoflife;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StateServiceUnitTest {

    private StateService sut;

    @Before
    public void before() {
        sut = new StateService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNextStateWillThrowAnExceptionIfGameStateIsNull() {

        // test fixtures
        final GameState gameState = null;
        final Coord coord = new Coord(0, 0);

        // when
        sut.getNextState(gameState, coord);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNextStateWillThrowAnExceptionIfCoordIsNull() {

        // test fixtures
        final GameState gameState = new GameState(0, 0);
        final Coord coord = null;

        // when
        sut.getNextState(gameState, coord);
    }

    @Test
    public void getNextStateWillReturnFalseIfLessThanTwoNeighboursAreTrue() {

        // test fixtures
        final GameState gameState = new GameState(0, 0);
        final Coord coord = null;

        // when
        Boolean result = sut.getNextState(gameState, coord);

        // then
        assertThat(result).isFalse();
    }
}