package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class Explosion {
    GamePanel gamePanel;
    ArrayList<Particle> particles = new ArrayList<>();

    public Explosion(GamePanel gamePanel, double x, double y, int particleAmount, int particleSize, Color color) {
        this.gamePanel = gamePanel;
        for (int i = 0; i < particleAmount; i++) {
            /* Continue */
        }
    }
}
