package com.mobplug.android.games.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.mobplug.android.games.framework.GameSurfaceView3D;
import com.mobplug.android.games.memorygame.game.MemoryGame;
import com.mobplug.android.games.memorygame.renderer.MemoryRenderer;
import com.mobplug.games.framework.BaseGameRunnable;
import com.mobplug.games.framework.interfaces.GameRenderer;
import com.mobplug.games.framework.interfaces.GameRunnable;

public class MemoryGameActivity extends Activity {
	private GameRunnable gameRunnable;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GameSurfaceView3D surfaceView = new GameSurfaceView3D(this);
        surfaceView.setEGLContextClientVersion(2);
        final MemoryGame game = new MemoryGame();
        surfaceView.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					game.click(event.getX(), surfaceView.getHeight() - event.getY());
					return true;
				}
				return false;
			}
		});        
        GameRenderer<MemoryGame> renderer = new MemoryRenderer(this, surfaceView, game);
        gameRunnable = new BaseGameRunnable<MemoryGame>(renderer, game);
        setContentView(surfaceView);     
    }
    
    @Override
    protected void onStart() {    	
    	super.onStart();
    	gameRunnable.start();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	gameRunnable.stop();
    }
}