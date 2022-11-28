/*
FirstName, Surname, StudentID
Burin, Intachuen, 6480250
Mhadhanagul, Charoenphon, 6381199
Tanakorn, Mankhetwit, 6480282
Sittipoj, Techathaicharoen, 6380361
*/
package Project3_6480250;

import com.sun.tools.javac.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class MainApplication extends JFrame {

    private JPanel contentpane;
    private JLabel drawpane;
    private JComboBox combo;
    private JToggleButton toggle;
    private ButtonGroup bgroup;
    private JButton randomname;//smth
    private JTextField text;
    private ImageIcon backgroundImg;
    private SoundEffect themeSound;
    private MainApplication currentFrame;
    private int frameWidth = 1280, frameHeight = 720;

    private String projectPath = "src/main/java/Project3_6480250";
    private String resourcePath = projectPath + "/resources/";

    public void setMainProp(){

        Thread mainPropThread = new Thread(){
            public void run(){
                //do smth with while loop here
            }
        };
        mainPropThread.start();
    }

    public void setEnemy(){
        Thread enemyThread = new Thread(){
            public void run(){
                Enemy enemy = new Enemy(currentFrame, false);
                drawpane.add(enemy);
                //while enemy isn't dead do something

                setCrashItem(enemy);//creates enemy's projectile
            }
        };
        //update player score or smth
        enemyThread.start();
    }
    public void setCrashItem(Enemy e){
        //what if we generate the projectile around our enemy as a circle
        Thread crashThread = new Thread(){
            public void run(){
                CrashItems item = new CrashItems(currentFrame, e);
                drawpane.add(item);
                //do smth in while loop
            }
        };
        //update playerhp
        crashThread.start();
    }


    public void execution(int n){
        //main part of the application
        if(n==0) {
            setTitle("This is a Frame");
            setBounds(200, 200, frameWidth, frameHeight);
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            contentpane = (JPanel) getContentPane();
            contentpane.setLayout(new BorderLayout());
            frameone();
            // call frame 2 once a button is pressed on frameone frametwo();
        }else if(n == 1){
            //settitle("gameplay");
            //setBounds
            //setVisibility(true);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            contentpane = (JPanel) getContentPane();
            contentpane.setLayout(new BorderLayout());
        }else if(n == 2){
            //settitle("deathscreen");
            //setBounds
            //setVisibility(true);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            contentpane = (JPanel) getContentPane();
            contentpane.setLayout(new BorderLayout());
        }
    }
    public MainApplication(int frame){
        execution(0);
    }

    public void frameone() {
        backgroundImg  = new ImageIcon(resourcePath + "gusBackground.jpg").resize(frameWidth, frameHeight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);




        //add listeners for interactives, difficuulty settings





        validate();
        this.setVisible(false);
        MainApplication frame2 = new MainApplication(1); //for making gameplay
        MainApplication death = new MainApplication(2); //for making death screen
    }

    public void frametwo(){
        //the frame with actual game
        //when player hp == 0 go to death screen
        //backgroundImg = new ImageIcon();//maingameplay backgroudn
        //drawpane.setIcon(frame2.backgroundImg);

        //add listeners for gameplays


    }
    public void deathScreen(){
        //the frame with actual game
        //when player hp == 0 go to death screen
        //backgroundImg = new ImageIcon();//maingameplay backgroudn
        //drawpane.setIcon(frame2.backgroundImg);

        //add listeners for gameplays

    }

    public void endCredit(){
        //end credit just make swing text box showing score + our names
    }

/*    class MyImageIcon extends ImageIcon
    {
        public MyImageIcon(String fname)  { super(fname); }
        public MyImageIcon(Image image)   { super(image); }

        public MyImageIcon resize(int width, int height)
        {
            Image oldimg = this.getImage();
            Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            return new MyImageIcon(newimg);
        }
    };*/


    public static void main(String [] args){
        new MainApplication(0);
    }
}

class ImageIcon extends javax.swing.ImageIcon{
    public ImageIcon(String fname){ super(fname);}
    public ImageIcon(Image image) {super(image);}

    public ImageIcon resize(int width, int height){
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
}

class SoundEffect{
    private Clip clip;
    public SoundEffect(String filename){
        try{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        }catch( Exception e){ e.printStackTrace();}
    }

    public void playOnce() { clip.setMicrosecondPosition(0); clip.start();}
    public void playLoop() {clip.loop(Clip.LOOP_CONTINUOUSLY);}
    public void stop() {clip.stop();}
}
