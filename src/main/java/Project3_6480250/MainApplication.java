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

    public void setMainProp(){
        Thread mainPropThread = new Thread(){
            public void run(){
                //do smth with while loop here
            }
        };
        mainPropThread.start();
    }
    public void setCrashItem(){
        Thread itemThread = new Thread(){
            public void run(){
                CrashItems item = new CrashItems(currentFrame);
                drawpane.add(item);
                //do smth in while loop
            }
        };
        //update playerhp
    }

    public MainApplication(){

    }
    public static void main(String [] args){

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
