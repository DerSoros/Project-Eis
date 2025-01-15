package Entity;

import java.awt.*;

import main.GamePanel;

public class Particle extends Entity {

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    // Eigenschaften Particle
    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);

        this.generator = generator;
        this.color = color;         // welche farbe es hat
        this.size = size;           // wie groß es ist
        this.speed = speed;         // wie schnell es fliegt
        this.maxLife = maxLife;     // wie lange es sichtbar bleibt
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (gp.tileSize/2) - (size/2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    // Particle update
    public void update() {

        life--;

        if(life < maxLife/3) {
            yd++;
        }

        worldX += xd*speed;
        worldY += yd*speed;

        // wenn life == 0, particle verschwindet
        if(life == 0) {
            alive = false;
        }
    }
    // draw particle
    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);

    }
}
