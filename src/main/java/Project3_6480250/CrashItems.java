package Project3_6480250;



import javax.swing.*;

public class CrashItems extends JLabel {
    //crash into mainprop, reducing  hp or such
    private ImageIcon itemImg;
    private SoundEffect hitSound;
    private MainApplication parentFrame;
    private String imageFiles; //file location of the image
    private int width, height;
    private int curX, curY;
    private int speed = 500; //set sleep time, editable

    public CrashItems(MainApplication pf){
        parentFrame = pf;
        //set images here
        //set sound

        //setIcon(icon);
        setBounds(curX, curY, width, height);
    }

    public void playHitSound() { hitSound.playOnce();}
    public int getHitPoints() { return -1;} //for reducing player's hp or smth
    public void disappear(){ setVisible(false);}
}
