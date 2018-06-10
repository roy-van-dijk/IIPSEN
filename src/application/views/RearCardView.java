package application.views;

import java.rmi.RemoteException;

import application.domain.Card;
import application.util.ImageCache;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Displays the rear side of a card
 * 
 * @author Sanchez
 *
 */
public class RearCardView extends CardView {

	private Card card;

	/**
	 * Creates a new rear card view
	 * 
	 * @param Card
	 *            card
	 * @param double
	 *            sizeX horizontal
	 * @param double
	 *            sizeY vertical
	 */
	public RearCardView(Card card, double sizeX, double sizeY) {
		super(sizeX, sizeY);
		this.card = card;

		this.buildUI();
	}
	
	private void buildUI()
	{
    	rect = new Rectangle(sizeX, sizeY);
    	
    	// Add image
    	Image image = ImageCache.getInstance().fetchImage(getImagePath(), true);
        ImagePattern imagePattern = new ImagePattern(image);
        rect.setFill(imagePattern);
        
        // Make rounded corners
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        
        // Add to root node
        root = new StackPane(rect);
	}

	@Override
	protected String getImagePath() {
		String path = "";
		try {
			path = String.format("file:resources/cards/%s/%s.png", card.getLevel().name().toLowerCase(),
					card.getLevel().name().toLowerCase());
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return path;
	}

}
