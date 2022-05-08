import Audio.Audio;
import Entities.Ghost;
import Entities.Player;
import graphics.Item;
import graphics.SpriteSheet;
import input.Input;
import level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BomberMan extends JPanel implements Runnable, KeyListener {
    boolean isRunning;
    boolean newGame = true;
    Level level = new Level();
    int[][] scene = level.level1;
    BufferedImage view, view1;
    Thread thread;

    int level_game = 1;
    public Audio audio = new Audio();

    int tileSize = 48, rows = 13, columns = 15;
    int frameExplosion = 0, intervalExplosion = 3, indexAnimExplosion = 0;
    int frameBomb = 0, intervalBomb = 7, indexAnimBomb = 0;
    int frameConcreteExploding = 0, intevalConcreteExploding = 4, indexConcreteExploding = 0;
    boolean concreteAnim = false;
    Input input = new Input();
    int bombX, bombY;
    boolean window_check;



    SpriteSheet spriteSheet = new SpriteSheet();




    final int SCALE = 1;
    final int WIDTH = (tileSize * SCALE) * columns;
    final int HEIGHT = (tileSize * SCALE) * rows;
    int size = tileSize * SCALE;


    Player player = new Player();


    int numGhost;
    int Enemi = 0;
    int num_item_speed;
    int dead ;

    List<Ghost> ghost = new ArrayList<Ghost>();
    List<Boolean> check = new ArrayList<Boolean>();


    Item item_speed = new Item(1,  3, spriteSheet.itemSpeed);
    Item item_shield = new Item(3, 1, spriteSheet.itemShield);
    int window_x = 9, window_y = 1;

    Bomb bomb;



    public BomberMan() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
    }


    static JFrame jFrame;
    static JLabel background;
    static ImageIcon img;
    static JButton myButton;
    static JButton achievementButton;
    static JFrame  w = new JFrame("Bomberman");

    public static void main(String[] args) {

        //button
        myButton = new JButton("start");
        myButton.setBounds(100, 300,300,40);
        myButton.setFocusable(false);
        myButton.setBackground(new Color(255,255,255,100));


        achievementButton = new JButton();
        achievementButton.setBounds(0,0,300,40);
        achievementButton.setFocusable(false);
        achievementButton.setBackground(new Color(0,0,0,0));

        ImageIcon img = new ImageIcon("D:\\oop\\boomwithonefile\\res\\klee-genshin-impact.gif");
        //img = new ImageIcon("C:\\Users\\Admin\\IdeaProjects\\boomwithonefile-main\\res\\start or continue.gif");
        background = new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,420,420);

        //JFrame
        jFrame = new JFrame("BomberMan.Version1");
        //jFrame.setIconImage(new ImageIcon("C:\\Users\\Admin\\Desktop\\klee.jpg").getImage());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500, 450);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        //add
        jFrame.add(myButton);
        jFrame.add(background);
        //jFrame.add(achievementButton);

        achievementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
               //NewWindow newWindow = new NewWindow();
            }
        });

        //Event
        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==myButton) {
                    jFrame.dispose();


                    w.setResizable(false);
                    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    w.add(new BomberMan());
                    w.pack();
                    w.setLocationRelativeTo(null);
                    w.setVisible(true);

                    MenuBar menuBar = new MenuBar();

                    w.setJMenuBar(menuBar.getMenuBar());
                }
            }
        });

    }


    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            isRunning = true;
            thread.start();
        }
    }


    int flag = 0;
    public void start() {
        view = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        item_speed = new Item(1,  3, spriteSheet.itemSpeed);
        item_shield = new Item(3, 1, spriteSheet.itemShield);
        for (Ghost g :ghost){
            g.setAlive(true);
        }
        dead = 1;
        numGhost = 2;
        level = new Level();
        scene = level.level1;
        Enemi = 0;
        ghost.clear();
        ghost.add(new Ghost(6, 1, 1));
        check.add(true);
        ghost.add(new Ghost(1,5, 1));
        check.add(true);
        player._x = tileSize * SCALE;
        player._y = tileSize * SCALE;
        window_check = false;
        num_item_speed = 1;
        item_speed.alive = true;
        item_shield.alive = true;
        audio.stop = true;
        if (flag == 0) {
            audio.playSound("res/Klee.wav", -1);
            flag++;
        }
    }
    public void start2() {
        view = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        item_speed = new Item(11,  1, spriteSheet.itemSpeed);
        item_shield = new Item(10, 1, spriteSheet.itemShield);
        dead = 1;

        for (Ghost g :ghost){
            g.setAlive(true);
        }

        numGhost = 3;
        Enemi = 0;
        level = new Level();
        scene = level.level2;
        ghost.clear();
        ghost.add(new Ghost(6, 1, 1));
        check.add(true);
        ghost.add(new Ghost(1,5, 1));
        check.add(true);
        ghost.add(new Ghost(13, 10, 2));
        check.add(true);

        player._x = tileSize * SCALE;
        player._y = tileSize * SCALE;
        window_check = false;
        num_item_speed = 1;
        item_speed.alive = true;
        item_shield.alive = true;
        audio.stop = true;
        if (flag == 0) {
            audio.playSound("res/Klee.wav", -1);
            flag++;
        }
    }


    public void update(){


        if (dead > 0) {
            player.update(scene);
        }



        int pX, pY;
        int gX[] = new int[numGhost];
        int gY[] = new int[numGhost];
        pX = (int)(player._x / size);
        pY = (int)(player._y / size);
        for (int i = 0 ; i < numGhost ; i ++){
            if (ghost.get(i).isAlive()){
                ghost.get(i).player_x = player._x;
                ghost.get(i).player_y = player._y;
                System.out.println(dead + " " + i);
                gX[i] = (int)(ghost.get(i)._x  / size);
                gY[i] = (int)(ghost.get(i)._y / size);

                if (pX == gX[i] & pY == gY[i] & check.get(i) ) {
                    dead --;
                    check.add(i, false);
                }

                if ((Math.abs(gX[i] - pX) > 1 || Math.abs(gY[i] - pY) > 1) & !check.get(i)) {
                    check.add(i, true);
                }
                ghost.get(i).update(scene);
            }


        }


        if (pX == window_x & pY == window_y) {
            window_check = true;
        }

        if (pX == item_speed._x & pY == item_speed._y & item_speed.alive){
            item_speed.alive = false;
            player.speed = 4;
        }

        if (pX == item_shield._x & pY == item_shield._y & item_shield.alive) {
            item_shield.alive = false;
            dead ++;
        }
        if (bomb != null) {

            frameBomb++;
            if (frameBomb == intervalBomb) {
                frameBomb = 0;
                indexAnimBomb++;
                if (indexAnimBomb > 2) {
                    indexAnimBomb = 0;
                    bomb.countToExplode++;
                }
                if (bomb.countToExplode >= bomb.intervalToExplode) {
                    concreteAnim = true;
                    bombX = bomb.x;
                    bombY = bomb.y;
                    bomb.exploded = true;
                    if (scene[bomb.y + 1][bomb.x] == 2) {
                        scene[bomb.y + 1][bomb.x] = -1;
                    }
                    if (scene[bomb.y - 1][bomb.x] == 2) {
                        scene[bomb.y - 1][bomb.x] = -1;
                    }
                    if (scene[bomb.y][bomb.x + 1] == 2) {
                        scene[bomb.y][bomb.x + 1] = -1;
                    }
                    if (scene[bomb.y][bomb.x - 1] == 2) {
                        scene[bomb.y][bomb.x - 1] = -1;
                    }
                }
            }

            if(bomb.exploded) {
                frameExplosion++;
                if (frameExplosion == intervalExplosion) {
                    frameExplosion = 0;
                    indexAnimExplosion++;
                    if (indexAnimExplosion == 4) {
                        indexAnimExplosion = 0;
                        audio.playSound("res/res_sounds_bomb.wav",0);
                        scene[bomb.y][bomb.x] = 0;
                        bomb = null;
                        for (int i = 0 ; i < numGhost ; i ++){
                            if (ghost.get(i).isAlive()){
                                if ((Math.abs(gX[i] - bombX) < 2 & gY[i] == bombY) || (Math.abs(gY[i] - bombY) < 2 & gX[i] == bombX) ) {
                                    Enemi ++;
                                    ghost.get(i).setAlive(false);
                                }
                            }
                        }

                        if (((Math.abs(pX - bombX) < 2 & pY == bombY)
                                || (Math.abs(pY - bombY) < 2 & pX == bombX)) & !item_shield.avail) {
                            dead --;

                        }

                    }
                }
            }
        }

        if (concreteAnim) {
            frameConcreteExploding++;
            if (frameConcreteExploding == intevalConcreteExploding) {
                frameConcreteExploding = 0;
                indexConcreteExploding++;
                if (indexConcreteExploding == 5) {
                    indexConcreteExploding = 0;
                    if (scene[bombY + 1][bombX] == -1) {
                        scene[bombY + 1][bombX] = 0;
                    }
                    if (scene[bombY - 1][bombX] == -1) {
                        scene[bombY - 1][bombX] = 0;
                    }
                    if (scene[bombY][bombX + 1] == -1) {
                        scene[bombY][bombX + 1] = 0;
                    }
                    if (scene[bombY][bombX - 1] == -1) {
                        scene[bombY][bombX - 1] = 0;
                    }
                    concreteAnim = false;
                }

            }
        }



    }

    public void draw() {
        Graphics2D g2 = (Graphics2D) view.getGraphics();
        g2.setColor(new Color(56, 135, 0));
        g2.fillRect(0, 0, WIDTH, HEIGHT);



        int size = tileSize * SCALE;
        if (item_speed.alive == true) {
            item_speed.raw(g2);
        }

        if (item_shield.alive){
            item_shield.raw(g2);
        }
        g2.drawImage(spriteSheet.window, window_x * size, window_y * size, size, size, null);
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (scene[j][i] == 1) {
                    g2.drawImage(spriteSheet.blockTile, i * size, j * size, size, size, null);
                } else if (scene[j][i] == 2) {
                    g2.drawImage(spriteSheet.concreteTile, i * size, j * size, size, size, null);
                }else if (scene[j][i] == 3) {
                    if (bomb != null) {
                        if (bomb.exploded) {
                            g2.drawImage(spriteSheet.fontExplosion[indexAnimExplosion], bomb.x * size, bomb.y * size, size, size, null);
                            if (scene[bomb.y][bomb.x + 1] == 0) {
                                g2.drawImage(spriteSheet.rightExplosion[indexAnimExplosion], (bomb.x + 1) * size, bomb.y * size, size, size, null);
                            }
                            if (scene[bomb.y][bomb.x - 1] == 0) {
                                g2.drawImage(spriteSheet.leftExplosion[indexAnimExplosion], (bomb.x - 1) * size, bomb.y * size, size, size, null);
                            }
                            if (scene[bomb.y - 1][bomb.x] == 0) {
                                g2.drawImage(spriteSheet.upExplosion[indexAnimExplosion], bomb.x * size, (bomb.y - 1) * size, size, size, null);
                            }
                            if (scene[bomb.y + 1][bomb.x] == 0) {
                                g2.drawImage(spriteSheet.downExplosion[indexAnimExplosion], bomb.x * size, (bomb.y + 1) * size, size, size, null);
                            }
                        } else {
                            g2.drawImage(spriteSheet.bombAnim[indexAnimBomb], i * size, j * size, size, size, null);
                        }
                    }
                }  else if (scene[j][i] == -1) {
                    g2.drawImage(spriteSheet.concreteExploding[indexConcreteExploding], i * size, j * size, size, size, null);
                }
            }
        }

        Graphics g_item = getGraphics();


        for (int i = 0 ; i < numGhost ; i ++){
            if (ghost.get(i).isAlive()){
                ghost.get(i).raw(g2);
            }
        }


        player.raw(g2);




        if (dead > 1) {
            g2.drawImage(spriteSheet.itemShield, 1  , 1 , size , size ,null);
        }



        Graphics g = getGraphics();
        g.drawImage(view, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
    }


    @Override
    public void run() {
        try {
            int count  = 0;
            requestFocus();
            start();



            while (isRunning) {
                update();
                draw();
                Thread.sleep(1000 / 60);

                if (dead == 0 ) {
                    player.image = spriteSheet.playerDead[count];
                    count ++;
                    if (count == 6){
                        count = 0;
                    }
                    audio.playSound("res/kleeVoice..wav",0);
                    ImageIcon img = new ImageIcon("res/klee4.jpg");
                    int answer = JOptionPane.showConfirmDialog(null,"oh no, you died, do you want to restart ?"
                            ,"Onii chan",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, img);
                    if (answer == 0) {
                        start();

                    } else {
                        w.dispose();

                        jFrame.setVisible(true);
                    }

                } else if (numGhost == Enemi & window_check) {

                        start2();


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            Random random = new Random();
            int a = random.nextInt(3);

            if (a == 0) {
                //audio.playSound("res/kleeVoice..wav",0);
                audio.playSound("res/klee-zenbu-dokaaa.wav", 0);
            } else if (a == 1){
                audio.playSound("res/klee-bomb-bomb-bakudan.wav", 0);
            } else {
                audio.playSound("res/klee-are.wav", 0);
            }
            if (bomb == null) {
                bomb = new Bomb();
                bomb.x = (player._x + ((SCALE * tileSize) / 2)) / (SCALE * tileSize);
                bomb.y = (player._y + ((SCALE * tileSize) / 2)) / (SCALE * tileSize);
                scene[bomb.y][bomb.x] = 3;
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            input.right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            input.left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            input.up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            input.down = true;
        }
        player.input = input;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            input.right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            input.left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            input.up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            input.down = false;
        }
        player.input = input;
    }
}