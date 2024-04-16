package spacetitanic;

import spacetitanic.gameobjects.Player;
import spacetitanic.gameobjects.equipments.Equipment;
import spacetitanic.gamestates.GameState;
import spacetitanic.gamestates.GameStateManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {

    public double scaleX = 1.0, scaleY = 1.0;
    public double tileSizeX = 64, tileSizeY = 64;

    /* Dependent on the map's image size of 5120x3840 pixels */
    public int originalTileSizeX = 64, originalTileSizeY = 64;
    public int worldRows = 60, worldColumns = 80;

    public int screenWidth, screenHeight, worldWidth, worldHeight;
    public int FPS = 60, drawFPS = 99;
    private Thread gameThread;
    public Input input;
    public GameStateManager gameStateManager;
    public Map map;
    public Player player;
    public Camera camera;
    private boolean showInfo = true;

    public GraphicsLoader graphicsLoader;

    public GamePanel() {
        /* The bases for the screen and game world size */
        screenWidth = 1024;
        screenHeight = 576;

        /* Full screen */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        scaleX = screenSize.getWidth() / screenWidth;
        scaleY = screenSize.getHeight() / screenHeight;

        tileSizeX = originalTileSizeX * scaleX;
        tileSizeY = originalTileSizeY * scaleY;

        worldWidth = (int) (tileSizeX * worldColumns);
        worldHeight = (int) (tileSizeY * worldRows);

        screenWidth = (int) (screenWidth * scaleX);
        screenHeight = (int) (screenHeight * scaleY);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        input = new Input(this);
        gameStateManager = new GameStateManager(this);

        graphicsLoader = new GraphicsLoader(this);
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

        if (input.isKeyDown(KeyEvent.VK_F1)) {
            showInfo = !showInfo;
        }

        /* Update input values */
        input.update();

    }


    @Override
    protected void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        Graphics2D graphics2D = (Graphics2D) g2;
        gameStateManager.render(graphics2D);

        if (showInfo) {
            g2.setColor(Color.WHITE);
            g2.drawString("FPS: " + drawFPS, 10, 20);
            g2.drawString("SCALE X: " + scaleX, 10, 40);
            g2.drawString("SCALE Y: " + scaleY, 10, 60);
            g2.drawString("WIDTH: " + screenWidth, 10, 80);
            g2.drawString("HEIGHT: " + screenHeight, 10, 100);
            g2.drawString("WORLD WIDTH: " + worldWidth, 10, 120);
            g2.drawString("WORLD HEIGHT: " + worldHeight, 10, 140);
            g2.drawString("OBJECTS: " + map.getGameObjects().size(), 10, 160);

        }

    }


    public void changeGameState(GameState gameState) {
        /*System.out.println("Game State Changed");*/
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

