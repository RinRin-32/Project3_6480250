/*
FirstName, Surname, StudentID
Burin, Intachuen, 6480250
Mhadhanagul, Charoenphon, 6381199
Tanakorn, Mankhetwit, 6480282
Sittipoj, Techathaicharoen, 6380361
*/
package Project3_6480250;

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

    private String projectPath = "src/main/java/Project3_6480250";

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
                Enemy enemy = new Enemy(currentFrame);
                drawpane.add(enemy);
                //while enemy isn't dead do something
            }
        };
        //update player score or smth
        enemyThread.start();
    }
    public void setCrashItem(){
        //what if we generate the projectile around our enemy as a circle
        Thread crashThread = new Thread(){
            public void run(){
                CrashItems item = new CrashItems(currentFrame);
                drawpane.add(item);
                //do smth in while loop
            }
        };
        //update playerhp
        crashThread.start();
    }

    public MainApplication(){
        //main part of the application
    }
    public static void main(String [] args){
        new MainApplication();
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
