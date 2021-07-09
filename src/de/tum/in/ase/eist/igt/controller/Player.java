package de.tum.in.ase.eist.igt.controller;

import de.tum.in.ase.eist.igt.model.SpaceCraft;
import de.tum.in.ase.eist.igt.model.PowerUp;

public class Player {

    private PowerUp powerUp;

    private SpaceCraft spaceCraft;

    public Player(SpaceCraft spaceCraft) {
        this.spaceCraft = spaceCraft;
    }

    public void setSpaceCraft(SpaceCraft spaceCraft) {
        this.spaceCraft = spaceCraft;
    }

    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public SpaceCraft getSpaceCraft() {
        return spaceCraft;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void setup() {
        // The player always starts in the upper left corner facing to the right
        //spaceCraft.setPosition(START_X_COORDINATE, START_Y_COORDINATE);
        //spaceCraft.setDirection(START_DIRECTION);
    }
}
