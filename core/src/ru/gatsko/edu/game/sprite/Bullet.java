package ru.gatsko.edu.game.sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.Sprite;
import ru.gatsko.edu.game.math.Rect;

/** Bullet.java
 * Created by gatsko on 27.09.2018.
 */

public class Bullet extends Sprite {
    private Rect worldBounds;
    private Vector2 bulletSpeed = new Vector2();
    private int damage;
    private Object owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public void set(Object owner, TextureRegion region, Vector2 posBase, Vector2 speed, float height, Rect worldBounds, int damage) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(posBase);
        System.out.println(this.pos);
        this.bulletSpeed.set(speed);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(bulletSpeed, delta);
        //System.out.println("Bullet position " + this.pos);
        if (isOutside(worldBounds)) {
            System.out.println("OUT OF BOUNDS!");
            destroy();
        }

    }
}