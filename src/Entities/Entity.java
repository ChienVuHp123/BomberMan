package Entities;

import graphics.IRender;
import graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity implements IRender {

    SpriteSheet sheet;
    int tileSize = 48, rows = 13, columns = 15;
    BufferedImage view;

    final int SCALE = 1;
    final int WIDTH = (tileSize * SCALE) * columns;
    final int HEIGHT = (tileSize * SCALE) * rows;

    public int size = tileSize * SCALE;
    int _x;

    int _y;


    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }


    @Override
    public abstract void update(int[][] scene);

    @Override
    public abstract void raw(Graphics2D g2);


}
