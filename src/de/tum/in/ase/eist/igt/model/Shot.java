package de.tum.in.ase.eist.igt.model;

import de.tum.in.ase.eist.igt.controller.GameBoard;

public class Shot extends MovableObject {

    private static final String SHOT_IMAGE_FILE = "Solid_green.png";

    private static final int SHOT_HEIGHT = 10;
    private static final int SHOT_WIDTH = 2;
    private static final int INITIAL_SPEED = 50;
    private static final int ACCELERATION = 1;

    /**
     * These objects are added to the game board in {@link GameBoard#spacecraftShoot()}
     * */
    public Shot(double startX, double startY, int direction) {
        super(startX, startY, 10, SHOT_WIDTH, SHOT_HEIGHT, SHOT_IMAGE_FILE, INITIAL_SPEED, ACCELERATION, direction);
    }
}

