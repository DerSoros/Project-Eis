package main;

import Entity.Entity;
import Entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // Fenster Einstellungen
    final int originalTileSize = 16;    // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;   // 48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    // Welt Einstellungen
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    Thread gameThread;

    // FPS
    int FPS = 60;

    // Entities und Objekte
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // Unterschiedliche Game States
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Start des Spiels
    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        //playMusic(0);
        //stopMusic();
        gameState = titleState;
    }
    // Bei Load Game/retry
    public void retry() {

        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();

    }
    // Bei restart/new game
    public void restart() {

        player.setDefaultValues();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
/*
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime =0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
*/
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                //System.out.println("FPS:"+drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
    public void update() {

        if (gameState == playState) {
            // PLAYER
            player.update();
            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            // MONSTER
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    // Wenn Monster alive, update
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    // Wenn monster tot, Item drop und monster entfernen
                    if(monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            // PROJECTILE
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    // Wenn projectile alive, update
                    if(projectileList.get(i).alive == true) {
                        projectileList.get(i).update();
                    }
                    // Wenn projectile tot, remove
                    if(projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }
            // PARTICLE
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    // Wenn particle alive, update
                    if(particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    // Wenn particle tot, remove
                    if(particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }
        if (gameState == pauseState) {

        }

    }
    // Paint-Methode
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }

        // Titelbildschirm
        if (gameState == titleState) {
            ui.draw(g2);
        }

        // Anderes
        else {
            // Tile
            tileM.draw(g2);

            // Interactive Tile
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // Entities zür Liste hinzufügen
            entityList.add(player);

            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for(int i = 0; i < obj[1].length; i++) {
                if(obj[currentMap][i] != null) {
                   entityList.add(obj[currentMap][i]);
                }
            }
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // Paint Entities
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // Clear Entity List
           entityList.clear();

            // Paint UI
            ui.draw(g2);
        }

        // DEBUG
        if (keyH.showDebugText == true) {

            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;

            g2.drawString("Draw Time: " + passed, x, y);

        }
        g2.dispose();
    }
    /*public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }*/
}
