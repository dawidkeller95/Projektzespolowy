package prototyp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Local video player component object
 * @author Aleksander Januszewski
 * @version 2.0
 */
public class PlayerComponent extends JPanel {

    /**
    * Default constructor
    */
    public PlayerComponent(){
        this("b.mp4");
    }

    /**
    * Constructor
    * @param file name of existing file on local disk
    */
    public PlayerComponent(String file){
            this.setPreferredSize(new Dimension(400, 300));
            setVisible(true);
            setLayout(new BorderLayout());
            playerBorder = BevelBorder.RAISED;
            this.setBorder(new BevelBorder(playerBorder));

            VLCPlayer vlcplayer = new VLCPlayer();
            vlcplayer.initializeCanvas();

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            vlcplayer.getCanvas().addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e) {
                    if (VLCPlayer.emp.isPlaying())
                        VLCPlayer.emp.pause();
                    else
                        VLCPlayer.emp.play();
                }       
            });
            panel.add(vlcplayer.getCanvas());
            add(panel,BorderLayout.CENTER);		
            vlcplayer.refreshCanvas();
            vlcplayer.setFile(file);
            first = true;
            
            /* DEFAULT COLORS */
            //                //
            progressBarBackG = Color.BLUE;
            buttonsPanelBackG = Color.ORANGE;
            midPanelBackG = Color.RED;
            stopButtonColor = Color.BLACK;
            stopButtonBackG = Color.LIGHT_GRAY;
            moveLeftColor = Color.BLACK;
            moveLeftBackG = Color.LIGHT_GRAY;
            playpauseColor = Color.BLACK;
            playpauseBackG = Color.LIGHT_GRAY;
            moveRightColor = Color.BLACK;
            moveRightBackG = Color.LIGHT_GRAY;
            speakerButtonColor = Color.BLACK;
            speakerBackG = Color.LIGHT_GRAY;
            sliderPanelBackG = Color.MAGENTA;

            stopButton = new StopButton();
            stopButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            VLCPlayer.emp.stop();
                            playpauseButton.play = true;
                            bar.setValue(0);
                    }
            });

            moveLeftButton = new MoveBackwardButton();
            moveLeftButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long newTime = 
                            VLCPlayer.emp.getTime() - Math.round(VLCPlayer.emp.getLength()/10.0);
                        if (newTime < 0)
                            VLCPlayer.emp.setTime(0);
                        else
                            VLCPlayer.emp.setTime(newTime);

                    }
            });

            playpauseButton = new PlayPauseButton();
            playpauseButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            if (isLocal())
                                vlcplayer.playLocalVideo();
                            else if (first){
                                    vlcplayer.playYoutubeVideo(url);
                                    first = false;
                            }

                            if (VLCPlayer.emp.isPlaying()){
                                VLCPlayer.emp.pause();
                            }
                            else{
                                VLCPlayer.emp.play();
                            }
                    }
            });

            moveRightButton = new MoveForwardButton();
            moveRightButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long newTime = 
                        VLCPlayer.emp.getTime() + Math.round(
                                VLCPlayer.emp.getLength()/10.0);
                        if (newTime > VLCPlayer.emp.getLength())
                            VLCPlayer.emp.setTime(VLCPlayer.emp.getLength());
                        else
                            VLCPlayer.emp.setTime(newTime);
                    }
            });
            
            sliderPanel = new JPanel();
            soundSlider = new JSlider();
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            soundSlider.setPaintTrack(true);
            soundSlider.setOrientation(JSlider.HORIZONTAL);
            soundSlider.addChangeListener(new SoundSliderListener());
            soundSlider.setPreferredSize(new Dimension(70,20));
            soundSlider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
            soundSlider.setValue(30);
            soundSlider.setOpaque(false);

            speakerButton = new SpeakerButton();      
            speakerButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!VLCPlayer.emp.isMute()){
                       VLCPlayer.emp.mute(true);
                    }
                    else{
                        VLCPlayer.emp.mute(false);
                    }
                }
            });
            sliderPanel.add(speakerButton);
            sliderPanel.add(soundSlider);
            
            bar = new JProgressBar(0,100);
            bar.setPreferredSize(new Dimension(this.getWidth(),10));
            bar.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e) {
                    long maxTime = VLCPlayer.emp.getLength();
                    double temp = e.getPoint().x / ((double)bar.getWidth());
                    long goTime = (long)(temp * maxTime);
                    VLCPlayer.emp.setTime(goTime);
                }   
            });

            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5)); 
            buttonPanel.add(stopButton);
            buttonPanel.add(moveLeftButton);
            buttonPanel.add(playpauseButton );
            buttonPanel.add(moveRightButton);

            // Borderlayout center
            midPanel = new JPanel();


            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BorderLayout(0,0));


            bottomPanel.add(buttonPanel,BorderLayout.WEST);
            bottomPanel.add(midPanel,BorderLayout.CENTER);
            bottomPanel.add(sliderPanel,BorderLayout.EAST);


            functionalities = new JPanel();
            functionalities.setLayout(new BorderLayout(0,0));
            functionalities.add(bar, BorderLayout.CENTER);
            functionalities.add(bottomPanel, BorderLayout.SOUTH);

            add(functionalities,BorderLayout.SOUTH);

            VLCPlayer.emp.setPlaySubItems(true);
            revalidate();
    }
        
    /* GETTERY */
    //         //
    public String getFile() {
            return this.file;
    }

    public Color getMidPanelBackG() {
        return midPanelBackG;
    }

    public Color getButtonsPanelBackG() {
        return buttonsPanelBackG;
    }

    public Color getBackGChooserButton() {
        return chooserButtonBackG;
    }

    public Color getBackGStopButton() {
        return stopButtonBackG;
    }

    public Color getBackGMoveLeft() {
        return moveLeftBackG;
    }

    public Color getBackGPlaypause() {
        return playpauseBackG;
    }
    
    public Color getMoveRightBackG() {
        return moveRightBackG;
    }

    public boolean isLocal() {
        return local;
    }
    
    public Color getSpeakerButtonColor() {
        return speakerButtonColor;
    }

    public String getUrl() {
        return url;
    }
     
    public Color getSpeakerBackG() {
        return speakerBackG;
    }
    
    public Color getProgressBarBackG() {
        return progressBarBackG;
    }
    
    
    /* SETTERS */
    //         //
    public void setLocal(boolean local) {
        this.local = local;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSpeakerButtonColor(Color speakerButtonColor) {
        this.speakerButtonColor = speakerButtonColor;
    }

    public void setSpeakerBackG(Color speakerBackG) {
        this.speakerBackG = speakerBackG;
    }

    public void setProgressBarBackG(Color progressBarBackG) {
        this.progressBarBackG = progressBarBackG;
    }
    
    public void setColorStopButton(Color newcolor){
        Color oldColor = stopButtonColor;
        stopButtonColor = newcolor;
        this.firePropertyChange("stopColor",
                               oldColor, newcolor);
    }

    public void setColorMoveLeftButton(Color newcolor){
        Color oldColor = moveLeftColor;
        moveLeftColor = newcolor;
        this.firePropertyChange("moveLeftColor",
                               oldColor, newcolor);
    }

    public void setColorPlayPauseButton(Color newcolor){
        Color oldColor = playpauseColor;
        playpauseColor = newcolor;
        this.firePropertyChange("playpauseColor",
                               oldColor, newcolor);
    }

    public void setColorMoveRightButton(Color newcolor){
        Color oldColor = moveRightColor;
        moveRightColor = newcolor;
        this.firePropertyChange("moveRightColor",
                               oldColor, newcolor);
    }

    public void setColorsoundSlider(Color newcolor){
        Color oldColor = soundSliderColor;
        soundSliderColor = newcolor;
        this.firePropertyChange("soundSliderColor",
                               oldColor, newcolor);
    }

    public void setFile(String file) {
            this.file = file;
    }
    
    public void setMidPanelBackG(Color midPanelBackG) {
        this.midPanelBackG = midPanelBackG;
    }
    
    public void setButtonsPanelBackG(Color buttonsPanelBackG) {
        this.buttonsPanelBackG = buttonsPanelBackG;
    }
   
    public void setBackGChooserButton(Color chooserButtonBackG) {
        this.chooserButtonBackG = chooserButtonBackG;
    }
    
    public void setBackGStopButton(Color stopButtonBackG) {
        this.stopButtonBackG = stopButtonBackG;
    }  
    
    public void setBackGMoveLeft(Color moveLeftBackG) {
        this.moveLeftBackG = moveLeftBackG;
    }

    public void setBackGPlaypause(Color playpauseBackG) {
        this.playpauseBackG = playpauseBackG;
    }

    public void setBackGMoveRight(Color moveRightBackG) {
        this.moveRightBackG = moveRightBackG;
    }
    
    public void setBackGSoundSlider(Color newcolor){
         this.soundSliderColor = newcolor;
     }
    
    public void setBackGSliderPanel(Color newcolor){
        this.sliderPanelBackG = newcolor;
    }
    
    @Override
    public synchronized void paintComponent(Graphics g){
        super.paintComponent(g);
        functionalities.setBackground(progressBarBackG);
        stopButton.setColor(stopButtonColor);
        moveLeftButton.setColor(moveLeftColor);
        playpauseButton.setColor(playpauseColor);
        moveRightButton.setColor(moveRightColor);
        soundSlider.setBackground(soundSliderColor);
        speakerButton.setColor(speakerButtonColor);

        stopButton.setBackgroundC(stopButtonBackG);
        moveLeftButton.setBackgroundC(moveLeftBackG);
        playpauseButton.setBackgroundC(playpauseBackG);
        moveRightButton.setBackgroundC(moveRightBackG);
        speakerButton.setBackgroundC(speakerBackG);
        buttonPanel.setBackground(buttonsPanelBackG);
        midPanel.setBackground(midPanelBackG);
        sliderPanel.setBackground(sliderPanelBackG);
    }

    private JPanel functionalities;
    private JPanel buttonPanel ;

    private String file;
    private int playerBorder;
    public static JProgressBar bar;

    private Color stopButtonColor;
    private Color moveLeftColor;
    private Color playpauseColor;
    private Color moveRightColor;
    private Color soundSliderColor;
    private Color speakerButtonColor;

    private Color chooserButtonBackG;
    private Color stopButtonBackG;
    private Color moveLeftBackG;
    private Color playpauseBackG;
    private Color moveRightBackG;
    private Color speakerBackG;
    private Color progressBarBackG;
    private Color buttonsPanelBackG;
    private Color midPanelBackG;
    private Color sliderPanelBackG;

    private boolean local;
    private boolean first;


    private static StopButton stopButton;
    private static MoveBackwardButton moveLeftButton;
    private static PlayPauseButton playpauseButton;
    private static MoveForwardButton moveRightButton;
    private static SpeakerButton speakerButton;
    public static JSlider soundSlider;
    public static JPanel midPanel;
    public static JPanel sliderPanel;

    private String url;

}

class SoundSliderListener implements ChangeListener{
       
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider j = (JSlider)e.getSource();
        if (!VLCPlayer.emp.isMute())
            VLCPlayer.emp.setVolume(j.getValue());
    }

}

