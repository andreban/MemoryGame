package com.mobplug.android.games.memorygame.game;


public class Card {
	private static final float OPEN_ROT = 0.0f;
	private static final float CLOSED_ROT = 180.0f;
	private static final float ROTATION_STEP = 3.0f;
	private static final long OPEN_TIMEOUT = 500;
	
	public static enum State {
		OPEN, CLOSED, OPENING, CLOSING, MATCHED
	}
	//card number
	private int number;
	private float posX;
	private float posY;
	private float rotY = CLOSED_ROT;
	private float size;
	private State state = State.CLOSED;
	private CardManager cardManager;
	private long openTimeout = -1;
	
	public Card(CardManager cardManager,  int number) {
		this.number = number;
		this.state = State.CLOSED;
		this.cardManager = cardManager;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getSize() {
		return size;
	}

	public float getRotY() {
		return this.rotY;
	}
	public void setSize(float size) {
		this.size = size;
	}
			
	public void update(long gameTime) {
		switch(state) {
			case OPENING:
				if (rotY > OPEN_ROT)
					rotY -= ROTATION_STEP;
				if (rotY <= OPEN_ROT) {
					if (openTimeout == -1) openTimeout = gameTime + OPEN_TIMEOUT;
					if (gameTime >= openTimeout) {
						cardManager.removeTransitoinCard(this);
						cardManager.addOpenCard(this);
						state = State.OPEN;
					}
				}
				break;
			case CLOSING: {
				rotY += ROTATION_STEP;
				if (rotY >= CLOSED_ROT) {
					cardManager.removeTransitoinCard(this);
					cardManager.addClosedCard(this);					
					state = State.CLOSED;
				}
				break;
			}
		}
	}
	
	public void match() {
		state = State.MATCHED;
		rotY = OPEN_ROT;
		cardManager.removeOpenCard(this);
		cardManager.removeClosedCard(this);
		cardManager.removeTransitoinCard(this);
		cardManager.addMatchedCard(this);
	}
	public void flip() {
		switch(state) {
			case OPEN:
				cardManager.removeOpenCard(this);
				cardManager.addTransitionCard(this);
			case OPENING: {
				state = State.CLOSING; 
				break;				
			}
			case CLOSED:
				cardManager.removeClosedCard(this);
				cardManager.addTransitionCard(this);
			case CLOSING: {
				state = State.OPENING; 
				openTimeout = -1;
				break;				
			}
		}
	}	
	
	public void reset() {
		this.state = State.CLOSED;
		this.rotY = CLOSED_ROT;
		cardManager.addClosedCard(this);
		cardManager.removeOpenCard(this);
		cardManager.removeTransitoinCard(this);
		cardManager.removeMatchedCard(this);
	}
	
	public State getState() {
		return this.state;
	}
}
