package Project3_6480250;



import javax.swing.*;

public class MainProp extends JLabel {
    private ImageIcon goodmanFace;
    private MainApplication parentFrame;
    private String imageFiles = "src/main/java/Project3_6480250/resources/goodman.png";//haven't added image yet
    private int width = 100, height = 100;//has to set size here
    private int curX = 640, curY = 360; //has to set location here
    private boolean walk = false, up = true;
    private int speed;//sleep duration
    private int hp = 40;

    public MainProp(MainApplication pf){
        parentFrame = pf;

        goodmanFace = new ImageIcon((imageFiles));
        //set images here
        setIcon(goodmanFace);
        //set sound

        //setIcon(icon);
        setBounds(curX, curY, width, height);
    }
    public void setSpeed(int s) { speed = s;}
    public void setWalk(boolean w) {walk = w;}
    public boolean isWalk() { return walk;}

    public void updateLocation(){

        if(up){
            //curY = smth
            //if on edge frame move to parentFrame.getHeight()
        }else{
            //curY = smth
            //if on edge frame move to curY = 0
        }
        setLocation(curX, curY);
        repaint();
        try{
            Thread.sleep(speed);
        }catch (InterruptedException e ){
            e.printStackTrace();
        }
    }

    public synchronized void updateHp(int n ){ hp += n;}

    public int getHealth(){ return hp;}

}
