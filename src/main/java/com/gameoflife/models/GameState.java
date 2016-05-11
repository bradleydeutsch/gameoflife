package com.gameoflife.models;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

public class GameState {

    private final Map<Integer, Map<Integer, Boolean>> game;

    private GameState(
            @Nonnull Map<Integer, Map<Integer, Boolean>> game
    ) {

        notNull(game);

        this.game = game;
    }

    @Nullable
    public Boolean getState(int x, int y) {

        return game.containsKey(x) ? game.get(x).get(y) : null;
    }

    @Nullable
    public Boolean getState(@Nonnull Coord coord) {

        notNull(coord);

        return game.containsKey(coord.getX()) ? game.get(coord.getX()).get(coord.getY()) : null;
    }

    public void setState(int x, int y, @Nonnull Boolean value) {

        notNull(value);

        game.get(x).put(y, value);
    }

    public void setState(@Nonnull Coord coord, @Nonnull Boolean value) {

        notNull(value);

        game.get(coord.getX()).put(coord.getY(), value);
    }

    public static class Builder {

        private int width;
        private int height;
        private Map<Coord, Boolean> values = Maps.newHashMap();

        @Nonnull
        public static Builder create() {
            return new Builder();
        }

        @Nonnull
        public Builder withWidth(int width) {
            this.width = width;
            return this;
        }

        @Nonnull
        public Builder withHeight(int height) {
            this.height = height;
            return this;
        }

        @Nonnull
        public Builder withCurrentValue(@Nonnull Coord coord, @Nonnull Boolean value) {
            values.put(coord, value);
            return this;
        }

        @Nonnull
        public Builder withCurrentValue(int x, int y, @Nonnull Boolean value) {
            values.put(new Coord(x, y), value);
            return this;
        }

        @Nonnull
        public GameState build() {

            Map<Integer, Map<Integer, Boolean>> gameMap = Maps.newHashMap();

            for (int i = 0; i < width; i++) {
                Map<Integer, Boolean> innerMap = Maps.newHashMap();
                for (int j = 0; j < height; j++) {
                    innerMap.put(j, false);
                }
                gameMap.put(i, innerMap);
            }

            final GameState gameState = new GameState(gameMap);

            for (Map.Entry<Coord, Boolean> entry : values.entrySet()) {
                gameState.setState(entry.getKey(), entry.getValue());
            }

            return gameState;
        }
    }
}
