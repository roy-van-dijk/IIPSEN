package application.views;

import java.rmi.RemoteException;
import java.util.Map;
import application.domain.Gem;
import application.domain.Noble;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * Displays nobles.
 *
 * @author Sanchez
 */
public class NobleView implements UIComponent {

	private final static double RECTANGLE_RESIZE_FACTOR = 0.24;
	private final static double PRESTIGELABEL_RESIZE_FACTOR = 0.30;

	private Noble noble;
	private Pane root;

	private double sizeX, sizeY;

	/**
	 * Create view of a noble with size x and y.
	 *
	 * @param noble
	 * @param sizeX the size horzizontal
	 * @param sizeY the size vertical
	 */
	public NobleView(Noble noble, double sizeX, double sizeY) {
		this.noble = noble;
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		try {
			this.buildUI();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the path for this noble's illustration.
	 *
	 * @return String
	 * @throws RemoteException
	 */
	public String getNobleImagePath() throws RemoteException {
		String illustration = String.valueOf(noble.getIllustration());
		return String.format("file:resources/nobles/%s.png", illustration);
	}

	/**
	 * Builds the UI.
	 *
	 * @throws RemoteException
	 */
	private void buildUI() throws RemoteException {
		Rectangle rect = new Rectangle(sizeX, sizeY);

		ImagePattern imagePattern = null;
		try {
			imagePattern = new ImagePattern(new Image(getNobleImagePath()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rect.setFill(imagePattern);

		rect.setArcHeight(10);
		rect.setArcWidth(10);

		root = new StackPane(rect, addNobleInformation());
	}

	/**
	 * Adds the noble information.
	 *
	 * @return BorderPane
	 * @throws RemoteException
	 */
	private BorderPane addNobleInformation() throws RemoteException {
		BorderPane borderPane = new BorderPane();

		BorderPane prestige = new BorderPane();

		int prestigeValue = noble.getPrestigeValue();

		double prestigeLabelSize = ((this.sizeX + this.sizeY) / 2.0) * PRESTIGELABEL_RESIZE_FACTOR;
		Label prestigeLabel = new Label(prestigeValue == 0 ? "" : String.valueOf(prestigeValue));
		prestigeLabel.setAlignment(Pos.CENTER);
		prestigeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, prestigeLabelSize));
		prestigeLabel.getStyleClass().add("prestige-test");

		prestige.setLeft(prestigeLabel);
		BorderPane.setAlignment(prestigeLabel, Pos.CENTER);

		FlowPane requirements = new FlowPane(Orientation.VERTICAL);
		requirements.setAlignment(Pos.BOTTOM_CENTER);
		requirements.setColumnHalignment(HPos.LEFT); // align labels on left

		// Render requirement rectangles
		for (Map.Entry<Gem, Integer> entry : noble.getRequirements().entrySet()) {
			if (entry.getValue() != 0) {
				double rectSizeX = (this.sizeX * 1.0) * RECTANGLE_RESIZE_FACTOR;
				double rectSizeY = (this.sizeY * 1.2) * RECTANGLE_RESIZE_FACTOR;
				Rectangle requirementRect = new Rectangle(rectSizeX, rectSizeY);
				String rectanglePath = String.format("file:resources/requirements/rectangle_%s.png",
						entry.getKey().toString().toLowerCase());
				ImagePattern imagePattern = new ImagePattern(new Image(rectanglePath));
				requirementRect.setFill(imagePattern);

				Label requirementLabel = new Label(String.valueOf(entry.getValue()));
				requirementLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));

				StackPane requirementPane = new StackPane(requirementRect, requirementLabel);
				StackPane.setMargin(requirementRect, new Insets(2, 0, 2, 0));
				requirements.getChildren().add(requirementPane);
			}
		}

		VBox requirementsAndPrestige = new VBox(0);

		requirementsAndPrestige.getChildren().addAll(prestige, requirements);
		requirementsAndPrestige.getStyleClass().add("cards-prestige-bonus");
		requirementsAndPrestige.setMaxHeight(sizeY);

		borderPane.setLeft(requirementsAndPrestige);

		return borderPane;
	}

	/* (non-Javadoc)
	 * @see application.views.UIComponent#asPane()
	 */
	public Pane asPane() {
		return root;
	}

}
