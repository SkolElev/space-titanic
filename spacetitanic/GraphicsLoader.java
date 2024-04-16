package spacetitanic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GraphicsLoader {
    private GamePanel gamePanel;

    public BufferedImage[] crater200x200;

    public GraphicsLoader(GamePanel gamePanel) {
        try {
            crater200x200 = getRandomSubImage(200, 200, "gameObjects/craters.jpg", 20);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage[] getRandomSubImage(int spriteWidth, int spriteHeight, String filename, int amount) throws IOException {
        String filepath = "/spacetitanic/resources/" + filename;
        BufferedImage bigImage = ImageIO.read(getClass().getResource(filepath));
        int bigWidth = bigImage.getWidth();
        int bigHeight = bigImage.getHeight();

        BufferedImage[] image = new BufferedImage[amount];
        for (int i = 0; i < amount; i++) {
            int randomX = (int) (Math.random() * (bigWidth - spriteWidth));
            int randomY = (int) (Math.random() * (bigHeight - spriteHeight));
            image[i] = bigImage.getSubimage(randomX, randomY, spriteWidth, spriteHeight);
        }
        return image;
    }

    /**
     * This constructor loads the spritesheet and outputs an array of images.
     *
     * @param spriteWidth  The width of a sprite image.
     * @param spriteHeight The height of a sprite image.
     * @param columnX      The number of sprites in a column
     * @param rowY         The number of sprites in a row
     * @param filename     The file name including the subfolder
     * @return An array of sprites
     * @throws IOException
     */
    public BufferedImage[] spriteSheetLoader(int spriteWidth, int spriteHeight, int columnX, int rowY, String filename) throws IOException {
        String filepath = "/spacetitanic/resources/" + filename;
        BufferedImage spriteSheet = ImageIO.read(getClass().getResource(filepath));
        BufferedImage[] sprites = new BufferedImage[columnX * rowY];
        for (int spriteY = 0; spriteY < rowY; spriteY++) {
            for (int spriteX = 0; spriteX < columnX; spriteX++) {
                sprites[(columnX * spriteY) + spriteX] = spriteSheet.getSubimage(spriteX * spriteWidth, spriteY * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        return sprites;
    }

}
