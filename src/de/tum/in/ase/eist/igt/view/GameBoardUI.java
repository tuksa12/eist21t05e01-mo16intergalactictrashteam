package de.tum.in.ase.eist.igt.view;

import de.tum.in.ase.eist.igt.controller.*;
import de.tum.in.ase.eist.igt.model.GameObject;
import de.tum.in.ase.eist.igt.model.SpaceCraft;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class implements the user interface for steering the player spacecraft.
 * The user interface is implemented as a Thread that is started by clicking the start button on the tool bar and stops
 * by the stop button.
 */
public class GameBoardUI extends Canvas {

	/**
	 * The update period of the game in ms, this gives us 25 fps.
	 */
	private static final int UPDATE_PERIOD = 1000 / 25;
	private static final int DEFAULT_WIDTH = 750; // 500
	private static final int DEFAULT_HEIGHT = 750; // 300
    private static final Color BACKGROUND_COLOR = Color.BLACK;
	private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	private KeyboardInput keyboardInput;

	public static Dimension2D getPreferredSize() {
		return DEFAULT_SIZE;
	}


	/**
	 * Timer responsible for updating the game every frame that runs in a separate
	 * thread.
	 */
	private Timer gameTimer;

	private GameBoard gameBoard;

	private final GameToolBar gameToolBar;

	private HashMap<String, Image> imageCache;

	public GameBoardUI(GameToolBar gameToolBar) {
		this.gameToolBar = gameToolBar;
		setup();
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public KeyboardInput getKeyboardInput() {
		return keyboardInput;
	}

	/**
	 * Removes all existing cars from the game board and re-adds them. Player car is
	 * reset to default starting position. Renders graphics.
	 */
	public void setup() {
		setupGameBoard();
		setupImageCache();
		this.gameToolBar.updateToolBarStatus(false);
		//this.addEventHandler(KeyEvent.KEY_PRESSED, KeyboardInput::keyPressed);
		paint();
		// keyboardInput.reset();
		
		// d try to get keyboard input
        //this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {System.out.println(key.getCharacter()); });

        /*this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) { KeyboardInput.keyPressed(event); } });*/

        /*
        gameBoardUI.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyPressed(event);
            }
        });*/

    }

	private void setupGameBoard() {
		Dimension2D size = getPreferredSize();
		this.gameBoard = new GameBoard(size);

        /*
        input is currently handled in GalacticGarbagemenApplication.start()
        this.mouseInput = new MouseSteering(this, this.gameBoard.getPlayerSpaceCraft());
        this.keyboardInput = new KeyboardInput(this, this.gameBoard.getPlayerSpaceCraft());
        */

		widthProperty().set(size.getWidth());
		heightProperty().set(size.getHeight());
	}

	private void setupImageCache() {
		this.imageCache = new HashMap<>();
		for (GameObject gameObject : this.gameBoard.getGameObjects()) {
			String imageLocation = gameObject.getIconLocation();
			this.imageCache.computeIfAbsent(imageLocation, this::getImage);
		}
		String playerImageLocation = this.gameBoard.getGameObjects().get(0).getIconLocation();
		this.imageCache.put(playerImageLocation, getImage(playerImageLocation));
	}

	public void addToImageCache(GameObject gameObject) {
    }

	/**
	 * Sets the game object image.
	 *
	 * @param imageFilePath an image file path that needs to be available in the resources folder of the project
	 */
	private Image getImage(String imageFilePath) {
		URL carImageUrl = getClass().getClassLoader().getResource(imageFilePath);
		if (carImageUrl == null) {
			throw new IllegalArgumentException(
					"Please ensure that your resources folder contains the appropriate files for this exercise.");
		}
		return new Image(carImageUrl.toExternalForm());
	}

	/**
	 * Starts the GameBoardUI Thread, if it wasn't running. Starts the game board,
	 * which causes the cars to change their positions (i.e. move). Renders graphics
	 * and updates tool bar status.
	 */
	public void startGame() {
		if (!this.gameBoard.isRunning()) {
			this.gameBoard.startGame();
			this.gameToolBar.updateToolBarStatus(true);
			startTimer();
			paint();
		}
	}

	private void startTimer() {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				updateGame();
			}
		};
		if (this.gameTimer != null) {
			this.gameTimer.cancel();
		}
		this.gameTimer = new Timer();
		this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
	}

	private void updateGame() {
		if (gameBoard.isRunning()) {
			// updates car positions and re-renders graphics
			this.gameBoard.update();
			// when this.gameBoard.getOutcome() is OPEN, do nothing
			if (this.gameBoard.getGameOutcome() == GameOutcome.LOST) {
				showAsyncAlert("Oh.. you lost.");
				this.stopGame();
			} else if (this.gameBoard.getGameOutcome() == GameOutcome.WON) {
				showAsyncAlert("Congratulations! You won!!");
				this.stopGame();
			}
			paint();
		}
	}

	/**
	 * Stops the game board and set the tool bar to default values.
	 */
	public void stopGame() {
		if (this.gameBoard.isRunning()) {
			this.gameBoard.stopGame();
			this.gameToolBar.updateToolBarStatus(false);
			this.gameTimer.cancel();
		}
		keyboardInput.detachEventHandler();
	}

	/**
	 * Render the graphics of the whole game by iterating through the cars of the
	 * game board at render each of them individually.
	 */
	private void paint() {
		getGraphicsContext2D().setFill(BACKGROUND_COLOR);
		getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());

		for (GameObject gameObject : this.gameBoard.getGameObjects()) {
			getGraphicsContext2D().drawImage(this.imageCache.get(gameObject.getIconLocation()), gameObject.getPosition().getX(),
					gameObject.getPosition().getY(), gameObject.getSize().getWidth(), gameObject.getSize().getHeight());
		}

	}


	/**
     * Creates keyboard input for the GameBoardUI by adding an event handler see    {@link KeyboardInput#KeyboardInput(Scene, GameBoardUI, SpaceCraft)}.
     * */
    public void setKeyboardInput(Scene scene){
        this.keyboardInput = new KeyboardInput(scene, this, gameBoard.getPlayerSpaceCraft());
    }

//	private void  paintObstacle(GameObject object){
//		Point2D carPosition = object.getPosition();
//
//		getGraphicsContext2D().drawImage(this.imageCache.get(), carPosition.getX(),
//				carPosition.getY(), car.getSize().getWidth(), car.getSize().getHeight());
//	}

	/**
	 * Method used to display alerts in moveCars().
	 *
	 * @param message you want to display as a String
	 */
	private void showAsyncAlert(String message) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(message);
			alert.showAndWait();
			this.setup();
		});
	}
}
