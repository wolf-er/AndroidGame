package ru.gatsko.edu.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by gatsko on 23.09.2018.
 */

public class ScaledTouchUpButton extends Sprite {
    private int pointer;
    private boolean pressed;
    private float pressScale;
    private ActionListener actionListener;

    public ScaledTouchUpButton(TextureRegion region, ActionListener actionListener, float pressScale) {
        super(region);
        this.actionListener = actionListener;
        this.pressScale = pressScale;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (pressed == true || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        scale = pressScale;
        this.pressed = true;
        return true;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !pressed) {
            return false;
        }
        pressed = false;
        scale = 1f;
        if (isMe(touch)){
            actionListener.actionPerformed(this);
            return true;
        }
        return false;
    }
}
