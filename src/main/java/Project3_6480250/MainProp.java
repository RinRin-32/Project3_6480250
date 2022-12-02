package Project3_6480250;



import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainProp extends JLabel {
    private ImageIcon goodmanFace;
    private MainApplication parentFrame;
    private String imageFiles = "src/main/java/Project3_6480250/resources/goodman.png";//haven't added image yet
    private int width = 100, height = 100;//has to set size here
    private int curX = 640, curY = 360; //has to set location here
    private boolean walk = false, up = true;
    private int speed;//sleep duration
    private int hp = 40;
    private MainProp thisprop;

    public MainProp(MainApplication pf){
        thisprop = this;
        parentFrame = pf;
        goodmanFace = new ImageIcon((imageFiles)).resize(width, height);
        //set images here
        setIcon(goodmanFace);
        //set sound

        //setIcon(icon);
        setBounds(curX, curY, width, height);
    }
    public void setSpeed(int s) { speed = s;}
    public void setWalk(boolean w) {walk = w;}
    public boolean isWalk() { return walk;}

    public void updateHP(){
        hp -= 5;
    }

    public void updateLocation(){

        if(up){
            //curY = smth
            //if on edge frame move to parentFrame.getHeight()
        }else{
            //curY = smth
            //if on edge frame move to curY = 0
        }
        setLocation(curX, curY);
        repaint();
        try{
            Thread.sleep(speed);
        }catch (InterruptedException e ){
            e.printStackTrace();
        }
    }

    public synchronized void updateHP(int n ){ hp += n;}

    public int getHealth(){ return hp;}

    public void shoot(){
        Thread shoot = new Thread(){
            public void run(){
                CrashItems item = new CrashItems(parentFrame, thisprop);
                parentFrame.getDrawpane().add(item);
                try {
                    item.moveup();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        shoot.start();
    }



    public void moving(int e) {
        int upvalue = this.getY();
        int rightvalue = this.getX();

        if (e == 87){ //w
            if (this.getY() > -1) {
                upvalue -= 50;
            }
        }else if (e == 83){ //s
            if(this.getY() < parentFrame.getFrameHeight()-200){
                upvalue += 50;
            }
        }else if (e == 65){ //a
            if(this.getX() > -1){
                rightvalue -= 50;
            }
        }else if (e == 68){ //d
            if(this.getX() < parentFrame.getFrameWidth()-100){
                rightvalue += 50;
            }
        }
        this.setLocation(rightvalue,upvalue);
    }
}
