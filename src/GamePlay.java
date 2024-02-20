import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 30;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballPosX = 370;
    private int ballPosY = 630;
    private int ballXdir = -1;
    private int ballYdir = -2;



    private MapGenerator mapGenerator;


    public GamePlay(){
        mapGenerator = new MapGenerator(5,10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    // Mouse control to be implemented Later...
//    public class Mouse extends MouseAdapter {
//        @Override
//        public void mousePressed(MouseEvent e){
//            int xPos = e.getX();
//            int yPos = e.getY();
//            System.out.print(xPos);
//            System.out.print(yPos);
//
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
       timer.start();
       if(play){
           if (new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,655,130,12))){
               // Bounce on Paddle Moment
               ballYdir = -ballYdir -2;
               playerX = playerX +5;

           }

         A:  for (int i = 0; i < mapGenerator.map.length; i++) {
               for (int j = 0; j < mapGenerator.map[0].length; j++) {
                   if (mapGenerator.map[i][j] > 0){
                       int brickX = j * mapGenerator.brickWidth + 50;
                       int brickY = i * mapGenerator.brickHeight + 30;
                       int brickWidth = mapGenerator.brickWidth;
                       int brickHeight = mapGenerator.brickHeight;
                       Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth,brickHeight);
                       Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                       Rectangle brickRect = rectangle;
                       if(ballRect.intersects(brickRect)){
                            mapGenerator.setBrickValue(0,i,j);
                            totalBricks--;
                            score +=2;
                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;

                       }
                   }
               }
               
           }
           
           ballPosX += ballXdir;
           ballPosY += ballYdir;
           if (ballPosX < 0){
               ballXdir = -ballXdir;
           }
           if (ballPosY < 0){
               ballYdir = -ballYdir;
           }
           if (ballPosX >= 770){
               ballXdir = -ballXdir;
           }
       }
       repaint();
    }

    @Override
    public void paint(Graphics g) {
        //background
       g.setColor(Color.green);
       g.fillRect(5,5,792,692);

       //drawing map
        mapGenerator.draw((Graphics2D)g);

       //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,692);
        g.fillRect(0,0,792,3);
        g.fillRect(781,0,3,692);

        // the score
        g.setColor(new Color(8, 23, 237));
        g.setFont(new Font("serif",Font.ITALIC,35));
        g.drawString(""+score,620,20);
        //the padle
        g.setColor(Color.BLUE);
        g.fillRect(playerX,655,100,6);

        //The Ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,30,30);

        if (ballPosY >= 680) {
            play = false;
            ballYdir = 0;
            ballXdir = 0;
            g.setColor(new Color(255,51,51));
            g.setFont(new Font("serif",Font.BOLD,35));
            g.drawString("You Lost With a Score of "+ score +  " Loser!! Get A Life ",20,300);
        }

        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                //Right Border Bounce
                if (playerX >=600){
                    playerX = 600;
                } else {
                    moveRight();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                //Left Border Bounce
                if (playerX <=25){
                    playerX = 25;
                } else {
                    moveLeft();
                }
            }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void moveRight(){
        play = true;
        playerX+=10;
    }
    public void moveLeft(){
        play = true;
        playerX-=10;
    }
}

