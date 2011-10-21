package com.mobplug.android.games.memorygame.game;

public class Card {
	//card number
	private int number;
	private float posX;
	private float posY;
	private float size;
	
	public Card(int number) {
		this.number = number;
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

	public void setSize(float size) {
		this.size = size;
	}
			
//	//position of the card in the board
//	private int row;
//	private int column;
	
	
}
