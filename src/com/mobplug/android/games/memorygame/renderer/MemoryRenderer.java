package com.mobplug.android.games.memorygame.renderer;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_STENCIL_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.mobplug.android.games.framework.AndroidGameRenderer3D;
import com.mobplug.android.games.memorygame.R;
import com.mobplug.android.games.memorygame.game.Card;
import com.mobplug.android.games.memorygame.game.MemoryGame;
import com.mobplug.android.games.memorygame.glutils.GLBatch;
import com.mobplug.android.games.memorygame.glutils.GLBatchFactory;
import com.mobplug.android.games.memorygame.glutils.GLFrustrum;
import com.mobplug.android.games.memorygame.glutils.GLShader;
import com.mobplug.android.games.memorygame.glutils.GLShaderFactory;
import com.mobplug.android.games.memorygame.glutils.GLTexture;
import com.mobplug.android.games.memorygame.glutils.GeometryTransform;
import com.mobplug.android.games.memorygame.glutils.MatrixStack;
import com.mobplug.android.games.memorygame.glutils.SimpleGLBatch;

public class MemoryRenderer extends AndroidGameRenderer3D<MemoryGame> {

    private GLShader shader;
    private GLShader textureShader;
    private GLFrustrum viewFrustrum;
    private MatrixStack modelViewStack;
    private MatrixStack projectionStack;
    private GeometryTransform geometryPipeline;
    private GLBatch card;
    private GLBatch background;
    private int width = -1;
    private int height = -1;
    private float cardSize = 0;
    private float rotation = 0;
    private Context context;   
    
    private GLTexture back;
    private GLTexture front1;
    private GLTexture front2;
    private GLTexture front3;
    private GLTexture front4;
    private GLTexture front5;
    private GLTexture front6;    
    
	public MemoryRenderer(Context context, GLSurfaceView glSurfaceView, MemoryGame game) {
		super(glSurfaceView, game);
		this.context = context;
	}

	@Override
	public void onDrawFrame(GL10 arg0) {
		glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		modelViewStack.push();
		
		shader.useShader();
		shader.setUniform4("vColor", 1, 0, 0, 1);

		//draw the background!s
		modelViewStack.push();
		modelViewStack.translate((float)width / 2, (float)height / 2, -100);		
		shader.setUniformMatrix4("mvpMatrix", false, geometryPipeline.getModelViewProjectionMatrix());
		background.draw(shader.getAttributeLocations());
		modelViewStack.pop();
		

				
		textureShader.useShader();
		for (int row = 0; row < game.getNumRows(); row++) {			
			for (int column = 0; column < game.getNumColumns(); column++) {								
				Card myCard = game.getCartAt(row, column);
				GLTexture front = null;
				switch(myCard.getNumber()) {
					case 0: front = front1; break;
					case 1: front = front2; break;
					case 2: front = front3; break;
					case 3: front = front4; break;
					case 4: front = front5; break;
					case 5: front = front6; break;					
				}
				
				modelViewStack.push();
				modelViewStack.translate(myCard.getPosX(), myCard.getPosY(), 0);
				modelViewStack.rotate(rotation, 0, 1, 0);
				
				//draw front of the card
//				shader.setUniform4("vColor", 0, 1, 0, 1);	
				front.useTexture(GLES20.GL_TEXTURE0);
				shader.setUniformMatrix4("mvpMatrix", false, geometryPipeline.getModelViewProjectionMatrix());				
				card.draw(textureShader.getAttributeLocations());
				
				//draw back of the card
				modelViewStack.translate(0, 0, -0.1f);
				modelViewStack.rotate(180, 0, 1, 0);
				back.useTexture(GLES20.GL_TEXTURE0);
//				shader.setUniform4("vColor", 0, 0, 1, 1);				
				shader.setUniformMatrix4("mvpMatrix", false, geometryPipeline.getModelViewProjectionMatrix());				
				card.draw(textureShader.getAttributeLocations());
				
				modelViewStack.pop();
			}
		}
		rotation += 0.5;		
		modelViewStack.pop();
		
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		this.width = width;
		this.height = height;
		glViewport(0, 0, width, height);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        viewFrustrum.setOrthographic(0, width, 0, height, -100, 100); 
		projectionStack= new MatrixStack(viewFrustrum.getProjectionMatrix());  
		geometryPipeline = new GeometryTransform(modelViewStack, projectionStack);
		background = GLBatchFactory.makeCube(width, height, -2);
		cardSize = height / 5;
		game.resize(width, height, cardSize);
		card = createCard(cardSize);
	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		glClearColor(0.0f,0.0f,0.0f,0.0f);
		shader = GLShaderFactory.getFlatShader();
		textureShader = GLShaderFactory.getTextureReplaceShader();
		viewFrustrum = new GLFrustrum();
		modelViewStack = new MatrixStack(); 

		back = new GLTexture(context, R.raw.back);
		front1 = new GLTexture(context, R.raw.f1);
		front2 = new GLTexture(context, R.raw.f2);
		front3 = new GLTexture(context, R.raw.f3);
		front4 = new GLTexture(context, R.raw.f4);
		front5 = new GLTexture(context, R.raw.f5);
		front6 = new GLTexture(context, R.raw.f6);		
		
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
        
        return new SimpleGLBatch(GLES20.GL_TRIANGLES, vertices, null, normals, texcoords, indices);
    }	
	
}
