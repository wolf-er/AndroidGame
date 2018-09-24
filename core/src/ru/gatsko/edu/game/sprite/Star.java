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

public class Star extends Sprite{
    private Vector2 speed = new Vector2();
    private Rect worldBounds;
    private Random rnd = new Random();

    private float nextFloat(float min, float max){
        return rnd.nextFloat() * (max - min) + min;
    }

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        float size = nextFloat(0.005f,0.02f);
        setHeightProportion(size);
        speed.set(nextFloat(-0.005f,0.005f) * size / 0.01f, nextFloat(-0.5f,-0.1f) * size / 0.01f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speed, delta);
        checkBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        pos.set(nextFloat(worldBounds.getLeft(),worldBounds.getRight()),nextFloat(worldBounds.getBottom(),worldBounds.getTop()));
    }

    private void checkBounds(){
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        } else if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
    }
}
