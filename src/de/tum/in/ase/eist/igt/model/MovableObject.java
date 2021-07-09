package de.tum.in.ase.eist.igt.model;

import de.tum.in.ase.eist.igt.controller.Dimension2D;
import de.tum.in.ase.eist.igt.controller.Point2D;

public abstract class MovableObject extends GameObject {

    public static final int MAX_ANGLE = 360;
    public static final int HALF_ANGLE = MAX_ANGLE / 2;

    private int direction;
    private int speed;

    /**
     * We simplify the formula acceleration = (delta speed) / (delta time) to acceleration = (delta speed) as we are
     *  modeling discrete time intervals. Therefore let the
     * */
    private int acceleration;

    /**
     * Describes weather the object is still on the game board. If not this variable is used to tag objects that need to
     *  be removed.
     * */
    private boolean onBoard;

    public MovableObject(double startX, double startY, int mass, int width, int height, String iconLocation, int speed,
                         int acceleration, int direction){
        super(startX, startY, mass, width, height, iconLocation);
        this.direction = direction;
        this.speed = speed;
        this.acceleration = acceleration;
        this.onBoard = true;
    }

    /**
     * Move the object in the game model according to the current orientation, speed and acceleration.
     * TODO: add arguments to model gravitational impacts on movement
     * */
    public void move(Dimension2D gameBoardSize) {

        /*if (this.crunched) { // debries evaporated?
            return;
        }*/

        double maxX = gameBoardSize.getWidth();
        double maxY = gameBoardSize.getHeight();
        // calculate delta between old coordinates and new ones based on speed and
        // direction
        double deltaX = this.speed * Math.sin(Math.toRadians(this.direction));
        double deltaY = this.speed * Math.cos(Math.toRadians(this.direction));
        double newX = this.position.getX() + deltaX;
        double newY = this.position.getY() + deltaY;

        // calculate position in case the boarder of the game board has been reached bounce the spacecraft off the edge
        if (this.getClass() == SpaceCraft.class){
            if (newX < 0) {
                newX = -newX;
                this.direction = MAX_ANGLE - this.direction;
            } else if (newX + this.size.getWidth() > maxX) {
                newX = 2 * maxX - newX - 2 * this.size.getWidth();
                this.direction = MAX_ANGLE - this.direction;
            }

            if (newY < 0) {
                newY = -newY;
                this.direction = HALF_ANGLE - this.direction;
                if (this.direction < 0) {
                    this.direction = MAX_ANGLE + this.direction;
                }
            } else if (newY + this.size.getHeight() > maxY) {
                newY = 2 * maxY - newY - 2 * this.size.getHeight();
                this.direction = HALF_ANGLE - this.direction;
                if (this.direction < 0) {
                    this.direction = MAX_ANGLE + this.direction;
                }
            }
        } else if (this.position.getX() < 0 || this.position.getX() > maxX || this.position.getY() < 0 || this.position.getY() > maxY) {

            // mark this object to be off board (this can happen only for debris and laser shots)
            tagOffBoard();
        }

        // set coordinates
        this.position = new Point2D(newX, newY);
    }

    /* ----------- GETTERS ---------- */
    public boolean isOnBoard() { return this.onBoard; }

    public int getSpeed() { return speed; }

    public int getDirection() { return this.direction; }

    public int getAcceleration() {
        return acceleration;
    }

    /* ----------- SETTERS ---------- */
    public void setSpeed(int speed) { this.speed = speed; }

    public void setAcceleration(int acceleration) { this.acceleration = acceleration; }
    /**
     * Method for objects that can accelerate on their own. This ability of acceleration is final.
     * */
    public void accelerate() { this.speed += this.acceleration; }

    /**
     * Method for objects that can accelerate on their own.
     * */
    public void decelerate() { this.speed = this.speed - this.acceleration; }

    /**
     * Tags objects that are no longer on the game board and can be removed.
     * */
    public void tagOffBoard() { this.onBoard = false; }

    /**
     * Alters the orientation of an object by adding an angle.
     *
     * @param angle The number of radii to tilt the object 360 means a full rotation and will have no effect. Also
     *               negative values are possible.
     * */
    public void setDirection(int angle) { this.direction += angle; }
}
