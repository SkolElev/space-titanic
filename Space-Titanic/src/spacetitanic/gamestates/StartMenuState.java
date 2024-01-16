package spacetitanic.gamestates;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartMenuState implements State {

    GamePanel gamePanel;
    public GameState thisGameState = GameState.START_MENU;

    public StartMenuState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void update() {
        if (gamePanel.input.isKey(KeyEvent.VK_ESCAPE)) {
            System.out.println("Quiting Game");
            System.exit(0);
        }
        if (gamePanel.input.isKey(KeyEvent.VK_SPACE)) {
            System.out.println("Start the Game");
            gamePanel.changeGameState(GameState.PLAYING);
        }
    }

    @Override
    public void render(Graphics2D g2) {
        /* temporary code */
        g2.setColor(Color.ORANGE);
        g2.fillRect(gamePanel.screenWidth / 8, gamePanel.screenHeight / 8, gamePanel.screenWidth * 6 / 8,
                gamePanel.screenHeight * 6 / 8);
        g2.setColor(Color.BLACK);
        g2.drawRect(gamePanel.screenWidth / 8, gamePanel.screenHeight / 8, gamePanel.screenWidth * 6 / 8,
                gamePanel.screenHeight * 6 / 8);
    }

    @Override
    public GameState getGameState() {
        return thisGameState;
    }

}
