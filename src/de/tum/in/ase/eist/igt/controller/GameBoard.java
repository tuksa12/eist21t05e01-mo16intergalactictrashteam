package de.tum.in.ase.eist.igt.controller;

import de.tum.in.ase.eist.igt.model.*;

import java.util.*;

public class GameBoard {

    // core functionality
    private GameOutcome gameOutcome = GameOutcome.OPEN;
    private boolean running;
    private final Dimension2D size;
    private final List<GameObject> gameObjects;
    private final Player player;
    private final Random random;

    // initialisation variables
    public static final int NUMBER_OF_DEBRIS = 5;
    // private static final int NUMBER_OF_PLANETS = 3;
    private static final long RANDOM_SEED = 42;
    private static final int MAX_DEBRIS_MASS = 500;
    private static final int MAX_DEBRIS_SPEED = 2;
    //starting position of the planets
    private Planet planet1 = new Planet(442, 442, 90000000, 260,260,"planet.png");
    private Planet planet2 = new Planet(100, 78, 1000000,140,140,"planet-brown.png");

    public GameBoard(Dimension2D size) {
        this.size = size;
        this.random = new Random();
        this.random.setSeed(RANDOM_SEED);
        this.gameObjects = new ArrayList<GameObject>();
        SpaceCraft playerCraft = new SpaceCraft(this.size.getWidth() / 2, this.size.getHeight() / 2);
        this.player = new Player(playerCraft);
        this.player.setup();
        createGameObjects();
    }

    /**
     * This constructor is meant for test cases.
     *
     * Once multiple test cases are demanded use the integer parameter as an indicator as what case shall be run and
     *  document the different options here.
     *
     * Number   Test case description
     * 0        Object out of bounds (do objects get removed which run out of bounds)
     * */
    public GameBoard(Dimension2D size, int testMode){
        this.size = size;
        this.random = new Random();
        this.random.setSeed(RANDOM_SEED);
        this.gameObjects = new ArrayList<GameObject>();
        SpaceCraft playerCraft = new SpaceCraft(this.size.getWidth() / 2, this.size.getHeight() / 2);
        this.player = new Player(playerCraft);
        this.player.setup();

        switch (testMode){
            case 0 -> setupOutOfBoundsTestCase();
            //case 1 ->
            case 2 -> System.out.println("No initialization of test case mode " + testMode + " defined! (GameBoard.GameBoard(D2D, int)");
        }

    }

    /**
     * Create game objects in the model, including all planets, debris and the space craft.
     * */
    private void createGameObjects() {
        // planets
        this.gameObjects.add(planet1);
        this.gameObjects.add(planet2);

        // spacecraft
        this.gameObjects.add(this.player.getSpaceCraft());

        // generate debris of semi random size, velocity and start point
        for (int i = 0; i < NUMBER_OF_DEBRIS; i++){
            this.gameObjects.add(new Debris(random.nextInt((int) size.getWidth()), random.nextInt((int) size.getHeight()),
                    random.nextInt(MAX_DEBRIS_MASS), random.nextInt(MAX_DEBRIS_SPEED), random.nextInt(MovableObject.MAX_ANGLE)));
        }
    }

    /**
     * Creates only one debris object that is initialized out of bounds at (-10, -10) and runs with max speed "upwards".
     * This object should be removed during the first game logic loop in {@link GameBoard#moveGameObjects()}.
     * */
    private void setupOutOfBoundsTestCase(){
        this.gameObjects.add(new Debris(-10, -10, 42, MAX_DEBRIS_SPEED, 0));
    }

    public Dimension2D getSize() {
        return size;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public GameOutcome getGameOutcome() {
        return gameOutcome;
    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public SpaceCraft getPlayerSpaceCraft() {
        return this.player.getSpaceCraft();
    }

    public void update() {
        moveGameObjects();
    }

    public void startGame(){
        this.running = true;
    }

    public void stopGame(){
        this.running = false;
    }

    private Collection<Debris> getDebris(){
        HashSet<Debris> debris = new HashSet<>();

        for (GameObject gameObject : this.gameObjects){
            if (gameObject.getClass() == Debris.class) debris.add((Debris) gameObject);
        }

        return debris;
    }


    /**
     * Move game objects and detect collisions.
     *
     * */
    public void moveGameObjects() {
        // update the positions of the player spacecraft and the debris
        for (Debris debris : this.getDebris()) {
            //calculate gravity for each debris
            double[] gravityP1 = planet1.gravityAttraction(debris);
            double[] gravityP2 = planet2.gravityAttraction(debris);
            double accelerationGX = (gravityP1[0] + gravityP2[0]) / debris.getMass();
            double accelerationGY = (gravityP1[1] + gravityP2[1]) / debris.getMass();

            // move debris
            debris.move(size, accelerationGX, accelerationGY);

            // remove object if it went off board, meaning it crossed the canvas boundaries
            // TODO: debug out of bounds object removal
            if (!debris.isOnBoard()) this.gameObjects.remove(debris);
        }
        //calculate gravity for the spacecraft
        double[] gravityP1 = planet1.gravityAttraction(player.getSpaceCraft());
        double[] gravityP2 = planet2.gravityAttraction(player.getSpaceCraft());
        double accelerationGX = (gravityP1[0] + gravityP2[0]) / player.getSpaceCraft().getMass();
        double accelerationGY = (gravityP1[1] + gravityP2[1]) / player.getSpaceCraft().getMass();

        this.player.getSpaceCraft().move(size, accelerationGX, accelerationGY);

        // collision detection

        // TODO: spacecraft planet
        for (GameObject gameObject : gameObjects){
            if (gameObject == getPlayerSpaceCraft()) continue;

            Collision collision = new Collision(getPlayerSpaceCraft(), gameObject);

            if (collision.isCollision()) {
                gameOutcome = collision.evaluate();

                /*Car loser = collision.evaluateLoser();
                printWinner(winner);
                loserCars.add(loser);

                loser.crunch();

                if(isWinner()){
                    gameOutcome = GameOutcome.WON;
                } else{
                    gameOutcome = GameOutcome.LOST;
                }*/

            }
        }

        // TODO: spacecraft debris

        // TODO: debris planet

        // iterate through all game objects and check for collisions
        for (Debris debris : this.getDebris()) {

            for (GameObject gameObject : gameObjects){
                Collision collision = new Collision(debris, gameObject);
                collision.evaluate();
            }
            // TODO: handle off board?
        }

        if (gameObjects.size() < 4) gameOutcome = GameOutcome.WON;
    }

    /**
     * Calls the shooting functionality on spacecraft and adds the resulting object to the game objects list.
     * */
    public void spacecraftShoot() {
        gameObjects.add(getPlayerSpaceCraft().shoot());
    }

    public void spacecraftPowerUpAcceleration(PowerUp powerUp) { getPlayerSpaceCraft().setAcceleration(powerUp.enhance()); }
}
