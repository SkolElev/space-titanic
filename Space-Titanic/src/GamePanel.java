import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public double scaleX = 1.0, scaleY = 1.0;
    public int screenWidth, screenHeight, worldWidth, worldHeight;
    public int FPS = 60, drawFPS = 99;
    private Thread gameThread;
    int x1, y1;

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

        /* Start gameThread */
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        System.out.println("lol");
        x1++;
        y1++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawLine(0, 0, x1, y1);
    }

    @Override
    public void run() {
        /* Implement interface */
        /* Delta accumulator game loop */

        /* intervall = 1 second per FPS. Approximately 0,016666 seconds per tick for 60 FPS */

        /* Initialize the variables */
        double drawIntervall = 1000000000 / FPS;
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

            /* Save the current time as the previous time */
            currentTime = lastTime;
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
}
