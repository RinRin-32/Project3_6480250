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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainApplication extends JFrame {

    private JPanel contentpane;
    private JLabel drawpane;
    private JComboBox combo;
    private JToggleButton []soundtoggle;
    private ButtonGroup bgroup;
    private JButton startgame;//smth
    private JTextField text;
    private MainProp gus;
    private ImageIcon backgroundImg;
    private SoundEffect themeSound;
    private MainApplication currentFrame;
    private int frameWidth = 1280, frameHeight = 720;

    private String projectPath = "src/main/java/Project3_6480250";
    private String resourcePath = projectPath + "/resources/";
    private String playername;

    private int score = 0;

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


    public void execution(){
        //main part of the application
            setTitle("It's Sauling time");
            setSize(frameWidth+1000, frameHeight+50);
            setBounds(275, 200, frameWidth, frameHeight);
            setResizable(false);
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            currentFrame = this;

            contentpane = (JPanel)getContentPane();
            contentpane.setLayout(new BorderLayout());

            frameone();
            // call frame 2 once a button is pressed on frameone frametwo();

    }
    public MainApplication(){
        execution();
    }

    public void frameone() {
        backgroundImg  = new ImageIcon(resourcePath + "gusBackground.jpg").resize(frameWidth, frameHeight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
            drawpane.setLayout(null);
        themeSound = new SoundEffect(resourcePath + "startscreensong.wav"); themeSound.playLoop();
        drawpane.setVisible(true);
        //drawpane.repaint();
        currentFrame.add(drawpane);
        startgame = new JButton("Start Game");
        startgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themeSound.stop();
                frametwo();
            }
        });
        text = new JTextField("Saul", 5);
        text.setEditable(true);
        soundtoggle = new JToggleButton[2];
        bgroup = new ButtonGroup();
        soundtoggle[0] = new JRadioButton("Mute"); soundtoggle[0].setName("Mute");
        soundtoggle[1] = new JRadioButton("Unmute"); soundtoggle[1].setName("Unmute");
        soundtoggle[1].setSelected(true);

        soundtoggle[0].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    themeSound.stop();
                }
            }
        });
        soundtoggle[1].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    themeSound.playLoop();
                }
            }
        });
        for (int i = 0; i<2; i++){
            bgroup.add(soundtoggle[i]);
        }

        JPanel control = new JPanel();
        control.setBounds(0,0, 1000, 50);
        control.add(new JLabel("Player's name :" ));
        control.add(text);
        String [] difficulty = {"easy", "medium", "slow"};
        combo = new JComboBox(difficulty);
        combo.setSelectedIndex(1);
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = "" + e.getItem();
                //if(item.compareToIgnoreCase("smth"))
            }
        });
        control.add(new JLabel("Start Game: "));
        control.add(startgame);
        control.add(combo);
        control.add(soundtoggle[0]); control.add(soundtoggle[1]);
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        currentFrame.repaint();




        //add listeners for interactives, difficuulty settings






        validate();

        /// no need for these now///
        //this.setVisible(false);
        //this.dispose(); //for closing this jframe
        //MainApplication frame2 = new MainApplication(1); //for making gameplay
        //MainApplication death = new MainApplication(2); //for making death screen
        /// no need for these now///
    }

    public void frametwo(){
        //the frame with actual game
        //when player hp == 0 go to death screen
        //backgroundImg = new ImageIcon();//maingameplay backgroudn
        //drawpane.setIcon(frame2.backgroundImg);

        //add listeners for gameplays
        text.setEditable(false);
        themeSound = new SoundEffect(resourcePath + "maingamesong.wav"); themeSound.playLoop();


    }
    public void deathScreen(){
        //the frame with actual game
        //when player hp == 0 go to death screen
        //backgroundImg = new ImageIcon();//maingameplay backgroudn
        //drawpane.setIcon(frame2.backgroundImg);

        //add listeners for gameplays

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
