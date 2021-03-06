package ru.gatsko.edu.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

import ru.gatsko.edu.game.math.Rect;
import ru.gatsko.edu.game.pool.BulletPool;
import ru.gatsko.edu.game.pool.ExplosionPool;
import ru.gatsko.edu.game.sprite.Bullet;
import ru.gatsko.edu.game.sprite.Explosion;

/**
 * Created by gatsko on 30.09.2018.
 */

public class Ship extends Sprite {
    public enum ShootState {SINGLE, DOUBLE, TRIPLE};
    public ShootState shootState = ShootState.SINGLE;
    protected Vector2 speed = new Vector2();
    protected Vector2 bulletSpeed = new Vector2();
    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected Sound shootSound;
    protected int bulletDamage;
    protected float reloadInterval;
    protected float reloadTimer;
    protected int leftFlag = 1;
    protected float damageInterval = 0.1f;
    protected float damageTimer;
    protected int hp;


    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.explosionPool = explosionPool;
        this.bulletDamage = 50;
        this.bulletHeight = 0.01f;
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound){
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void shoot(){
        shootSound.play(0.3f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletSpeed, bulletHeight, worldBounds, bulletDamage);
        if (shootState == ShootState.DOUBLE) {
            Bullet bullet2 = bulletPool.obtain();
            bullet2.set(this, bulletRegion, pos, bulletSpeed.cpy().add(new Vector2(0.05f * leftFlag,0f)), bulletHeight, worldBounds, bulletDamage);
            leftFlag *= -1;
        } else if (shootState == ShootState.TRIPLE) {
            Bullet bullet2 = bulletPool.obtain();
            bullet2.set(this, bulletRegion, pos, bulletSpeed.cpy().add(new Vector2(0.05f, 0f)), bulletHeight, worldBounds, bulletDamage);
            Bullet bullet3 = bulletPool.obtain();
            bullet3.set(this, bulletRegion, pos, bulletSpeed.cpy().add(new Vector2(-0.05f, 0f)), bulletHeight, worldBounds, bulletDamage);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageTimer += delta;
        if (damageTimer >= damageInterval) frame = 0;
    }

    public void boom(){
        Explosion exp = explosionPool.obtain();
        exp.set(getHeight(), pos);
    }

    public void damage(int damage){
        frame = 1;
        damageTimer = 0;
        hp -= damage;
        if (hp <= 0) {
            destroy();
            boom();
        }
    }

    public int getHP(){
        return hp;
    }

    public float getReloadInterval() {
        return reloadInterval;
    }

    public void setReloadInterval(float reloadInterval) {
        this.reloadInterval = reloadInterval;
    }
}
