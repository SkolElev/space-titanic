package spacetitanic.gameobjects;

import spacetitanic.GamePanel;
import spacetitanic.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ship extends GameObject {

    protected Vector2D positionVector = new Vector2D(0.0, 0.0);
    protected Vector2D velocityVector = new Vector2D(0.0, 0.0);
    protected double throttle = 0.0, turnSpeed, maxTurnSpeed;
    protected int updateTimer, updateDelay;
    protected boolean accelerating = false, decelerating = false, turningLeft = false, turningRight = false;
    protected String shipModelName = "---", shipName = "no name";
    protected boolean showShipInfo = false;

    public Ship(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        rotation = 45.0;
        x = (double) gamePanel.screenWidth / 2;
        y = (double) gamePanel.screenHeight / 2;
        /* "The Doritos Ship" */
        int[] xPoints = {30, -15, -8, -15};
        int[] yPoints = {0, 15, 0, -15};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);

        acceleration = 0.004;
        deceleration = 0.002;
        maxSpeed = 9.5;

        rotationSpeed = 0.5;
        maxTurnSpeed = 3.5;
    }

    @Override
    public void update() {
        /* Check collision */

        /* Updates the position and speed */
        updatePosition();
        /* Applies friction */
        updateVelocity();

        /*if (turnSpeed > 0 && (!turningLeft && !turningLeft)) {
            turnSpeed *= 0.995;
            if (turnSpeed < 0.3) {
                turnSpeed = 0.0;
                System.out.println("NOLLL");
            }
        }*/

        /* Update cargo bay */

        /* Weapon update */
    }

/*    @Override
    public void render(Graphics2D g2) {
        *//* temporary transform *//*
        AffineTransform oldCoordinates = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(x - gamePanel.camera.getXOffset(), y - gamePanel.camera.getYOffset());
        objectTransform.rotate(Math.toRadians(rotation));
        *//*objectTransform.scale(1.5, 1.5);*//*
        g2.transform(objectTransform);

        *//* Render "The Doritos Ship" *//*
        g2.setColor(Color.red);
        g2.fill(collisionShape);
        g2.setColor(Color.white);
        g2.draw(collisionShape);

        *//* Changes the coordinates to its previous values *//*
        g2.setTransform(oldCoordinates);
    }*/

    public void rotateLeft() {
        turnSpeed += rotationSpeed;
        if (turnSpeed > maxTurnSpeed) {
            turnSpeed = maxTurnSpeed;
        }
        rotation -= turnSpeed;
        if (rotation >= 180.0) {
            rotation -= 360.0;
        }
        turningLeft = true;
    }

    public void stopRotatingLeft() {
        turnSpeed = 0.0;
        turningLeft = false;
    }

    public void rotateRight() {
        turnSpeed += rotationSpeed;
        if (turnSpeed > maxTurnSpeed) {
            turnSpeed = maxTurnSpeed;
        }
        rotation += turnSpeed;
        if (rotation < 180.0) {
            rotation += 360.0;
        }
        turningRight = true;
    }

    public void stopRotatingRight() {
        turnSpeed = 0.0;
        turningRight = false;
    }

    public void accelerate() {
        throttle = throttle + acceleration;
        accelerating = true;
    }

    public void stopAccelerating() {
        accelerating = false;
        throttle = 0.0;
    }

    public void decelerate() {
        throttle -= deceleration;
        decelerating = true;
    }

    public void stopDecelerating() {
        decelerating = false;
        throttle = 0.0;
    }

    private void updatePosition() {
        positionVector.add(velocityVector);
        x = positionVector.x;
        y = positionVector.y;
        double friction = 0.995;
        velocityVector.scale(friction);

        x = (x + gamePanel.worldWidth) % gamePanel.worldWidth;
        y = (y + gamePanel.worldHeight) % gamePanel.worldHeight;
    }

    private void updateVelocity() {
        Vector2D directionVector = new Vector2D(Math.cos(Math.toRadians(rotation)), Math.sin(Math.toRadians(rotation)));
        velocityVector.add(directionVector.getScaled(throttle));

        double currentSpeed = velocityVector.getLength();
        if (currentSpeed > maxSpeed) {
            velocityVector.scale(maxSpeed / currentSpeed);
        }
    }

    /* Ship name randomizer */
    public void generateName() {
        String[] pre = {
                "Zephyr ", "Nova ", "Quasar ", "Photon ", "Blaze ", "Orbit ", "Zenith ", "Celestia ", "Vortex ", "Luminous ", "The ",
                "Astro ", "Stellar ", "Cosmic ", "Galaxy ", "Infinity ", "Radiant ", "Eclipse ", "Aurora ", "Spectrum ", "Radius "};
        String[] post = {
                "Apex", "Serenity", "Galactic", "Cynosure", "Phoenix", "Solstice", "Asteria", "Pioneer", "Radiant", "Nimbus",
                "Elysium", "Mirage", "Titan", "Hyperion", "Aurora", "Infinity", "Cosmos", "Vanguard", "Stellar", "Nebula", shipModelName};
        shipName = pre[(int) (Math.random() * pre.length)] + post[(int) (Math.random() * post.length)];
    }

    public void showInfo() {
        showShipInfo = !showShipInfo;
    }

}
