package spacetitanic;

import spacetitanic.gameobjects.Player;
import spacetitanic.gamestates.GameState;
import spacetitanic.gamestates.GameStateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {

    public double scaleX = 1.0, scaleY = 1.0;
    public double tileSizeX = 64, tileSizeY = 64;
    public int screenWidth, screenHeight, worldWidth, worldHeight;
    public int FPS = 60, drawFPS = 99;
    private Thread gameThread;
    public Input input;
    public GameStateManager gameStateManager;
    public Map map;
    public Player player;
    public Camera camera;

    public GamePanel() {
        /* The bases for screen and game world size */
        screenWidth = 1000;
        screenHeight = 900;
        worldWidth = 4000;
        worldHeight = 3000;

        /* Scaled values for the world */
        worldWidth = (int) (worldWidth * scaleX);
        worldHeight = (int) (worldHeight * scaleY);
        screenWidth = (int) (screenWidth * scaleX);
        screenHeight = (int) (screenHeight * scaleY);
        tileSizeX = tileSizeX * scaleX;
        tileSizeY = tileSizeY * scaleY;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        input = new Input(this);
        gameStateManager = new GameStateManager(this);

        map = new Map(this);
        player = new Player(this, "playerName");

        camera = new Camera(this, player.getShip());

        this.setBackground(Color.BLUE);

        this.setFocusable(true);
        this.requestFocus();

        /* Start gameThread */
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case START_MENU -> {
                gameStateManager.update();
            }
            case PLAYING -> {
                gameStateManager.update();
            }
            case GAME_OVER -> {
                gameStateManager.update();
            }
            case KILLED -> {
                gameStateManager.update();
            }
            case STATION -> {
                gameStateManager.update();
            }
            case STATION_STORAGE -> {
                gameStateManager.update();
            }
            case STATION_BUY_SHIP -> {
                gameStateManager.update();
            }
            case STATION_UPGRADE_SHIP -> {
                gameStateManager.update();
            }
            case STATION_BUY_UPGRADE -> {
                gameStateManager.update();
            }
            default -> throw new IllegalStateException("Unexpected value: " + GameState.state);
        }

        /* temporary code */
        /*if (input.isKey(KeyEvent.VK_ENTER)) {
            System.out.println("Space being pressed...");
        }*/
        /*System.out.println("" + input.getScroll());*/

        /* temporary update */
        camera.update();

        /* Update input values */
        input.update();
    }

    @Override
    protected void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        Graphics2D graphics2D = (Graphics2D) g2;
        gameStateManager.render(graphics2D);

        g2.setColor(Color.WHITE);
        g2.drawString("FPS: " + drawFPS, 10, 20);
    }

    public void changeGameState(GameState gameState) {
        System.out.println("Game State Changed");
        GameState.state = gameState;
        switch (gameState) {
            case START_MENU -> {
                gameStateManager.push(gameStateManager.startMenuState);
            }
            case PLAYING -> {
                gameStateManager.push(gameStateManager.playingState);
            }
            case GAME_OVER -> {
            }
            case KILLED -> {
            }
            case STATION -> {
            }
            case STATION_STORAGE -> {
            }
            case STATION_BUY_SHIP -> {
            }
            case STATION_UPGRADE_SHIP -> {
            }
            case STATION_BUY_UPGRADE -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + gameState);
        }
    }

    @Override
    public void run() {
        /* Implement interface */
        /* Delta accumulator game loop */

        /* intervall = 1 second per FPS. Approximately 0,016666 seconds per tick for 60 FPS */

        /* Initialize the variables */
        double drawIntervall = 1000000000.0 / FPS;
        double delta = 0.0;
        long lastTime = System.nanoTime(); // The operativsystems time.
        long currentTime;

        /* Show variable on screen */
        long timer = 0L;
        int drawCount = 0;

        /* Start the game loop */
        while (gameThread != null) {
            /* Read the current system time */
            currentTime = System.nanoTime();
            /* Calculate the deference from previous updates */

            /* Calculate the delta value.
             * When the delta value is equals to 1 then run an update  */
            delta += (currentTime - lastTime) / drawIntervall;
            timer += (currentTime - lastTime);

            /* Save the current time as the previous time */
            lastTime = currentTime;
            /* When delta is larger or equals to 1.0 then update the screen */
            if (delta >= 1.0) {
                /* Update */
                update();
                /* Render */
                repaint();

                delta--;
                drawCount++;
            }
            /* When the timer is larger than 1 then store values inside the drawCount to drawFPS. */
            if (timer >= 1000000000) {
                drawFPS = drawCount;
                /* Reset drawCount */
                drawCount = 0;
                timer = 0L;
            }

        }


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

