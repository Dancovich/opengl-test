package com.questingsoftware.opengl;

import java.io.LineNumberReader;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class OpenGLTest implements ApplicationListener {

	/******************/
	private MeshHelper meshHelper;
	private PerspectiveCamera camera;
	

	@Override
	public void create() {
		meshHelper = new MeshHelper();
		
		// opengl uses -1 to 1 for coords and also is inverted
		meshHelper.createMesh( loadModel() );		
	}

	@Override
	public void dispose() {
		meshHelper.dispose();
	}

	@Override
	public void render() {
		camera.position.z -= 1f * Gdx.graphics.getDeltaTime();
		camera.update();
		
		Gdx.graphics.getGL20().glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.graphics.getGL20().glClear(GL10.GL_COLOR_BUFFER_BIT);
        meshHelper.drawMesh(camera);
	}

	@Override
	public void resize(int width, int height) {
		camera = new PerspectiveCamera(45f,width,height);
		camera.position.z = -4f;
		camera.lookAt(0f, 0f, 0f);
		camera.update();
		
		
		Gdx.app.log("OPENGL", "Near = "+camera.near);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	private float[] loadModel(){
		FileHandle handler = Gdx.files.internal("data/cube.obj");
		
		try {
			LineNumberReader reader = new LineNumberReader(handler.reader());
			ArrayList<Vector3> vectorArray = new ArrayList<Vector3>();
			
			String line = reader.readLine();
			
			while (line!=null){
				if (line.startsWith("v ")){
					String[] coordinates = line.split(" ");
					vectorArray.add(new Vector3( Float.parseFloat(coordinates[1]) , Float.parseFloat(coordinates[2]) , Float.parseFloat(coordinates[3]) ));
				}

				line = reader.readLine();
			}
			
			reader.close();
			
			float[] returnValue = new float[4 * vectorArray.size()];
			
			int cont = 0;
			for (Vector3 vector : vectorArray){
				returnValue[ cont ] = vector.x;
				returnValue[ cont+1 ] = vector.y;
				returnValue[ cont+2 ] = vector.z;
				returnValue[ cont+3 ] = Color.toFloatBits(255, 0, 0, 255);
				
				cont += 4;
			}
			
			return returnValue;
		} catch (Exception e) {
			Gdx.app.log("FILE", "Erro lendo arquivo",e);
			return null;
		}
	}
}
