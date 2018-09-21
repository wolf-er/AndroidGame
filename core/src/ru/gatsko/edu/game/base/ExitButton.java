package ru.gatsko.edu.game.base;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.Sprite;
import ru.gatsko.edu.game.math.Rect;

/**
 * Created by gatsko on 21.09.2018.
 */

public class ExitButton extends Sprite {
    public ExitButton(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            System.out.println("Exit button clicked!");
            return true;
        }
        return super.touchDown(touch, pointer);
    }
}
