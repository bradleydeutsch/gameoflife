package com.gameoflife.controllers;

import com.gameoflife.models.GameState;
import com.gameoflife.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.Assert.notNull;

@RestController
@RequestMapping(value = "/game", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GameController {

    private final int gameWidth;
    private final int gameHeight;
    private final String startingPointsFilePath;
    private final GameService gameService;

    private GameState gameState;

    @Autowired
    public GameController(
            @Value("${game.width}") int gameWidth,
            @Value("${game.height}") int gameHeight,
            @Nonnull @Value("${game.starting.points.file.path}") String startingPointsFilePath,
            @Nonnull GameService gameService
    ) {

        notNull(startingPointsFilePath);
        notNull(gameService);

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.startingPointsFilePath = startingPointsFilePath;
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(OK)
    @Nonnull
    public GameState startNewGame() {

        gameState = gameService.createNewGame(gameWidth, gameHeight, startingPointsFilePath);

        return gameState;
    }

    @RequestMapping(value = "/pass", method = RequestMethod.GET)
    @ResponseStatus(OK)
    @Nonnull
    public GameState makeGamePass() {

        gameService.makeABoardPass(gameState);

        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
