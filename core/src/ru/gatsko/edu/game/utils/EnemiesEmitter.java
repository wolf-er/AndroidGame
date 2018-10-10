package ru.gatsko.edu.game.utils;

/**
 * Created by gatsko on 30.09.2018.
 */

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.math.Rect;
import ru.gatsko.edu.game.math.Rnd;
import ru.gatsko.edu.game.pool.EnemyPool;
import ru.gatsko.edu.game.sprite.EnemyShip;


public class EnemiesEmitter {

    private static float ENEMY_SMALL_HEIGHT = 0.08f;
    private static float ENEMY_SMALL_BULLET_HEIGHT = 0.015f;
    private static float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static int ENEMY_SMALL_POINTS = 2;
    private static int ENEMY_SMALL_HP = 1;

    private static float ENEMY_MEDIUM_HEIGHT = 0.08f;
    private static float ENEMY_MEDIUM_BULLET_HEIGHT = 0.022f;
    private static float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private static float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static int ENEMY_MEDIUM_POINTS = 7;
    private static int ENEMY_MEDIUM_HP = 5;

    private static float ENEMY_BIG_HEIGHT = 0.16f;
    private static float ENEMY_BIG_BULLET_HEIGHT = 0.035f;
    private static float ENEMY_BIG_BULLET_VY = -0.3f;
    private static int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static float ENEMY_BIG_RELOAD_INTERVAL = 3f;
    private static int ENEMY_BIG_POINTS = 15;
    private static int ENEMY_BIG_HP = 8;

    private final TextureRegion[] enemySmallRegion;
    private final TextureRegion[] enemyMediumRegion;
    private final TextureRegion[] enemyBigRegion;

    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);
    private final Vector2 enemyMediumV = new Vector2(0f, -0.05f);
    private final Vector2 enemyBigV = new Vector2(0f, -0.01f);

    private final EnemyPool enemyPool;
    private Rect worldBounds;

    private TextureRegion bulletRegion;
    private int level = 1;

    private float generateInterval = 3f;
    private float generateTimer;

    public EnemiesEmitter(EnemyPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        TextureRegion textureRegion0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegion0, 1, 2, 2);
        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        this.enemyMediumRegion = Regions.split(textureRegion1, 1, 2, 2);
        TextureRegion textureRegion2 = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(textureRegion2, 1, 2, 2);

        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generateEnemies(float delta, int level) {

        generateTimer += delta;
        if (generateTimer >= generateInterval - level * 0.2f) {
            System.out.println("new enemy!");
            generateTimer = 0f;
            EnemyShip enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.94f - level * 0.04f) {
                enemy.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY + 0.01f * level,
                        ENEMY_SMALL_BULLET_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL - 0.01f * level,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_POINTS + level,
                        ENEMY_SMALL_HP + (int)(level / 5),
                        worldBounds
                );
            } else if (type < (0.94f - level * 0.04f) + 0.14f + level * 0.01f) {
                enemy.set(
                        enemyMediumRegion,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY + 0.01f * level,
                        ENEMY_MEDIUM_BULLET_DAMAGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL - 0.01f * level,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_POINTS + level,
                        ENEMY_MEDIUM_HP + (int)(level / 3),
                        worldBounds
                );
            } else {
                enemy.set(
                        enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY + 0.01f * level,
                        ENEMY_BIG_BULLET_DAMAGE,
                        ENEMY_BIG_RELOAD_INTERVAL - 0.01f * level,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_POINTS + level * 2,
                        ENEMY_BIG_HP + level,
                        worldBounds
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

}
