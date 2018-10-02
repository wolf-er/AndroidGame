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

/**
 * Created by gatsko on 30.09.2018.
 */

public class Ship extends Sprite {
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
    protected int hp;


    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, Sound shootSound) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.bulletDamage = 1;
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
        shootSound.play(0.5f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletSpeed, 0.03f, worldBounds, bulletDamage);
    }
}
