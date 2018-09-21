package ru.gatsko.edu.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.Base2DScreen;

/**
 * Created by gatsko on 17.09.2018.
 */

public class MenuScreenOld extends Base2DScreen {
    Boolean infMotion;
    SpriteBatch batch;
    Texture background, ship;
    Vector2 position, speed, direction, dummy;

    public MenuScreenOld(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        background = new Texture("background.jpg");
        ship = new Texture("ship.jpg");
        position = new Vector2(100 + ship.getWidth()/2,ship.getHeight()/2);
        infMotion = false;
        speed = new Vector2(0,0);
        direction = new Vector2(0,0);
        dummy = new Vector2(0,0);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        dummy.set(direction);
        if (!infMotion && dummy.sub(position).len2() <= speed.len2()) {
            position.set(direction);
            speed.set(0,0);
        } else {
            dummy.set(position);
            dummy.add(speed);
            if (dummy.x < ship.getWidth()/2) {
                speed.set(0,0);
                dummy.x = ship.getWidth()/2;
            }
            if (dummy.x > Gdx.graphics.getWidth() - ship.getWidth()/2) {
                speed.set(0,0);
                dummy.x = Gdx.graphics.getWidth() - ship.getWidth()/2;
            }
            if (dummy.y < ship.getHeight()/2) {
                speed.set(0,0);
                dummy.y = ship.getHeight()/2;
            }
            if (dummy.y > Gdx.graphics.getHeight() - ship.getHeight()/2) {
                speed.set(0,0);
                dummy.y = Gdx.graphics.getHeight() - ship.getHeight()/2;
            }
            position.set(dummy);
        }
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(ship, position.x - ship.getWidth()/2, position.y - ship.getHeight()/2);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        infMotion = false;
        direction.set(screenX,Gdx.graphics.getHeight() - screenY);
        speed = direction.cpy().sub(position).nor();
        return super.touchDown(screenX, Gdx.graphics.getHeight() - screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 19:
                speed.set(0, 1);
                break;
            case 20:
                speed.set(0, -1);
                break;
            case 21:
                speed.set(-1, 0);
                break;
            case 22:
                speed.set(1, 0);
                break;
        }
        switch (keycode) {
            case 20: case 21: case 22: case 23:
                infMotion = true;
        }
        return super.keyDown(keycode);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ship.dispose();
        super.dispose();
    }
}
