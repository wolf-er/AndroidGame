package ru.gatsko.edu.game;

import com.badlogic.gdx.Game;

import ru.gatsko.edu.game.screen.MenuScreen;

/**
 * Created by gatsko on 17.09.2018.
 */

public class Star2DGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
