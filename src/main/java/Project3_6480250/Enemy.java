package Project3_6480250;

import javax.swing.*;

public class Enemy extends JLabel {
    private ImageIcon faceLeft, faceRight;
    private MainApplication parentFrame;
    private String [] imageFiles ;//haven't added image yet
    private int width, height;//has to set size here
    private int curX, curY; //has to set location here
    private int speed = 500; //set sleep time, editable

    public Enemy(MainApplication pf){
        parentFrame = pf;
        //set images here
        //set sound

        //setIcon(icon);
        setBounds(curX, curY, width, height);
    }

    public void gotHit(){
        //play sound
    }

    public int getpoint(){
        //return point
        return 0;
    }

    public void disappear(){ setVisible(false);}
}
