package de.tum.in.ase.eist.igt.controller;

import de.tum.in.ase.eist.igt.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Collision class handling collisions between all sorts of game objects.
 * If the spacecraft is involved it must be handed down as the first object!
 *
 * To realize debris splitting collisions can hold lists of objects (Debris) that needs to be added or removed.
 * */
public class Collision {

    protected final GameObject gameObject1;
    protected final GameObject gameObject2;
    private final boolean isCollision;
    private List<GameObject> objectsToAdd;
    private List<GameObject> objectsToRemove;

    public Collision(GameObject gameObject1, GameObject gameObject2) { // Perhaps multiple constructors depending on the collision
        this.gameObject1 = gameObject1;
        this.gameObject2 = gameObject2;
        assert this.gameObject2.getClass() != SpaceCraft.class;
        this.isCollision = detectCollision();
    }

    /**
     * Getter method of boolean collision value
     * */
    public boolean isCollision() { return isCollision; }

    /**
     * Determines weather two objects overlap, meaning if they collide.
     * */
    public boolean detectCollision() {
        Point2D p1 = gameObject1.getPosition();
        Dimension2D d1 = gameObject1.getSize();

        Point2D p2 = gameObject2.getPosition();
        Dimension2D d2 = gameObject2.getSize();

        boolean above = p1.getY() + d1.getHeight() < p2.getY();
        boolean below = p1.getY() > p2.getY() + d2.getHeight();
        boolean right = p1.getX() + d1.getWidth() < p2.getX();
        boolean left = p1.getX() > p2.getX() + d2.getWidth();

        return !above && !below && !right && !left;
    }

    /**
     * Evaluates collision outcome. Here some general rules:
     *  - planets absorb all other objects, leading to Game Over if the space craft is involved
     *  - debris splits on contact
     *  - large debris leads to fatal collision for space craft as long as the mass of the debris is larger
     *
     * */
    public GameOutcome evaluate() {

        if (!isCollision()){
            return GameOutcome.OPEN;
        }


        objectsToAdd = new ArrayList<>();
        objectsToRemove = new ArrayList<>();

        Point2D p1 = gameObject1.getPosition();
        Point2D p2 = gameObject2.getPosition();

        // involving spacecraft
        if (gameObject1.getClass() == SpaceCraft.class){

            // spacecraft - planet collisions lead to space craft crash and therefore game over
            if (gameObject2.getClass() == Planet.class) return GameOutcome.LOST;

            // spacecraft - debris collisions
            if (gameObject2.getClass() == Debris.class) {

                // large debris inflicts critical damage to the space craft
                if (gameObject2.getMass() > gameObject1.getMass()) return GameOutcome.LOST;
                else {
                    int direction1 = ((SpaceCraft) gameObject1).getDirection();
                    int direction2 = ((Debris) gameObject2).getDirection();

                    //TODO: change path for both objects
                    int newDirection1 = direction1 + direction2;
                    int newDirection2 = direction2 - direction1;

                    ((SpaceCraft) gameObject1).setDirection(newDirection1);
                    ((Debris) gameObject2).setDirection(newDirection2);
                }
            }
        } else { // involving debris

            // debris - debris collisions
            if (gameObject2.getClass() == Debris.class) {
                objectsToRemove.add(gameObject1);
                objectsToRemove.add(gameObject2);
                objectsToAdd.addAll(((Debris) gameObject1).split());
                objectsToAdd.addAll(((Debris) gameObject2).split());
            }

            // debris - planet collisions
            else if (gameObject2.getClass() == Planet.class){
                Debris debris = (Debris) gameObject1;
                debris.evaporate();
            }

            else if (gameObject2.getClass() == Shot.class){
                objectsToRemove.add(gameObject1);
                objectsToRemove.add(gameObject2);
                objectsToAdd.addAll(((Debris) gameObject1).split());
            }

        }

        return GameOutcome.OPEN;
    }

}
