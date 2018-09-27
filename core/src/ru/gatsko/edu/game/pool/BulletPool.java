package ru.gatsko.edu.game.pool;

import ru.gatsko.edu.game.base.SpritesPool;
import ru.gatsko.edu.game.sprite.Bullet;

/**
 * Created by gatsko on 27.09.2018.
 */

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    protected void log() {
        System.out.println("Bullet active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
