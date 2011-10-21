package com.mobplug.android.games.memorygame.game;

import com.mobplug.games.framework.BaseGame;

public class MemoryGame extends BaseGame {
	private static final long serialVersionUID = 1L;
	
	private static final int NUM_ROWS = 3;
	private static final int NUM_COLUMNS = 4;
	private Card[] cards = {
			new Card(0), new Card(0),
			new Card(1), new Card(1),
			new Card(2), new Card(2),
			new Card(3), new Card(3),
			new Card(4), new Card(4),
			new Card(5), new Card(5),			
	};
	

	public MemoryGame() {
 
	}
	
	@Override
	public int getUpdateCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(long gameTime) {
		// TODO Auto-generated method stub

	}
	
	public int getNumRows() {
		return NUM_ROWS;
	}
	
	public int getNumColumns() {
		return NUM_COLUMNS;
	}
	
	public Card getCartAt(int row, int column) {
		return cards[row * 4 + column];
	}
	
	public void resize(float w, float h, float size) {
		float paddingLeft = ((float)w - size * 5.5f)/2;
		float paddingBottom = ((float)h - size * 4f)/2;		
		for (int row = 0; row < NUM_ROWS; row++) {	
			float ypos = paddingBottom + size * 1.5f * row + size / 2;			
			for (int column = 0; column < NUM_COLUMNS; column++) {	
				float xpos = paddingLeft + size * 1.5f * column + size / 2;	
				Card c = getCartAt(row, column);
				c.setPosX(xpos);
				c.setPosY(ypos);
				c.setSize(size);
			}
		}
	}

}
