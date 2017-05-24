/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototyp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author Janusz
 */
public class PlayPauseButton extends JButton implements MouseListener {
    
    private Color color;
    private Color background;
    public boolean play;
    
    public PlayPauseButton(){
        this.setPreferredSize(new Dimension(32,32));
     //   this.color = Color.BLACK;
     //   this.background = Color.LIGHT_GRAY;
        this.play = true;
        this.addMouseListener(this);
    }
    
    @Override
    public synchronized void paint(Graphics g){
        Dimension d = getSize();
        
        int width = d.width ;
        int height = d.height;
        
        g.setColor(background);
        g.fillRect(0, 0, width, height);
        g.setColor(color);
        if (play){
            int[] x = { 10, 10 , width - 10 }  ;
            int[] y = { 10, height - 10 , height/2 }  ;

            g.fillPolygon(x, y, 3);
        }
        else{
            g.fillRect(width/2 - 6      , 10 , 3 , height - 20  );
            g.fillRect(width/2 + 2  , 10 , 3 , height - 20  );
        }
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
         if (play)
             play = false;
         else
             play = true;
         repaint();
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

    /**
     * @return the background
     */
    public Color getBackgroundC() {
        return background;
    }

    /**
     * @param background the background to set
     */
    public void setBackgroundC(Color background) {
        this.background = background;
    }
    
}
