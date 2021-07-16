package de.tum.in.ase.eist.igt.controller;

import de.tum.in.ase.eist.igt.model.GameObject;
import de.tum.in.ase.eist.igt.model.Shot;
import de.tum.in.ase.eist.igt.model.SpaceCraft;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInput implements KeyListener {

    private GameObject gameObject;
    private int speedX;
    private int speedY;

    public PlayerInput (GameObject gameObject) {
        this.gameObject = gameObject;
        speedX = 10;
        speedY = 10;
    }

    public PlayerInput (GameObject gameObject, int speedX, int speedY) {
        this.gameObject = gameObject;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Keyboard controls on the space craft.
     * */
    @Override
    public void keyPressed(KeyEvent e ) {
        switch(e.getKeyCode()) {

            case KeyEvent.VK_UP: gameObject.setPosition(gameObject.getPosition().getX(), gameObject.getPosition().getY() - speedY);
            break;

            case KeyEvent.VK_DOWN: gameObject.setPosition(gameObject.getPosition().getX(), gameObject.getPosition().getY() + speedY);
            break;

            case KeyEvent.VK_LEFT: gameObject.setPosition(gameObject.getPosition().getX() - speedX, gameObject.getPosition().getY());
            break;

            case KeyEvent.VK_RIGHT: gameObject.setPosition(gameObject.getPosition().getX() + speedX, gameObject.getPosition().getY());
            break;

            case KeyEvent.VK_X: new Shot(gameObject.getPosition().getX(),gameObject.getPosition().getY(),1);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
