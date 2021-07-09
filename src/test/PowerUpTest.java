package test;

import de.tum.in.ase.eist.igt.controller.GameBoard;
import de.tum.in.ase.eist.igt.model.PowerUp;
import de.tum.in.ase.eist.igt.view.GameBoardUI;
import org.junit.Assert;
import org.junit.Test;
import org.easymock.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.easymock.EasyMock.*;

@ExtendWith(EasyMockExtension.class)
public class PowerUpTest {

    @TestSubject
    private GameBoard testGameBoard = new GameBoard(GameBoardUI.getPreferredSize());

    @Mock
    private PowerUp mockPoweUp = mock(PowerUp.class);

    /**
     * Test the shooting functionality with mock Shot objects.
     * The system under test is {@link GameBoard#spacecraftShoot()}.
     * */
    @Test
    public void testSpacecraftPowerUp() {
        int expectedSpaceCraftAcceleration = testGameBoard.getPlayerSpaceCraft().getAcceleration() + 2;

        expect(mockPoweUp.enhance()).andReturn(2);
        replay(mockPoweUp);

        //System.out.println(mockPoweUp.enhance());
        testGameBoard.getPlayerSpaceCraft().setAcceleration(mockPoweUp.enhance());
        Assert.assertEquals(expectedSpaceCraftAcceleration, testGameBoard.getPlayerSpaceCraft().getAcceleration());
    }
}