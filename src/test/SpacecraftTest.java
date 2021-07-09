package test;

import de.tum.in.ase.eist.igt.controller.GameBoard;
import de.tum.in.ase.eist.igt.view.GameBoardUI;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Class for collecting test case ideas and for implementing first tests.
 * */
public class SpacecraftTest {

    private final GameBoard testGameBoard = new GameBoard(GameBoardUI.getPreferredSize());

    /**
     * Test creation of space craft.
     * */
    @Test
    public void testSpaceCraftInitialization() {
        // SpaceCraft testSpaceCraft = new SpaceCraft();
        Assertions.assertNotNull(testGameBoard.getPlayerSpaceCraft());
    }

    /**
     * Test space craft acceleration on method call.
     * */
    @Test
    public void spaceCraftAccelerate() {
        int initialSpeed = testGameBoard.getPlayerSpaceCraft().getSpeed();

        // accelerate spacecraft
        testGameBoard.getPlayerSpaceCraft().accelerate();

        // assert that the speed increased
        Assert.assertTrue(initialSpeed < testGameBoard.getPlayerSpaceCraft().getSpeed());
    }

    /**
     * Test deceleration of the space craft.
     * */
    @Test
    public void spaceCraftDecelerate() {
        int initialSpeed = testGameBoard.getPlayerSpaceCraft().getSpeed();
        testGameBoard.getPlayerSpaceCraft().decelerate();
        Assert.assertTrue(initialSpeed > testGameBoard.getPlayerSpaceCraft().getSpeed());
    }

}
