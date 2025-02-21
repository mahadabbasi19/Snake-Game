
package snakegame;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;


public class GamePanel extends JPanel implements ActionListener , KeyListener{
    
    private int[] snakexlength=new int[750];
    private int[] snakeylength=new int[750];
    private int lengthofsnake=3;
    
    
    private boolean left=false;
    private boolean right=true;
    private boolean down=false;
    private boolean up=false;
    
    private int moves =0;
    private int score=0;
    private int HighestScore;
    private boolean GameOver=false;
    
    private ImageIcon snaketitle=new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth=new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth=new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth=new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth=new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage=new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy=new ImageIcon(getClass().getResource("enemy.png"));
   


    
    private Timer timer;
    public int delay;
    
    private int enemyX,enemyY;
    private Random random= new Random();
    private boolean Hardmode;
    
    private int[] xPos = {
    25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400,
    425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800,
    825, 850
                         };
    
   private int[] yPos = {
    75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450,
    475, 500, 525, 550, 575, 600, 625
                        };


    
    GamePanel(int n , boolean hardmode){
            delay=n;
            Hardmode=hardmode;
            addKeyListener(this);
        
            timer =new Timer(delay,this);
            timer.start();
        
            newEnemy();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        
        // UPPER BORDER
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);
        
        // LOWER BORDER
        g.drawRect(24, 74,851 , 576);
        
        //adding snake title in upper border
        snaketitle.paintIcon(this, g, 25, 11);
        
        //setting color of lower border to black
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);
         
        if (moves==0){
            
        // theese valeus are pixel and each unit has 25 width and length
          snakexlength[0]=100;
          snakexlength[1]=75;
          snakexlength[2]=50;

          snakeylength[0]=100;
          snakeylength[1]=100;
          snakeylength[2]=100;
        }
        if(left){
            leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
         if(right){
            rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if(up){
            upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
         if(down){
            downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

         for (int i=1;i<lengthofsnake;i++){
             snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
         }
         
     enemy.paintIcon(this, g, enemyX, enemyY);
     
        try{
           char[] array = new char[100];
            FileReader reader = new FileReader("snakegame.txt");
            reader.read(array);
            reader.close();
            String highestScore = new String(array).trim(); // Trim any trailing spaces or null characters
            HighestScore = Integer.parseInt(highestScore);
        }
        
        catch(Exception e){
                System.out.println("Unexpected Error While Opening Text File");
                }
     
        if(GameOver){
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,50));
            g.drawString("GAME OVER", 300, 300);

          try{
            if(score>HighestScore){
                    HighestScore=score;
                    
                    g.setColor(Color.GREEN);
                    g.setFont(new Font("Ariel",Font.PLAIN,20));
                    g.drawString("Congratulations! You Made The New Highest Score.", 220, 350);
               
                    FileWriter writer = new FileWriter("snakegame.txt");
                    writer.write(Integer.toString(HighestScore));
                    writer.close();
                    
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Ariel",Font.PLAIN,20));
                    g.drawString("Press Space To Restart", 320, 400);
            }
            else{
                g.setColor(Color.WHITE);
                g.setFont(new Font("Ariel",Font.PLAIN,20));
                g.drawString("Press Space To Restart", 320, 350);
            }
            }
          
         catch(Exception e){
              System.out.println("Unexpexted Error While Writing into File");
    }
     }
     
     g.setColor(Color.WHITE);
     g.setFont(new Font("Arial",Font.PLAIN,14));
     g.drawString("Current Score: "+score, 750, 25);
     g.drawString("Length: "+lengthofsnake, 750, 40);
     g.drawString("Highest Score: "+HighestScore, 750, 60);
     
     g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for(int i=lengthofsnake;i>0;i--){
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];        
        }
       if(left){
           snakexlength[0]=snakexlength[0]-25;  
       }
        if(right){
           snakexlength[0]=snakexlength[0]+25;
        }
         if(up){
           snakeylength[0]=snakeylength[0]-25;
         }
          if(down){
           snakeylength[0]=snakeylength[0]+25;
          }
          
           if (Hardmode && (snakexlength[0] > 850 || snakexlength[0] < 25 || snakeylength[0] > 625 || snakeylength[0] < 75)) {
                 timer.stop();
                GameOver=true;
            }
          
          
          if(! Hardmode){
          if(snakexlength[0]>850){
              snakexlength[0]=25;
          }
          if(snakexlength[0]<25){
              snakexlength[0]=850;
          }
          if(snakeylength[0]>625){
              snakeylength[0]=75;
          }
          if(snakeylength[0]<75){
              snakeylength[0]=625;
          }
          }
          colidesWithEnemy();
          colideWithBody();
          
          
      repaint();
    }

  

@Override
public void keyPressed(KeyEvent e) {
    
    if(e.getKeyCode() == KeyEvent.VK_SPACE && GameOver){
        restart();
    }

    
    if (e.getKeyCode() == KeyEvent.VK_LEFT && (!right)) {
        left = true;
        right = false;
        up = false;
        down = false;
         moves++;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)) {
        left = false;
        right = true;
        up = false;
        down = false;
         moves++;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP && (!down)) {
        left = false;
        right = false;
        up = true;
        down = false;
         moves++;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN && (!up)) {
        left = false;
        right = false;
        up = false;
        down = true;
         moves++;
    }
}

    private void newEnemy() {
     enemyX=xPos[random.nextInt(34)];
        enemyY=yPos[random.nextInt(23)];
        
        for(int i=0;i<lengthofsnake;i++){
            if(snakexlength[i]==enemyX && snakeylength[i]==enemyY){
                newEnemy();
            }
        }
    }
    
     private void colidesWithEnemy() {
    if(snakexlength[0]==enemyX && snakeylength[0]==enemyY){
        newEnemy();
        lengthofsnake++;
        score++;
    
    } }
     
    private void colideWithBody() {
        for(int i=lengthofsnake-1;i>0;i--){
            if(snakexlength[i]==snakexlength[0]  && snakeylength[i]==snakeylength[0]){
                timer.stop();
                GameOver=true;
            }
        }
       }

        private void restart() {
            GameOver=false;
            moves=0;
            score=0;
            lengthofsnake=3;
            left=false;
            right=true;
            up=false;
            down=false;
            newEnemy();
            timer.start();
            repaint();
            
        }

@Override
public void keyReleased(KeyEvent e) {
    // Implementation not needed for this case
}

@Override
public void keyTyped(KeyEvent e) {
    // Implementation not needed for this case
}
}
  