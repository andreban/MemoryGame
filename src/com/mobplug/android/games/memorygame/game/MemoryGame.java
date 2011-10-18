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
		return cards[row * 3 + column];
	}

}
