package Project3_6480250;



import javax.swing.*;

public class CrashItems extends JLabel {
    //crash into mainprop, reducing  hp or such
    private ImageIcon itemImg;
    private SoundEffect shootSound, hitSound;
    private MainApplication parentFrame;
    private String [] soundFiles = {"src/main/java/Project3_6480250/resources/bigpew.mp3", "src/main/java/Project3_6480250/resources/smolpew.mp3"};

    private String hit = "src/main/java/Project3_6480250/resources/collisionsound.mp3";
    private String ImageCrystal = "src/main/java/Project3_6480250/resources/crystal_projectile.png"; //file location of the image

    private String sue = "src/main/java/Project3_6480250/resources/goodmanprojectile.png";
    private int width = 90, height = 90;
    private int curX, curY;
    private int speed = 500; //set sleep time, editable
    private int damage;

    public CrashItems(MainApplication pf, Enemy e){
        parentFrame = pf;
        itemImg = new ImageIcon(ImageCrystal).resize(width,height);
        //set images here
        //set sound
        //hitSound = new SoundEffect(); //I don't know if you use a big pew or small pew
        //setIcon(icon);
        hitSound = new SoundEffect(hit);
        if(e.isBoss()){
            shootSound = new SoundEffect(soundFiles[1]);//boss sound effect
            damage = -5;
        }else{
            shootSound = new SoundEffect(soundFiles[0]);
            damage = -1;
        }
        setIcon(itemImg);
        curX = e.getX(); curY = e.getY()-40;//makes the object directly under the character
        setBounds(curX, curY, width, height);
    }

    public CrashItems(MainApplication pf, MainProp m){
        width += 30;
        parentFrame = pf;
        itemImg = new ImageIcon(sue).resize(width,height);
        hitSound = new SoundEffect(hit);
        setIcon(itemImg);
        curX = m.getX(); curY = m.getY()-50;
        setBounds(curX,curY,width, height);
    }

    public void movedown(){
        //setLocation();
    }

    public void playHitSound() { hitSound.playOnce();}

    public int getDamage () {return damage;} //for something like getting the crashitems damage then using it to subtract hitpoints?
    public int getHitPoints() { return -1;} //for reducing player's hp or smth
    public void disappear(){ setVisible(false);}

    public void setSpeed(int newSpeed) {this.speed = newSpeed;}
}
