import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        //add panel
        add(new GamePanel());
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}
