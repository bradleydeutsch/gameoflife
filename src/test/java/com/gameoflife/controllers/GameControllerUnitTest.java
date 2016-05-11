package com.gameoflife.controllers;

import com.gameoflife.models.GameState;
import com.gameoflife.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerUnitTest {

    private GameController sut;

    private int gameWidth = 5;
    private int gameHeight = 10;
    private String startingPointsFilePath = "some-path";

    @Mock
    private GameService gameService;

    @Before
    public void before() {
        sut = new GameController(gameWidth, gameHeight, startingPointsFilePath, gameService);
    }

    @Test
    public void startNewGameReturnsNewGameState() {

        // test harness
        GameState gameState = GameState.Builder.create()
                .withWidth(1)
                .withHeight(1)
                .build();

        // given
        given(gameService.createNewGame(anyInt(), anyInt(), anyString()))
                .willReturn(gameState);

        // when
        GameState result = sut.startNewGame();

        // then
        assertThat(result).isSameAs(gameState);

        verify(gameService).createNewGame(gameWidth, gameHeight, startingPointsFilePath);
    }

    @Test
    public void makeGamePassWillUpdateAllCellsAndReturnTheLatestStateOfTheBoard() {

        // test harness
        GameState gameState = GameState.Builder.create()
                .withWidth(1)
                .withHeight(1)
                .build();
        sut.setGameState(gameState);

        // when
        GameState result = sut.makeGamePass();

        // then
        assertThat(result).isSameAs(gameState);

        verify(gameService).makeABoardPass(same(gameState));
    }
}