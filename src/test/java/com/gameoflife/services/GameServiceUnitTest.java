package com.gameoflife.services;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceUnitTest {

    private GameService sut;

    @Mock
    private StateService stateService;

    @Before
    public void before() {
        sut = new GameService(stateService);
    }

    @Test
    public void createNewGameWillReturnNewGameState() {

        // test fixtures
        final int gameWidth = 1;
        final int gameHeight = 2;
        final String startingPointsFilePath = null;

        // when
        GameState result = sut.createNewGame(gameWidth, gameHeight, startingPointsFilePath);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCells()).hasSize(1);
        assertThat(result.getCells().get(0)).hasSize(2);

        verifyNoMoreInteractions(stateService);
    }

    @Test
    public void createNewGameWillReturnNewGameStateWithPreSelectedCells() {

        // test fixtures
        final int gameWidth = 10;
        final int gameHeight = 15;
        final String startingPointsFilePath = "some-path";

        // test harness
        Map<Coord, Boolean> startingPoints = Maps.newHashMap();
        startingPoints.put(new Coord(1, 1), true);
        startingPoints.put(new Coord(3, 4), true);

        // given
        given(stateService.getStartingPoints(anyString()))
                .willReturn(startingPoints);

        // when
        GameState result = sut.createNewGame(gameWidth, gameHeight, startingPointsFilePath);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCells()).hasSize(10);
        assertThat(result.getCells().get(0)).hasSize(15);
        assertThat(result.getCells().get(0).get(0)).isFalse();
        assertThat(result.getCells().get(1).get(1)).isTrue();
        assertThat(result.getCells().get(3).get(4)).isTrue();

        verify(stateService).getStartingPoints(startingPointsFilePath);
    }

    @Test(expected = IllegalArgumentException.class)
    public void makeABoardPassWithNullGameStateWillThrowAnException() {

        // test fixtures
        final GameState gameState = null;

        // when
        sut.makeABoardPass(gameState);
    }

    @Test
    public void makeABoardPassWillGetAndUpdateANewStateForEachCell() {

        // test fixtures
        final GameState gameState = GameState.Builder.create()
                .withWidth(2)
                .withHeight(2)
                .build();

        // when
        sut.makeABoardPass(gameState);

        // then
        verify(stateService).getNewState(same(gameState), eq(new Coord(0, 0)));
        verify(stateService).getNewState(same(gameState), eq(new Coord(1, 0)));
        verify(stateService).getNewState(same(gameState), eq(new Coord(0, 1)));
        verify(stateService).getNewState(same(gameState), eq(new Coord(1, 1)));

        verifyNoMoreInteractions(stateService);
    }
}