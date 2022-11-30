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

public class MainApplication extends JFrame implements KeyListener {

    private JPanel contentpane;
    private JLabel drawpane;
    private JComboBox combo;
    private JToggleButton [] soundtoggle, speedtoggle;
    private ButtonGroup bgroup, sgroup;

    private MyButton button;
    private JButton setname;
    private JTextField text;
    private ImageIcon backgroundImg;
    private SoundEffect themeSound;
    private MainApplication currentFrame;

    private MainProp player = new MainProp(this);
    private int frameWidth = 1280, frameHeight = 720;
    private String projectPath = "src/main/java/Project3_6480250";
    private String resourcePath = projectPath + "/resources/";
    private String playername;

    private int score = 0; private boolean muted = false; private int diff, speed;

    public String getPlayername(){
        return playername;
    }


    public JLabel getDrawpane() {
        return drawpane;
    }
    public int getDiff(){
        return diff;
    }
    public void setEnemy(){
        //spawn enemy with the rate of diff
        //not implemented yet
        //once created the enemy automatically start shooting
        Thread enemyThread = new Thread(){
            public void run(){
                int i = 0;
                if(diff != 5) {
                    while (player.getHealth() > 0) {
                        if (i < 5) {
                            Enemy enemy = new Enemy(currentFrame, false);
                            drawpane.add(enemy);
                            drawpane.repaint();
                        }
                        i++;
                        try {
                            Thread.sleep((i+1) * speed * 1000 / diff);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };
        //update player score or smth
        enemyThread.start();
    }
    public void setBoss(){
        //spawn boss with the rate of diff
        //not yet implemented
        //once created the enemy automatically start shooting
        Thread enemyThread = new Thread(){
            public void run(){
                int i = 0;
                if(diff != 5) {
                    while (player.getHealth() > 0) {
                        if (i < diff) {
                            Enemy walt = new Enemy(currentFrame, true);
                            drawpane.add(walt);
                            drawpane.repaint();
                        }
                        i++;
                        try {
                            Thread.sleep((i+1) * speed * 5000 / diff);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }else{
                    while (player.getHealth() > 0) {
                            Enemy walt = new Enemy(currentFrame, true);
                            drawpane.add(walt);
                            drawpane.repaint();
                        try {
                            Thread.sleep((i+1) * speed* 5000  / diff);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };
        enemyThread.start();
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

    public int getFrameHeight(){
        return frameHeight;
    }
    public int getFrameWidth(){
        return frameWidth;
    }
    public int getSpeed(){
        return speed;
    }


    public void execution(int n){
        //main part of the application
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
    public MainApplication(int frame, String pn, boolean sound, int difficulty, int speed){
        playername = pn;
        muted = sound;
        diff = difficulty;
        this.speed = speed;
        execution(frame);

    }

    public MainApplication(int frame, String pn, boolean sound){
        playername = pn;
        muted = sound;
        diff = 2;
        execution(frame);

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
                    muted = true;
                }
            }
        });
        soundtoggle[1].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    themeSound.playLoop();
                    muted = false;
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
        String [] difficulty = {"Jesse Easy","Easy", "Medium", "Hard", "Walt ONLY"};
        combo = new JComboBox(difficulty);
        combo.setSelectedIndex(1);
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = "" + e.getItem();
                if(item.compareToIgnoreCase("Jesse Easy") == 0){
                    diff = 1;
                }else if(item.compareToIgnoreCase("Easy") == 0){
                    diff = 2;
                }else if(item.compareToIgnoreCase("Medium") ==0){
                    diff = 3;
                }else if(item.compareToIgnoreCase("Hard")==0){
                    diff = 4;
                }else if(item.compareToIgnoreCase("Walt ONLY") == 0){
                    diff = 5;
                }else{
                    diff = 3;
                }
            }
        });
        speedtoggle = new JToggleButton[5];
        sgroup = new ButtonGroup();
        speedtoggle[0] = new JRadioButton("Super Slow"); speedtoggle[0].setName("Super Slow");
        speedtoggle[1] = new JRadioButton("Slow"); speedtoggle[1].setName("Slow");
        speedtoggle[2] = new JRadioButton("Moderate"); speedtoggle[2].setName("Moderate");
        speedtoggle[3] = new JRadioButton("Fast"); speedtoggle[3].setName("Fast");
        speedtoggle[4] = new JRadioButton("SUPER SPEED"); speedtoggle[4].setName("SUPER SPEED");
        speedtoggle[2].setSelected(true);

        speedtoggle[0].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    speed = 10;
                }
            }
        });
        speedtoggle[1].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    speed = 7;
                }
            }
        });
        speedtoggle[2].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    speed = 5;
                }
            }
        });
        speedtoggle[3].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    speed = 4;
                }
            }
        });
        speedtoggle[4].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    speed = 2;
                }
            }
        });

        for (int i = 0; i<5; i++){
            sgroup.add(speedtoggle[i]);
        }
        //control.add(new JLabel("Start Game: "));
        //control.add(startgame);
        control.add(new JLabel("Difficulty set : "));
        control.add(combo);
        control.add(new JLabel("Speed Set : "));
        control.add(speedtoggle[0]);control.add(speedtoggle[1]);control.add(speedtoggle[2]);control.add(speedtoggle[3]);control.add(speedtoggle[4]);
        control.add(new JLabel("Sound toggle : "));
        control.add(soundtoggle[0]); control.add(soundtoggle[1]);
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        button = new MyButton(this);
        movebutton();
        drawpane.add(button);

        this.repaint();
        validate();
    }

    public void frametwo(){
        //the frame with actual game
        //when player hp == 0 go to death screen
        //keyevent handling
        //5 jradiobutton (gameplay speed)
        //5 jlist (powerups)
        setFocusable(true);
        requestFocus();
        this.addKeyListener(this);
        backgroundImg  = new ImageIcon(resourcePath + "gamebackground.jpg").resize(frameWidth, frameHeight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

        this.add(drawpane);
        themeSound = new SoundEffect(resourcePath + "maingamesong.wav");
        if(!muted){
            themeSound.playLoop();
        }

        JPanel control = new JPanel();
        control.setBounds(0,0, 1000, 50);

        soundtoggle = new JToggleButton[2];
        bgroup = new ButtonGroup();
        soundtoggle[0] = new JRadioButton("Mute"); soundtoggle[0].setName("Mute");
        soundtoggle[1] = new JRadioButton("Unmute"); soundtoggle[1].setName("Unmute");
        if(muted){
            soundtoggle[0].setSelected(true);
        }else{
            soundtoggle[1].setSelected(true);
        }

        soundtoggle[0].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    themeSound.stop();
                    muted = true;
                }
            }
        });
        soundtoggle[1].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == e.SELECTED){
                    themeSound.playLoop();
                    muted = false;
                }
            }
        });

        for (int i = 0; i<2; i++){
            bgroup.add(soundtoggle[i]);
        }


        contentpane.add(new JLabel("Sound Toggle : "));
        control.add(soundtoggle[0]); control.add(soundtoggle[1]);
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        drawpane.add(player);
        drawpane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setFocusable(true);
                requestFocus();
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        setEnemy();//generate enemy
        setBoss();//generate boss


        //implement mouselistener
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
    public boolean isMuted(){
        return muted;
    }







    public static void main(String [] args){

        new MainApplication(0, null, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.moving(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

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
        boolean muted = parentframe.isMuted();
        String curplay = parentframe.getPlayername();
        int pfspeed = parentframe.getSpeed();
        int diff = parentframe.getDiff();
        try {
            parentframe.getrid();
        }catch (Exception error){
            error.printStackTrace();
        }
        new MainApplication(1, curplay, muted, diff, pfspeed);
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
