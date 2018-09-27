package ru.gatsko.edu.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import ru.gatsko.edu.game.screen.MenuScreen;

/**
 * Created by gatsko on 17.09.2018.
 */

public class Star2DGame extends Game {
    Music music;
    @Override
    public void create() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
        music.setPosition(34f);
        setScreen(new MenuScreen(this));
    }
}
