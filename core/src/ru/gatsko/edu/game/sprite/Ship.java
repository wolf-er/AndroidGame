package ru.gatsko.edu.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ru.gatsko.edu.game.base.Sprite;
import ru.gatsko.edu.game.math.Rect;

/**
 * Created by gatsko on 23.09.2018.
 */

public class Ship extends Sprite {
    private Vector2 speed = new Vector2();
    private Vector2 direction;
    private Rect worldBounds;
    private Boolean leftPressed = false, rightPressed = false, downPressed = false, upPressed = false;
    private Boolean moveByTouch = false;

    public Ship(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship"), 0,0, atlas.findRegion("main_ship").getRegionWidth()/2, atlas.findRegion("main_ship").getRegionHeight()));
        setHeightProportion(0.2f);
        speed.set(0,0);
    }

    @Override
    public void update(float delta) {
        if (!moveByTouch) {
            speed.set(0, 0);
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
        } else {
            checkTouchAchieved(delta);
        }
        pos.mulAdd(speed, delta);
        checkBounds();
    }

    private void checkTouchAchieved(float delta) {
        //условие на то, что следующий шаг переведёт корабль через точку цели по оси Х или У. Не использовал расстояние, т.к. точка тапа может быть недостижима из-за границы экрана
        if ((direction.x  - pos.x) * (direction.x  - pos.x - speed.x * delta) < 0 || (direction.y  - pos.y) * (direction.y  - pos.y - speed.y * delta) < 0){
            speed.set(0,0);
            pos.set(direction);
            moveByTouch = false;
        }
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
        moveByTouch = false;
        switch (keycode) {
            case 19:
                upPressed = true;
                break;
            case 20:
                downPressed = true;
                break;
            case 21:
                leftPressed = true;
                break;
            case 22:
                rightPressed = true;
                break;
        }
    }
    public void keyUp(int keycode) {
        switch (keycode) {
            case 19:
                upPressed = false;
                break;
            case 20:
                downPressed = false;
                break;
            case 21:
                leftPressed = false;
                break;
            case 22:
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
        direction = new Vector2(touch);
        speed = direction.cpy().sub(pos).nor();
        moveByTouch = true;
        return true;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

}
