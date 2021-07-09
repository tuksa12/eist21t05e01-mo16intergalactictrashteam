package de.tum.in.ase.eist.igt.model;

/**
 * Models a planet or moon, which is a immobilized object of huge mass that has gravitational impact on smaller game
 *  objects.
 *
 * TODO: implement gravity functionality
 *
 * */
public class Planet extends StationaryObject {

    /**
     * Gravitational constant is 6.67430e-11. Use "force = gravity * (mass_1 * mass_2)/ distance^2" for calculating
     *  gravitational force.
     *
     * https://en.wikipedia.org/wiki/Gravitational_constant
     * TODO: check if scientific constant provides good gameplay
     * */
    private final double gravity = 6.67430e-11;

    public Planet(double startX, double startY, int mass, int planet_width, int planet_height, String fileName) {
        super(startX, startY, mass, planet_width, planet_height, fileName);
    }


    /**
     * Calculates gravitational force between this object and another MovableObject.
     *
     * @param movableObject Either a Debris object or the spacecraft, ignore laser projectiles.
     * @return gravitational pull as double
     */
    public double gravityAttraction(MovableObject movableObject) {
        double gravityValue = this.gravity * this.getMass() * movableObject.getMass();
        double distance = Math.hypot(this.getPosition().getX() - movableObject.getPosition().getX(), this.getPosition().getY() - movableObject.getPosition().getY());
        return gravityValue/(distance * distance);
    }


    public boolean closeEnough(GameObject obj) {
        double distance = Math.hypot(this.getPosition().getX() - obj.getPosition().getX(), this.getPosition().getY() - obj.getPosition().getY());

        return distance <= 2.5;
    }
}

