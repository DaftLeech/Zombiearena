package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import engine.ThreadManager;
import entitys.Player;
import general.DPoint;
import render.Render;
import render.Window;
import settings.Settings;
import weapons.pistole;
import weapons.rifle;
import world.Map;

import java.awt.*;

public class DesktopLauncher {
	public static Player pLocal;
	public static void main (String[] arg) {


		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "yourGame";
		cfg.width = Window.WIDTH;
		cfg.height = Window.HEIGHT;
		cfg.fullscreen = true;
		new LwjglApplication(new Render(), cfg);


	}


}
