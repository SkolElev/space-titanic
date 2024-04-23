package spacetitanic.gamestates;

import java.awt.*;

public interface State {

    public void update();

    public void render(Graphics2D g2);

    public GameState getGameState();

}
