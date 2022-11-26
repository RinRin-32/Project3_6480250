package Project3_6480250;



import javax.swing.*;

public class CrashItems extends JLabel {
    //crash into mainprop, reducing  hp or such
    private ImageIcon itemImg;
    private SoundEffect hitSound;
    private MainApplication parentFrame;
    private String soundFile = "src/main/java/Project3_6480250/resources";
    private String ImageCrystal = "src/main/java/Project3_6480250/resources/crystal_projectile.png"; //file location of the image
    private int width, height;
    private int curX, curY;
    private int speed = 500; //set sleep time, editable

    public CrashItems(MainApplication pf){
        parentFrame = pf;
        itemImg = new ImageIcon(ImageCrystal).resize(width,height);
        //set images here
        //set sound
        hitSound = new SoundEffect(); //I don't know if you use a big pew or small pew
        //setIcon(icon);
        setIcon(itemImg);
        setBounds(curX, curY, width, height);
    }

    public void playHitSound() { hitSound.playOnce();}

    public int getDamage () {return damage;} //for something like getting the crashitems damage then using it to subtract hitpoints?
    public int getHitPoints() { return -1;} //for reducing player's hp or smth
    public void disappear(){ setVisible(false);}

    public void setSpeed(int newSpeed) {this.speed = newSpeed;}
}
