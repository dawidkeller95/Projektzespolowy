
package prototyp;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 * Media Player event adapter
 * @author Aleksander Januszewski
 */
public class VideoPlayerEventAdapter extends MediaPlayerEventAdapter {
    
    @Override
    public void timeChanged(MediaPlayer mp, long l) {       
        float videoProgress =  
             (float)((float)VLCPlayer.emp.getTime()/VLCPlayer.emp.getLength());
        PlayerComponent.bar.setValue(Math.round(videoProgress * 100));
    }
    @Override
    public void finished(MediaPlayer mp) {
         VLCPlayer.emp.stop();
    }
}
