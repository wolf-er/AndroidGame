package ru.gatsko.edu.game.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/**
 * Created by gatsko on 17.09.2018.
 */

public class Base2DScreen implements Screen, InputProcessor{
    protected Game game;

    public Base2DScreen(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        System.out.println("Show");
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize width =" + width + " hight = " + height);
    }

    @Override
    public void pause() {
        System.out.println("Pause");
    }

    @Override
    public void resume() {
        System.out.println("Resume");
    }

    @Override
    public void hide() {
        System.out.println("Hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("Dispose");
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("key Down keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown " + screenX + ' ' + screenY + ' ' + pointer + ' ' + button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp " + screenX + ' ' + screenY + ' ' + pointer + ' ' + button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged " + screenX + ' ' + screenY + ' ' + pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
