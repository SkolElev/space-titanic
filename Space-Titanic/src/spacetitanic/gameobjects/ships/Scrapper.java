package spacetitanic.gameobjects.ships;

import spacetitanic.GamePanel;
import spacetitanic.gameobjects.Ship;
import spacetitanic.gameobjects.equipments.Cannon;
import spacetitanic.gameobjects.equipments.Hardpoint;
import spacetitanic.gameobjects.equipments.HardpointType;

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

        generateShape();

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
/*
    @Override
    public void render(Graphics2D g2) {
        // temporary transform
        AffineTransform oldCoordinates = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(x - gamePanel.camera.getXOffset(), y - gamePanel.camera.getYOffset());
        objectTransform.rotate(Math.toRadians(rotation));
        *//*objectTransform.scale(1.75, 1.75);*//*
        g2.transform(objectTransform);

        if (accelerating) {
            g2.drawImage(mainEngine[mainCounter], (int) (-mainEngine[mainCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-mainEngine[mainCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (mainEngine[mainCounter].getWidth() * gamePanel.scaleX), (int) (mainEngine[mainCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!accelerating && mainCounter != 0) {
            g2.drawImage(mainEngine[mainCounter], (int) (-mainEngine[mainCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-mainEngine[mainCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (mainEngine[mainCounter].getWidth() * gamePanel.scaleX), (int) (mainEngine[mainCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (decelerating) {
            g2.drawImage(breakEngine[breakCounter], (int) (-breakEngine[breakCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-breakEngine[breakCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (breakEngine[breakCounter].getWidth() * gamePanel.scaleX), (int) (breakEngine[breakCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!decelerating && breakCounter != 0) {
            g2.drawImage(breakEngine[breakCounter], (int) (-breakEngine[breakCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-breakEngine[breakCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (breakEngine[breakCounter].getWidth() * gamePanel.scaleX), (int) (breakEngine[breakCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (turningLeft) {
            g2.drawImage(leftEngine[leftCounter], (int) (-leftEngine[leftCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-leftEngine[leftCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (leftEngine[leftCounter].getWidth() * gamePanel.scaleX), (int) (leftEngine[leftCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!turningLeft && leftCounter != 0) {
            g2.drawImage(leftEngine[leftCounter], (int) (-leftEngine[leftCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-leftEngine[leftCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (leftEngine[leftCounter].getWidth() * gamePanel.scaleX), (int) (leftEngine[leftCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (turningRight) {
            g2.drawImage(rightEngine[rightCounter], (int) (-rightEngine[rightCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-rightEngine[rightCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (rightEngine[rightCounter].getWidth() * gamePanel.scaleX), (int) (rightEngine[rightCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!turningRight && rightCounter != 0) {
            g2.drawImage(rightEngine[rightCounter], (int) (-rightEngine[rightCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-rightEngine[rightCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (rightEngine[rightCounter].getWidth() * gamePanel.scaleX), (int) (rightEngine[rightCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        g2.drawImage(images[currentImage], (int) (-images[currentImage].getWidth() / 2 * gamePanel.scaleX), (int) (-images[currentImage].getHeight() / 2 * gamePanel.scaleY),
                (int) (images[currentImage].getWidth() * gamePanel.scaleX), (int) (images[currentImage].getHeight() * gamePanel.scaleY),
                null);

        //  Render "The Doritos Ship"
        g2.setColor(Color.red);
        g2.draw(collisionShape);

        //  Changes the coordinates to its previous values
        g2.setTransform(oldCoordinates);

        // Ship info
        if (showShipInfo) {
            g2.setColor(Color.cyan);
            g2.drawString(shipName, (int) (x - 35 - gamePanel.camera.getXOffset()), (int) (y - 80 - gamePanel.camera.getYOffset()));
            g2.drawString((int) (x / gamePanel.tileSizeX) + ", " + (int) (y / gamePanel.tileSizeY), (int) (x - 35 - gamePanel.camera.getXOffset()), (int) (y - 60 - gamePanel.camera.getYOffset()));
            g2.drawString((int) x + ", " + (int) y, (int) (x - 35 - gamePanel.camera.getXOffset()), (int) (y - 45 - gamePanel.camera.getYOffset()));
        }

    }*/

    @Override
    protected void renderObject(Graphics2D g2, double positionX, double positionY) {
        AffineTransform old = g2.getTransform();
        objectTransform = new AffineTransform();
        objectTransform.translate(positionX, positionY);
        objectTransform.rotate(Math.toRadians(rotation));
        g2.transform(objectTransform);

        if (accelerating) {
            g2.drawImage(mainEngine[mainCounter], (int) (-mainEngine[mainCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-mainEngine[mainCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (mainEngine[mainCounter].getWidth() * gamePanel.scaleX), (int) (mainEngine[mainCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!accelerating && mainCounter != 0) {
            g2.drawImage(mainEngine[mainCounter], (int) (-mainEngine[mainCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-mainEngine[mainCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (mainEngine[mainCounter].getWidth() * gamePanel.scaleX), (int) (mainEngine[mainCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (decelerating) {
            g2.drawImage(breakEngine[breakCounter], (int) (-breakEngine[breakCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-breakEngine[breakCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (breakEngine[breakCounter].getWidth() * gamePanel.scaleX), (int) (breakEngine[breakCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!decelerating && breakCounter != 0) {
            g2.drawImage(breakEngine[breakCounter], (int) (-breakEngine[breakCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-breakEngine[breakCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (breakEngine[breakCounter].getWidth() * gamePanel.scaleX), (int) (breakEngine[breakCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (turningLeft) {
            g2.drawImage(leftEngine[leftCounter], (int) (-leftEngine[leftCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-leftEngine[leftCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (leftEngine[leftCounter].getWidth() * gamePanel.scaleX), (int) (leftEngine[leftCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!turningLeft && leftCounter != 0) {
            g2.drawImage(leftEngine[leftCounter], (int) (-leftEngine[leftCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-leftEngine[leftCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (leftEngine[leftCounter].getWidth() * gamePanel.scaleX), (int) (leftEngine[leftCounter].getHeight() * gamePanel.scaleY),
                    null);
        }

        if (turningRight) {
            g2.drawImage(rightEngine[rightCounter], (int) (-rightEngine[rightCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-rightEngine[rightCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (rightEngine[rightCounter].getWidth() * gamePanel.scaleX), (int) (rightEngine[rightCounter].getHeight() * gamePanel.scaleY),
                    null);
        } else if (!turningRight && rightCounter != 0) {
            g2.drawImage(rightEngine[rightCounter], (int) (-rightEngine[rightCounter].getWidth() / 2 * gamePanel.scaleX), (int) (-rightEngine[rightCounter].getHeight() / 2 * gamePanel.scaleY),
                    (int) (rightEngine[rightCounter].getWidth() * gamePanel.scaleX), (int) (rightEngine[rightCounter].getHeight() * gamePanel.scaleY),
                    null);
        }
        g2.drawImage(images[currentImage], (int) (-images[currentImage].getWidth() / 2 * gamePanel.scaleX), (int) (-images[currentImage].getHeight() / 2 * gamePanel.scaleY),
                (int) (images[currentImage].getWidth() * gamePanel.scaleX), (int) (images[currentImage].getHeight() * gamePanel.scaleY),
                null);

        if (!hardpoints.isEmpty()) {
            for (Hardpoint hardpoint : hardpoints) {
                hardpoint.render(g2);
            }
        }

        g2.setTransform(old);

        if (showShipInfo) {
            g2.setColor(Color.cyan);
            g2.drawString(shipName, (int) (x - 35 - gamePanel.camera.getXOffset()), (int) (y - 80 - gamePanel.camera.getYOffset()));
            g2.drawString((int) (x / gamePanel.tileSizeX) + ", " + (int) (y / gamePanel.tileSizeY), (int) (x - 35 - gamePanel.camera.getXOffset()), (int) (y - 60 - gamePanel.camera.getYOffset()));
            g2.drawString((int) x + ", " + (int) y, (int) (x - 35 - gamePanel.camera.getXOffset()), (int) (y - 45 - gamePanel.camera.getYOffset()));
        }
    }

    private void generateShape() {
        /* "The Doritos Ship" */
        int[] xPoints = {(int) (31 * gamePanel.scaleX), (int) (-12 * gamePanel.scaleX), (int) (-15 * gamePanel.scaleX), (int) (-12 * gamePanel.scaleX)};
        int[] yPoints = {(int) (-1 * gamePanel.scaleY), (int) (14 * gamePanel.scaleY), (int) (0 * gamePanel.scaleY), (int) (-16 * gamePanel.scaleY)};
        collisionShape = new Polygon(xPoints, yPoints, xPoints.length);

        Hardpoint h = new Hardpoint(gamePanel, this, (int) (18 * gamePanel.scaleX), (int) (-1 * gamePanel.scaleX), HardpointType.WEAPON, "Front gun");
        hardpoints.add(h);
        Cannon pewpew = new Cannon(gamePanel, h);
        h.setEquipped(pewpew);

        h = new Hardpoint(gamePanel, this, (int) (-5 * gamePanel.scaleX), (int) (-8 * gamePanel.scaleX), HardpointType.WEAPON, "Left gun");
        hardpoints.add(h);
        Cannon pewpew2 = new Cannon(gamePanel, h);
        h.setEquipped(pewpew2);

        h = new Hardpoint(gamePanel, this, (int) (-5 * gamePanel.scaleX), (int) (8 * gamePanel.scaleX), HardpointType.WEAPON, "Right gun");
        hardpoints.add(h);
        Cannon pewpew3 = new Cannon(gamePanel, h);
        h.setEquipped(pewpew3);
    }
}
