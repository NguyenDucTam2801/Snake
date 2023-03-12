import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int WIDTH=700;
    static final int HEIGHT=700;
    static final int UNIT_SIZE=50;
    static final int GAME_UNITS = (WIDTH*HEIGHT)/UNIT_SIZE;
    static final int DELAY= 150;
    final int x[]= new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyPart=4;
    int apple;
    int appleX;
    int appleY;
    char direction='R' ;
    boolean run=false;
    Timer timer;
    Random random;
    GamePanel(){
        random=new Random();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        startGame();
        addKeyListener(new MyKeyAdapter());
    }

    public void startGame(){
        newApple();
        run=true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (run) {
            for (int i = 0; i < WIDTH / UNIT_SIZE; i++) {
                g.drawLine(0, i * UNIT_SIZE, WIDTH, i * UNIT_SIZE);
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, HEIGHT);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyPart; i++) {
                if (i == 0) {
                    g.setColor(Color.CYAN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score: "+apple,(WIDTH-metrics.stringWidth("Score: "+apple))/2,g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void move(){
        for(int i=bodyPart;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;

        }
    }

    public void newApple(){
        for(int i=bodyPart;i>0;i--) {
            do {
                appleX = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
                appleY = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            }
            while (appleX ==x[i]&&appleY==y[i]);
        }
    }

    public void checkApple(){
        if(x[0]==appleX&&y[0]==appleY){
            bodyPart++;
            apple++;
            newApple();
        }
    }

    public void checkConllisions(){
        //Check tu can
        for(int i=bodyPart;i>0;i--){
            if(x[0]==x[i]&&y[0]==y[i]){
                run=false;
            }
        }
        //check left border
        if(x[0]>WIDTH-UNIT_SIZE){
            run=false;
        }
        //check right border
        if(x[0]<0){
            run=false;
        }
        //check bottom
        if(y[0]>HEIGHT-UNIT_SIZE){
            run=false;
        }
        //check top
        if(y[0]<0){
            run=false;
        }
        if(!run){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Game Over",(WIDTH-metrics.stringWidth("GameOver"))/2,HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(run){
            move();
            checkApple();
            checkConllisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_DOWN:
                    if(direction!='U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_UP:
                        if(direction!='D') {
                            direction = 'U';
                        }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(direction!='R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    startGame();
                    break;
            }
        }
    }
}
