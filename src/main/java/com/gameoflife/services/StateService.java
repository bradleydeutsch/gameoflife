package com.gameoflife.services;

import com.gameoflife.models.Coord;
import com.gameoflife.models.GameState;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.util.Assert.notNull;

@Component
public class StateService {

    private final NeighboursService neighboursService;
    private final FileService fileService;

    @Autowired
    public StateService(
            @Nonnull final NeighboursService neighboursService,
            @Nonnull final FileService fileService
    ) {

        notNull(neighboursService);
        notNull(fileService);

        this.neighboursService = neighboursService;
        this.fileService = fileService;
    }

    @Nonnull
    public Map<Coord, Boolean> getStartingPoints(@Nonnull final String filePath) {

        notNull(filePath);

        final Map<Coord, Boolean> startingPoints = Maps.newHashMap();

        try {
            final BufferedReader bufferedReader = getBufferedReaderForFile(filePath);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                startingPoints.put(getCoordFromFileLine(line), Boolean.TRUE);
            }

            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println(format("Problem with reading the given filePath \"%s\"", filePath));
        }

        return startingPoints;
    }

    @Nonnull
    public Boolean getNewState(@Nonnull final GameState gameState, @Nonnull final Coord coord) {

        notNull(gameState);
        notNull(coord);

        final Boolean currentlyLive = gameState.getState(coord);
        final List<Boolean> neighbourValues = neighboursService.getNeighbourValues(gameState, coord);
        final int trueCount = trueCount(neighbourValues);

        if (currentlyLive == null) {
            throw new IllegalArgumentException(
                    format("The co-ordinate provided (%s, %s) is invalid", coord.getX(), coord.getY()));
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

    @Nonnull
    private BufferedReader getBufferedReaderForFile(@Nonnull final String filePath) throws FileNotFoundException {

        final File file = fileService.readFile(filePath);
        final FileInputStream fileInputStream = new FileInputStream(file);

        return new BufferedReader(new InputStreamReader(fileInputStream));
    }

    @Nonnull
    private Coord getCoordFromFileLine(@Nonnull final String line) {

        final String[] coords = line.split(",");

        return new Coord(Integer.parseInt(coords[0].trim()), Integer.parseInt(coords[1].trim()));
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
