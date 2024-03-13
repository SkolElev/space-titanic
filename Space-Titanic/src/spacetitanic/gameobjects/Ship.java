package spacetitanic.gameobjects;

import spacetitanic.GamePanel;
import spacetitanic.Vector2D;
import spacetitanic.gameobjects.equipments.Hardpoint;

import java.awt.*;
import java.util.ArrayList;

public class Ship extends GameObject {

    protected double throttle = 0.0, turnSpeed, maxTurnSpeed;
    protected int updateTimer, updateDelay;
    protected boolean accelerating = false, decelerating = false, turningLeft = false, turningRight = false;
    protected String shipModelName = "---", shipName = "no name";
    protected boolean showShipInfo = false;
    protected ArrayList<Hardpoint> hardpoints = new ArrayList<>();

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
        boolean isColliding = checkCollisions();
        if (isColliding) {
            handleCollisions();
        }

        /* Updates the position and speed */
        updatePosition();
        /* Applies friction */
        updateVelocity();

        /* Update cargo bay */

        /* Weapon update */
    }

    protected boolean checkCollisions() {
        boolean isColliding = false;
        for (GameObject gameObject : gamePanel.map.getGameObjects()) {
            if (checkCollision(gameObject.getCollisionShape())) {
                gameObject.setHit(true);
                isColliding = true;
                /*System.out.println("Ship Crashed");*/
                break;
            } else {
                gameObject.setHit(false);
            }
        }
        return isColliding;
    }

    protected void handleCollisions() {
        /* When the ship hits an asteroid, set the velocity to zero */
        velocityVector.setZero();
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        /*int screenX = (int) (x - gamePanel.camera.getXOffset());
        int screenY = (int) (y - gamePanel.camera.getYOffset());

        renderObject(g2, screenX, screenY);

        boolean nearRightEdge = screenX > gamePanel.screenWidth - gamePanel.tileSizeX;
        boolean nearBottomEdge = screenY > gamePanel.screenHeight - gamePanel.tileSizeY;
        boolean nearLeftEdge = screenX < 0;
        boolean nearTopEdge = screenY < 0;

        *//* When going straight over the map edges *//*
        if (nearLeftEdge) {
            renderObject(g2, screenX + gamePanel.worldWidth, screenY);
        }
        if (nearRightEdge) {
            renderObject(g2, screenX - gamePanel.worldWidth, screenY);
        }
        if (nearTopEdge) {
            renderObject(g2, screenX, screenY + gamePanel.worldHeight);
        }
        if (nearBottomEdge) {
            renderObject(g2, screenX, screenY - gamePanel.worldHeight);
        }
        *//* When going over a corner *//*
        if (nearLeftEdge && nearTopEdge) {
            renderObject(g2, screenX + gamePanel.worldWidth, screenY + gamePanel.worldHeight);
        }
        if (nearLeftEdge && nearBottomEdge) {
            renderObject(g2, screenX + gamePanel.worldWidth, screenY - gamePanel.worldHeight);
        }
        if (nearRightEdge && nearTopEdge) {
            renderObject(g2, screenX - gamePanel.worldWidth, screenY + gamePanel.worldHeight);
        }
        if (nearRightEdge && nearBottomEdge) {
            renderObject(g2, screenX - gamePanel.worldWidth, screenY - gamePanel.worldHeight);
        }*/

        /*if (!hardpoints.isEmpty()) {
            for (Hardpoint hardpoint : hardpoints) {
                hardpoint.render(g2);
            }
        }*/
    }

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
        positionVector.set(x, y);
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
