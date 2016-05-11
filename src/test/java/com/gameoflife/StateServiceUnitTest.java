package com.gameoflife;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StateServiceUnitTest {

    private StateService sut;

    @Mock
    private NeighboursService neighboursService;

    @Before
    public void before() {
        sut = new StateService(neighboursService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNewStateWillThrowAnExceptionIfGameStateIsNull() {

        // test fixtures
        final GameState gameState = null;
        final Coord coord = new Coord(0, 0);

        // when
        sut.getNewState(gameState, coord);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNewStateWillThrowAnExceptionIfCoordIsNull() {

        // test fixtures
        final GameState gameState = GameState.Builder.create()
                .withWidth(0)
                .withHeight(0)
                .build();
        final Coord coord = null;

        // when
        sut.getNewState(gameState, coord);
    }

    @Test
    public void getNewStateWillReturnFalseIfCurrentStateIsTrueAndLessThanTwoNeighboursAreTrue() {

        // test fixtures
        // 0 0 0
        // 1 x 0
        // 0 0 0
        final GameState gameState = GameState.Builder.create()
                .withWidth(3)
                .withHeight(3)
                .withCurrentValue(0, 1, Boolean.TRUE)
                .withCurrentValue(1, 1, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(1, 1);

        // test harness
        final List<Boolean> neighbourValues = buildNeighbourValues(1);

        // given
        given(neighboursService.getNeighbourValues(any(GameState.class), any(Coord.class)))
                .willReturn(neighbourValues);

        // when
        Boolean result = sut.getNewState(gameState, coord);

        // then
        assertThat(result).isFalse();

        verify(neighboursService).getNeighbourValues(same(gameState), same(coord));
    }

    @Test
    public void getNewStateWillReturnFalseIfCurrentStateIsTrueAndMoreThanThreeNeighboursAreTrue() {

        // test fixtures
        // 0 0 0
        // 1 x 1
        // 0 1 1
        final GameState gameState = GameState.Builder.create()
                .withWidth(3)
                .withHeight(3)
                .withCurrentValue(0, 1, Boolean.TRUE)
                .withCurrentValue(1, 1, Boolean.TRUE) // x is true
                .withCurrentValue(2, 1, Boolean.TRUE)
                .withCurrentValue(1, 2, Boolean.TRUE)
                .withCurrentValue(2, 2, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(1, 1);

        // test harness
        final List<Boolean> neighbourValues = buildNeighbourValues(4);

        // given
        given(neighboursService.getNeighbourValues(any(GameState.class), any(Coord.class)))
                .willReturn(neighbourValues);

        // when
        Boolean result = sut.getNewState(gameState, coord);

        // then
        assertThat(result).isFalse();

        verify(neighboursService).getNeighbourValues(same(gameState), same(coord));
    }

    @Test
    public void getNewStateWillReturnTrueIfCurrentStateIsTrueAndItHasTwoNeighboursThatAreTrue() {

        // test fixtures
        // 0 0 0
        // 1 x 0
        // 0 1 0
        final GameState gameState = GameState.Builder.create()
                .withWidth(3)
                .withHeight(3)
                .withCurrentValue(0, 1, Boolean.TRUE)
                .withCurrentValue(1, 1, Boolean.TRUE) // x is true
                .withCurrentValue(1, 2, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(1, 1);

        // test harness
        final List<Boolean> neighbourValues = buildNeighbourValues(2);

        // given
        given(neighboursService.getNeighbourValues(any(GameState.class), any(Coord.class)))
                .willReturn(neighbourValues);

        // when
        Boolean result = sut.getNewState(gameState, coord);

        // then
        assertThat(result).isTrue();

        verify(neighboursService).getNeighbourValues(same(gameState), same(coord));
    }

    @Test
    public void getNewStateWillReturnTrueIfCurrentStateIsTrueAndItHasThreeNeighboursThatAreTrue() {

        // test fixtures
        // 0 0 1
        // 1 x 0
        // 0 1 0
        final GameState gameState = GameState.Builder.create()
                .withWidth(3)
                .withHeight(3)
                .withCurrentValue(2, 0, Boolean.TRUE)
                .withCurrentValue(0, 1, Boolean.TRUE)
                .withCurrentValue(1, 1, Boolean.TRUE) // x is true
                .withCurrentValue(1, 2, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(1, 1);

        // test harness
        final List<Boolean> neighbourValues = buildNeighbourValues(3);

        // given
        given(neighboursService.getNeighbourValues(any(GameState.class), any(Coord.class)))
                .willReturn(neighbourValues);

        // when
        Boolean result = sut.getNewState(gameState, coord);

        // then
        assertThat(result).isTrue();

        verify(neighboursService).getNeighbourValues(same(gameState), same(coord));
    }

    @Test
    public void getNewStateWillReturnTrueIfCurrentStateIsFalseAndItHasThreeNeighboursThatAreTrue() {

        // test fixtures
        // 1 0 0
        // 1 x 0
        // 0 1 0
        final GameState gameState = GameState.Builder.create()
                .withWidth(3)
                .withHeight(3)
                .withCurrentValue(0, 0, Boolean.TRUE)
                .withCurrentValue(0, 1, Boolean.TRUE)
                .withCurrentValue(1, 1, Boolean.FALSE) // x is false
                .withCurrentValue(1, 2, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(1, 1);

        // test harness
        final List<Boolean> neighbourValues = buildNeighbourValues(3);

        // given
        given(neighboursService.getNeighbourValues(any(GameState.class), any(Coord.class)))
                .willReturn(neighbourValues);

        // when
        Boolean result = sut.getNewState(gameState, coord);

        // then
        assertThat(result).isTrue();

        verify(neighboursService).getNeighbourValues(same(gameState), same(coord));
    }

    @Test
    public void getNewStateWillReturnFalseIfCurrentStateIsFalseAndItDoesntHaveThreeNeighboursThatAreTrue() {

        // test fixtures
        // 1 0 0
        // 1 x 1
        // 0 1 0
        final GameState gameState = GameState.Builder.create()
                .withWidth(3)
                .withHeight(3)
                .withCurrentValue(0, 0, Boolean.TRUE)
                .withCurrentValue(0, 1, Boolean.TRUE)
                .withCurrentValue(1, 1, Boolean.FALSE) // x is false
                .withCurrentValue(2, 1, Boolean.TRUE)
                .withCurrentValue(1, 2, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(1, 1);

        // test harness
        final List<Boolean> neighbourValues = buildNeighbourValues(4);

        // given
        given(neighboursService.getNeighbourValues(any(GameState.class), any(Coord.class)))
                .willReturn(neighbourValues);

        // when
        Boolean result = sut.getNewState(gameState, coord);

        // then
        assertThat(result).isFalse();

        verify(neighboursService).getNeighbourValues(same(gameState), same(coord));
    }

    @Test
    public void getNewStateWillReturnTheSameStateIfTheNumberOfNeighboursIsLessThanEight() {

        // test fixtures
        final GameState gameState = GameState.Builder.create()
                .withWidth(3)
                .withHeight(3)
                .withCurrentValue(0, 0, Boolean.TRUE)
                .build();
        final Coord coord = new Coord(0, 0);

        // test harness
        final List<Boolean> neighbourValues = Lists.newArrayList(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);

        // given
        given(neighboursService.getNeighbourValues(any(GameState.class), any(Coord.class)))
                .willReturn(neighbourValues);

        // when
        Boolean result = sut.getNewState(gameState, coord);

        // then
        assertThat(result).isTrue();

        verify(neighboursService).getNeighbourValues(same(gameState), same(coord));
    }

    private List<Boolean> buildNeighbourValues(int trueCount) {

        List<Boolean> neighbourValues = Lists.newArrayList();

        for (int i = 0; i < 8; i++) {
            neighbourValues.add((i < trueCount) ? Boolean.TRUE : Boolean.FALSE);
        }

        return neighbourValues;
    }
}