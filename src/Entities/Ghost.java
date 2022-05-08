package Entities;

import graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ghost extends Entity{

    public int _x;

    public int _y;

    public int player_x;
    public int player_y;
    public int style;


    private boolean alive = true;

    int speedBot = 1;
    public BufferedImage image;

    int frameGhost , intervalGhost , indexGhost ;

    int gmX = 0;
    int gmy = 2;
    boolean GateR = true;
    boolean GateU = false;
    boolean GateL = false;
    boolean GateD = false;


    public int[][] Scene;


    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public Ghost(int i, int j, int style){

        this.style = style;
        sheet = new SpriteSheet();
        image = sheet.Ghost[0];
        this._x = i * (tileSize * SCALE);
        this._y = j * (tileSize * SCALE);
        frameGhost = 0;
        intervalGhost = 30;
        indexGhost = 0;
    }

    public Ghost(){

    }

    public int Random() {
        Random rand = new Random();
        return rand.nextInt(8);
    }

    public void choice() {
        int a = Random();
        if (a == 0 || a == 1) GateR = true;
        else if (a == 2 || a == 3) GateU = true;
        else if (a == 4 || a == 5) GateL = true;
        else if (a == 6 || a == 7) GateD = true;
    }

    public boolean isFreeG(int nextX, int nextY) {
        int size = SCALE * tileSize;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        return !(Scene[nextY_1][nextX_1] == 1 || Scene[nextY_1][nextX_1] == 2||
                Scene[nextY_2][nextX_2] == 1 || Scene[nextY_2][nextX_2] == 2 ||
                Scene[nextY_3][nextX_3] == 1 || Scene[nextY_3][nextX_3] == 2 ||
                Scene[nextY_4][nextX_4] == 1 || Scene[nextY_4][nextX_4] == 2 ||
                Scene[nextY_1][nextX_1] == 3 || Scene[nextY_2][nextX_2] == 3 ||
                Scene[nextY_3][nextX_3] == 3 || Scene[nextY_4][nextX_4] == 3);
    }

public boolean check() {
        int count = 0;

        if (isFreeG(this._x + speedBot, this._y)) {
            count++;
        }
        if (isFreeG(this._x, this._y - speedBot)) {
            count++;
        }
        if (isFreeG(this._x - speedBot, this._y)) {
            count++;
        }
        if (isFreeG(this._x, this._y + speedBot)) {
            count++;
        }

        if (count >= 3) {
            return true;
        } else {
            return false;
        }
    }

    int flag = 0;
    public void MovingBot() {
        if (flag == 0) {
            choice();
            flag++;
        }
        Random random = new Random();
        //System.out.println(GateR + " " + GateU + " " + GateL + " " + GateD);
        if (GateR) {
            if (isFreeG(this._x + speedBot, this._y)) {
                this._x += speedBot;
                if (check()) {
                    int a = random.nextInt(3);
                    if (a == 2) {
                        GateR = false;
                        choice();
                    }
                }
            } else {
                GateR = false;
                choice();
            }
        }
        else if (GateU) {
            if (isFreeG(this._x, this._y - speedBot)) {
                this._y -= speedBot;

                if (check()) {
                    int a = random.nextInt(3);
                    if (a == 2) {
                        GateU = false;
                        choice();
                    }
                }
            } else {
                GateU = false;
                choice();
            }
        }
        else if (GateL) {
            if (isFreeG(this._x - speedBot, this._y)) {
                this._x -= speedBot;
                if (check()) {
                    int a = random.nextInt(3);
                    if (a == 2) {
                        GateL = false;
                        choice();
                    }
                }
            } else {
                GateL = false;
                choice();
            }
        }
        else if (GateD) {
            if (isFreeG(this._x, this._y + speedBot)) {
                this._y += speedBot;
                if (check()) {
                    int a = random.nextInt(3);
                    if (a == 2) {
                        GateD = false;
                        choice();
                    }
                }
            } else {
                GateD = false;
                choice();
            }
        }
    }

    public void MovingBot2() {
        //System.out.println(GateR + " " + GateU + " " + GateL + " " + GateD);

        if (GateR) {
            if (isFreeG(this._x + speedBot, this._y)) {
                if (this._x < player_x) {
                    this._x += speedBot;
                } else {
                    GateR = false;
                    GateU = true;
                }
            } else {
                GateR = false;
                GateU = true;
            }
        } else if(GateU) {
            if (isFreeG(this._x, this._y - speedBot)) {
                if (this._y > player_y) {
                    this._y -= speedBot;
                } else {
                    GateU = false;
                    GateL = true;
                }
            } else {
                GateU = false;
                GateL = true;
            }
        } else if (GateL) {
            if (isFreeG(this._x - speedBot, this._y)) {
                if (this._x > player_x) {
                    this._x -= speedBot;
                } else {
                    GateL = false;
                    GateD = true;
                }
            } else {
                GateL = false;
                GateD = true;
            }
        } else if (GateD) {
            if (isFreeG(this._x, this._y + speedBot)) {
                if (this._y < player_y) {
                    this._y += speedBot;
                } else {
                    GateD = false;
                    GateR = true;
                }
            } else {
                GateD = false;
                GateR = true;
            }
        }
    }

    @Override
    public void update(int[][] scene) {

        Scene = scene;
        if (style == 1){
            MovingBot();
        } else {
            MovingBot2();
        }


        if (frameGhost < intervalGhost) {
            indexGhost = (int) frameGhost / 5;
            frameGhost++;
        } else {
            frameGhost = 0;
            indexGhost = 0;
        }
        if (style == 1){
            image = sheet.Ghost[indexGhost];
        } else if (style == 2){
            image = sheet.Undead[indexGhost];
        }
    }

    @Override
    public void raw(Graphics2D g2) {
        g2.drawImage(image, this._x, this._y, size, size, null);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
