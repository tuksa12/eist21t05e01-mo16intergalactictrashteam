package de.tum.in.ase.eist.igt.controller;

import de.tum.in.ase.eist.igt.model.Debris;
import de.tum.in.ase.eist.igt.model.GameObject;
import de.tum.in.ase.eist.igt.model.Planet;
import de.tum.in.ase.eist.igt.model.SpaceCraft;


/**
 * Collision class handling collisions between all sorts of game objects.
 * If the spacecraft is involved it must be handed down as the first object!
 *
 * */
public class Collision {

    protected final GameObject gameObject1;
    protected final GameObject gameObject2;
    private final boolean isCollision;

    public Collision(GameObject gameObject1, GameObject gameObject2) { // Perhaps multiple constructors depending on the collision
        this.gameObject1 = gameObject1;
        this.gameObject2 = gameObject2;
        assert this.gameObject2.getClass() != SpaceCraft.class;
        this.isCollision = detectCollision();
    }

    public boolean isCollision() {
        return isCollision;
    }

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

    public GameOutcome evaluate() { // ??? How to evaluate collision: Lives?
                                  // Make SpaceCraft an obstacle??

        Point2D p1 = gameObject1.getPosition();
        Point2D p2 = gameObject2.getPosition();

        // involving spacecraft
        if (gameObject1.getClass() == SpaceCraft.class){

            // spacecraft - planet collisions
            if (gameObject2.getClass() == Planet.class) return GameOutcome.LOST;

            // spacecraft - debris collisions
            // TODO
        } else { // involving debris

            // debris - debris collisions
            if (gameObject2.getClass() == Debris.class){
                // TODO split both debris depending on size
            }

            // debris - planet collisions
            else if (gameObject2.getClass() == Planet.class){
                // TODO evaporation animation

                Debris debris = (Debris) gameObject1;
                //debris.tagOffBoard();
            }

        }

        return GameOutcome.OPEN;
    }

    /*public Obstacles evaluateLoser() {
        Car winner = evaluate();
        if (this.car1 == winner) {
            return this.car2;
        }
        return this.car1;
    }*/

}
