package Entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_OldMan extends Entity {

    // NPC Eigenschaften und Hitbox
    public NPC_OldMan(GamePanel gp) {
        super(gp);

        // Spawnrichtung und Laufgeschwindigkeit
        direction = "down";
        speed = 1;

        // Hitbox
        solidArea = new Rectangle();
        solidArea.x = 10;
        solidArea.y = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;

        getImage();        // Aussehen
        setDialogue();     // Dialog
    }
    // Asset Pfad NPC
    public void getImage() {

        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }
    // NPC Dialog
    public void setDialogue() {

        // bis dialogues[20] erweiterbar
       dialogues[0] = "Hi!";
        /* dialogues[1] =
        dialogues[2] =
        dialogues[3] =

         */


    }
    // NPC Movement (Random)
    public void setAction() {

            actionLockCounter++;

            if (actionLockCounter == 120) {

                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if (i <= 25) { direction = "up"; }
                if (i > 25 && i <= 50) { direction = "down"; }
                if (i > 50 && i <= 75) { direction = "left"; }
                if (i > 75 && i <= 100) { direction = "right"; }
                actionLockCounter = 0;
            }

    }
    public void speak() {

        super.speak();

    }
}
