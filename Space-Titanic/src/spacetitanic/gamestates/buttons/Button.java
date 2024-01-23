package spacetitanic.gamestates.buttons;

import spacetitanic.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public abstract class Button {
    protected GamePanel gamePanel;
    protected int x, y; /* Deposit point */
    protected Point textPosition = null;
    protected boolean hover = false, pressed = false;
    protected Color baseColor, edgeColor, textColor;
    protected String text;
    protected BufferedImage[] normalImage, hoverImage, pressedImage;
    protected BufferedImage[] currentImage;
    protected int imageCounter, imageTimer, imageDelay = 8;
    protected double centerX, centerY;
    protected Area area;
    protected BasicStroke stroke = new BasicStroke();
    protected AffineTransform localTransform = new AffineTransform();

    public Button(GamePanel gamePanel, int x, int y, Area area, String text) {
        this.gamePanel = gamePanel;
        /* code that will be used in the future */
        localTransform.translate(x, y);
        area.transform(localTransform);
        /* default values */
        this.x = x;
        this.y = y;
        this.area = area;
        this.text = text;
        centerX = area.getBounds().getCenterX();
        centerY = area.getBounds().getCenterY();
        /* temporary code that will be fixed at a later time */
        baseColor = Color.orange;
        edgeColor = Color.black;
        textColor = Color.lightGray;
    }

    public void update() {
        imageTimer++;
        if (imageTimer >= imageDelay) {
            imageCounter++;
            imageTimer = 0;
        }
        if (isHit(gamePanel.input.getMouseX(), gamePanel.input.getMouseY()) && gamePanel.input.isButton(1)) {
            /* button pressed state */
            pressed = true;
            hover = false;
            currentImage = pressedImage;
        } else if (isHit(gamePanel.input.getMouseX(), gamePanel.input.getMouseY())) {
            /* button hover state */
            hover = true;
            pressed = false;
            currentImage = hoverImage;
        } else {
            /* button normal state */
            hover = false;
            pressed = false;
            currentImage = normalImage;
        }
        /*if (imageCounter >= currentImage.length) {
            imageCounter = 0;
        }*/

    }

    public void render(Graphics2D g2) {
        g2.setStroke(stroke);
        /* Code that will be used at a later time */
        if (currentImage != null) {
            /* Draw image */
        } else {
            /* If the image is missing */
            if (pressed) {
                g2.setColor(edgeColor);
                g2.fill(area);
                g2.setColor(Color.green);
                g2.draw(area);
                if (!text.isEmpty()) {
                    int width = g2.getFontMetrics().stringWidth(text);
                    g2.drawString(text, (int) (centerX - width / 2), (int) centerY);
                }
            } else if (hover) {
                g2.setColor(baseColor.darker());
                g2.fill(area);
                g2.setColor(baseColor.brighter());
                g2.draw(area);
                if (!text.isEmpty()) {
                    int width = g2.getFontMetrics().stringWidth(text);
                    g2.drawString(text, (int) (centerX - width / 2), (int) centerY);
                }
            } else {
                g2.setColor(baseColor);
                g2.fill(area);
                g2.setColor(edgeColor);
                g2.draw(area);
                if (!text.isEmpty()) {
                    int width = g2.getFontMetrics().stringWidth(text);
                    g2.drawString(text, (int) (centerX - width / 2), (int) centerY);
                }
            }
        }
    }

    public Button() {

    }

    public boolean isHit(double x, double y) {
        return area.contains(x, y);
    }

    public String getText() {
        return text;
    }

}
