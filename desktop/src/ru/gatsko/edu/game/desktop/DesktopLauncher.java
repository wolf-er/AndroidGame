package ru.gatsko.edu.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.gatsko.edu.game.MyGdxGame;
import ru.gatsko.edu.game.Star2DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		float aspect = 3f / 4f;
		config.resizable = false;
		config.width = 600;
		config.height = (int) (config.width / aspect);
		new LwjglApplication(new Star2DGame(), config);
	}
}
