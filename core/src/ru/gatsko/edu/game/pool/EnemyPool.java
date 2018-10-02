package ru.gatsko.edu.game.pool;

/**
 * Created by gatsko on 30.09.2018.
 */

import com.badlogic.gdx.audio.Sound;

import ru.gatsko.edu.game.base.SpritesPool;
import ru.gatsko.edu.game.math.Rect;
import ru.gatsko.edu.game.sprite.EnemyShip;
import ru.gatsko.edu.game.sprite.MainShip;


public class EnemyPool extends SpritesPool<EnemyShip> {
    private BulletPool bulletPool;
    private Sound shootSound;
    private ExplosionPool explosionPool;
    private MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, MainShip mainShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.mainShip = mainShip;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, shootSound, mainShip);
    }

    public void resize(Rect worldBounds) {
    }
}