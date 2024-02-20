package spacetitanic;

import spacetitanic.gameobjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    private GamePanel gamePanel;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private BufferedImage background;
    private BufferedImage[][] backgroundTiles;

    public Map(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadBackground("/worldGraphics/space_background_tile_64px_80x60.jpg");
    }

    public void update() {

    }

    public void render(Graphics2D g2) {
        /* Calculate the amount of visible tiles based on the screen size */
        int visibleTilesX = (int) (gamePanel.screenWidth / gamePanel.tileSizeX + 2);
        int visibleTilesY = (int) (gamePanel.screenHeight / gamePanel.tileSizeY + 2);

        /* Calculate the starting point of the camera */
        int startTileX = (int) (gamePanel.camera.getXOffset() / gamePanel.tileSizeX);
        int startTileY = (int) (gamePanel.camera.getYOffset() / gamePanel.tileSizeY);

        for (int y = 0; y < visibleTilesY; y++) {
            for (int x = 0; x < visibleTilesX; x++) {
                int currentTileX = (startTileX + x - 1) % gamePanel.worldColumns; /* The output will always be a positive value */
                int currentTileY = (startTileY + y - 1) % gamePanel.worldRows; /* The output will always be a positive value */
                if (currentTileX < 0) {
                    currentTileX += gamePanel.worldColumns; /* Adjust for negative values */
                }
                if (currentTileY < 0) {
                    currentTileY += gamePanel.worldRows; /* Adjust for negative values */
                }
                int screenX = x * (int) gamePanel.tileSizeX - ((int) gamePanel.camera.getXOffset() % gamePanel.worldColumns);
                int screenY = (x * (int) gamePanel.tileSizeY - ((int) gamePanel.camera.getYOffset() % gamePanel.worldRows));

                g2.drawImage(backgroundTiles[y][x], screenX, screenY, (int) gamePanel.tileSizeX, (int) gamePanel.tileSizeY, null);
            }
        }
    }

    private void loadBackground(String filename) {
        BufferedImage[] tempBackground = null;
        try {
            tempBackground = gamePanel.spriteSheetLoader((int) gamePanel.tileSizeX, (int) gamePanel.tileSizeY,
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


}
