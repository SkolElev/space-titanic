package spacetitanic.gameobjects;

import java.awt.*;

public interface GraphicEffects {
    public void update();

    public void render(Graphics2D g2);

    public boolean isDead();
}
