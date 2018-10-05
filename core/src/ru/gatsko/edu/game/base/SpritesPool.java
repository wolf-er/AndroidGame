package ru.gatsko.edu.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatsko on 27.09.2018.
 */

public abstract  class SpritesPool<T extends Sprite> {
    protected List<T> activeObjects = new ArrayList<T>();
    protected List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain(){
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        log();
        return object;
    }

    public void updateActiveObjects(float delta){
        for (int i = 0; i < activeObjects.size(); i++){
            Sprite sprite = activeObjects.get(i);
            if (!sprite.isDestroyed()) {
                sprite.update(delta);
            }
        }
    }

    public void freeAllDestroyedActiveObjects(){
        for (int i = 0; i < activeObjects.size(); i++){
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed()) {
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    private void free(T object) {
        if (activeObjects.remove(object)){
            freeObjects.add(object);
            log();
        }
    }

    public void drawActiveObjects(SpriteBatch batch){
        for (int i = 0; i < activeObjects.size(); i++){
            T sprite = activeObjects.get(i);
            if (!sprite.isDestroyed()) {
                sprite.draw(batch);
            }
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose(){
        activeObjects.clear();
        freeObjects.clear();
    }

    public void freeAllActiveObjects(){
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    protected void log(){
    }
}
