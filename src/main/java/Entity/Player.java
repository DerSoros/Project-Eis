package Entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Solid Area = Hitbox
        solidArea = new Rectangle();
        solidArea.x = 10;
        solidArea.y = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;

        setDefaultValues();         // Player Stats bei neuem Spiel
        getPlayerImage();           // Player Aussehen
        getPlayerAttackImage();     // Player Angriff
        setItems();                 // Player Inventar
    }
    // Player Default Stats
    public void setDefaultValues() {

        worldX = gp.tileSize * 12;
        worldY = gp.tileSize * 9;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        //ammo = 10;
        strength = 1; // mehr strength = mehr damage
        dexterity = 1; // mehr dexterity = player bekommt weniger damage
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        //projectile = new OBJ_Rock(gp);
        attack = getAttack(); // gesamt attackValue => strength + waffe (z.B. Schwert)
        defense = getDefense(); // gesamt defense => dexterity + Schild
    }
    // Player Spawn position
    public void setDefaultPositions() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";


    }
    // Mana und Leben auf max wiederherstellen
    public void restoreLifeAndMana() {

        life = maxLife;
        mana = maxMana;
        invincible = false;
    }
    // Items zu Spielstart (New Game) in Inventar hinzufügen
    public void setItems() {

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));

    }
    // Berechnung für AttackDamage
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    // Berechnung für Defense
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }
    // Asset Pfad für Player
    public void getPlayerImage() {

        up1 = setup("/player/player_walk_up01", gp.tileSize, gp.tileSize);
        up2 = setup("/player/player_walk_up02", gp.tileSize, gp.tileSize);
        down1 = setup("/player/player_walk_down01", gp.tileSize, gp.tileSize);
        down2 = setup("/player/player_walk_down02", gp.tileSize, gp.tileSize);
        left1 = setup("/player/player_walk_left_idle", gp.tileSize, gp.tileSize);
        left2 = setup("/player/player_walk_left02", gp.tileSize, gp.tileSize);
        right1 = setup("/player/player_walk_right_idle", gp.tileSize, gp.tileSize);
        right2 = setup("/player/player_walk_right01", gp.tileSize, gp.tileSize);
        title1 = setup("/player/title_screen1", gp.tileSize, gp.tileSize); // Titelbildschirm logo
    }
    // Asset Pfad für Player Attack
    public void getPlayerAttackImage() {

        // Schwert
        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/player_attack_up01", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/player_attack_up02", gp.tileSize, gp.tileSize*2);
            attackUp3 = setup("/player/player_attack_up03", gp.tileSize, gp.tileSize*2);
            //attackUp4 = setup("/player/player_attack_up04", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/player_attack_down01", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/player_attack_down02", gp.tileSize, gp.tileSize*2);
            attackDown3 = setup("/player/player_attack_down03", gp.tileSize, gp.tileSize*2);
            //attackDown4 = setup("/player/player_attack_down04", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/player_attack_left01", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/player_attack_left02", gp.tileSize*2, gp.tileSize);
            attackLeft3 = setup("/player/player_attack_left03", gp.tileSize*2, gp.tileSize);
            //attackLeft4 = setup("/player/player_attack_left04", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/player_attack_right01", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/player_attack_right02", gp.tileSize*2, gp.tileSize);
            attackRight3 = setup("/player/player_attack_right03", gp.tileSize*2, gp.tileSize);
            //attackRight4 = setup("/player/player_attack_right04", gp.tileSize*2, gp.tileSize);
        }
        // Axt
        if(currentWeapon.type == type_axe) {
            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
        }


    }
    public void update() {

        if(attacking == true) {
            attacking();
        }
        else if (keyH.upPressed == true|| keyH.downPressed == true ||
                keyH.leftPressed == true|| keyH.rightPressed == true || keyH.enterPressed == true) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Check player collision mit tile
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check player collision mit objekt
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // Check player collision mit objekt
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Check player collision mit monster
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // Check player collision mit interactive tile
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);


            // Check Event
            gp.eHandler.checkEvent();

            // Wenn Collision false, player kann sich bewegen
            if (collisionOn == false && keyH.enterPressed == false) {

                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // wenn attack taste gedrückt und attackcanceled == false, player greift an
            if(keyH.enterPressed == true && attackCanceled == false) {
                //gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            keyH.enterPressed = false;

            // Counter für attack animation
            spriteCounter++;
            if (spriteCounter > 13) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        // if-bedingung für projectile schießen
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false
                && shotAvailableCounter == 30 && projectile.haveResource(this) == true) {

            // set default koordinaten, richtung und user
            projectile.set(worldX, worldY, direction, true, this);

            // kosten abziehen (Mana, ammo etc.)
            projectile.subtractResource(this);

            // Zur Liste hinzufügen
            gp.projectileList.add(projectile);

            // shot counter auf 0
            shotAvailableCounter = 0;

            //gp.playSE(10);
        }

        // Außerhalb von key if-bedingung:
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        // shot delay, schießen erst möglich wenn counter == 30
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        // life begrenzung, nicht über max life möglich
        if(life > maxLife) {
            life = maxLife;
        }
        // mana begrenzung, nicht über max mana möglich
        if(mana > maxMana) {
            mana = maxMana;
        }
        // Wenn leben <= 0, dann gameOverState
        if(life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            //gp.stopMusic();
            //gp.playSE(12);
        }
    }
    public void attacking() {

        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 3;

            // speichern von momentanen worldX, worldY and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // anpassen von player world X/Y für attackArea
            switch(direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // attackArea wird solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // check monster collision mit angepasstem worldX, worldY and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            // check interactive tile collision (für zerstörbare umgebung)
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            // nach collision check, return zu original state
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;


        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    // Pick up Items (Monster Drops etc.)
    public void pickUpObject(int i) {

        if (i != 999) {

            // Monster drops einsammeln
            if(gp.obj[gp.currentMap][i].type == type_pickUpOnly) {

                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            // aufgehobenes Item dem Inventar hinzufügen
            else{
                String text;

                if(inventory.size() != maxinventorySize) {

                    inventory.add(gp.obj[gp.currentMap][i]);
                    //gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                }
                else {
                    text = "You cannot carry more Items";
                }
                gp.ui.addmessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
            }


    }
    // interaktion mit NPC
    public void interactNPC(int i) {

        if(gp.keyH.enterPressed == true) {
            if (i != 999) {
                    attackCanceled = true;                  // Player kann NPC nicht angreifen (angriff und mit npc reden taste ist die gleiche)
                    gp.gameState = gp.dialogueState;
                    gp.npc[gp.currentMap][i].speak();
            }
        }
    }
    // Kontakt mit Monster, wenn gegen Monster gelaufen, get damage
    public void contactMonster(int i) {

        if(i != 999) {

            if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
                //gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }
    // damage an Monster und Text feedback
    public void damageMonster(int i, int attack) {

        if(i != 999) {

            if(gp.monster[gp.currentMap][i].invincible == false) {

                //gp.playSE(5);

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0) {
                    damage = 0;
                }
                // Text nachricht für Schaden an monster
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addmessage(damage+ " damage!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                // wenn monster getötet
                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addmessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addmessage("Exp " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    // damage an Interactive Tiles (zerstörbare Umgebung)
    public void damageInteractiveTile(int i) {

        if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true
                && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {

            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            // Generate Particle
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            // wenn interactive tile zerstört, zeig zerstörte form (baumstumpf nach baumfällen)
            if(gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }
    // check für Level up nach Monster tötung
    public void checkLevelUp() {

        if(exp >= nextLevelExp) {

            // wenn level up erhöhe stats
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();       // neue attack werte nach level up
            defense = getDefense();     // neue defense werte nach level up

            //gp.playSE(8); // level up Soundeffekt
            // level up dialog fenster
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!\n"
                    + "You feel stronger!";
        }
    }
    // Select Inventory Item, waffen wechsel
    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if(itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            // berechnung von attack und wählen von assets, bei waffen änderung
            if(selectedItem.type == type_sword || selectedItem.type == type_axe) {

                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            // schild ändern
            if(selectedItem.type == type_shield) {

                currentShield = selectedItem;
                defense = getDefense();
            }
            // consumables nutzen und aus inventar entfernen
            if(selectedItem.type == type_consumable) {

                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // draw player bewegung und angriff animation
        switch (direction) {
            case "up":
                // bewegung
                if(attacking == false) {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                    if (spriteNum == 3) {image = up1;}
                    if (spriteNum == 4) {image = up2;}
                }
                // angriff
                if(attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                    if (spriteNum == 3) {image = attackUp3;}
                    if (spriteNum == 4) {image = attackUp4;}
                }
                break;
            case "down":
                if(attacking == false) {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                    if (spriteNum == 3) {image = down1;}
                    if (spriteNum == 4) {image = down2;}
                }
                if(attacking == true) {
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                    if (spriteNum == 3) {image = attackDown3;}
                    if (spriteNum == 4) {image = attackDown4;}
                }
                break;
            case "left":
                if(attacking == false) {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                    if (spriteNum == 3) {image = left1;}
                    if (spriteNum == 4) {image = left2;}
                }
                if(attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                    if (spriteNum == 3) {image = attackLeft3;}
                    if (spriteNum == 4) {image = attackLeft4;}
                    
                }
                break;
            case "right":
                if(attacking == false) {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                    if (spriteNum == 3) {image = right1;}
                    if (spriteNum == 4) {image = right2;}
                }
                if(attacking == true) {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                    if (spriteNum == 3) {image = attackRight3;}
                    if (spriteNum == 4) {image = attackRight4;}
                }
                break;
        }
        // invincible animation wenn damage bekommen
        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));

        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG INVINCIBLE TIME
        //g2.setFont(new Font("Arial", Font.PLAIN, 26));
        //g2.setColor(Color.white);
        //g2.drawString("Invincible: "+invincibleCounter, 10, 400);

    }
}

