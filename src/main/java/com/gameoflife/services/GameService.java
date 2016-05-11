package com.gameoflife.services;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Component
public class GameService {

    private final StateService stateService;

    @Autowired
    public GameService(
            @Nonnull final StateService stateService
    ) {

        notNull(stateService);

        this.stateService = stateService;
    }

    @Nonnull
    public GameState createNewGame(final int gameWidth, final int gameHeight,
                                   @Nullable final String startingPointsFilePath) {

        final GameState.Builder gameStateBuilder = GameState.Builder.create()
                .withWidth(gameWidth)
                .withHeight(gameHeight);

        setStartingPoints(gameStateBuilder, startingPointsFilePath);

        return gameStateBuilder.build();
    }

    public void makeABoardPass(@Nonnull final GameState gameState) {

        notNull(gameState);

        for (int i = 0; i < gameState.getCells().size(); i++) {
            int j = 0;
            for (; j < gameState.getCells().get(0).size(); j++) {
                Coord coord = new Coord(i, j);
                gameState.setState(coord, stateService.getNewState(gameState, coord));
            }
        }
    }

    private void setStartingPoints(@Nonnull final GameState.Builder gameStateBuilder,
                                   @Nullable final String startingPointsFilePath) {

        if (startingPointsFilePath == null) {
            return;
        }

        final Map<Coord, Boolean> startingPoints = stateService.getStartingPoints(startingPointsFilePath);
        for (Map.Entry<Coord, Boolean> entry : startingPoints.entrySet()) {
            gameStateBuilder.withCurrentValue(entry.getKey(), entry.getValue());
        }
    }
}
