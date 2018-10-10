package ru.gatsko.edu.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import ru.gatsko.edu.game.base.ActionListener;
import ru.gatsko.edu.game.base.Font;
import ru.gatsko.edu.game.math.Rect;
import ru.gatsko.edu.game.sprite.Background;
import ru.gatsko.edu.game.base.Base2DScreen;
import ru.gatsko.edu.game.sprite.ButtonExit;
import ru.gatsko.edu.game.sprite.ButtonPlay;
import ru.gatsko.edu.game.sprite.Star;

/**
 * Created by gatsko on 17.09.2018.
 */

public class MenuScreen extends Base2DScreen implements ActionListener{
    private static final int STARS_COUNT = 100;
    Background background;
    ButtonExit buttonExit;
    ButtonPlay buttonPlay;
    TextureAtlas atlas;
    StringBuilder sbAuthor = new StringBuilder();
    Font font;
    Texture bg;
    Star stars[];

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt();
        atlas = new TextureAtlas("menuAtlas.tpack");
        bg = new Texture("background.jpg");
        background = new Background(new TextureRegion(bg));
        buttonExit = new ButtonExit(atlas, this);
        buttonPlay = new ButtonPlay(atlas, this);
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < STARS_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        font = new Font("font.fnt","font.png");
        font.setFontSize(0.015f);

    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].resize(worldBounds); }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void draw(){
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].draw(batch); }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        sbAuthor.setLength(0);
        font.draw(batch, sbAuthor.append("VITALY GATSKO PRODUCTION"), worldBounds.getLeft() + 0.01f, worldBounds.getTop() - 0.01f, Align.left);
        batch.end();
    }

    public void update(float delta){
        for (int i = 0; i < STARS_COUNT; i++) { stars[i].update(delta); }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch,pointer);
        buttonPlay.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch,pointer);
        buttonPlay.touchUp(touch,pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonExit) {
            Gdx.app.exit();
        } else if (src == buttonPlay) {
            game.setScreen(new GameScreen(game));
        }
    }
}
