package ru.gatsko.edu.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import ru.gatsko.edu.game.pool.EnemyPool;
import ru.gatsko.edu.game.pool.ExplosionPool;
import ru.gatsko.edu.game.sprite.Background;
import ru.gatsko.edu.game.sprite.Explosion;
import ru.gatsko.edu.game.sprite.MainShip;
import ru.gatsko.edu.game.sprite.Star;
import ru.gatsko.edu.game.utils.EnemiesEmitter;

/**
 * Created by gatsko on 23.09.2018.
 */

public class GameScreen extends Base2DScreen implements ActionListener {
    private static final int STARS_COUNT = 100;
    BulletPool bulletPool;
    Background background;
    Music music;
    Sound shootSound, enemySound, boomSound;
    TextureAtlas atlas;
    Texture bg;
    Star stars[];
    MainShip ship;
    EnemyPool enemyPool;
    ExplosionPool explosionPool;
    EnemiesEmitter enemiesEmitter;
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
        shootSound = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
        enemySound = Gdx.audio.newSound(Gdx.files.internal("bullet.wav"));
        boomSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        ship = new MainShip(atlas, bulletPool, shootSound);
        explosionPool = new ExplosionPool(atlas, boomSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, enemySound, ship);

        enemiesEmitter = new EnemiesEmitter(enemyPool, atlas, worldBounds);
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < STARS_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        //music.play();
        music.setPosition(34f);
    }


    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
        enemyPool.resize(worldBounds);
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
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    public void draw(){
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].draw(batch); }
        ship.draw(batch);
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        batch.end();
    }

    public void update(float delta){
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].update(delta); }
        ship.update(delta);
        bulletPool.updateActiveObjects(delta);
        enemyPool.updateActiveObjects(delta);
        explosionPool.updateActiveObjects(delta);
        enemiesEmitter.generateEnemies(delta);
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
        switch (keycode) {
            case Input.Keys.R:
                Explosion exp = explosionPool.obtain();
                exp.set(0.15f, worldBounds.pos);
                break;
        }
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
        explosionPool.dispose();
        enemyPool.dispose();
        super.dispose();
    }
}
