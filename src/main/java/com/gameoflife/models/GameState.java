package com.gameoflife.models;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

public class GameState {

    private final int width;
    private final int height;
    private final Map<Integer, Map<Integer, Boolean>> cells;

    private GameState(
            int width,
            int height,
            @Nonnull final Map<Integer, Map<Integer, Boolean>> cells
    ) {

        notNull(cells);

        this.width = width;
        this.height = height;
        this.cells = cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Nonnull
    public Map<Integer, Map<Integer, Boolean>> getCells() {
        return cells;
    }

    @Nullable
    public Boolean getState(final int x, final int y) {

        return cells.containsKey(x) ? cells.get(x).get(y) : null;
    }

    @Nullable
    public Boolean getState(@Nonnull final Coord coord) {

        notNull(coord);

        return cells.containsKey(coord.getX()) ? cells.get(coord.getX()).get(coord.getY()) : null;
    }

    public void setState(final int x, final int y, @Nonnull final Boolean value) {

        notNull(value);

        cells.get(x).put(y, value);
    }

    public void setState(@Nonnull final Coord coord, @Nonnull final Boolean value) {

        notNull(value);

        cells.get(coord.getX()).put(coord.getY(), value);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /*@Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, Map<Integer, Boolean>> x : cells.entrySet()) {
            for (Map.Entry<Integer, Boolean> y : x.getValue().entrySet()) {
                sb.append(y.getValue() ? "X" : ".").append(" ");
            }
            sb.replace(sb.length() - 1, sb.length(), "").append("\n");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }*/

    public static class Builder {

        private int width;
        private int height;
        private Map<Coord, Boolean> values = Maps.newHashMap();

        @Nonnull
        public static Builder create() {
            return new Builder();
        }

        @Nonnull
        public Builder withWidth(final int width) {
            this.width = width;
            return this;
        }

        @Nonnull
        public Builder withHeight(final int height) {
            this.height = height;
            return this;
        }

        @Nonnull
        public Builder withCurrentValue(@Nonnull final Coord coord, @Nonnull final Boolean value) {
            values.put(coord, value);
            return this;
        }

        @Nonnull
        public Builder withCurrentValue(final int x, final int y, @Nonnull final Boolean value) {
            values.put(new Coord(x, y), value);
            return this;
        }

        @Nonnull
        public GameState build() {

            final Map<Integer, Map<Integer, Boolean>> gameMap = Maps.newHashMap();

            for (int i = 0; i < width; i++) {
                Map<Integer, Boolean> innerMap = Maps.newHashMap();
                for (int j = 0; j < height; j++) {
                    innerMap.put(j, false);
                }
                gameMap.put(i, innerMap);
            }

            final GameState gameState = new GameState(width, height, gameMap);

            for (Map.Entry<Coord, Boolean> entry : values.entrySet()) {
                gameState.setState(entry.getKey(), entry.getValue());
            }

            return gameState;
        }
    }
}
