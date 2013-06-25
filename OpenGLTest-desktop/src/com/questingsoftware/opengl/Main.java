package com.questingsoftware.opengl;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "OpenGLTest";
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 270;
		
		new LwjglApplication(new OpenGLTest(), cfg);
	}
}
