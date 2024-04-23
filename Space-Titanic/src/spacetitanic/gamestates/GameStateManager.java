package spacetitanic.gamestates;

import spacetitanic.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {

    private GamePanel gamePanel;
    private ArrayList<State> gameStates = new ArrayList<>();

    public State startMenuState;
    public State playingState;

    public GameStateManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        initializeGameStates();
    }

    public void update() {
        peek().update();
    }

    public void render(Graphics2D g2) {
        peek().render(g2);
        /* Maybe update more gamestates */
    }

    public ArrayList<State> getGameStates() {
        return gameStates;
    }

    public void push(State gamestate) {
        gameStates.add(gamestate);
    }

    public State peek() {
        return gameStates.get(gameStates.size() - 1);
    }

    public void pop() {
        gameStates.remove(gameStates.size() - 1);
        GameState.state = gameStates.get(gameStates.size() - 1).getGameState();
    }

    private void initializeGameStates() {
        startMenuState = new StartMenuState(gamePanel);
        playingState = new PlayingState(gamePanel);

        /* Add and set startmenu as startstate */
        gameStates.add(startMenuState);
        GameState.state = GameState.START_MENU;
    }


}
