package graphics;

import java.awt.*;

public interface IRender {
    public void update(int[][] scene);
    public void raw(Graphics2D g2);
}
