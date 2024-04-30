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
            /*System.out.println("Exited Playing State");*/
            gamePanel.gameStateManager.pop();
        }
        gamePanel.player.update();
        gamePanel.map.update();
    }

    @Override
    public void render(Graphics2D g2) {
        if (gamePanel.map != null) {
            gamePanel.map.render(g2);
        }

        g2.setFont(gamePanel.graphics.axaxaxMicro);
        g2.setColor(Color.orange);
        g2.drawString(gamePanel.player.getCredz() + " £", (int) (120 * gamePanel.scaleX), (int) (20 * gamePanel.scaleY));

        g2.drawImage(gamePanel.graphics.backpanel, 0, (int) (gamePanel.screenHeight - gamePanel.graphics.backpanel.getHeight() * gamePanel.scaleY),
                (int) (gamePanel.screenWidth), (int) (gamePanel.graphics.backpanel.getHeight() * gamePanel.scaleY), null);

        gamePanel.player.render(g2);
    }

    @Override
    public GameState getGameState() {
        return thisGameState;
    }
}
