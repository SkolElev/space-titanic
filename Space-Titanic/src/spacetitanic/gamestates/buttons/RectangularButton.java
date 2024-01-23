package spacetitanic.gamestates.buttons;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.geom.Area;

public class RectangularButton extends Button {

    /* The default Area is switched to a Rectangle */
    public RectangularButton(GamePanel gamePanel, int x, int y, Rectangle area, String text) {
        super(gamePanel, x, y, new Area(area), text);

    }


}
