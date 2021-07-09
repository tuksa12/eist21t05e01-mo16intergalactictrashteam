package de.tum.in.ase.eist.igt.model;

import de.tum.in.ase.eist.igt.controller.Dimension2D;
import de.tum.in.ase.eist.igt.controller.Point2D;

/**
 * All objects composing this game inherit from this class. Each object in space is modeled with basic physical
 *  attributes like mass and GUI attributes like position and canvas size.
 *
 *
 * */
public abstract class GameObject {

    private String iconLocation;
    private final int mass;
    protected Point2D position;
    protected Dimension2D size;


    public GameObject(double startX, double startY, int mass, int width, int height, String iconLocation) {
        this.position = new Point2D(startX, startY);
        this.mass = mass;
        this.size = new Dimension2D(width, height);
        this.setIconLocation(iconLocation);
    }

    /* ---------- GETTERS ---------- */
    public Dimension2D getSize(){
        return size;
    }

    public int getMass() {
        return this.mass;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public String getIconLocation() {return this.iconLocation; }

    /* --------- SETTERS ---------- */
    /**
     * Sets the image path of an game object.
     *
     * @param iconLocation the path to the image file
     * @throws NullPointerException if iconLocation is null
     */
    public void setIconLocation(String iconLocation){
        if (iconLocation == null) throw new NullPointerException("Image location mustn't be null!");
        this.iconLocation = iconLocation;
    }

    public void setPosition(double x, double y) {
        position = new Point2D(x, y);
    }

}

