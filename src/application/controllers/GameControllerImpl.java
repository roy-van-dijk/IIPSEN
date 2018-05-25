package application.controllers;

import application.domain.Card;
import application.domain.CardRowImpl;
import application.domain.Game;

public class GameControllerImpl implements GameController {
	private Game game;

	public GameControllerImpl(Game game) {
		this.game = game;
	}

	@Override
	public void reserveCard() {
		// Creating POC variables - basically specifying: Hey controller, I clicked on this card
		CardRowImpl row = game.getPlayingField().getCardRows().get(1); // Second row
		Card card = row.getCardSlots()[1]; // Second card
		
		//if(!card.equals(card2wantdezeisspeciaal)) return;
		
		game.reserveCardFromField(row, card);
	}

	
	
}
