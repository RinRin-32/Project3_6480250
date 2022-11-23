package Project3_6480250;



import javax.swing.*;

public class MainProp extends JLabel {
    private ImageIcon faceLeft, faceRight;
    private MainApplication parentFrame;
    private String [] imageFiles ;//haven't added image yet
    private int width, height;//has to set size here
    private int curX, curY; //has to set location here
    private boolean left = true, walk = false, up = true;
    private int speed;//sleep duration
    //set default values like facing left or right and or movement

    public MainProp(MainApplication pf){
        parentFrame = pf;

        //set images here
        //set sound

        //setIcon(icon);
        setBounds(curX, curY, width, height);
    }
    public void setSpeed(int s) { speed = s;}
    public void turnLeft() { /*setIcon(icon);*/ left = true;}
    public void turnRight(){ /*setIcon(icon;*/ left = false;}
    public void setWalk(boolean w) {walk = w;}
    public boolean isWalk() { return walk;}
    public void updateLocation(){
        if(left){
            //curX = smth
            //if on edge frame move to parentFrame.getWidth()
        }else{
            //curX = smth
            //if on edge frame move to curX = 0
        }
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

}
