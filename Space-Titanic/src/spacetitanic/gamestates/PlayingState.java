package spacetitanic.gamestates;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayingState implements State {
    GamePanel gamePanel;
    public GameState thisGameState = GameState.PLAYING;

    public PlayingState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void update() {
        if (gamePanel.input.isKeyUp(KeyEvent.VK_ESCAPE)) {
            System.out.println("Exit Playing State");
            gamePanel.gameStateManager.pop();
        }
    }

    @Override
    public void render(Graphics2D g2) {
        /* temporary code */
        g2.setColor(Color.RED);
        g2.fillRect(gamePanel.screenWidth / 6, gamePanel.screenHeight / 6, gamePanel.screenWidth * 4 / 6,
                gamePanel.screenHeight * 4 / 6);
        g2.setColor(Color.WHITE);
        g2.drawRect(gamePanel.screenWidth / 6, gamePanel.screenHeight / 6, gamePanel.screenWidth * 4 / 6,
                gamePanel.screenHeight * 4 / 6);
    }

    @Override
    public GameState getGameState() {
        return thisGameState;
    }
}
