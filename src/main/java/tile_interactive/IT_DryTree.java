package tile_interactive;

import Entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DryTree extends InteractiveTile{

    GamePanel gp;

    // Eigenschaften DryTree
    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col , row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/tiles_interactive/drytree", gp.tileSize,gp.tileSize);
        destructible = true;
        life = 3;
    }
    // Richtige Waffe für richtiges Tile ausgerüstet
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if(entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE() {
        //gp.playSE(11);
    }

    // Form nach Zerstörung, IT_Trunk
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }
    // Particle Eigenschaften, wenn Dry Tree wird angegriffen
    public Color getParticleColor() {
        Color color = new Color(65, 50, 30);
        return color;
    }
    public int getParticleSize() {
        int size = 6;   // 6 pixels
        return size;
    }
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
