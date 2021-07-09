package de.tum.in.ase.eist.igt.model;

public abstract class StationaryObject extends GameObject {

    public StationaryObject(double startX, double startY, int mass, int width, int height, String iconLocation){
        super(startX, startY, mass, width, height, iconLocation);
    }

    /**
     * setPosition does nothing because this kind of object is stationary
     * @param x
     * @param y
     */
    @Override
    public void setPosition(double x, double y) {

    }
}
