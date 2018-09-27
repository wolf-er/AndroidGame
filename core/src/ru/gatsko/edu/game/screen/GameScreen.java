package ru.gatsko.edu.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.ActionListener;
import ru.gatsko.edu.game.base.Base2DScreen;
import ru.gatsko.edu.game.math.Rect;
import ru.gatsko.edu.game.pool.BulletPool;
import ru.gatsko.edu.game.sprite.Background;
import ru.gatsko.edu.game.sprite.MainShip;
import ru.gatsko.edu.game.sprite.Star;

/**
 * Created by gatsko on 23.09.2018.
 */

public class GameScreen extends Base2DScreen implements ActionListener {
    private static final int STARS_COUNT = 100;
    BulletPool bulletPool;
    Background background;
    TextureAtlas atlas;
    Texture bg;
    Vector2 speed;
    Star stars[];
    MainShip ship;

    public GameScreen(Game game) { super(game); }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt();
        atlas = new TextureAtlas("mainAtlas.tpack");
        bg = new Texture("background.jpg");
        background = new Background(new TextureRegion(bg));
        bulletPool = new BulletPool();
        ship = new MainShip(atlas, bulletPool);
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < STARS_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
    }


    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].resize(worldBounds); }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }
    public void checkCollisions(){
    }
    public void deleteAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
    }

    public void draw(){
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].draw(batch); }
        ship.draw(batch);
        bulletPool.drawActiveObjects(batch);
        batch.end();
    }

    public void update(float delta){
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].update(delta); }
        ship.update(delta);
        bulletPool.updateActiveObjects(delta);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        ship.touchDragged(touch, pointer);
        return super.touchDragged(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        ship.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }
}
