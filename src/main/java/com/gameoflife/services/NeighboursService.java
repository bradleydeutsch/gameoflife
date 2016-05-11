package com.gameoflife.services;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static org.springframework.util.Assert.notNull;

@Component
public class NeighboursService {

    @Nonnull
    public List<Boolean> getNeighbourValues(@Nonnull GameState gameState, @Nonnull Coord coord) {

        notNull(gameState);
        notNull(coord);

        List<Boolean> neighbourValues = Lists.newArrayList();

        // topLeft
        neighbourValues.add(gameState.getState(coord.getX() - 1, coord.getY() - 1));
        // top
        neighbourValues.add(gameState.getState(coord.getX(), coord.getY() - 1));
        // topRight
        neighbourValues.add(gameState.getState(coord.getX() + 1, coord.getY() - 1));
        // left
        neighbourValues.add(gameState.getState(coord.getX() - 1, coord.getY()));
        // right
        neighbourValues.add(gameState.getState(coord.getX() + 1, coord.getY()));
        // bottomLeft
        neighbourValues.add(gameState.getState(coord.getX() - 1, coord.getY() + 1));
        // bottom
        neighbourValues.add(gameState.getState(coord.getX(), coord.getY() + 1));
        // bottomRight
        neighbourValues.add(gameState.getState(coord.getX() + 1, coord.getY() + 1));

        removeNullsFromList(neighbourValues);

        return neighbourValues;
    }

    private void removeNullsFromList(@Nonnull List<Boolean> neighbourValues) {

        neighbourValues.removeAll(Collections.singleton(null));
    }
}
