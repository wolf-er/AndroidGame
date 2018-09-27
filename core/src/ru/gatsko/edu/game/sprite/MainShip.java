package ru.gatsko.edu.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

import ru.gatsko.edu.game.base.Sprite;
import ru.gatsko.edu.game.math.Rect;
import ru.gatsko.edu.game.pool.BulletPool;

/**
 * Created by gatsko on 23.09.2018.
 */

public class MainShip extends Sprite {
    private Vector2 speed = new Vector2(), bulletSpeed = new Vector2(0,0.5f);
    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Sound shootSound;
    private Boolean leftPressed = false, rightPressed = false, downPressed = false, upPressed = false;
    private int touchRight;
    private HashMap<Integer, Vector2> currTouch = new HashMap<Integer, Vector2>();

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1,2, 2);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        setHeightProportion(0.2f);
        this.bulletPool = bulletPool;
        shootSound = Gdx.audio.newSound(Gdx.files.internal("blaster.mp3"));
        speed.setZero();
    }

    @Override
    public void update(float delta) {
            speed.setZero();
            if (leftPressed) {
                speed.x -= 1;
            }
            if (rightPressed) {
                speed.x += 1;
            }
            if (downPressed) {
                speed.y -= 1;
            }
            if (upPressed) {
                speed.y += 1;
            }
            if (currTouch.size() > 0) {
                for (Vector2 values : currTouch.values()) {
                    if (values.x < worldBounds.pos.x) {
                        touchRight--;
                    } else if (values.x > worldBounds.pos.x) {
                        touchRight++;
                    }
                }
                speed.x += Integer.signum(touchRight);
            }
            touchRight = 0;

        pos.mulAdd(speed, delta);
        checkBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + (worldBounds.getTop() - worldBounds.getBottom()) * 0.02f);
        setRight(worldBounds.getLeft() + (worldBounds.getRight() - worldBounds.getLeft()) / 2 + this.getHalfWidth());
    }

    private void checkBounds(){
        if (getLeft() <= worldBounds.getLeft()) {
            speed.x = 0;
            setLeft(worldBounds.getLeft());
        }
        if (getRight() >= worldBounds.getRight()) {
            speed.x = 0;
            setRight(worldBounds.getRight());
        }
        if (getTop() >= worldBounds.getTop()) {
            speed.y = 0;
            setTop(worldBounds.getTop());
        }
        if (getBottom() <= worldBounds.getBottom()) {
            speed.y = 0;
            setBottom(worldBounds.getBottom());
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                upPressed = true;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                downPressed = true;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                leftPressed = true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                rightPressed = true;
                break;
            case Input.Keys.E:
                frame = 1;
                break;
            case Input.Keys.Q:
                frame = 0;
                break;
            case Input.Keys.SPACE:
                shoot();
                break;
        }
    }
    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                upPressed = false;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                downPressed = false;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                leftPressed = false;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                rightPressed = false;
                break;
        }
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        currTouch.put(pointer, touch);
        return true;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        currTouch.remove(pointer);
        return false;
    }

    public void touchDragged(Vector2 touch, int pointer) {
        System.out.println("Before " + currTouch.get(pointer).x);
        currTouch.get(pointer).x = touch.x;
        System.out.println("After " + currTouch.get(pointer).x);
        currTouch.get(pointer).y = touch.y;
    }

    public void shoot(){
        shootSound.play(0.5f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletSpeed, 0.03f, worldBounds, 1);
    }
}
