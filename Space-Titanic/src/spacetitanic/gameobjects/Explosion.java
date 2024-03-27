package spacetitanic.gameobjects;

import spacetitanic.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class Explosion implements GraphicEffects {
    private GamePanel gamePanel;
    private ArrayList<Particle> particles = new ArrayList<>();
    private boolean dead = false;

    public Explosion(GamePanel gamePanel, double x, double y, int particleAmount, int maxParticleSize, Color color) {
        this.gamePanel = gamePanel;
        for (int i = 0; i < particleAmount; i++) {
            float pSize = (float) (Math.random() * maxParticleSize + 1.0);
            Particle p = new Particle(gamePanel, x, y, pSize, color);
            particles.add(p);
        }
        Particle p = new Particle(gamePanel, x, y, color);
        particles.add(p);
    }

    @Override
    public void update() {
        int deadParticles = 0;
        for (Particle p : particles) {
            if (!p.isDead()) {
                p.update();
            } else {
                deadParticles++;
            }
        }
        if (deadParticles > particles.size() * 0.95) {
            dead = true;
        }
    }

    @Override
    public void render(Graphics2D g2) {
        for (Particle p : particles) {
            if (!p.isDead()) {
                p.render(g2);
            }

        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }
}
