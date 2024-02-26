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
        int visibleTilesX = (int) (gamePanel.screenWidth / gamePanel.tileSizeX + 3);
        int visibleTilesY = (int) (gamePanel.screenHeight / gamePanel.tileSizeY + 3);

        /* Calculate the starting point of the camera */
        int startTileX = (int) (gamePanel.camera.getXOffset() / gamePanel.tileSizeX);
        int startTileY = (int) (gamePanel.camera.getYOffset() / gamePanel.tileSizeY);

        for (int y = 0; y < visibleTilesY; y++) {
            for (int x = 0; x < visibleTilesX; x++) {
                int currentTileX = (startTileX + x - 1 + gamePanel.worldColumns) % gamePanel.worldColumns; /* The output will always be a positive value */
                int currentTileY = (startTileY + y - 1 + gamePanel.worldRows) % gamePanel.worldRows; /* The output will always be a positive value */

                int screenX = (int) (x * (int) gamePanel.tileSizeX - (int) (gamePanel.camera.getXOffset() % gamePanel.tileSizeX) - gamePanel.tileSizeX);
                int screenY = (int) (y * (int) gamePanel.tileSizeY - (int) (gamePanel.camera.getYOffset() % gamePanel.tileSizeY) - gamePanel.tileSizeY);

                g2.drawImage(backgroundTiles[currentTileY][currentTileX], screenX, screenY, (int) gamePanel.tileSizeX, (int) gamePanel.tileSizeY, null);
                g2.setColor(Color.yellow);

                g2.drawRect(screenX, screenY, (int) gamePanel.tileSizeX, (int) gamePanel.tileSizeY);
            }
        }
        g2.setColor(Color.red);
        g2.drawRect((int) (0 - gamePanel.camera.getXOffset()), (int) (0 - gamePanel.camera.getYOffset()), gamePanel.worldWidth, gamePanel.worldHeight);
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


}
