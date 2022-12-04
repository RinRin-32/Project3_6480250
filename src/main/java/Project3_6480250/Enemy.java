package Project3_6480250;

import javax.swing.*;

public class Enemy extends JLabel {
    private final ImageIcon jesseEm, waltBo;
    private MainApplication parentFrame;
    private final String [] imageFiles = {"src/main/java/Project3_6480250/resources/jesse.png","src/main/java/Project3_6480250/resources/walt_Boss.png"};//haven't added image yet
    private final String [] soundFiles = {"src/main/java/Project3_6480250/resources/bigpew.wav", "src/main/java/Project3_6480250/resources/smolpew.wav"};

    private int hp;

    private boolean isBoss = false;
    private final int jwidth = 125, jheight = 129, wWidth = 250, wheight = 209;//has to set size here
    private int curX, curY = 50; //has to set location here

    private final SoundEffect   shootSound;

    public synchronized int getHealth(){
        return hp;
    }
    public Enemy(MainApplication pf, boolean mobselect){
        parentFrame = pf;

        //set images here
        jesseEm = new ImageIcon(imageFiles[0]).resize(jwidth, jheight);
        waltBo = new ImageIcon(imageFiles[1]).resize(wWidth, wheight); //resize()

        //set sound


        //setIcon(icon);
        if(mobselect){
            curX = (int)(Math.random() * parentFrame.getWidth()-300) + 100;
            setIcon(waltBo);
            isBoss = true;
            hp = 100;
            shootSound = new SoundEffect(soundFiles[0]);
            curY -= 100;
        }else {
            curX = (int)(Math.random() * parentFrame.getWidth()-200) + 100;
            setIcon(jesseEm);
            hp = 20;
            shootSound = new SoundEffect(soundFiles[1]);
            curY -= 50;

        }
        setBounds(curX,curY, wWidth, wheight);

    }
    public void killall(){
        hp = 0;
    }

    public boolean isBoss(){ return isBoss;}

    public synchronized void damaged(){
        hp -= 5;
    }

    public void shoot(){
        shootSound.playOnce();
    }

    public void disappear(){ setVisible(false);}
}

