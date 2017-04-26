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

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * Local video player component object
 * @author Aleksander Januszewski
 * @version 1.0
 */
public class PlayerComponent extends JPanel {
        
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
		panel.add(vlcplayer.getCanvas());
		add(panel,BorderLayout.CENTER);		
		vlcplayer.refreshCanvas();
                vlcplayer.setFile(file);
                
		stopButton = new JButton();
                setButtonIcon(stopButton,"stop.png" );
		stopButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				VLCPlayer.emp.stop();
                                setButtonIcon(playpauseButton,"play.png" );
                                bar.setValue(0);
			}
		});
                
                
                moveLeftButton = new JButton();
                setButtonIcon(moveLeftButton,"pdt.png" );
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
                
		playpauseButton = new JButton();
                setButtonIcon(playpauseButton,"play.png" );
		playpauseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				vlcplayer.playVideo();
                                if (VLCPlayer.emp.isPlaying()){
                                    VLCPlayer.emp.pause();
                                    setButtonIcon(playpauseButton,"play.png" );
                                }
                                else{
                                    VLCPlayer.emp.play();
                                    setButtonIcon(playpauseButton,"pause.png" );
                                }
                        }
		});
                
                moveRightButton = new JButton();
                setButtonIcon(moveRightButton,"pdp.png" );
		moveRightButton .addActionListener(new ActionListener(){
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
                
                soundSlider = new JSlider();
                soundSlider.setOrientation(JSlider.HORIZONTAL);
                
                soundSlider.addChangeListener(new SoundSliderListener());
                
                
                bar = new JProgressBar(0,100);
               
                
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 6));
		//buttonPanel.add(chooserButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(moveLeftButton);
                buttonPanel.add(playpauseButton );
		buttonPanel.add(moveRightButton);
		buttonPanel.add(soundSlider);
		
                JPanel functionalities = new JPanel();
                functionalities.setLayout(new BoxLayout(functionalities,BoxLayout.Y_AXIS));
                functionalities.add(bar, BorderLayout.NORTH);
                functionalities.add(buttonPanel, BorderLayout.CENTER);
                
		add(functionalities,BorderLayout.SOUTH);
                
              
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
    //        chooserButton.setBackground(chooserButtonColor);
            stopButton.setBackground(stopButtonColor);
            moveLeftButton.setBackground(moveLeftColor);
            playpauseButton.setBackground(playpauseColor);
            moveRightButton.setBackground(moveRightColor);
            soundSlider.setBackground(soundSliderColor);
            
        }
    
	private String file;
        private int playerBorder;
        public static  JProgressBar bar;
        private final PropertyChangeSupport mPcs = 
                new PropertyChangeSupport(this);
        private Color chooserButtonColor;
        private Color stopButtonColor;
        private Color moveLeftColor;
        private Color playpauseColor;
        private Color moveRightColor;
        private Color soundSliderColor;
        
    //    public static JButton chooserButton;
        public static JButton stopButton;
        public static JButton moveLeftButton;
        public static JButton playpauseButton;
        public static JButton moveRightButton;
        public static JSlider soundSlider;

}

class SoundSliderListener implements ChangeListener{
       
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider j = (JSlider)e.getSource();
        VLCPlayer.emp.setVolume(j.getValue());
    }

}

