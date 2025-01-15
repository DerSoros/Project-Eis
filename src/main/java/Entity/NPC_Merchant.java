package Entity;

import main.GamePanel;
import object.*;

public class NPC_Merchant extends Entity {

        // NPC Eigenschaften und Hitbox
        public NPC_Merchant(GamePanel gp) {
            super(gp);

            // Spawnrichtung und Laufgeschwindigkeit
            direction = "down";
            speed = 1;

            // Hitbox
            solidArea.x = 15;
            solidArea.y = 21;
            solidArea.width = 25;
            solidArea.height = 30;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;

            getImage();         // Aussehen
            setDialogue();      // Dialog
            setItems();         // Items im Inventar
        }

        // Asset Pfad Merchant NPC
        public void getImage() {

            up1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
            up2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
            down1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
            left2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
            right1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
            right2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        }

        // Merchant Dialog
        public void setDialogue() {

            dialogues[0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";

        }
        // Merchant Items in Inventory
        public void setItems() {

            inventory.add(new OBJ_Potion_Red(gp));
            inventory.add(new OBJ_Key(gp));
            inventory.add(new OBJ_Sword_Normal(gp));
            inventory.add(new OBJ_Axe(gp));
            inventory.add(new OBJ_Shield_Wood(gp));
            inventory.add(new OBJ_Shield_Blue(gp));


        }
        // wechsle zu trade state
        public void speak() {

            super.speak();
            gp.gameState = gp.tradeState;
            gp.ui.npc = this;

        }
    }
