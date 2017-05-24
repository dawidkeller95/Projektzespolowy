/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototyp;

/**
 *
 * @author Janusz
 */
public class PlaySelect {
    
    public static String url;
    public static boolean local;
    
    public PlaySelect(){
        
    }
    
    public static void play(){
        if (local)
            VLCPlayer.emp.play();
        else
            VLCPlayer.emp.playMedia(url);
    }

}
