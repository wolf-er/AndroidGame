package ru.gatsko.edu.game.pool;

/**
 * Created by gatsko on 30.09.2018.
 */

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.gatsko.edu.game.base.SpritesPool;
import ru.gatsko.edu.game.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private final TextureRegion textureRegion;
    private final Sound boomSound;

    public ExplosionPool(TextureAtlas atlas, Sound boomSound) {
        this.textureRegion = atlas.findRegion("explosion");
        this.boomSound = boomSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(textureRegion, 9, 9, 74, boomSound);
    }
}