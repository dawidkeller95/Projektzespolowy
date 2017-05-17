package prototyp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Local video player component object
 * @author Aleksander Januszewski
 * @version 1.0
 */
public class PlayerComponent extends JPanel {

    /**
     * @return the chooserButtonBackG
     */
    public Color getBackGChooserButton() {
        return chooserButtonBackG;
    }

    /**
     * @param chooserButtonBackG the chooserButtonBackG to set
     */
    public void setBackGChooserButton(Color chooserButtonBackG) {
        this.chooserButtonBackG = chooserButtonBackG;
    }

    /**
     * @return the stopButtonBackG
     */
    public Color getBackGStopButton() {
        return stopButtonBackG;
    }

    /**
     * @param stopButtonBackG the stopButtonBackG to set
     */
    public void setBackGStopButton(Color stopButtonBackG) {
        this.stopButtonBackG = stopButtonBackG;
    }

    /**
     * @return the moveLeftBackG
     */
    public Color getBackGMoveLeft() {
        return moveLeftBackG;
    }

    /**
     * @param moveLeftBackG the moveLeftBackG to set
     */
    public void setBackGMoveLeft(Color moveLeftBackG) {
        this.moveLeftBackG = moveLeftBackG;
    }

    /**
     * @return the playpauseBackG
     */
    public Color getBackGPlaypause() {
        return playpauseBackG;
    }

    /**
     * @param playpauseBackG the playpauseBackG to set
     */
    public void setBackGPlaypause(Color playpauseBackG) {
        this.playpauseBackG = playpauseBackG;
    }

    /**
     * @param moveRightBackG the moveRightBackG to set
     */
    public void setBackGMoveRight(Color moveRightBackG) {
        this.moveRightBackG = moveRightBackG;
    }

    /**
     * @param aMoveLeftButton the moveLeftButton to set
     */
    public static void setMoveLeftButton(MoveBackwardButton aMoveLeftButton) {
        moveLeftButton = aMoveLeftButton;
    }

    /**
     * @param aPlaypauseButton the playpauseButton to set
     */
    public static void setPlaypauseButton(PlayPauseButton aPlaypauseButton) {
        playpauseButton = aPlaypauseButton;
    }

    /**
     * @param aMoveRightButton the moveRightButton to set
     */
    public static void setMoveRightButton(MoveForwardButton aMoveRightButton) {
        moveRightButton = aMoveRightButton;
    }
        
        /**
        * Default constructor
        */
        public PlayerComponent(){
            this("c.mp4");
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
                
                downPanelColor = Color.LIGHT_GRAY;
                
		stopButton = new StopButton();
                stopButtonColor = Color.BLACK;
                stopButtonBackG = Color.LIGHT_GRAY;
		stopButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				VLCPlayer.emp.stop();
                                playpauseButton.play = true;
                                bar.setValue(0);
			}
		});
                
                
                moveLeftButton = new MoveBackwardButton();
                moveLeftColor = Color.BLACK;
                moveLeftBackG = Color.LIGHT_GRAY;
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
                playpauseColor = Color.BLACK;
                playpauseBackG = Color.LIGHT_GRAY;
		playpauseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				vlcplayer.playVideo();
                                if (VLCPlayer.emp.isPlaying()){
                                    VLCPlayer.emp.pause();
                                 //   setButtonIcon(playpauseButton,"play.png" );
                                }
                                else{
                                    VLCPlayer.emp.play();
                                 //   setButtonIcon(playpauseButton,"pause.png" );
                                }
                        }
		});
                
                moveRightButton = new MoveForwardButton();
                moveRightColor = Color.BLACK;
                moveRightBackG = Color.LIGHT_GRAY;
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
                JPanel sliderPanel = new JPanel();
                soundSlider = new JSlider();
                soundSlider.setOrientation(JSlider.HORIZONTAL);
              
                soundSlider.addChangeListener(new SoundSliderListener());
                soundSlider.setPreferredSize(new Dimension(70,20));
                soundSlider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
                soundSlider.setValue(30);
                
                SpeakerButton s = new SpeakerButton();
                s.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!VLCPlayer.emp.isMute()){
                           oldVolume = VLCPlayer.emp.getVolume();
                           VLCPlayer.emp.mute(true);
                        }
                        else{
                            VLCPlayer.emp.mute(false);
                        //   VLCPlayer.emp.setVolume(oldVolume);
                        }
                    }
                });
                sliderPanel.add(s);
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
                
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5)); 
                buttonPanel.add(stopButton);
		buttonPanel.add(moveLeftButton);
                buttonPanel.add(playpauseButton );
		buttonPanel.add(moveRightButton);
                
                // Borderlayout center
                JPanel empty = new JPanel();
                
                
		JPanel bottomPanel = new JPanel();
                bottomPanel.setLayout(new BorderLayout(0,0));
                
         
		bottomPanel.add(buttonPanel,BorderLayout.WEST);
                bottomPanel.add(empty,BorderLayout.CENTER);
                bottomPanel.add(sliderPanel,BorderLayout.EAST);
                
                
                functionalities = new JPanel();
                functionalities.setLayout(new BorderLayout(0,0));
                functionalities.add(bar, BorderLayout.CENTER);
                functionalities.add(bottomPanel, BorderLayout.SOUTH);
                
		add(functionalities,BorderLayout.SOUTH);
                
                revalidate();
	}
        
        /**
         * Sets icon from filepath to button
         * @param button where icon is attached
         * @param filepath of the icon  
         */
        public static void setButtonIcon(JButton button, String filepath){
            try {
                   Image img = ImageIO.read(new File(filepath));
                   button.setIcon(new ImageIcon(img));
                 
            } catch (IOException ex) {
                   System.err.println("No file found: "+filepath);
            }
        }
        
        /**
         * @return name of existing file on local disk 
         */
	public String getFile() {
		return this.file;
	}

        /**
         * @param newcolor new color of play button
         */
        public void setColorChooserButton(Color newcolor){
            Color oldColor = chooserButtonColor;
            chooserButtonColor = newcolor;
            this.firePropertyChange("playColor",
                                   oldColor, newcolor);
        }
        
        /**
         * @param newcolor new color of play button
         */
        public void setColorStopButton(Color newcolor){
            Color oldColor = stopButtonColor;
            stopButtonColor = newcolor;
            this.firePropertyChange("stopColor",
                                   oldColor, newcolor);
        }
        
        /**
         * @param newcolor new color of play button
         */
        public void setColorMoveLeftButton(Color newcolor){
            Color oldColor = moveLeftColor;
            moveLeftColor = newcolor;
            this.firePropertyChange("moveLeftColor",
                                   oldColor, newcolor);
        }
        
        /**
         * @param newcolor new color of play button
         */
        public void setColorPlayPauseButton(Color newcolor){
            Color oldColor = playpauseColor;
            playpauseColor = newcolor;
            this.firePropertyChange("playpauseColor",
                                   oldColor, newcolor);
        }
        
        /**
         * @param newcolor new color of play button
         */
        public void setColorMoveRightButton(Color newcolor){
            Color oldColor = moveRightColor;
            moveRightColor = newcolor;
            this.firePropertyChange("moveRightColor",
                                   oldColor, newcolor);
        }
        
        /**
         * @param newcolor new color of play button
         */
        public void setColorsoundSlider(Color newcolor){
            Color oldColor = soundSliderColor;
            soundSliderColor = newcolor;
            this.firePropertyChange("soundSliderColor",
                                   oldColor, newcolor);
        }
        
        /**
         * @param file name of existing file on local disk
         */
	public void setFile(String file) {
		this.file = file;
	}

        
        @Override
        public synchronized void paintComponent(Graphics g){
            super.paintComponent(g);
            functionalities.setBackground(downPanelColor);
            stopButton.setColor(stopButtonColor);
            moveLeftButton.setColor(moveLeftColor);
            playpauseButton.setColor(playpauseColor);
            moveRightButton.setColor(moveRightColor);
            soundSlider.setBackground(soundSliderColor);
            
            stopButton.setBackground(stopButtonBackG);
            moveLeftButton.setBackground(moveLeftBackG);
            playpauseButton.setBackground(playpauseBackG);
            moveRightButton.setBackground(moveRightBackG);
            
        }
        
        private JPanel functionalities;
        private JPanel buttonPanel ;

	private String file;
        private int playerBorder;
        public static  JProgressBar bar;
        private final PropertyChangeSupport mPcs = 
                new PropertyChangeSupport(this);
        
        private Color downPanelColor;
        
        private Color chooserButtonColor;
        private Color stopButtonColor;
        private Color moveLeftColor;
        private Color playpauseColor;
        private Color moveRightColor;
        private Color soundSliderColor;
        
        private Color chooserButtonBackG;
        private Color stopButtonBackG;
        private Color moveLeftBackG;
        private Color playpauseBackG;
        private Color moveRightBackG;
        
        private int oldVolume;
        
    //    public static JButton chooserButton;
        private static StopButton stopButton;
        private static MoveBackwardButton moveLeftButton;
        private static PlayPauseButton playpauseButton;
        private static MoveForwardButton moveRightButton;
        public static JSlider soundSlider;

    /**
     * @return the moveRightBackG
     */
    public Color getMoveRightBackG() {
        return moveRightBackG;
    }

    /**
     * @return the downPanelColor
     */
    public Color getDownPanelColor() {
        return downPanelColor;
    }

    /**
     * @param downPanelColor the downPanelColor to set
     */
    public void setDownPanelColor(Color downPanelColor) {
        this.downPanelColor = downPanelColor;
    }

}

class SoundSliderListener implements ChangeListener{
       
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider j = (JSlider)e.getSource();
        if (!VLCPlayer.emp.isMute())
            VLCPlayer.emp.setVolume(j.getValue());
    }

}

