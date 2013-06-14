package com.questingsoftware.opengl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class OpenGLTest implements ApplicationListener {

	/******************/
	private PerspectiveCamera pCamera;
	private Mesh testMesh;
	private Texture texture;
	
	private ShaderProgram shaderProgram;

	@Override
	public void create() {

		testMesh = new Mesh(true, 4, 6, VertexAttribute.Position(),
				VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
		testMesh.setVertices(new float[] { -0.5f, -0.5f, 0, 1, 1, 1, 1, 0, 1,
				0.5f, -0.5f, 0, 1, 1, 1, 1, 1, 1, 0.5f, 0.5f, 0, 1, 1, 1, 1, 1,
				0, -0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0 });
		testMesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 });
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));

		shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl")
				, Gdx.files.internal("shaders/fragment.glsl"));
		
		if (!shaderProgram.isCompiled()){
			Gdx.app.log(OpenGLTest.class.getCanonicalName(), shaderProgram.getLog());
		}
	}

	@Override
	public void dispose() {
		testMesh.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		pCamera.lookAt(0, 0, 1);
		pCamera.update();
		
		texture.bind();
		shaderProgram.begin();
		shaderProgram.setUniformMatrix("u_worldView", pCamera.combined);
		shaderProgram.setUniformi("u_texture", 0);
		testMesh.render(shaderProgram, GL10.GL_TRIANGLES);
		shaderProgram.end();
	}

	@Override
	public void resize(int width, int height) {
		pCamera = new PerspectiveCamera(45f, width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
