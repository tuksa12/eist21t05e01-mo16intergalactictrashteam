package de.tum.in.ase.eist.igt.model;


/**
 * Spacecraft is player controlled and can accelerate, turn, decelerate and fire projectiles.
 *
 * TODO: implement life point system
 * */
public class SpaceCraft extends MovableObject{

    private static final double START_X_COORDINATE = 250.0;
    private static final double START_Y_COORDINATE = 250.0;
    private static final int START_DIRECTION = 180;
    private static final String SPACE_CRAFT_IMAGE_FILE = "spacecraft.png";
    private static final int SPACE_CRAFT_HEIGHT = 25;
    private static final int SPACE_CRAFT_WIDTH = 25;

    private static final int MASS = 10;
    private static final int INITIAL_SPEED = 0;
    private static final int ACCELERATION = 1;

    // private int lifePoints;

    public SpaceCraft() {
        super(START_X_COORDINATE, START_Y_COORDINATE, MASS, SPACE_CRAFT_WIDTH, SPACE_CRAFT_HEIGHT, SPACE_CRAFT_IMAGE_FILE,
                INITIAL_SPEED, ACCELERATION, START_DIRECTION);
        //this.lifePoints = 3;
    }

    public SpaceCraft(double start_X, double start_Y) {
        super(start_X, start_Y, MASS, SPACE_CRAFT_WIDTH, SPACE_CRAFT_HEIGHT, SPACE_CRAFT_IMAGE_FILE,
                INITIAL_SPEED, ACCELERATION, START_DIRECTION);
        //this.lifePoints = 3;
    }

    /**
     *
     * TODO: determine direction
     * */
    public Shot shoot() {
        return new Shot(this.getPosition().getX(), this.getPosition().getY(), this.getDirection());
    }

    public void setAcceleration(int powerUp) {
        super.setAcceleration(this.getAcceleration() + powerUp);
    }

}
