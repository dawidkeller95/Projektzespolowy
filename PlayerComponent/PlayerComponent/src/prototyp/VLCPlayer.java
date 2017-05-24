package prototyp;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JOptionPane;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * Video player class
 * @author Aleksander Januszewski
 */
public class VLCPlayer {
    
    /**
     * Constructor. Sets libraries and mediaplayer object
     */
    public VLCPlayer(){
        currentFile = "";
        //NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), 
          //      "lib");
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), 
                "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);
        mpf = new MediaPlayerFactory();
        emp = mpf.newEmbeddedMediaPlayer();
        emp.addMediaPlayerEventListener(new VideoPlayerEventAdapter());
    }    
    
    /**
     * Play video
     */
    public void playLocalVideo(){
        if(currentFile.compareTo(file) != 0){
            emp.setVideoSurface(mpf.newVideoSurface(canvas));
            emp.setEnableMouseInputHandling(false);
            emp.setEnableKeyInputHandling(false);
            emp.prepareMedia(file);
            currentFile = "" + file;
        }
    }
    
    /**
     * Play video
     */
    public void playYoutubeVideo(String url){
        emp.setVideoSurface(mpf.newVideoSurface(canvas));
        emp.setEnableMouseInputHandling(false);
        emp.setEnableKeyInputHandling(false);
        emp.playMedia(url);
    }
    
    /**
     * Initializes canvas where video is played
     */
    public void initializeCanvas(){
        canvas = new java.awt.Canvas();
        canvas.setBackground(Color.black);
        canvas.setBounds(new Rectangle(200,100));

        refreshCanvas();
    }
    
    /** 
     * @return canvas where video is played 
     */
    public Canvas getCanvas(){
        return canvas;
    }
    
    /**
     * Invalidate and repaint canvas
     */
    public void refreshCanvas(){
        canvas.revalidate();
        canvas.repaint();
    }
    
    /**
     * @param newfile sets filename 
     */
    public void setFile(String newfile){
        file = newfile;
    }

    public static EmbeddedMediaPlayer emp;
    private final MediaPlayerFactory mpf;
    private String file;
    private String currentFile;
    private Canvas canvas;

}
