package com.gameoflife;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;

import javax.annotation.Nonnull;

import static org.springframework.util.Assert.notNull;

public class StateService {

    public Boolean getNextState(@Nonnull GameState gameState, @Nonnull Coord coord) {

        notNull(gameState);
        notNull(coord);

        return null;
    }
}
