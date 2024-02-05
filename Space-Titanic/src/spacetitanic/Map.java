package spacetitanic;

import spacetitanic.gameobjects.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    private GamePanel gamePanel;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private BufferedImage background;

    public Map(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadBackground("/worldGraphics/space_background.jpg");
    }

    public void update() {

    }

    public void render(Graphics2D g2) {
        g2.drawImage(background, (int) (0 - gamePanel.camera.getxOffset()), (int) (0 - gamePanel.camera.getyOffset()), null);
    }

    private void loadBackground(String filename) {
        String filepath = "/spacetitanic/resources/" + filename;
        try {
            background = ImageIO.read(getClass().getResource(filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
