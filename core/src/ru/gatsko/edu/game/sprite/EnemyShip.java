package ru.gatsko.edu.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.Ship;
import ru.gatsko.edu.game.math.Rect;
import ru.gatsko.edu.game.pool.BulletPool;
import ru.gatsko.edu.game.pool.ExplosionPool;

/**
 * Created by gatsko on 30.09.2018.
 */

public class EnemyShip extends Ship {
    private MainShip mainShip;
    private final Vector2 incommingSpeed = new Vector2(0,-0.3f);
    private Vector2 baseSpeed = new Vector2();
    private float height;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, MainShip mainShip) {
        super(bulletPool, explosionPool, shootSound);
        this.mainShip = mainShip;
        this.speed.set(baseSpeed);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() <= worldBounds.getTop()) {
            pos.mulAdd(speed, delta);
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {
                reloadTimer = 0f;
                shoot();
            }
        } else  {
            pos.mulAdd(incommingSpeed, delta);
        }
        if (getBottom() <= worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(TextureRegion[] regions,
                    Vector2 baseSpeed,
                    TextureRegion bulletRegion,
                    float bulletHeight,
                    float bulletVY,
                    int bulletDamage, float reloadInterval, float height, int hp, Rect worldBounds) {
        this.regions = regions;
        this.baseSpeed.set(baseSpeed);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed.set(0, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        this.worldBounds = worldBounds;
        this.height = height;
        setHeightProportion(height);
        reloadTimer = reloadInterval;
        speed.set(baseSpeed);
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft() || bullet.getLeft() > getRight() || bullet.getBottom() > getTop() || bullet.getTop() < pos.y);
    }
}
