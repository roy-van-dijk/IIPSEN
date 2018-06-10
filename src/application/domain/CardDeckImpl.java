package application.domain;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

/**
 * @author Sanchez
 *
 */
public class CardDeckImpl extends UnicastRemoteObject implements CardDeck, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5422878514985364368L;
	
	
	private Stack<Card> cards;
	private CardLevel level;
	private boolean isSelectable;
	private boolean isSelected;
	
	public CardDeckImpl(Stack<Card> cards, CardLevel level) throws RemoteException
	{
		this.cards = cards;		
		this.level = level;
	}
	
	public CardLevel getLevel()
	{
		return level;
	}
	
	public void add(Card card)
	{
		cards.push(card);
	}
	
	public Card top()
	{
		return cards.peek();
	}
	
	public Card pull()
	{
		return cards.pop();
	}

	public Stack<Card> getAll() {
		return cards;
	}
	
	public boolean isSelectable() {
		return isSelectable;
	}
	
	public void setSelectable() {
		isSelectable = true;
		isSelected = false;
	}
	
	public void clearSelectable() {
		isSelectable = false;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelected() {
		isSelected = true;
		isSelectable = false;
	}
	
	public void clearSelection() {
		isSelected = false;
	}
}
