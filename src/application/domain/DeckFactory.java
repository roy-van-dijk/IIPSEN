package application.domain;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Stack;

import application.services.CardsReader;
import application.services.NoblesReader;

/**
 * @author Sanchez
 *
 */
public class DeckFactory {
	private static CardsReader cardsReader;
	private static NoblesReader noblesReader;
	// TODO: add NoblesReader
	
	private static DeckFactory deckFactory; 
	
	public static DeckFactory getInstance()
	{
		if(deckFactory == null)
			return new DeckFactory();
		return deckFactory;
	}

	private DeckFactory() {
		try {
			cardsReader = new CardsReader();
			noblesReader = new NoblesReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CardDeck createCardDeck(CardLevel level) throws RemoteException
	{
		Stack<Card> cards = cardsReader.getCards(level);
		
		CardDeck deck = new CardDeckImpl(cards, level);
		return deck;
	}
	
	public NobleDeck createNobleDeck() throws RemoteException
	{
		Stack<Noble> nobles = noblesReader.getNobles();
		
		NobleDeck deck = new NobleDeck(nobles);
		return deck;
	}
}
