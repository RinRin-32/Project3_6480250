package Project3_6480250;

import javax.swing.*;

public class Enemy extends JLabel {
    private ImageIcon jesseEm, waltBo;
    private MainApplication parentFrame;
    private String [] imageFiles = {"src/main/java/Project3_6480250/resources/jesse.png","src/main/java/Project3_6480250/resources/walt_Boss.png"};//haven't added image yet
    private String soundFiles = "src/main/java/Project3_6480250/resources/bigpew.mp3";

    private int jwidth = 250, jheight = 359, wWidth = 500, wheight = 418;//has to set size here
    private int curX, curY = 0; //has to set location here
    private int speed = 500; //set sleep time, editable

    private SoundEffect   shootSound;

    public Enemy(MainApplication pf){
        parentFrame = pf;

        curX = (int)(Math.random() * 5555) % (parentFrame.getWidth()-100);
        //set images here
        jesseEm = new ImageIcon(imageFiles[0]).resize(jwidth, jheight);
        waltBo = new ImageIcon(imageFiles[1]).resize(wWidth, wheight); //resize()

        //set sound
        shootSound = new SoundEffect(soundFiles);

        //setIcon(icon);
        setIcon(jesseEm);
        setBounds(curX, curY, jwidth, jheight);
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
