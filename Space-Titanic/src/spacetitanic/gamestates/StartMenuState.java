package spacetitanic.gamestates;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import spacetitanic.gamestates.buttons.Button;
import spacetitanic.gamestates.buttons.RectangularButton;
import spacetitanic.gamestates.buttons.SpecialButton;

public class StartMenuState implements State {

    GamePanel gamePanel;
    public GameState thisGameState = GameState.START_MENU;

    private ArrayList<Button> buttons = new ArrayList<>();

    public StartMenuState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        initialize();
    }

    @Override
    public void update() {
        if (gamePanel.input.isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.out.println("Key: Quiting Game");
            System.exit(0);
        }
        if (gamePanel.input.isKeyDown(KeyEvent.VK_ENTER)) {
            System.out.println("Key: Starting Game");
            gamePanel.changeGameState(GameState.PLAYING);
        }

        for (Button button : buttons) {
            button.update();
            if (gamePanel.input.isButtonUp(1) && button.isHit(gamePanel.input.getMouseX(), gamePanel.input.getMouseY())) {
                System.out.println("Button hit!");
                if (button.getText().equals("Start Game")) {
                    System.out.println("Button: Starting Game");
                    gamePanel.changeGameState(GameState.PLAYING);
                }
                if (button.getText().equals("Quit Game")) {
                    System.out.println("Button: Quiting Game");
                    System.exit(0);
                }
            }
        }

    }

    @Override
    public void render(Graphics2D g2) {
        /* temporary code */
        g2.setColor(Color.cyan);
        g2.fillRect(gamePanel.screenWidth / 8, gamePanel.screenHeight / 8, gamePanel.screenWidth * 6 / 8,
                gamePanel.screenHeight * 6 / 8);
        g2.setColor(Color.BLACK);
        g2.drawRect(gamePanel.screenWidth / 8, gamePanel.screenHeight / 8, gamePanel.screenWidth * 6 / 8,
                gamePanel.screenHeight * 6 / 8);

        for (Button button : buttons) {
            button.render(g2);
        }
    }

    @Override
    public GameState getGameState() {
        return thisGameState;
    }

    private void initialize() {
        Rectangle r = new Rectangle(0, 0, 100, 30);
        Button b = new RectangularButton(gamePanel, gamePanel.screenWidth * 3 / 8, gamePanel.screenHeight * 2 / 8, r, "Start Game");
        buttons.add(b);

        b = new RectangularButton(gamePanel, gamePanel.screenWidth * 3 / 8, gamePanel.screenHeight * 3 / 8, r, "Quit Game");
        buttons.add(b);

        b = new SpecialButton(gamePanel, gamePanel.screenWidth * 2 / 8, gamePanel.screenHeight * 2 / 8, "Start Game");
        buttons.add(b);

        b = new SpecialButton(gamePanel, gamePanel.screenWidth * 2 / 8, gamePanel.screenHeight * 3 / 8, "Quit Game");
        buttons.add(b);
    }

}
