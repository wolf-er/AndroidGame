package ru.gatsko.edu.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.ActionListener;
import ru.gatsko.edu.game.base.ScaledTouchUpButton;
import ru.gatsko.edu.game.math.Rect;

/**
 * Created by gatsko on 23.09.2018.
 */

public class ButtonPlay extends ScaledTouchUpButton {
    public ButtonPlay(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("btPlay"), actionListener,0.9f);
        setHeightProportion(0.5f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setTop(worldBounds.getBottom() + (worldBounds.getTop() - worldBounds.getBottom()) / 2 + this.getHalfHeight());
        setRight(worldBounds.getLeft() + (worldBounds.getRight() - worldBounds.getLeft()) / 2 + this.getHalfWidth());
    }
}
