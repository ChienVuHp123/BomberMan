package graphics;

import Entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item extends Entity {

    public int _x;

    public int _y;

    public BufferedImage image;

    public boolean avail = false;

    @Override
    public void set_x(int _x) {
        this._x = _x;
    }

    @Override
    public void set_y(int _y) {
        this._y = _y;
    }

    public boolean alive = true;


    @Override
    public void update(int[][] scene) {

    }

    public Item(int x, int y, BufferedImage image) {
        this._x = x;
        this._y = y;
        this.image = image;
    }

    @Override
    public void raw(Graphics2D g2) {
        g2.drawImage(image, this._x * size, this._y * size, size, size, null);
    }
}
