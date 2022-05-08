package Entities;

import Audio.Audio;
import graphics.SpriteSheet;
import input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    int size = SCALE * tileSize;
    public int _x;
    public int _y;
    public int speed = 3;
    Audio audio = new Audio();

    int framePlayer , intervalPlayer , indexAnimPlayer;
    public  BufferedImage image;
    int[][] Scene;

    public Input input = new Input();
    public boolean moving;


    public Player(){
        sheet = new SpriteSheet();
        this._x = (tileSize * SCALE);
        this._y = (tileSize * SCALE);
        framePlayer = 0;
        intervalPlayer = 5;
        indexAnimPlayer = 0;
    }

    public boolean isFree(int nextX, int nextY) {
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
                Scene[nextY_4][nextX_4] == 1 || Scene[nextY_4][nextX_4] == 2 );
    }



    @Override
    public void update(int[][] scene) {
        moving = false;
        Scene = scene;


        if (input.right && isFree(this._x + speed, this._y)) {
            this._x += speed;
            moving = true;
        }
        if (input.left && isFree(this._x - speed, this._y)) {
            this._x -= speed;
            moving = true;
        }
        if (input.up && isFree(this._x, this._y - speed)) {
            this._y -= speed;
            moving = true;
        }
        if (input.down && isFree(this._x, this._y + speed)) {
            this._y += speed;
            moving = true;
        }


        if (moving) {
            framePlayer++;
            if (framePlayer > intervalPlayer) {
                framePlayer = 0;
                indexAnimPlayer++;

                if (indexAnimPlayer > 2) {
                    indexAnimPlayer = 0;
                }
            }
            audio.playSound("res/res_sounds_walk.wav", 0);
            if (input.right) {
                image = sheet.playerAnimRight[indexAnimPlayer];
            } else if (input.left) {
                image = sheet.playerAnimLeft[indexAnimPlayer];
            } else if (input.up) {
                image = sheet.playerAnimUp[indexAnimPlayer];
            } else if (input.down) {
                image = sheet.playerAnimDown[indexAnimPlayer];
            }
        } else {
            image = sheet.playerAnimDown[1];
        }

    }


    @Override
    public void raw(Graphics2D g2) {
        int size = tileSize * SCALE;
        g2.drawImage(image, this._x,this._y, size, size, null);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }



}

