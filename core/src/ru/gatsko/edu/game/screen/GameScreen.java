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
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.gatsko.edu.game.base.ActionListener;
import ru.gatsko.edu.game.base.Base2DScreen;
import ru.gatsko.edu.game.base.Font;
import ru.gatsko.edu.game.base.Ship;
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
    private static final String POINTS = "Points: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
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
    int points;
    private int level;
    Font font;
    StringBuilder sbPoints = new StringBuilder();
    StringBuilder sbLevel = new StringBuilder();
    StringBuilder sbHP = new StringBuilder();
    MessageGameOver messageGameOver;
    public GameScreen(Game game) { super(game); }

    public int getLevel() {
        return level;
    }

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
        font = new Font("font.fnt","font.png");
        font.setFontSize(0.014f);
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

    private int upgradeLevel(int points, int level){
        if (points < 20) {return 1;}
        if (points < 50) {
            if (level != 2) {
                ship.upgradeLevel(0.01f, 0.01f, 1);
            }
            return 2;
        }
        if (points < 100) {
            if (level != 3) {
                ship.upgradeLevel(0.01f, 0.01f, 2);
            }
            return 3;
        }
        if (points < 200) {
            if (level != 4) {
                ship.upgradeLevel(0.01f, 0.01f, 3);
            }
            return 4;
        }
        if (points < 300) {
            if (level != 5) {
                ship.upgradeLevel(-0.04f, -0.04f, 4);
                ship.shootState = Ship.ShootState.DOUBLE;
            }
            return 5;
        }
        if (points < 500) {
            if (level != 6) {
                ship.upgradeLevel(0.02f, 0.02f, 5);
            }
            return 6;
        }
        if (points < 1000) {
            if (level != 7) {
                ship.upgradeLevel(0.02f, 0.02f, 6);
            }
            return 7;
        }
        if (points < 1500) {
            if (level != 8) {
                ship.upgradeLevel(0.02f, 0.02f, 7);
            }
            return 8;
        }
        if (points < 2000) {
            if (level != 9) {
                ship.upgradeLevel(0.02f, 0.02f, 8);
            }
            return 9;
        }
        if (points < 2500) {
            if (level != 10) {
                ship.upgradeLevel(-0.04f, -0.04f, 9);
                ship.shootState = Ship.ShootState.TRIPLE;
            }
            return 10;
        }
        if (points < 3000) {
            if (level != 11) {
                ship.upgradeLevel(0.02f, 0.02f, 10);
            }
            return 11;
        }
        if (points < 3500) {
            if (level != 12) {
                ship.upgradeLevel(0.02f, 0.02f, 11);
            }
            return 12;
        }
        if (points < 4000) {
            if (level != 13) {
                ship.upgradeLevel(0.02f, 0.02f, 12);
            }
            return 13;
        }
        if (points < 5000) {
            if (level != 14) {
                ship.upgradeLevel(0.02f, 0.02f, 13);
            }
            return 14;
        }
        if (level != 15) {
            ship.upgradeLevel(0.02f, 0.02f, 14);
        }
        return 15;
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
                    points += enemy.getPoints();
                    level = upgradeLevel(points, level);
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
                        if (enemy.isDestroyed()) {
                            points += enemy.getPoints();
                            level = upgradeLevel(points, level);
                        }
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
    public void printInfo(){
        sbPoints.setLength(0);
        sbLevel.setLength(0);
        sbHP.setLength(0);
        font.draw(batch, sbPoints.append(POINTS).append(points), worldBounds.getLeft() + 0.01f, worldBounds.getTop() - 0.01f, Align.left);
        font.draw(batch, sbLevel.append(LEVEL).append(level), worldBounds.pos.x, worldBounds.getTop() - 0.01f, Align.center);
        font.draw(batch, sbHP.append(HP).append(ship.getHP()), worldBounds.getRight() - 0.01f, worldBounds.getTop() - 0.01f, Align.right);
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
        printInfo();
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
                enemiesEmitter.generateEnemies(delta, level);
                break;
            case GAMEOVER:
                break;
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch, pointer);
        if (state == State.GAMEOVER) {
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
        ship.touchUp(touch, pointer);
        if (state == State.GAMEOVER) {
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
        music.dispose();
        shootSound.dispose();
        font.dispose();
        super.dispose();
    }

    private void startNewGame() {
        state = State.PLAYING;
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        ship.startNewGame(worldBounds);
        points = 0;
        level = 1;
        ship.shootState = Ship.ShootState.SINGLE;
    }
}
