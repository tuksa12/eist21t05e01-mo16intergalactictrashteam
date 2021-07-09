package test;

import de.tum.in.ase.eist.igt.controller.GameBoard;
import de.tum.in.ase.eist.igt.model.Debris;
import de.tum.in.ase.eist.igt.model.Planet;
import de.tum.in.ase.eist.igt.view.GameBoardUI;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.stream.Collectors;

/*
 * TODO: these are test case ideas:
 *  -[A] object initialization in model
 *  -1[A] key input is properly recognized (requires simulation from javafx)
 *  -2[A] key input is properly processed (requires simulation from javafx)
 *  -3[A] planets stationary, debris moving
 *  -4[A] acceleration and stirring of the spacecraft
 *  -5[A] stopping the game (requires stop button pressed simulation from javafx), test continuation in case of negative
 *      dialog; same idea for the start game button
 *  -6[A] test object list size: do objects that float off screen get removed from the object list
 *  -7[A] mock projectile testing shooting functionality
 *  -[B] game over when space craft crashes into planet
 *  -[B] evaporation of debris
 *  -[B] spacecraft - debris collisions
 *  -[B] mock projectile test debris splitting
 *  -[C] objects behave correctly according to gravity
 *
 *
 * TODO: implement new functionality
 *  -[A] collision
 *  -[A] shooting functionality
 *  -[B] gravity
 *  -[B] debris splitting
 *  -[B] life points
 *  -[C] shooting projectiles
 *  -[C] tilting the spacecraft icon when rotating
 *  -[C] exchange space craft bouncing from edges for respawn in the center of the screen
 *
 * DONE test cases:
 *  -
 *
 *
 * Distribution of tasks:
 * Together:
 *  -
 *
 * Felipe: 3 4 6 7
 *
 *
 * Rapha: 1 2 5 object initialization in model
 *
 *
 *
 * Marcus:
 *  - collision
 *  - shooting functionality
 *  - gravity/debris splitting
 *
 *
 * */

public class GameInitializationTest {

    // create a game board for testing
    private final GameBoard testGameBoard = new GameBoard(GameBoardUI.getPreferredSize());

    /**
     * Test the initialization of the game board (controller), dont test any javafx related calls, rather verify that
     *  all objects are initialized properly.
     * */
    @Test
    public void testGameInitialization(){

        // start game
        testGameBoard.startGame();

        // test if the game logic is running
        Assertions.assertTrue(testGameBoard.isRunning());

        // check that the game objects list is not empty
        Assertions.assertFalse(testGameBoard.getGameObjects().isEmpty());

        // check that the player spacecraft is initialized
        Assertions.assertNotNull(testGameBoard.getPlayerSpaceCraft());

        // check number of planets
        Assertions.assertEquals(2, testGameBoard.getGameObjects().stream()
                .filter(gameObject -> {
                    return gameObject.getClass() == Planet.class;
                }).collect(Collectors.toSet()).size());

        // test number of debris
        Assertions.assertEquals(GameBoard.NUMBER_OF_DEBRIS, testGameBoard.getGameObjects().stream()
                .filter(gameObject -> {
                    return gameObject.getClass() == Debris.class;
                }).collect(Collectors.toSet()).size());

        // test if debris is moving by counting all stationary debris
        Assertions.assertEquals(0, testGameBoard.getGameObjects().stream().filter(gameObject -> {return gameObject.getClass() == Debris.class;})
                .filter(gameObject -> {return ((Debris) gameObject).getSpeed() <= 0;}).collect(Collectors.toSet()).size());

        // test if spacecraft is standing still at the beginning
        Assertions.assertEquals(0, testGameBoard.getPlayerSpaceCraft().getSpeed());
    }

    /**
     * Tests if the game logic is stopped properly by the according method
     * */
    @Test
    public void testStopGame() {
        testGameBoard.stopGame();
        Assertions.assertFalse(testGameBoard.isRunning());
    }
}
