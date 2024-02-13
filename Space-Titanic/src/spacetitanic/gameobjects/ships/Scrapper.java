package spacetitanic.gameobjects.ships;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.Ship;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Scrapper extends Ship {

    private BufferedImage[] mainEngine, breakEngine, leftEngine, rightEngine;
    private int mainCounter, breakCounter, leftCounter, rightCounter;

    public Scrapper(GamePanel gamePanel) {
        super(gamePanel);
        rotation = 45.0;
        x = (double) gamePanel.screenWidth / 2;
        y = (double) gamePanel.screenHeight / 2;

        shipModelName = "Scrapper";
        this.generateName();
        System.out.println(shipName);

        try {
            images = gamePanel.spriteSheetLoader(64, 64, 17, 1, "ships/scrapper.png");
            mainEngine = gamePanel.spriteSheetLoader(64, 64, 17, 1, "ships/scrapper_main_engine.png");
            breakEngine = gamePanel.spriteSheetLoader(64, 64, 17, 1, "ships/scrapper_break_engine.png");
            leftEngine = gamePanel.spriteSheetLoader(64, 64, 17, 1, "ships/scrapper_left_turn.png");
            rightEngine = gamePanel.spriteSheetLoader(64, 64, 17, 1, "ships/scrapper_right_turn.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* "The Doritos Ship" */
        int[] xPoints = {31, -12, -15, -12};
        int[] yPoints = {-1, 14, 0, -16};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);

        acceleration = 0.004;
        deceleration = 0.002;
        maxSpeed = 9.5;

        rotationSpeed = 0.5;
        maxTurnSpeed = 3.5;

        updateTimer = 0;
        updateDelay = 3; /* Approximately 20 updates per second */
    }

    @Override
    public void update() {
        super.update();
        updateTimer++;
        if (updateTimer >= updateDelay) {
            /* Update the frame of the graphics */
            currentImage++;
            if (currentImage >= images.length - 1) {
                currentImage = 0;
            }

            if (accelerating) {
                if (mainCounter >= mainEngine.length - 5) {
                    mainCounter = 7;
                }
                mainCounter++;
            } else if (!accelerating && mainCounter >= mainEngine.length - 1) {
                mainCounter = 0;
            } else if (mainCounter != 0) {
                mainCounter++;
            }

            if (decelerating) {
                if (breakCounter >= breakEngine.length - 5) {
                    breakCounter = 7;
                }
                breakCounter++;
            } else if (!decelerating && breakCounter >= breakEngine.length - 1) {
                breakCounter = 0;
            } else if (breakCounter != 0) {
                breakCounter++;
            }

            if (turningLeft) {
                if (leftCounter >= leftEngine.length - 5) {
                    leftCounter = 7;
                }
                leftCounter++;
            } else if (!turningLeft && leftCounter >= leftEngine.length - 1) {
                leftCounter = 0;
            } else if (leftCounter != 0) {
                leftCounter++;
            }

            if (turningRight) {
                if (rightCounter >= rightEngine.length - 5) {
                    rightCounter = 7;
                }
                rightCounter++;
            } else if (!turningRight && rightCounter >= rightEngine.length - 1) {
                rightCounter = 0;
            } else if (rightCounter != 0) {
                rightCounter++;
            }

            updateTimer = 0;
        }
    }

    public void render(Graphics2D g2) {
        /* temporary transform */
        AffineTransform oldCoordinates = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(x - gamePanel.camera.getxOffset(), y - gamePanel.camera.getyOffset());
        objectTransform.rotate(Math.toRadians(rotation));
        objectTransform.scale(1.75, 1.75);
        g2.transform(objectTransform);

        if (accelerating) {
            g2.drawImage(mainEngine[mainCounter], -mainEngine[mainCounter].getWidth() / 2, -mainEngine[mainCounter].getHeight() / 2,
                    (int) (mainEngine[mainCounter].getWidth() * gamePanel.scaleX), (int) (mainEngine[mainCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!accelerating && mainCounter != 0) {
            g2.drawImage(mainEngine[mainCounter], -mainEngine[mainCounter].getWidth() / 2, -mainEngine[mainCounter].getHeight() / 2,
                    (int) (mainEngine[mainCounter].getWidth() * gamePanel.scaleX), (int) (mainEngine[mainCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (decelerating) {
            g2.drawImage(breakEngine[breakCounter], -breakEngine[breakCounter].getWidth() / 2, -breakEngine[breakCounter].getHeight() / 2,
                    (int) (breakEngine[breakCounter].getWidth() * gamePanel.scaleX), (int) (breakEngine[breakCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!decelerating && breakCounter != 0) {
            g2.drawImage(breakEngine[breakCounter], -breakEngine[breakCounter].getWidth() / 2, -breakEngine[breakCounter].getHeight() / 2,
                    (int) (breakEngine[breakCounter].getWidth() * gamePanel.scaleX), (int) (breakEngine[breakCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (turningLeft) {
            g2.drawImage(leftEngine[leftCounter], -leftEngine[leftCounter].getWidth() / 2, -leftEngine[leftCounter].getHeight() / 2,
                    (int) (leftEngine[leftCounter].getWidth() * gamePanel.scaleX), (int) (leftEngine[leftCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!turningLeft && leftCounter != 0) {
            g2.drawImage(leftEngine[leftCounter], -leftEngine[leftCounter].getWidth() / 2, -leftEngine[leftCounter].getHeight() / 2,
                    (int) (leftEngine[leftCounter].getWidth() * gamePanel.scaleX), (int) (leftEngine[leftCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (turningRight) {
            g2.drawImage(rightEngine[rightCounter], -rightEngine[rightCounter].getWidth() / 2, -rightEngine[rightCounter].getHeight() / 2,
                    (int) (rightEngine[rightCounter].getWidth() * gamePanel.scaleX), (int) (rightEngine[rightCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!turningRight && rightCounter != 0) {
            g2.drawImage(rightEngine[rightCounter], -rightEngine[rightCounter].getWidth() / 2, -rightEngine[rightCounter].getHeight() / 2,
                    (int) (rightEngine[rightCounter].getWidth() * gamePanel.scaleX), (int) (rightEngine[rightCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        g2.drawImage(images[currentImage], -images[currentImage].getWidth() / 2, -images[currentImage].getHeight() / 2,
                (int) (images[currentImage].getWidth() * gamePanel.scaleX), (int) (images[currentImage].getHeight() * gamePanel.scaleY),
                null);

        /* Render "The Doritos Ship" */
        /*g2.setColor(Color.red);
        g2.draw(collisionShape);*/

        /* Changes the coordinates to its previous values */
        g2.setTransform(oldCoordinates);

        /* Ship info */
        if (showShipInfo) {
            g2.setColor(Color.cyan);
            g2.drawString(shipName, (int) (x - 35 - gamePanel.camera.getxOffset()), (int) (y - 80 - gamePanel.camera.getyOffset()));
            g2.drawString((int) x + ", " + (int) y, (int) (x - 35 - gamePanel.camera.getxOffset()), (int) (y - 60 - gamePanel.camera.getyOffset()));
        }

    }

}
