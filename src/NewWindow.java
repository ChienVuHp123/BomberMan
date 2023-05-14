import javax.swing.*;
import java.awt.*;

public class NewWindow {
    JFrame frame = new JFrame(); // tạo ra graphic window
    JLabel label = new JLabel(); // hiện thị văn bản trên window

   public NewWindow() {
        label.setBounds(0,0,100,50); // đạt vị trí và kích thước của label
        label.setFont(new Font(null, Font.PLAIN,25));  // set kiểu Font cho label
        frame.add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //thiết lập hành vi mặc định khi đóng cửa sổ.
        frame.setSize(420, 420); // đăt kích thước của sổ
        frame.setLayout(null);
        frame.setVisible(true); // hiển thị của sổ lên màn hình
    }
}
