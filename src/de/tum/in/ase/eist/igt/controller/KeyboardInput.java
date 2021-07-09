package de.tum.in.ase.eist.igt.controller;

import de.tum.in.ase.eist.igt.model.SpaceCraft;
import de.tum.in.ase.eist.igt.view.GameBoardUI;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;


/**
 * Keyboard input handling for GalacticGarbagemen.
 *
 * @implNote currently only W, A, S and D are working.
 *
 * */
public class KeyboardInput {

    private final SpaceCraft spaceCraft;
    private final GameBoardUI gameBoardUI;
    private final Scene scene;

    public KeyboardInput(Scene scene, GameBoardUI gameBoardUI, SpaceCraft userSpaceCraft) {
        this.spaceCraft = userSpaceCraft;
        this.gameBoardUI = gameBoardUI;
        this.scene = scene;
        this.scene.addEventHandler(KeyEvent.KEY_PRESSED, this::keyPressed);
    }

    /**
     * Handles keyboard input and manipulates the space craft accordingly with acceleration / deceleration and tilting
     *  operations.
     * */
    public void keyPressed(KeyEvent keyInput) {

        int ANGLE_ON_TURN = 3;
        switch (keyInput.getCode()) {
            case W, UP -> {
                spaceCraft.accelerate();
                // System.out.println("Up pressed"); // debug
            }
            case S, DOWN -> {
                spaceCraft.decelerate();
                // System.out.println("Down pressed"); // debug
            }
            case A, LEFT -> {
                spaceCraft.setDirection(ANGLE_ON_TURN);
                // System.out.println("Left pressed"); // debug
            }
            case D, RIGHT -> {
                spaceCraft.setDirection(-ANGLE_ON_TURN);
                // System.out.println("Right pressed"); // debug
            }
            case X -> {
                gameBoardUI.getGameBoard().spacecraftShoot();
            }
        }

        // System.out.println(keyInput.getCharacter()); // debug
    }

    public void reset() { scene.addEventHandler(KeyEvent.KEY_PRESSED, this::keyPressed);}

}
