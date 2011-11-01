package com.mobplug.android.games.memorygame.game;

import com.mobplug.games.framework.BaseGame;
import com.mobplug.games.framework.interfaces.GameResult;

public class MemoryGame extends BaseGame {
	private static final long serialVersionUID = 1L;
	private static final int NUM_ROWS = 3;
	private static final int NUM_COLUMNS = 4;
	private CardManager cardManager = new CardManager();

	private int numMoves = 0;
	private Card[] cards = {
			new Card(cardManager, 0), new Card(cardManager, 0),
			new Card(cardManager, 1), new Card(cardManager, 1),
			new Card(cardManager, 2), new Card(cardManager, 2),
			new Card(cardManager, 3), new Card(cardManager, 3),
			new Card(cardManager, 4), new Card(cardManager, 4),
			new Card(cardManager, 5), new Card(cardManager, 5),			
	};
	

	public MemoryGame() {
		newGame();
	}
	
	@Override
	public int getUpdateCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void newGame() {
		numMoves = 0;		
		for (int i = 0; i < cards.length; i++) {
			int tmp = cards[i].getNumber();			
			int randomPos = (int)(Math.random() * cards.length);
			cards[i].setNumber(cards[randomPos].getNumber());						
			cards[randomPos].setNumber(tmp);						
		}
		for (Card c: cards)c.reset();		
		super.newGame();
	}
	
	@Override
	public void update(long gameTime) {
		if (cards.length == cardManager.getMatchedCards().size()) {
			gameOver(GameResult.WIN);
		}
				
		for (Card c: cards) {
			c.update();
				
		}		
		
		if (cardManager.getOpenCards().size() == 2) {
			Card c1 = cardManager.getOpenCards().get(0);
			Card c2 = cardManager.getOpenCards().get(1);
			if (c1.getNumber() == c2.getNumber()) {
				c1.match();
				c2.match();
			} else {
				c1.flip();
				c2.flip();
			}
		}
		
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
	
	public void click(float x, float y) {
		if (isPaused()) return;
		//TODO this method should run in the game Thread!!!
		if (cardManager.getOpenCards().size() + cardManager.getTransitionCards().size() >= 2) return;
		for (Card c: cards) {
			if (Math.abs(x - c.getPosX()) <= c.getSize()
					&& Math.abs(y - c.getPosY()) <= c.getSize()) {
				if (c.getState() == Card.State.CLOSED) numMoves++;
				c.flip();
				break;
			}
		}
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
	
	public long getGameTime() {
		return this.gameTime;
	}
	
	public int getNumMoves() {
		return this.numMoves;
	}

}
