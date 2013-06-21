package com.questingsoftware.opengl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class OpenGLTest implements ApplicationListener {

	/******************/
	private MeshHelper meshHelper;
	private PerspectiveCamera camera;
	

	@Override
	public void create() {
		meshHelper = new MeshHelper();
		
		// opengl uses -1 to 1 for coords and also is inverted
		meshHelper.createMesh(new float[] { -0.5f, -0.5f, Color.toFloatBits(0, 0, 255, 255),0f,1f,
				0.5f, -0.5f, Color.toFloatBits(0, 255, 0, 255),1f,1f,
				0f, 0.5f, Color.toFloatBits(255, 0, 0, 255),0.5f,0f });
		
	}

	@Override
	public void dispose() {
		meshHelper.dispose();
	}

	@Override
	public void render() {
		camera.position.z -= (1 * Gdx.graphics.getDeltaTime());
		camera.update();
		
		Gdx.graphics.getGL20().glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.graphics.getGL20().glClear(GL10.GL_COLOR_BUFFER_BIT);
        meshHelper.drawMesh(camera);
	}

	@Override
	public void resize(int width, int height) {
		camera = new PerspectiveCamera(45f, width, height);
		camera.update();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
