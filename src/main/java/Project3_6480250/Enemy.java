package Project3_6480250;

import javax.swing.*;

public class Enemy extends JLabel {
    private ImageIcon jesseEm, waltBo;
    private MainApplication parentFrame;
    private String [] imageFiles = {"src/main/java/Project3_6480250/resources/jesse.png","src/main/java/Project3_6480250/resources/walt_Boss.png"};//haven't added image yet
    //private String [] soundFiles = {"src/main/java/Project3_6480250/resources/bigpew.mp3", "src/main/java/Project3_6480250/resources/smolpew.mp3"};

    private int hp;

    private boolean isBoss = false;
    private int jwidth = 250, jheight = 359, wWidth = 500, wheight = 418;//has to set size here
    private int curX, curY = 0; //has to set location here
    private int speed = 500; //set sleep time, editable

    private SoundEffect   shootSound;

    public Enemy(MainApplication pf, boolean mobselect){
        parentFrame = pf;

        curX = (int)(Math.random() * 5555) % (parentFrame.getWidth()-100);
        //set images here
        jesseEm = new ImageIcon(imageFiles[0]).resize(jwidth, jheight);
        waltBo = new ImageIcon(imageFiles[1]).resize(wWidth, wheight); //resize()

        //set sound
        curY = 200;


        //setIcon(icon);
        if(mobselect){
            setIcon(waltBo);
            setBounds(curX,curY, wWidth, wheight);
            isBoss = true;
            hp = 100;
            //shootSound = new SoundEffect(soundFiles[0]);
        }else {
            setIcon(jesseEm);
            setBounds(curX, curY, jwidth, jheight);
            hp = 20;
            //shootSound = new SoundEffect(soundFiles[1]);
        }

        //setlocation

    }

    public boolean isBoss(){ return isBoss;}

    public void moveset(){
        //make enemy move across the screen from the top
    }


    public void shoot(){
        shootSound.playOnce();
    }
    public synchronized void gotHit(int n){
        hp-= n;

    }

    public int getpoint(){
        //return point
        if(isBoss){
            return 10;
        }else{
            return 2;
        }
    }

    public void disappear(){ setVisible(false);}
}

