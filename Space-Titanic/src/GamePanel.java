import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public double scaleX = 1.0, scaleY = 1.0;
    public int screenWidth, screenHeight, worldWidth, worldHeight;
    public int FPS = 60, drawFPS = 99;
    private Thread gameThread;

    public GamePanel() {
        /* The bases for screen and game world size */
        screenWidth = 800;
        screenHeight = 800;
        worldWidth = 4000;
        worldHeight = 3000;

        /* Scaled values for the world */
        worldWidth = (int) (worldWidth * scaleX);
        worldHeight = (int) (worldHeight * scaleY);
        screenWidth = (int) (screenWidth * scaleX);
        screenHeight = (int) (screenHeight * scaleY);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        this.setBackground(Color.BLUE);

    }

    @Override
    public void run() {
        /* Implement interface */
        /* Delta accumulator game loop */

        /* intervall = 1 second per FPS. Approximately 0,016666 seconds per tick for 60 FPS */

        /* Initialize the variables */
        double delta = 0.0;

        /* Show variable on screen */

        /* Start the game loop */

        /* Read the current system time */

        /* Calculate the deference from previous updates */

        /* Save the current time as the previous time */

        /* When delta is larger or equals to 1.0 then update the screen */

    }
}
