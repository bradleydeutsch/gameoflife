package com.gameoflife.models;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

public class GameState {

    private final Map<Integer, Map<Integer, Boolean>> game;

    public GameState(
            int width,
            int height
    ) {

        game = Maps.newHashMap();

        for (int i = 0; i < width; i++) {
            Map<Integer, Boolean> innerMap = Maps.newHashMap();
            for (int j = 0; j < height; j++) {
                innerMap.put(j, false);
            }
            game.put(i, innerMap);
        }
    }

    public Boolean getState(int x, int y) {

        return game.containsKey(x) ? game.get(x).get(y) : null;
    }

    public void setState(int x, int y, @Nonnull Boolean value) {

        notNull(value);

        game.get(x).put(y, value);
    }
}
