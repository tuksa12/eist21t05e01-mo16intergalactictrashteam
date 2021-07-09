package de.tum.in.ase.eist.igt.model;

import java.util.ArrayList;

/**
 * Debris which can be shot by the player to break it down into smaller debris and push into planets.
 *
 * The mass of this object serves as a counter in 10 (or sufficient number) ticks it will be counted down.
 *
 * TODO: spawn derbies on a random edge position
 * TODO: different sizes
 * */
public class Debris extends MovableObject{

    private static final String DEBRIS_IMAGE_FILE = "debris_asteroid.png";
    private static final int DEBRIS_HEIGHT = 25;
    private static final int DEBRIS_WIDTH = 25;
    private static final int MINIMAL_MASS = 10;

    /**
     * Debris cannot accelerate on it's own.
     * */
    private static final int ACCELERATION = 0;

    private static final int INITIAL_SPEED = 10;
    private static final int INITIAL_DIRECTION = 0;


    public Debris(double startX, double startY, int mass, int initial_speed, int initial_direction) {
        super(startX, startY, mass, DEBRIS_WIDTH, DEBRIS_HEIGHT, DEBRIS_IMAGE_FILE, Math.max(1, initial_speed),
                ACCELERATION, initial_direction);
    }

    public Debris(double startX, double startY, int mass, int width, int height, int initial_speed, int initial_direction) {
        super(startX, startY, mass, width, height, DEBRIS_IMAGE_FILE, Math.max(1, initial_speed),
                ACCELERATION, initial_direction);
    }

    /**
     * When a debris is destroyed it splits in multiple smaller debris as long as it's mass is large enough.
     */
    public ArrayList<Debris> split(){
        Debris d1 = new Debris(getPosition().getX(), getPosition().getY(), getMass()/2, DEBRIS_WIDTH/2, DEBRIS_HEIGHT/2, getSpeed(), getDirection());
        Debris d2 = new Debris(getPosition().getX(), getPosition().getY(), getMass()/2, DEBRIS_WIDTH/2, DEBRIS_HEIGHT/2, getSpeed(), getDirection());
        ArrayList<Debris> ds = new ArrayList<>();
        ds.add(d1);
        ds.add(d2);
        evaporate();
        return ds;
    }

    /**
     * Determine into how many pieces a debris breaks appart.
     * */
    private int calculateNumberOfPieces(){ return this.getMass() / MINIMAL_MASS; }

    /**
     * Destroys this debris when collided with a planet and tags it for removal from the game board.
     *
     * TODO: implement evaporation picture
     */
    public void evaporate(){ this.tagOffBoard(); }

}
