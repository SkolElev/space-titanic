package spacetitanic;

import spacetitanic.gameobjects.GameObject;
import spacetitanic.gameobjects.GraphicEffects;
import spacetitanic.gameobjects.obstacle.Asteroid;
import spacetitanic.gameobjects.obstacle.Block;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Map {
    private GamePanel gamePanel;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private BufferedImage background;
    private BufferedImage[][] backgroundTiles;
    private ArrayList<GraphicEffects> graphicEffects = new ArrayList<>();
    private ArrayList<GameObject> addedObjects = new ArrayList<>();

    public Map(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadBackground("/worldGraphics/space_background_tile1.jpg");
        for (int i = 0; i < 20; i++) {
            GameObject block = new Block(gamePanel, (int) (Math.random() * gamePanel.worldWidth), (int) (Math.random() * gamePanel.worldHeight));
            gameObjects.add(block);
        }
        for (int i = 0; i < 20; i++) {
            GameObject asteroid = new Asteroid(gamePanel, (int) (Math.random() * gamePanel.worldWidth), (int) (Math.random() * gamePanel.worldHeight), (int) (Math.random() * 20 + 70));
            gameObjects.add(asteroid);
        }
    }

    public void update() {
        GameObject deadObject = null;
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
            if (gameObject.isDead()) {
                deadObject = gameObject;
                continue;
            }
        }
        if (deadObject != null) {
            gameObjects.remove(deadObject);
        }
        GraphicEffects deadEffect = null;
        for (GraphicEffects ge : graphicEffects) {
            ge.update();
            if (ge.isDead()) {
                deadEffect = ge;
                continue;
            }
        }
        if (deadEffect != null) {
            graphicEffects.remove(deadEffect);
        }

        if (!addedObjects.isEmpty()) {
            for (GameObject addObject : addedObjects) {
                gameObjects.add(addObject);
            }
            addedObjects.clear();
        }

    }

    public void render(Graphics2D g2) {
        int tileX = (int) gamePanel.tileSizeX;
        int tileY = (int) gamePanel.tileSizeY;

        /* Calculate the amount of visible tiles based on the screen size */
        int visibleTilesX = (int) (gamePanel.screenWidth / tileX + 3);
        int visibleTilesY = (int) (gamePanel.screenHeight / tileY + 3);

        /* Calculate the starting point of the camera */
        int startTileX = (int) (gamePanel.camera.getXOffset() / tileX);
        int startTileY = (int) (gamePanel.camera.getYOffset() / tileY);

        for (int y = 0; y < visibleTilesY; y++) {
            for (int x = 0; x < visibleTilesX; x++) {
                int currentTileX = (startTileX + x - 1 + gamePanel.worldColumns) % gamePanel.worldColumns; /* The output will always be a positive value */
                int currentTileY = (startTileY + y - 1 + gamePanel.worldRows) % gamePanel.worldRows; /* The output will always be a positive value */

                int screenX = x * tileX - (int) (gamePanel.camera.getXOffset() % tileX) - tileX;
                int screenY = y * tileY - (int) (gamePanel.camera.getYOffset() % tileY) - tileY;

                g2.drawImage(backgroundTiles[currentTileY][currentTileX], screenX, screenY, tileX, tileY, null);
                g2.setColor(Color.yellow);

                g2.drawRect(screenX, screenY, tileX, tileY);
            }
        }
        g2.setColor(Color.red);
        g2.drawRect((int) (0 - gamePanel.camera.getXOffset()), (int) (0 - gamePanel.camera.getYOffset()), gamePanel.worldWidth, gamePanel.worldHeight);

        /* Render gameObjects */
        for (int i = 0; i < gameObjects.size(); i++) {
            try {
                gameObjects.get(i).render(g2);
            } catch (ConcurrentModificationException e) {
                System.out.println("Caught a ConcurrentModificationException!");
                i--;
            }
        }

        /* Render graphicEffects */
        for (int i = 0; i < graphicEffects.size(); i++) {
            try {
                graphicEffects.get(i).render(g2);
            } catch (ConcurrentModificationException e) {
                System.out.println("Caught a ConcurrentModificationException!");
                i--;
            }
        }

    }

    private void loadBackground(String filename) {
        BufferedImage[] tempBackground = null;
        try {
            tempBackground = gamePanel.spriteSheetLoader((int) gamePanel.originalTileSizeX, (int) gamePanel.originalTileSizeY,
                    gamePanel.worldColumns, gamePanel.worldRows, filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        backgroundTiles = new BufferedImage[gamePanel.worldRows][gamePanel.worldColumns];
        int counter = 0;
        for (int y = 0; y < gamePanel.worldRows; y++) {
            for (int x = 0; x < gamePanel.worldColumns; x++) {
                backgroundTiles[y][x] = tempBackground[counter];
                counter++;
            }
        }
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addEffect(GraphicEffects ge) {
        graphicEffects.add(ge);
        /*System.out.println("ex. " + graphicEffects.size());*/
    }

    public void addObjects(GameObject gameObject) {
        addedObjects.add(gameObject);
    }


}
