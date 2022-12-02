package Project3_6480250;

import javax.swing.*;

public class Enemy extends JLabel {
    private ImageIcon jesseEm, waltBo;
    private MainApplication parentFrame;
    private String [] imageFiles = {"src/main/java/Project3_6480250/resources/jesse.png","src/main/java/Project3_6480250/resources/walt_Boss.png"};//haven't added image yet
    private String [] soundFiles = {"src/main/java/Project3_6480250/resources/bigpew.wav", "src/main/java/Project3_6480250/resources/smolpew.wav"};

    private int hp;

    private boolean isBoss = false;
    private int jwidth = 125, jheight = 129, wWidth = 250, wheight = 209;//has to set size here
    private int curX, curY = 50; //has to set location here
    private int speed = 500; //set sleep time, editable
    private Enemy thisene;

    private SoundEffect   shootSound;

    public synchronized int getHealth(){
        return hp;
    }
    public Enemy(MainApplication pf, boolean mobselect){
        thisene = this;
        parentFrame = pf;

        curX = (int)(Math.random() * parentFrame.getWidth()-1) + 1;
        //set images here
        jesseEm = new ImageIcon(imageFiles[0]).resize(jwidth, jheight);
        waltBo = new ImageIcon(imageFiles[1]).resize(wWidth, wheight); //resize()

        //set sound


        //setIcon(icon);
        if(mobselect){
            setIcon(waltBo);
            isBoss = true;
            hp = 100;
            shootSound = new SoundEffect(soundFiles[0]);
            curY -= 100;
        }else {
            setIcon(jesseEm);
            hp = 20;
            shootSound = new SoundEffect(soundFiles[1]);
            curY -= 50;

        }
        setBounds(curX,curY, wWidth, wheight);

    }

    public boolean isBoss(){ return isBoss;}

    public void moveset(){
        //make enemy move across the screen from the top
    }

    public void damaged(){
        hp -= 5;
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

