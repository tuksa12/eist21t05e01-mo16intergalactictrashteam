package de.tum.in.ase.eist.igt;

import de.tum.in.ase.eist.igt.view.GameBoardUI;
import de.tum.in.ase.eist.igt.view.GameToolBar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Starts the Bumpers Application, loads the GameToolBar and GameBoardUI. This class is the root of the JavaFX
 *  Application.
 *
 * @see Application
 */
public class GalacticGarbagemenApplication extends Application {

	private static final int GRID_LAYOUT_PADDING = 5;
	private static final int GRID_LAYOUT_PREF_HEIGHT = 350;
	private static final int GRID_LAYOUT_PREF_WIDTH = 505;
	private GameBoardUI gameBoardUI;
	private GameToolBar gameToolBar;

	/**
	 * Starts the Bumpers Window by setting up a new tool bar, a new user interface and adding them to the stage.
     *
	 * @param primaryStage the primary stage for this application, onto which the application scene can be set.
	 */
	@Override
	public void start(Stage primaryStage) {
		// the tool bar object with start and stop buttons

        gameToolBar = new GameToolBar();
		gameBoardUI = new GameBoardUI(gameToolBar);
		gameToolBar.initializeActions(gameBoardUI);

		Pane gridLayout = createLayout(gameBoardUI, gameToolBar);

		// scene and stages
		Scene scene = new Scene(gridLayout);
		primaryStage.setTitle("Galactic Garbagemen");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(closeEvent -> gameBoardUI.stopGame());
		primaryStage.show();

		// add keyboard input handling to scene
		gameBoardUI.setKeyboardInput(scene);
	}

	/**
	 * Creates a new {@link Pane} that arranges the game's UI elements.
	 */
	private static Pane createLayout(GameBoardUI gameBoardUI, GameToolBar toolBar) {
		// GridPanes are divided into columns and rows, like a table
		GridPane gridLayout = new GridPane();
		gridLayout.setPrefSize(GRID_LAYOUT_PREF_WIDTH, GRID_LAYOUT_PREF_HEIGHT);
		gridLayout.setVgap(GRID_LAYOUT_PADDING);
		gridLayout.setPadding(new Insets(GRID_LAYOUT_PADDING));

		// add all components to the gridLayout
		// second parameter is column index, second parameter is row index of grid
		gridLayout.add(gameBoardUI, 0, 1);
		gridLayout.add(toolBar, 0, 0);
		return gridLayout;
	}

	/**
	 * The whole game will be executed through the launch() method.
	 * <p>
	 * Use {@link GalacticGarbagemen#main(String[])} to run the Java application.
	 */
	public static void startApp(String[] args) {
		launch(args);
	}

	public GameBoardUI getGameBoardUI() {
		return gameBoardUI;
	}

	public GameToolBar getGameToolBar() {
		return gameToolBar;
	}
}
