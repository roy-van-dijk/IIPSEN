package application.views;

import java.util.ArrayList;
import java.util.List;

import application.StageManager;
import application.controllers.GameController;
import application.domain.CardLevel;
import application.domain.Game;
import application.domain.GameObserver;
import application.domain.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameView implements GameObserver, UIComponent  {
	
	public final static int cardSizeX = 125, cardSizeY = 200; 
	public final static int tokenSizeRadius = 50;
	
	public final static int opponentsSpacing = 20;
	
	private Game game;
	private GameController gameController;
	
	
	private BorderPane root;
	
	private Button btnReserveCard;
	private Button btnPurchaseCard;
	private Button btnTakeTwoTokens;
	private Button btnTakeThreeTokens;
	
	private Pane playingField;
	private Pane buttons;
	private Pane opponents;
	private Pane player;
	
	// POC var - see opt 2. @ this.modelChanged() 
	//private List<UIComponent> comps = new ArrayList<>();

	public GameView(Game game, GameController gameController) {
		this.game = game;
		this.gameController = gameController;
		
		// game.addObserver(this); // Causes modelChanged() to be called upon initialization, before the scene is even added to the primaryStage.
		
		this.buildUI();
		
	}
	
	
	// POC
	public void modelChanged(Game game)
	{
		/*
		 * Big question of what to do here.
		 * 1. We can either re-build the ENTIRE UI (easiest, but very inefficient) -> means calling this.buildUI() and switching scene's root node
		 * 2. [No] Or we can update either the ENTIRE UI (harder, but more efficient) -> means looping through all childviews and triggering modelChanged [THIS IS NOT EASILY POSSIBLE]  
		 * 3. Or we should do neither and instead have ALL "subdomain" objects implement the observer pattern and have them ONLY notify THEIR listeners. (shitload of classes, but most efficient)
		 */
		System.out.println("Updating game view");
		this.buildUI(); // Rebuild entire UI for now
		StageManager.getInstance().switchScene(root);
	}
	
	
	private void buildUI()
	{
		root = new BorderPane();
		root.getStyleClass().add("game-view");
		
		//HBox topLayout = new HBox(10);
		playingField = buildPlayingField();
		buttons = buildButtons();
		opponents = buildOpponents();
		player = buildPlayer();
		
		VBox center = new VBox(playingField, buttons);
		
		//gameLayout.setTop(topLayout);
		root.setCenter(center);
		root.setLeft(opponents);
		root.setBottom(player);

		//root.setPadding(new Insets(0));
	}
	
	
	
	private Pane buildPlayingField()
	{
		Pane playingField = new PlayingFieldPanel(game.getPlayingField(), gameController).asPane();
		return playingField;
	}
	
	private HBox buildButtons()
	{
		HBox buttons = new HBox(20);
		buttons.getStyleClass().add("button-view");
		buttons.setAlignment(Pos.CENTER);
		
		btnReserveCard = new Button("Reserve card");
		btnReserveCard.setOnAction(e -> gameController.reserveCard());
		//btnReserveCard.setOnAction(
				
				//);
		btnPurchaseCard = new Button("Purchase card");
		btnTakeTwoTokens = new Button("Take two tokens");
		btnTakeThreeTokens = new Button("Take three tokens");
		
		
		buttons.getChildren().addAll(btnReserveCard, btnPurchaseCard, btnTakeTwoTokens, btnTakeThreeTokens);
		return buttons;
	}
	
	private Pane buildOpponents()
	{
		VBox opponentsRows = new VBox(opponentsSpacing);

		opponentsRows.setAlignment(Pos.CENTER_LEFT);
		opponentsRows.getStyleClass().add("opponents");
		opponentsRows.setPrefWidth(600);
		
		for(Player player : game.getPlayers())
		{
			if(player.equals(game.getPlayers().get(0))) continue; // For now we'll assume that the first player in the list is 'our' player
			
			Pane opponentView = new OpponentPanel(player).asPane();
			opponentsRows.getChildren().add(opponentView);
		}
		
		return opponentsRows;
	}
	
	private Pane buildPlayer()
	{
		Pane player = new PlayerPanel(game.getPlayers().get(0)).asPane();
		return player;
	}
	

	public Pane asPane() {
		return root;
	}
	
}
