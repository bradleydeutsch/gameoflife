package com.gameoflife;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Component
public class Game {

    private final int iterations;
    private final int gameWidth;
    private final int gameHeight;
    private final String startingPointsFilePath;
    private final StateService stateService;

    private final GameState gameState;

    @Autowired
    public Game(
            @Value("${game.iterations}") int iterations,
            @Value("${game.width}") int gameWidth,
            @Value("${game.height}") int gameHeight,
            @Nonnull @Value("${game.starting.points.file.path}") String startingPointsFilePath,
            @Nonnull StateService stateService
    ) {

        notNull(startingPointsFilePath);
        notNull(stateService);

        this.iterations = iterations;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.startingPointsFilePath = startingPointsFilePath;
        this.stateService = stateService;

        final Map<Coord, Boolean> startingPoints = stateService.getStartingPoints(startingPointsFilePath);

        final GameState.Builder gameStateBuilder = GameState.Builder.create()
                .withWidth(gameWidth)
                .withHeight(gameHeight);

        for (Map.Entry<Coord, Boolean> entry : startingPoints.entrySet()) {
            gameStateBuilder.withCurrentValue(entry.getKey(), entry.getValue());
        }

        gameState = gameStateBuilder.build();

        iterateGame();
    }

    private void iterateGame() {

        printGameState();

        for (int loopCount = iterations; loopCount > 0; loopCount--) {
            int i;
            for (i = 0; i < gameWidth; i++) {
                int j;
                for (j = 0; j < gameHeight; j++) {
                    Coord coord = new Coord(i, j);
                    gameState.setState(coord, stateService.getNewState(gameState, coord));
                }
                j = 0;
            }
            i = 0;

            printGameState();
        }
    }

    private void printGameState() {

        System.out.println(gameState.toString());
        System.out.println("-------------------");
    }
}
