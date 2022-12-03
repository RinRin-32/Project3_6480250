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
    private int hp = 40;
    private boolean invinsible = false, heal = false;

    public MainProp(MainApplication pf){
        parentFrame = pf;
        goodmanFace = new ImageIcon((imageFiles)).resize(width, height);
        //set images here
        setIcon(goodmanFace);
        //set sound

        //setIcon(icon);
        setBounds(curX, curY, width, height);
    }
    public void isInvinsible(){
        invinsible = true;
    }
    public void invoff(){
        invinsible = false;
    }
    public void ishealing(){
        heal = true;
    }
    public void healoff(){
        heal = false;
    }


    public synchronized void updateHP(int n ){
        if(heal){
            hp-=n;
            //System.out.println("heal");
        }else if(invinsible){
            //System.out.println("inv");
        }else{
            hp += n;
            //System.out.println("norm");
        }

    }

    public int getHealth(){ return hp;}




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
