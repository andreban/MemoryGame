package com.mobplug.android.games.memorygame.game;

import java.util.ArrayList;
import java.util.List;

public class CardManager {
	private List<Card> openCards = new ArrayList<Card>();
	private List<Card> closedCards = new ArrayList<Card>();
	private List<Card> transitionCards = new ArrayList<Card>();
	private List<Card> matchedCards = new ArrayList<Card>();
	
	public CardManager() {
		
	}
	
	public void addOpenCard(Card c) {
		this.openCards.add(c);
	}
	
	public void removeOpenCard(Card c) {
		this.openCards.remove(c);
	}
	
	public void addClosedCard(Card c) {
		closedCards.add(c);
	}
	public void removeClosedCard(Card c) {
		closedCards.remove(c);
	}
	
	public void addTransitionCard(Card c) {
		transitionCards.add(c);
	}
	
	public void removeTransitoinCard(Card c) {
		transitionCards.remove(c);
	}
	
	public void addMatchedCard(Card c) {
		matchedCards.add(c);
	}

	public List<Card> getOpenCards() {
		return openCards;
	}

	public List<Card> getClosedCards() {
		return closedCards;
	}

	public List<Card> getTransitionCards() {
		return transitionCards;
	}

	public List<Card> getMatchedCards() {
		return matchedCards;
	}
		
	
}
