package ru.gatsko.edu.game.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.math.MatrixUtils;
import ru.gatsko.edu.game.math.Rect;


/**
 * Created by gatsko on 17.09.2018.
 */

public class Base2DScreen implements Screen, InputProcessor{
    protected Game game;
    protected SpriteBatch batch;
    private Rect screenBounds;
    protected Rect worldBounds;
    private Rect glBounds;
    protected Matrix4 worldToGl;
    protected Matrix3 screenToWorld;
    Vector2 touch;

    public Base2DScreen(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);
        this.batch = new SpriteBatch();
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0,0,1f,1f);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        this.touch = new Vector2();
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
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);
        float ascept = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * ascept);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);

        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    protected void resize(Rect worldBounds){
        System.out.println("resize width =" + worldBounds.getWidth() + " hight = " + worldBounds.getHeight());
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
        batch.dispose();
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
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println("touch " + touch.x + ' ' + touch.y + ' ' + pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp " + screenX + ' ' + screenY + ' ' + pointer + ' ' + button);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        System.out.println("touch " + touch.x + ' ' + touch.y + ' ' + pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged " + screenX + ' ' + screenY + ' ' + pointer);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);

        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged " + touch.x + ' ' + touch.y + ' ' + pointer);
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
