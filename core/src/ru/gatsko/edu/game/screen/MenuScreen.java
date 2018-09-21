package ru.gatsko.edu.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gatsko.edu.game.base.Background;
import ru.gatsko.edu.game.base.Base2DScreen;
import ru.gatsko.edu.game.base.ExitButton;
import ru.gatsko.edu.game.base.Sprite;

/**
 * Created by gatsko on 17.09.2018.
 */

public class MenuScreen extends Base2DScreen {

    Sprite sprite;
    Background background;
    ExitButton exitButton;
    Texture bg, ship, exitBt;
    Vector2 position, speed;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt();
        exitBt = new Texture("exit-icon.png");
        bg = new Texture("background.jpg");
        ship = new Texture("ship.jpg");
        position = new Vector2(0,0);
        sprite = new Sprite(new TextureRegion(ship));
        sprite.setHeightProportion(0.5f);
        background = new Background(new TextureRegion(bg));
        background.setHeightProportion(1f);
        exitButton = new ExitButton(new TextureRegion(exitBt));
        exitButton.setHeightProportion(0.2f);
        exitButton.setRight(0.5f);
        exitButton.setBottom(-0.5f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        //batch.draw(background, -1f, -1f,2f,2f);
        //batch.draw(ship, position.x, position.y, 0.5f,0.5f);
        sprite.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (exitButton.touchDown(touch,pointer)) Gdx.app.exit();//dispose();
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 19:
                speed.set(0, 1);
                break;
            case 20:
                speed.set(0, -1);
                break;
            case 21:
                speed.set(-1, 0);
                break;
            case 22:
                speed.set(1, 0);
                break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public void dispose() {
        bg.dispose();
        ship.dispose();
        exitBt.dispose();
        super.dispose();
    }
}
