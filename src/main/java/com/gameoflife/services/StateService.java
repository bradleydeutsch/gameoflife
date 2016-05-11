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
            @Nonnull NeighboursService neighboursService,
            @Nonnull FileService fileService
    ) {

        notNull(neighboursService);
        notNull(fileService);

        this.neighboursService = neighboursService;
        this.fileService = fileService;
    }

    @Nonnull
    public Map<Coord, Boolean> getStartingPoints(@Nonnull String filePath) {

        notNull(filePath);

        Map<Coord, Boolean> startingPoints = Maps.newHashMap();

        try {

            BufferedReader bufferedReader = getBufferedReaderForFile(filePath);

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
    private BufferedReader getBufferedReaderForFile(@Nonnull String filePath) throws FileNotFoundException {

        File file = fileService.readFile(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        return new BufferedReader(new InputStreamReader(fileInputStream));
    }

    @Nonnull
    private Coord getCoordFromFileLine(@Nonnull String line) {

        String[] coords = line.split(",");

        return new Coord(Integer.parseInt(coords[0].trim()), Integer.parseInt(coords[1].trim()));
    }

    @Nonnull
    public Boolean getNewState(@Nonnull GameState gameState, @Nonnull Coord coord) {

        notNull(gameState);
        notNull(coord);

        Boolean currentlyLive = gameState.getState(coord);
        List<Boolean> neighbourValues = neighboursService.getNeighbourValues(gameState, coord);
        int trueCount = trueCount(neighbourValues);

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
