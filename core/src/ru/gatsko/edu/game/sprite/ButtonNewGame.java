package ru.gatsko.edu.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gatsko.edu.game.base.ActionListener;
import ru.gatsko.edu.game.base.ScaledTouchUpButton;
import ru.gatsko.edu.game.math.Rect;

/**
 * Created by gatsko on 05.10.2018.
 */


public class ButtonNewGame extends ScaledTouchUpButton {
    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("button_new_game"), actionListener,0.9f);
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setTop(worldBounds.getBottom() + (worldBounds.getTop() - worldBounds.getBottom()) / 4 + this.getHalfHeight());
        setRight(worldBounds.getLeft() + (worldBounds.getRight() - worldBounds.getLeft()) / 2 + this.getHalfWidth());
    }
    }

