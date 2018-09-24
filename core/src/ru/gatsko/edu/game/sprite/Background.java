package ru.gatsko.edu.game.sprite;

/**
 * Created by gatsko on 21.09.2018.
 */

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.gatsko.edu.game.base.Sprite;
import ru.gatsko.edu.game.math.Rect;


public class Background extends Sprite {
    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
