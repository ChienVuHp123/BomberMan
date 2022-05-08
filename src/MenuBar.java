import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import sun.applet.Main;


public class MenuBar extends JPanel implements ActionListener {

    private JMenuBar menubar;

    public boolean newGame = true;



    public MenuBar () {

        menubar = new JMenuBar();

        JMenu new_game = new JMenu("New Game");
        //JMenu load_game = new JMenu("Load");
        //JMenu save_game = new JMenu("Save");
        JMenu exit_game = new JMenu("Exit");
        JMenu help = new JMenu("Help");

        //help
        help.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                //System.out.println("hi");
            }
        });

        //new game
        new_game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                newGame = false;
            }
        });

        //exit game
        exit_game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        menubar.add(new_game);
        menubar.add(help);
        menubar.add(exit_game);


    }

    public JMenuBar getMenuBar() {
        return menubar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}