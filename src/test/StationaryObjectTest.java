package test;

import de.tum.in.ase.eist.igt.controller.GameBoard;
import de.tum.in.ase.eist.igt.controller.Point2D;
import de.tum.in.ase.eist.igt.model.GameObject;
import de.tum.in.ase.eist.igt.model.Planet;
import de.tum.in.ase.eist.igt.model.StationaryObject;
import de.tum.in.ase.eist.igt.view.GameBoardUI;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class StationaryObjectTest {

    private final GameBoard testGameBoard = new GameBoard(GameBoardUI.getPreferredSize());

    /**
     * test creation of a planet
     */
    @Test
    public void testPlanetInitialization() {
        Planet testPlanet = new Planet(1,1,10,10,10,"earth.png");
        Assertions.assertNotNull(testPlanet);
    }

    /**
     * Test if stationary objects are truly stationary
     */
    @Test
    public void stationaryObjectTest() {
        for(GameObject object : testGameBoard.getGameObjects()) {
            if(object instanceof StationaryObject) {
                Point2D start = object.getPosition();
                object.setPosition(object.getPosition().getX() + 1, object.getPosition().getY());
                Assert.assertEquals(start.getX(), object.getPosition().getX(), 0.0);
            }
        }
    }
}
