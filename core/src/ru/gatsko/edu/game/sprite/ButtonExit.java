package ru.gatsko.edu.game.sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.ActionListener;
import ru.gatsko.edu.game.base.ScaledTouchUpButton;
import ru.gatsko.edu.game.base.Sprite;
import ru.gatsko.edu.game.math.Rect;

/**
 * Created by gatsko on 21.09.2018.
 */

public class ButtonExit extends ScaledTouchUpButton {
    public ButtonExit(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("btExit"), actionListener,0.9f);
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }
}
