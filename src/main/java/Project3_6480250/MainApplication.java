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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private ArrayList<Enemy> allenemy = new ArrayList<>();

    private MainProp player = new MainProp(this);
    private int frameWidth = 1280, frameHeight = 720;
    private String projectPath = "src/main/java/Project3_6480250";
    private String resourcePath = projectPath + "/resources/";
    private String playername;

    private int powerUpSelect, totalenemy = 0;

    private static int score; private boolean muted = false, kill = false; private int diff, speed;

    public String getPlayername(){
        return playername;
    }
    public synchronized void addscore(int n){
        score += n;
    }
    public void shooting(CrashItems lawsuit){
        while(lawsuit.getY()>0){
            try {
                lawsuit.moveup();
                repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for(Enemy i: allenemy){
                if(i.getBounds().intersects(lawsuit.getBounds())){
                    lawsuit.crashItemHit();
                    lawsuit.disappear();
                    drawpane.remove(lawsuit);
                    i.damaged();
                    if(i.isBoss()){
                        addscore(2);
                    }else{
                        addscore(1);
                    }
                    repaint();
                }
                if(i.getHealth() <= 0){
                    i.disappear(); totalenemy--;
                    drawpane.remove(i);

                    if(i.isBoss()){
                        addscore(5);
                    }else{
                        addscore(2);
                    }
                    repaint();
                }
            }
            try {
                Thread.sleep((speed+1) * 2500/ diff);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        lawsuit.disappear();
        drawpane.remove(lawsuit);
        repaint();
    }



    public void shoot(MainProp mainchar){
        CrashItems lawsuit = new CrashItems(currentFrame, player);
        drawpane.add(lawsuit);
        drawpane.repaint();
        Thread mcshoot = new Thread(){
            public void run(){
                shooting(lawsuit);
            }
        };
        mcshoot.start();
    }


    public void shoot(MainProp mainchar, int n){
        CrashItems lawsuit = new CrashItems(currentFrame, player, n);
        drawpane.add(lawsuit);
        drawpane.repaint();
        Thread mcshoot = new Thread(){
            public void run(){
                shooting(lawsuit);
            }
        };
        mcshoot.start();
    }

    public void shoot(Enemy enemy, int n){
        Thread projspawn = new Thread(){
          public void run(){
              while(enemy.getHealth()>0 && !kill && player.getHealth() > 0){
                  if(enemy.getHealth() <= 0||player.getHealth()<0){
                      return;
                  }
                  enemy.shoot();
                  CrashItems projectile = new CrashItems(currentFrame, enemy, n);
                  try {
                      Thread.sleep((speed+1) * 5000/ diff);
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
                  drawpane.add(projectile);
                  repaint();
                  Thread moveprojectile = new Thread(){
                      public void run(){
                          while(projectile.getY()< currentFrame.getFrameHeight() && !kill && player.getHealth() > 0 ) {
                              if(player.getHealth() <= 0){
                                  try{
                                      currentFrame.getrid();
                                  }catch (Exception error){
                                      error.printStackTrace();
                                  }
                                  new MainApplication(2, playername, muted);
                                  break;
                              }
                              if(enemy.getHealth() <= 0){
                                  projectile.disappear();
                                  drawpane.remove(projectile);
                              }
                              drawpane.repaint();
                              try {
                                  projectile.movedown();
                              } catch (InterruptedException e) {
                                  throw new RuntimeException(e);
                              }
                              if(player.getBounds().intersects(projectile.getBounds())){
                                  addscore(-2);
                                  player.updateHP(projectile.damage());
                                  text.setText(Integer.toString(player.getHealth()));
                                  projectile.crashItemHit();
                                  projectile.disappear();
                                  drawpane.remove(projectile);
                                  if(player.getHealth() <= 0){
                                      try{
                                          currentFrame.getrid();
                                      }catch (Exception error){
                                          error.printStackTrace();
                                      }
                                      new MainApplication(2, playername, muted);
                                  }
                                  break;
                              }

                          }
                      }
                  }; moveprojectile.start();

              }
          }
        };projspawn.start();
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
                    while (player.getHealth() > 0&&!kill) {
                        if (i < 5) {
                            Enemy enemy = new Enemy(currentFrame, false);
                            allenemy.add(enemy); totalenemy++;
                            shoot(enemy,0);
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
                    while (player.getHealth() > 0&&!kill) {
                        try {
                            Thread.sleep((i+1) * speed * 5000 / diff);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (i < diff) {
                            Enemy walt = new Enemy(currentFrame, true);
                            allenemy.add(walt);
                            shoot(walt,0);
                            shoot(walt, 1);
                            drawpane.add(walt); totalenemy++;
                            drawpane.repaint();
                        }
                        i++;

                    }
                }else{
                    while (player.getHealth() > 0&&!kill) {
                        try {
                            Thread.sleep((i+1) * speed * 5000 / diff);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Enemy walt = new Enemy(currentFrame, true);
                        drawpane.add(walt); totalenemy++;
                        shoot(walt,0);
                        shoot(walt, 1);
                        drawpane.repaint();

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
                    if(button.isClicked()){
                        break;
                    }
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

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                super.windowClosing(e);
                currentFrame.getrid();
                String output = "Total score = " + (score * player.getHealth() * diff/(10000)) +"\nGame by: \n" +"Burin, Intachuen, 6480250\n" +
                        "Mhadhanagul, Charoenphon, 6381199\n" +
                        "Tanakorn, Mankhetwit, 6480282\n" +
                        "Sittipoj, Techathaicharoen, 6380361";
                JOptionPane.showMessageDialog(null, output, "Thank you for playing our game " + playername, JOptionPane.INFORMATION_MESSAGE);
            }
        });
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
        speed = 5;
        score = 0;
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
        if(playername!=null){
            text.setText(playername);
        }else{
            playername = "Saul";
        }
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

        //control.add(goDeath);

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
        button = new MyButton(this, true);
        movebutton();
        drawpane.add(button);

        this.repaint();
        validate();
    }

    public synchronized void timer(JPanel control, JList power){
        Thread timer = new Thread(){
            public void run(){
                power.setEnabled(false);
                try{
                    Thread.sleep(100*(speed+1+20)*diff);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                powerUpSelect = 0;
                power.setEnabled(true);
            }
        };timer.start();
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
        JButton restart = new JButton("Restart");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.getrid();
                new MainApplication(1, currentFrame.getPlayername(), muted, diff, speed);
            }
        });

        JButton newgame = new JButton("New Game");
        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.getrid();
                new MainApplication(0, null, false);
            }
        });
        text = new JTextField();
        text.setEditable(false);
        text.setText(Integer.toString(player.getHealth()));

        contentpane.add(new JLabel("Sound Toggle : "));
        control.add(soundtoggle[0]); control.add(soundtoggle[1]);
        control.add(restart);
        control.add(newgame);
        control.add(text);

        DefaultListModel<String> powerups = new DefaultListModel<>();
        powerups.addElement("Rebuttal");
        powerups.addElement("Lawsuit Hell Rain");
        powerups.addElement("Saul Badman");
        powerups.addElement("No YOU!");
        powerups.addElement("MEMES");

        JList<String> power = new JList<>(powerups);
        JList jlistselected;
        power.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        power.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(power.getSelectedValue().compareTo("Rebuttal")==0){
                    powerUpSelect = 1;
                    player.isInvinsible();
                    player.healoff();
                    timer(control, power);
                }
                if(power.getSelectedValue().compareTo("Lawsuit Hell Rain")==0){
                    powerUpSelect = 2;
                    player.healoff();
                    player.invoff();
                    timer(control, power);
                }
                if(power.getSelectedValue().compareTo("Saul Badman")==0){
                    powerUpSelect = 3;
                    player.ishealing();
                    player.invoff();
                    timer(control, power);
                }
                if(power.getSelectedValue().compareTo("No YOU!")==0){
                    powerUpSelect = 4;
                    player.healoff(); player.invoff();
                    for(Enemy i : allenemy){
                        i.killall();
                        drawpane.remove(i);
                    }
                    repaint();
                    timer(control, power);
                }
                if(power.getSelectedValue().compareTo("MEMES")==0){
                    try{
                        currentFrame.getrid();
                    }catch (Exception error){
                        error.printStackTrace();
                    }
                    new MainApplication(2, playername, muted);

                }else {

                }
            }
        });



        control.add(power);

        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        drawpane.add(player);
        drawpane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                //call method that shoot
                //shoot is a thread so it move the location of the projectile
                if(powerUpSelect == 2){
                    shoot(player, 0);
                    shoot(player, 1);
                    shoot(player,2);
                }else {
                    shoot(player);
                }
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

        do {
            setEnemy();//generate enemy
            setBoss();//generate boss
        }while(totalenemy > 0);

    }
    public void deathScreen(){
        //the frame with actual game
        //when player hp == 0 go to death screen
        //backgroundImg = new ImageIcon();//maingameplay backgroudn
        //drawpane.setIcon(frame2.backgroundImg);

        backgroundImg  = new ImageIcon(resourcePath + "deathScreen.jpg").resize(frameWidth, frameHeight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);
        drawpane.setVisible(true);
        themeSound = new SoundEffect(resourcePath + "deathsong.wav"); themeSound.playLoop();
        if(muted){
            themeSound.stop();
        }
        this.add(drawpane);
        button = new MyButton(this, false);
        drawpane.add(button);


        //add listeners for gameplays

    }
    public void getrid(){
        themeSound.stop();
        kill = true;
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
    private ImageIcon Sbutton, Rbutton;
    private MainApplication parentframe;
    private String [] image = {"src/main/java/Project3_6480250/resources/suebutton.png", "src/main/java/Project3_6480250/resources/RestartButton.png"};
    private int width = 466, height = 399;
    private int curX = 400, curY = 200;
    private int speed = 700;
    private boolean clicked = false, isNull = false, restart = false;

    public MyButton(MainApplication pf, boolean start){
        parentframe = pf;

        if(start){
            Sbutton = new ImageIcon(image[0]).resize(width,height);
            setIcon(Sbutton);
            restart = false;
        } else {
            Rbutton = new ImageIcon(image[1]).resize(width,height);
            setIcon(Rbutton);
            restart = true;
        }
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
        if(!restart){
            new MainApplication(1, curplay, muted, diff, pfspeed);
        } else {
            new MainApplication(0, null, false);
        }

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
            setIcon(Sbutton);
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
