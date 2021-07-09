package test;

import de.tum.in.ase.eist.igt.controller.GameBoard;
import de.tum.in.ase.eist.igt.controller.Point2D;
import de.tum.in.ase.eist.igt.model.Debris;
import de.tum.in.ase.eist.igt.model.GameObject;
import de.tum.in.ase.eist.igt.model.MovableObject;
import de.tum.in.ase.eist.igt.view.GameBoardUI;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MovableObjectTest {

    private final GameBoard testGameBoard = new GameBoard(GameBoardUI.getPreferredSize());
    @Mock
    private Debris mockDebris;

    /**
     * Test creation of debris.
     * */
    @Test
    public void testDebrisInitialization() {
        Debris testDebris = new Debris(1,1,10,1,1);
        Assertions.assertNotNull(testDebris);
    }

    /**
     * Test if movable objects are truly movable.
     */
    @Test
    public void movableObjectTest() {
        for(GameObject object : testGameBoard.getGameObjects()) {
            if(object instanceof MovableObject) {
                Point2D start = object.getPosition();
                object.setPosition(object.getPosition().getX() + 1, object.getPosition().getY());
                Assert.assertNotEquals(start.getX(), object.getPosition().getX(), 0.0);
            }
        }
    }

    /**
     * This test checks if objects running out of bounds get removed from the objects list in {@link GameBoard}.
     * */
    @Test
    public void testObjectOutOfBounds() {
        final GameBoard outOfBoundsTestBoard = new GameBoard(GameBoardUI.getPreferredSize(), 0);

        // check if the debris out of bounds was pawned correctly
        Assert.assertEquals(1, outOfBoundsTestBoard.getGameObjects().size());

        // move the game objects on the board (this should remove the out of bounds objects)
        outOfBoundsTestBoard.moveGameObjects();

        // check if the debris out of bounds was removed correctly
        Assert.assertEquals(0, outOfBoundsTestBoard.getGameObjects().size());
    }

    /**
     * simulate split() method from Debris with mock objects
     */

    /*
    @Test
    public void testSplit() {
        //create mockDebris and add it to the gameboard

        //call split() and return a ArrayList<Debris> of length 2 with 2 child debris

        //add child debris to the gameboard and remove mockDebris from it

        //check if gameObjects.size() = 2
    }

     */
}
