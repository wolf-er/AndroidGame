package ru.gatsko.edu.game.sprite;

/**
 * Created by gatsko on 30.09.2018.
 */

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.Sprite;


public class Explosion extends Sprite {

    private float animateInterval = 0.017f;
    private float animateTimer;
    private Sound boomSound;

    public Explosion(TextureRegion region, int rows, int cols, int frames, Sound boomSound) {
        super(region, rows, cols, frames);
        this.boomSound = boomSound;
    }

    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);
        boomSound.play(0.5f);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}