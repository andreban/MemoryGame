package com.mobplug.android.games.memorygame.renderer;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_STENCIL_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.games.memorygame.game.MemoryGame;
import com.mobplug.android.games.memorygame.glutils.GLBatch;
import com.mobplug.android.games.memorygame.glutils.GLBatchFactory;
import com.mobplug.android.games.memorygame.glutils.GLFrustrum;
import com.mobplug.android.games.memorygame.glutils.GLShader;
import com.mobplug.android.games.memorygame.glutils.GLShaderFactory;
import com.mobplug.android.games.memorygame.glutils.GeometryTransform;
import com.mobplug.android.games.memorygame.glutils.MatrixStack;
import com.mobplug.android.games.memorygame.glutils.SimpleGLBatch;

public class MemoryRenderer extends AndroidGameRenderer3D<MemoryGame> {

    private GLShader shader;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    private GeometryTransform geometryPipeline;
    private GLBatch card;
    private GLBatch background;
    private int width = -1;
    private int height = -1;
    private float cardSize = 0;
    
	public MemoryRenderer(Context context, GLSurfaceView glSurfaceView, MemoryGame game) {
		super(glSurfaceView, game);
	}

	@Override
	public void onDrawFrame(GL10 arg0) {
		glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		modelViewStack.push();
		
		shader.useShader();
		shader.setUniform4("vColor", 1, 0, 0, 1);

		//draw the background!s
		modelViewStack.push();
		modelViewStack.translate((float)width / 2, (float)height / 2, 0);		
		shader.setUniformMatrix4("mvpMatrix", false, geometryPipeline.getModelViewProjectionMatrix());
		background.draw(shader.getAttributeLocations());
		modelViewStack.pop();
		
		float paddingLeft = ((float)width - cardSize * 5.5f)/2;
		float paddingBottom = ((float)height - cardSize * 4f)/2;
		
		shader.setUniform4("vColor", 0, 1, 0, 1);		
		for (int row = 0; row < game.getNumRows(); row++) {
			float ypos = paddingBottom + cardSize * 1.5f * row + cardSize / 2;
			
			for (int column = 0; column < game.getNumColumns(); column++) {				
				float xpos = paddingLeft + cardSize * 1.5f * column + cardSize / 2;
				
				modelViewStack.push();
				modelViewStack.translate(xpos, ypos, 0);
				
				//draw front of the card
				shader.setUniformMatrix4("mvpMatrix", false, geometryPipeline.getModelViewProjectionMatrix());				
				card.draw(shader.getAttributeLocations());
				
				//draw back of the card
				modelViewStack.rotate(180, 0, 1, 0);
				shader.setUniformMatrix4("mvpMatrix", false, geometryPipeline.getModelViewProjectionMatrix());				
				card.draw(shader.getAttributeLocations());
				
				modelViewStack.pop();
			}
		}
		
		modelViewStack.pop();
		
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		this.width = width;
		this.height = height;
		glViewport(0, 0, width, height);
        viewFrustrum.setOrthographic(0, width, 0, height, 0, 100); 
		projectionStack= new MatrixStack(viewFrustrum.getProjectionMatrix());  
		geometryPipeline = new GeometryTransform(modelViewStack, projectionStack);
		background = GLBatchFactory.makeCube(width, height, 1);
		cardSize = height / 5;
		card = createCard(cardSize);
	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		glClearColor(0.0f,0.0f,0.0f,0.0f);
		shader = GLShaderFactory.getFlatShader();
		viewFrustrum = new GLFrustrum();
		modelViewStack = new MatrixStack(); 
//		cube = GLBatchFactory.makeCube(100, 100, 100);
	}
	
	private GLBatch createCard(float size) {
		float sz = size / 2;
        float[] vertices = {
            -sz, sz, 0, 1, //0
            -sz, -sz, 0, 1, //1
            sz, -sz, 0, 1, //2
            sz, sz, 0, 1,  // 3                      
        };   
        
        float[] normals = {
            0, 0, 1,
            0, 0, 1,            
            0, 0, 1,            
            0, 0, 1,        
        };
        
        float[] texcoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0,       
        };
        
        short[] indices = {
            0, 1, 2,
            2, 3, 0,                    
        };
        
        return new SimpleGLBatch(GL11.GL_TRIANGLES, vertices, null, normals, texcoords, indices);
    }	
	
}
