package spacetitanic.gamestates.buttons;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpecialButton extends Button {


    public SpecialButton(GamePanel gamePanel, int x, int y, String text) {
        Shape shape = new Ellipse2D.Double(0, 0, 32, 32);
        this.gamePanel = gamePanel;
        area = new Area(shape);
        /* code that will be used in the future */
        localTransform.translate(x, y);
        area.transform(localTransform);
        /* default values */
        this.x = x;
        this.y = y;
        this.text = text;
        centerX = area.getBounds().getCenterX();
        centerY = area.getBounds().getCenterY();
        /* temporary code that will be fixed at a later time */
        baseColor = Color.orange;
        edgeColor = Color.black;
        textColor = Color.lightGray;
        /* load images */
        BufferedImage[] sprites;
        try {
            sprites = gamePanel.spriteSheetLoader(32, 32, 5, 1, "buttons/testbutton.png");
            normalImage = new BufferedImage[1];
            normalImage[0] = sprites[0];
            pressedImage = new BufferedImage[1];
            pressedImage[0] = sprites[4];
            hoverImage = new BufferedImage[8];
            for (int i = 0; i < sprites.length; i++) {
                hoverImage[i] = sprites[i];
            }
            hoverImage[5] = sprites[3];
            hoverImage[6] = sprites[2];
            hoverImage[7] = sprites[1];
            currentImage = normalImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
