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

import java.util.List;

import ru.gatsko.edu.game.base.ActionListener;
import ru.gatsko.edu.game.base.Base2DScreen;
import ru.gatsko.edu.game.math.Rect;
import ru.gatsko.edu.game.pool.BulletPool;
import ru.gatsko.edu.game.pool.EnemyPool;
import ru.gatsko.edu.game.pool.ExplosionPool;
import ru.gatsko.edu.game.sprite.Background;
import ru.gatsko.edu.game.sprite.Bullet;
import ru.gatsko.edu.game.sprite.ButtonNewGame;
import ru.gatsko.edu.game.sprite.EnemyShip;
import ru.gatsko.edu.game.sprite.Explosion;
import ru.gatsko.edu.game.sprite.MainShip;
import ru.gatsko.edu.game.sprite.MessageGameOver;
import ru.gatsko.edu.game.sprite.Star;
import ru.gatsko.edu.game.utils.EnemiesEmitter;

/**
 * Created by gatsko on 23.09.2018.
 */

public class GameScreen extends Base2DScreen implements ActionListener {
    private static final int STARS_COUNT = 100;
    private enum State {PLAYING, GAMEOVER};
    BulletPool bulletPool;
    Background background;
    ButtonNewGame buttonNewGame;
    Music music;
    Sound shootSound, enemySound, boomSound;
    TextureAtlas atlas;
    Texture bg;
    Star stars[];
    MainShip ship;
    EnemyPool enemyPool;
    ExplosionPool explosionPool;
    EnemiesEmitter enemiesEmitter;
    State state;
    MessageGameOver messageGameOver;
    public GameScreen(Game game) { super(game); }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt();
        atlas = new TextureAtlas("mainAtlas.tpack");
        bg = new Texture("background.jpg");
        buttonNewGame = new ButtonNewGame(atlas, this);
        background = new Background(new TextureRegion(bg));
        bulletPool = new BulletPool();
        shootSound = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
        enemySound = Gdx.audio.newSound(Gdx.files.internal("bullet.wav"));
        boomSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        explosionPool = new ExplosionPool(atlas, boomSound);
        ship = new MainShip(atlas, bulletPool, explosionPool, shootSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, enemySound, ship);
        messageGameOver = new MessageGameOver(atlas);
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
        startNewGame();
    }


    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
        enemyPool.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
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
        //столкновение кораблей
        if (!ship.isDestroyed()) {
            List<EnemyShip> enemyList = enemyPool.getActiveObjects();
            for (EnemyShip enemy : enemyList) {
                if (enemy.isDestroyed()) continue;
                float minDist = enemy.getHalfWidth() + ship.getHalfWidth();
                if (enemy.pos.dst(ship.pos) < minDist) {
                    enemy.destroy();
                    enemy.boom();
                    ship.damage(enemy.getHP() * 5);
                }
            }
            //попадание нашей пули во врага
            List<Bullet> bulletList = bulletPool.getActiveObjects();
            for (EnemyShip enemy : enemyList) {
                if (enemy.isDestroyed()) continue;
                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() != ship || bullet.isDestroyed()) continue;
                    if (enemy.isBulletCollision(bullet)) {
                        bullet.destroy();
                        enemy.damage(bullet.getDamage());
                    }
                }
            }
            //попадание врага в нас
            if (!ship.isDestroyed()) {
                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() == ship || bullet.isDestroyed()) continue;
                    if (ship.isBulletCollision(bullet)) {
                        bullet.destroy();
                        ship.damage(bullet.getDamage());
                    }
                }
            }
        }
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
        if (state == State.GAMEOVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        batch.end();
    }

    public void update(float delta){
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].update(delta); }
        explosionPool.updateActiveObjects(delta);
        bulletPool.updateActiveObjects(delta);
        if (ship.isDestroyed()) {
            state = State.GAMEOVER;
        }
        switch (state) {
            case PLAYING:
                ship.update(delta);
                enemyPool.updateActiveObjects(delta);
                enemiesEmitter.generateEnemies(delta);
                break;
            case GAMEOVER:
                break;
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state != State.GAMEOVER) {
            ship.touchDown(touch, pointer);
        } else {
            buttonNewGame.touchDown(touch,pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (state != State.GAMEOVER) ship.touchDragged(touch, pointer);
        return super.touchDragged(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state != State.GAMEOVER) {
            ship.touchUp(touch, pointer);
        } else {
            buttonNewGame.touchUp(touch,pointer);
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonNewGame) {
            startNewGame();
        }
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

    private void startNewGame() {
        state = State.PLAYING;
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        ship.startNewGame(worldBounds);
    }
}
