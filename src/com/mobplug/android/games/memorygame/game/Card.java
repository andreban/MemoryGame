package com.mobplug.android.games.memorygame.game;

public class Card {
	private static final float OPEN_ROT = 0.0f;
	private static final float CLOSED_ROT = 180.0f;
	private static final float ROTATION_STEP = 3.0f;
	
	private static enum State {
		OPEN, CLOSED, OPENING, CLOSING
	}
	//card number
	private int number;
	private float posX;
	private float posY;
	private float rotY = CLOSED_ROT;
	private float size;
	private State state = State.CLOSED;
	
	public Card(int number) {
		this.number = number;
		this.state = State.CLOSED;
	}

	public int getNumber() {
		return number;
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
			
	public void update() {
		switch(state) {
			case OPENING:
				rotY -= ROTATION_STEP;
				if (rotY <= OPEN_ROT) {
					state = State.OPEN;
				}
				break;
			case CLOSING: {
				rotY += ROTATION_STEP;
				if (rotY >= CLOSED_ROT) {
					state = State.CLOSED;
				}
				break;
			}
		}
	}
	
	public void flip() {
		switch(state) {
			case OPEN:
			case OPENING: state = State.CLOSING; break;
			case CLOSED:
			case CLOSING: state = State.OPENING; break;				
		}
	}	
}
