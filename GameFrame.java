
package snakegame;

import java.awt.Color;
import javax.swing.JFrame;


public class GameFrame extends JFrame{
public static void main(int n, boolean hardmode) {
    
    
    GameFrame frame = new GameFrame();
    frame.setTitle("SNAKE GAME");
    frame.setBounds(10,10 , 905, 700);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

GamePanel panel =new GamePanel(n,hardmode);
 panel.setBackground(Color.DARK_GRAY);
frame.add(panel);
 panel.setFocusable(true);  
    panel.requestFocus();

} 
}
