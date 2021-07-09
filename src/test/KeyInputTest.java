package test;

import de.tum.in.ase.eist.igt.model.SpaceCraft;
import de.tum.in.ase.eist.igt.view.GameBoardUI;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import org.easymock.*;

public class KeyInputTest {

    @Mock
    private KeyEvent keyEventMock;
    @Mock
    private Scene sceneMock;
    @Mock
    private GameBoardUI gameBoardUIMock;
    @Mock
    private SpaceCraft spaceCraftMock;


    /*@Test
    public void testKeyInput() {
        SpaceCraft testSpaceCraft = new SpaceCraft();
        KeyboardInput keyboardInput = new KeyboardInput(new Scene(), new GameBoardUI(new GameToolBar()),testSpaceCraft);
        keyboardInput.keyPressed(event);
        Assertions.assertNotNull(event);
    }*/

    /**
     * test if KeyboardInput is properly initialized
     */
   /*
    @Test
    public void testKeyboardInputInitialize() {
        EasyMock.replay(sceneMock);
        EasyMock.replay(spaceCraftMock);
        EasyMock.replay(gameBoardUIMock);
        KeyboardInput tester = new KeyboardInput(sceneMock,gameBoardUIMock, spaceCraftMock);
        Assertions.assertNotNull(tester);
    }
    */
    /**
     * test if KeyboardInput can process a Keyevent
     */
    /*@Test
    public void testKeyeventHandling() {
        EasyMock.expect(keyEventMock.getCode()).andReturn(KeyCode.W);
        EasyMock.replay(keyEventMock);
        EasyMock.replay(sceneMock);
        EasyMock.replay(spaceCraftMock);
        EasyMock.replay(gameBoardUIMock);
        KeyboardInput tester = new KeyboardInput(sceneMock,gameBoardUIMock, spaceCraftMock);
        tester.keyPressed(keyEventMock);
    }
    */
}
