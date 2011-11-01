package com.mobplug.android.games.memorygame;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobplug.android.games.framework.GameSurfaceView3D;
import com.mobplug.android.games.memorygame.game.MemoryGame;
import com.mobplug.android.games.memorygame.renderer.MemoryRenderer;
import com.mobplug.games.framework.BaseGameRunnable;
import com.mobplug.games.framework.interfaces.GameRenderer;
import com.mobplug.games.framework.interfaces.GameRunnable;

public class MemoryGameActivity extends Activity {
	private SimpleDateFormat sdf = new SimpleDateFormat("'Time Elapsed: 'mm:ss");
	private class UpdateUITask extends TimerTask {
		@Override
		public void run() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					TextView tv = (TextView)findViewById(R.id.time_elapsed);
					tv.setText(sdf.format(new Date(game.getGameTime())));		
					tv = (TextView)findViewById(R.id.num_moves);
					tv.setText(String.format("Moves: %d", game.getNumMoves()));
				}				
			});			
		}		
	}
	private Handler handler = new Handler();
	private Timer timer = new Timer();
	private GameRunnable gameRunnable;
	private MemoryGame game;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FrameLayout v = (FrameLayout)findViewById(R.id.game);
        final GameSurfaceView3D surfaceView = new GameSurfaceView3D(this);
        surfaceView.setEGLContextClientVersion(2);
        game = new MemoryGame();
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
        v.addView(surfaceView);
        
        ImageButton newGameButton = (ImageButton)findViewById(R.id.btn_new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				game.newGame();
			}
		});
        
        ImageButton pauseGameButton = (ImageButton)findViewById(R.id.btn_pause_game);
        pauseGameButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				game.togglePause();
				
			}
		});
//        setContentView(surfaceView);    
        timer.scheduleAtFixedRate(new UpdateUITask(), 0, 500);
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