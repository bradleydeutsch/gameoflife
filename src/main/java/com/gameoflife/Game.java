package com.gameoflife;

import com.gameoflife.models.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Game {

    private final int gameWidth;
    private final int gameHeight;

    private final GameState gameState;

    @Autowired
    public Game(
            @Value("${game.width}") int gameWidth,
            @Value("${game.height}") int gameHeight
    ) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        this.gameState = new GameState(gameWidth, gameHeight);
    }

    @PostConstruct
    public void game() {
        System.out.println(gameState);
        System.out.println(gameHeight);
    }
}
