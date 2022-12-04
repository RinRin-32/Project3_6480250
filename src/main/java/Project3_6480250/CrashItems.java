package Project3_6480250;



import javax.swing.*;

public class CrashItems extends JLabel {
    //crash into mainprop, reducing  hp or such
    private final ImageIcon itemImg;
    private SoundEffect hitSound;
    private MainApplication parentFrame;

    private final String hit = "src/main/java/Project3_6480250/resources/slap oh (meme) sound effect.wav";
    private final String ImageCrystal = "src/main/java/Project3_6480250/resources/crystal_projectile.png"; //file location of the image

    private final String sue = "src/main/java/Project3_6480250/resources/goodmanprojectile.png";
    private int width = 45, height = 45;
    private final int curX, curY;
    private int damage;

    private MainProp saul; private Enemy enemy;

    public CrashItems(MainApplication pf, Enemy e, int n){
        enemy = e;
        parentFrame = pf;
        itemImg = new ImageIcon(ImageCrystal).resize(width,height);
        //set images here
        //set sound
        hitSound = new SoundEffect(hit); //I don't know if you use a big pew or small pew
        if(e.isBoss()){
            damage = -10;
        }else{
            damage = -5;
        }
        setIcon(itemImg);
        curX = enemy.getX()+30+(n*100); curY = enemy.getY() + 150;//makes the object directly under the character
        setBounds(curX, curY, width, height);
    }


    public CrashItems(MainApplication pf, MainProp m){
        saul = m;
        width += 30;
        parentFrame = pf;
        itemImg = new ImageIcon(sue).resize(width,height);
        hitSound = new SoundEffect(hit);
        setIcon(itemImg);
        curX = saul.getX(); curY = saul.getY()-50;
        setBounds(curX,curY,width, height);
    }

    public CrashItems(MainApplication pf, MainProp m, int n){
        saul = m;
        width += 30;
        parentFrame = pf;
        itemImg = new ImageIcon(sue).resize(width,height);
        hitSound = new SoundEffect(hit);
        setIcon(itemImg);
        curX = saul.getX()+(n*100); curY = saul.getY()-50;
        setBounds(curX,curY,width, height);
    }


    public void movedown() throws InterruptedException {
        setLocation(getX(), getY()+55);
        Thread.sleep((parentFrame.getSpeed()+1) * 1000/ parentFrame.getDiff());
        parentFrame.getDrawpane().repaint();
    }

    public void crashItemHit(){
        if(damage != 0) {
            hitSound.playOnce();
        }
    }

   public void moveup() throws InterruptedException{
        setLocation(getX(), getY()-55);
        Thread.sleep((parentFrame.getSpeed()+1) * 1000/ parentFrame.getDiff());
        parentFrame.repaint();
   }

    public void disappear(){ setVisible(false); damage = 0; hitSound = null;}


    public int damage(){
        return damage;
    }
}
