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
import java.awt.event.*;

public class MainApplication extends JFrame {

    private JPanel contentpane;
    private JLabel drawpane;
    private JComboBox combo;
    private JToggleButton []soundtoggle;
    private ButtonGroup bgroup;

    private MyButton button;
    private JButton setname;
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

    public String getPlayername(){
        return playername;
    }

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
    public void movebutton(){
        Thread buttonmover = new Thread(){
            public void run(){
                while(!button.isClicked()) {
                    button.blink();
                }
            }
        };
        buttonmover.start();
    }


    public void execution(int n){
        //main part of the application
            // call frame 2 once a button is pressed on frameone frametwo();
        setSize(frameWidth+1000, frameHeight+50);
        setBounds(275, 200, frameWidth, frameHeight);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        currentFrame = this;

        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        if(n == 1){
            setTitle("gameplay");
            frametwo();
        }else if(n == 2){
            setTitle("deathscreen");
            deathScreen();
        }else{
            setTitle("It's Sauling time");
            frameone();
        }
        setVisible(true);
    }
    public MainApplication(int frame, String pn){
        execution(frame);
        playername = pn;
    }

    public void frameone() {
        backgroundImg  = new ImageIcon(resourcePath + "gusBackground.jpg").resize(frameWidth, frameHeight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
            drawpane.setLayout(null);
        themeSound = new SoundEffect(resourcePath + "startscreensong.wav"); themeSound.playLoop();
        drawpane.setVisible(true);
        //drawpane.repaint();
        this.add(drawpane);
        /*
        startgame = new JButton("Start Game");
        startgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themeSound.stop();
                frametwo();
            }
        });*/
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

        setname = new JButton("Set Name");
        setname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setEditable(false);
                playername = text.getText();
            }
        });

        JPanel control = new JPanel();
        control.setBounds(0,0, 1000, 50);
        control.add(new JLabel("Player's name :" ));
        control.add(text);
        control.add(setname);
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
        //control.add(new JLabel("Start Game: "));
        //control.add(startgame);
        control.add(new JLabel("Difficulty set : "));
        control.add(combo);
        control.add(soundtoggle[0]); control.add(soundtoggle[1]);
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        button = new MyButton(this);
        movebutton();
        drawpane.add(button);

        this.repaint();




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

        //text.setEditable(false);

        themeSound = new SoundEffect(resourcePath + "maingamesong.wav"); themeSound.playLoop();


    }
    public void deathScreen(){
        //the frame with actual game
        //when player hp == 0 go to death screen
        //backgroundImg = new ImageIcon();//maingameplay backgroudn
        //drawpane.setIcon(frame2.backgroundImg);

        //add listeners for gameplays

    }
    public void getrid(){
        themeSound.stop();
        this.dispose();
    }







    public static void main(String [] args){

        new MainApplication(0, null);
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

class MyButton extends JLabel implements MouseListener {
    private ImageIcon button;
    private MainApplication parentframe;
    private String image = "src/main/java/Project3_6480250/resources/suebutton.png";
    private int width = 466, height = 399;
    private int curX = 400, curY = 200;
    private int speed = 700;
    private boolean clicked = false, isNull = false;

    public MyButton(MainApplication pf){
        parentframe = pf;
        button = new ImageIcon(image).resize(width,height);
        setIcon(button);
        setBounds(curX, curY, width, height);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clicked = true;
        String curplay = parentframe.getPlayername();
        try {
            parentframe.getrid();
        }catch (Exception error){
            error.printStackTrace();
        }
        new MainApplication(1, curplay);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public boolean isClicked(){
        return clicked;
    }
    public void blink(){
        if(isNull){
            setIcon(button);
            isNull = false;
        }else{
            setIcon(null);
            isNull = true;
        }
        repaint();
        try{
            Thread.sleep(speed);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
