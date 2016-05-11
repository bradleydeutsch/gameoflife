package com.gameoflife;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

import static org.springframework.util.Assert.notNull;

@Component
public class StateService {

    private final NeighboursService neighboursService;

    @Autowired
    public StateService(
            @Nonnull NeighboursService neighboursService
    ) {

        notNull(neighboursService);

        this.neighboursService = neighboursService;
    }

    @Nonnull
    public Boolean getNewState(@Nonnull GameState gameState, @Nonnull Coord coord) {

        notNull(gameState);
        notNull(coord);

        Boolean currentlyLive = gameState.getState(coord);
        List<Boolean> neighbourValues = neighboursService.getNeighbourValues(gameState, coord);
        int trueCount = trueCount(neighbourValues);

        if (neighbourValues.size() < 8) {
            return currentlyLive;
        }

        if (currentlyLive && trueCount < 2) {
            return Boolean.FALSE;
        } else if (currentlyLive && trueCount > 3) {
            return Boolean.FALSE;
        } else if (currentlyLive && trueCount == 2) {
            return Boolean.TRUE;
        } else if (currentlyLive && trueCount == 3) {
            return Boolean.TRUE;
        } else if (!currentlyLive && trueCount == 3) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
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
