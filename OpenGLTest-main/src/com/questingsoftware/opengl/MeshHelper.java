package com.questingsoftware.opengl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

public class MeshHelper implements Disposable{
	
	private Mesh mesh;
	private ShaderProgram meshShader;
	private Texture t;
	
	
	public MeshHelper(){
		t = new Texture(Gdx.files.internal("data/marble.jpg"));
		createShader();
	}

	public void createMesh(float[] vertices) {
		mesh = new Mesh(true, vertices.length, 0,
				new VertexAttribute(Usage.Position, 2, "a_position"),
				new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
				new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(vertices);
	}

	private void createShader() {
		// make an actual shader from our strings
		meshShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl"), Gdx.files.internal("shaders/fragment.glsl"));
		
		// check there's no shader compile errors
		if (!meshShader.isCompiled()){
			throw new IllegalStateException(meshShader.getLog());
		}
	}
	
	public void drawMesh(Camera camera) {
		// this should be called in render()
		if (mesh == null)
			throw new IllegalStateException("drawMesh called before a mesh has been created.");
		
		GL20 gl = Gdx.graphics.getGL20();
	    //we don't necessarily need these, but its good practice to enable
	    //the things we need. we enable 2d textures and set the active one
	    //to 0. we could have multiple textures but we don't need it here.
	    gl.glEnable(GL20.GL_TEXTURE_2D);
	    gl.glActiveTexture(GL20.GL_TEXTURE0);

		meshShader.begin();
		
		meshShader.setUniformMatrix("u_ModelViewProjectionMatrix", camera.combined);
		
		meshShader.setUniformi("u_texture", 0);
		t.bind();
		
		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();
	}
	
	public void dispose() {
        mesh.dispose();
        meshShader.dispose();
    }
}
