package spacetitanic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GraphicsLoader {
    private GamePanel gamePanel;
    public BufferedImage[] crater200x200;
    public BufferedImage[] resourceIcons;
    public BufferedImage[][] gemIcons;

    public BufferedImage backpanel, weaponPanel, cargoPanel;

    public Font basic, axaxax, axaxaxMicro, axaxaxSmall, axaxaxBig;

    public GraphicsLoader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadFonts();
        try {
            crater200x200 = getRandomSubImage(200, 200, "gameObjects/craters.jpg", 20);
            resourceIcons = spriteSheetLoader(50, 50, 5, 1, "gameObjects/ResourceIcons50x50.png");
            gemIcons = spriteSheetLoader2D(16, 32, 21, 6, "gameObjects/gems_small.png");
            loadScreenImages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadScreenImages() throws IOException {
        String filepath = "/spacetitanic/resources/screen/frame.png";
        this.backpanel = ImageIO.read(getClass().getResource(filepath));
        filepath = "/spacetitanic/resources/screen/weapon_panel.png";
        this.weaponPanel = ImageIO.read(getClass().getResource(filepath));
        filepath = "/spacetitanic/resources/screen/cargo_panel.png";
        this.cargoPanel = ImageIO.read(getClass().getResource(filepath));

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

    public BufferedImage[][] spriteSheetLoader2D(int spriteWidth, int spriteHeight, int columnX, int rowY, String filename) throws IOException {
        String filepath = "/spacetitanic/resources/" + filename;
        BufferedImage spriteSheet = ImageIO.read(getClass().getResource(filepath));
        BufferedImage[][] sprites = new BufferedImage[rowY][columnX];
        for (int spriteY = 0; spriteY < rowY; spriteY++) {
            for (int spriteX = 0; spriteX < columnX; spriteX++) {
                sprites[spriteY][spriteX] = spriteSheet.getSubimage(spriteX * spriteWidth, spriteY * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        return sprites;
    }

    private void loadFonts() {
        try {
            basic = gamePanel.getFont();
            InputStream ttf = gamePanel.getClass().getResourceAsStream("/spacetitanic/resources/screen/axaxax-bd.ttf");
            axaxax = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont((float) (20f * Math.min(gamePanel.scaleX, gamePanel.scaleY)));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(axaxax);
            ttf = gamePanel.getClass().getResourceAsStream("/spacetitanic/resources/screen/axaxax-bd.ttf");
            axaxaxMicro = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont((float) (8f * Math.min(gamePanel.scaleX, gamePanel.scaleY)));
            ge.registerFont(axaxaxMicro);
            ttf = gamePanel.getClass().getResourceAsStream("/spacetitanic/resources/screen/axaxax-bd.ttf");
            axaxaxSmall = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont((float) (16f * Math.min(gamePanel.scaleX, gamePanel.scaleY)));
            ge.registerFont(axaxaxSmall);
            ttf = gamePanel.getClass().getResourceAsStream("/spacetitanic/resources/screen/axaxax-bd.ttf");
            axaxaxBig = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont((float) (28f * Math.min(gamePanel.scaleX, gamePanel.scaleY)));
            ge.registerFont(axaxaxBig);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

    }

}
