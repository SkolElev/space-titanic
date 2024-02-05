package spacetitanic;

import spacetitanic.gameobjects.GameObject;

public class Camera {
    GamePanel gamePanel;
    GameObject target;

    private float xOffset, yOffset;

    public Camera(GamePanel gamePanel, GameObject target) {
        this.gamePanel = gamePanel;
        this.target = target;
    }

    public void update() {
        /* temporary values */
        float targetX = (float) (target.getX() - gamePanel.screenWidth / 2.0f);
        float targetY = (float) (target.getY() - gamePanel.screenHeight / 2.0f);
        xOffset = targetX;
        yOffset = targetY;
        /* temporary code */
        /*xOffset = xOffset - (xOffset - targetX) * 0.05f;
        yOffset = yOffset - (yOffset - targetY) * 0.05f;*/

        /* If the camera stops at the world's edge */
        if (xOffset < 0) {
            xOffset = 0;
        }
        if (xOffset + gamePanel.screenWidth > gamePanel.worldWidth) {
            xOffset = gamePanel.worldWidth - gamePanel.screenWidth;
        }
        if (yOffset < 0) {
            yOffset = 0;
        }
        if (yOffset + gamePanel.screenHeight > gamePanel.worldHeight) {
            yOffset = gamePanel.worldHeight - gamePanel.screenHeight;
        }
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
