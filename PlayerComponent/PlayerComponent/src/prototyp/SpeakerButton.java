/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototyp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author Janusz
 */
public class SpeakerButton extends JButton implements MouseListener{
    private Color speakerColor;
    private Color background;
    
    public boolean soundOn;
    
    
    
    public SpeakerButton(){
        this.setPreferredSize(new Dimension(20,32));
        this.speakerColor= Color.BLACK;
        this.background = Color.LIGHT_GRAY;
        this.soundOn = true;
        this.addMouseListener(this);
    }
    
    @Override
    public synchronized void paint(Graphics g){
        Dimension d = getSize();
        
        int width = d.width;
        int height = d.height ;
        
        g.setColor(background);
        g.fillRect(0, 0, width, height);
        g.setColor(speakerColor);
        int[] x1 = { 5, width - 5 , width - 5 };
        int[] y1 = { height/2, 5  , height - 5  };
        
        g.fillRect(5, height/3, width/3 , height - height*2 / 3 );
        g.fillPolygon(x1, y1, 3);
        
       
        // if sound's off draw half-cross additionaly
        if (!soundOn){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(5, 5, width - 5, height - 5);
        }
        
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return speakerColor;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.speakerColor = color;
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (soundOn){
            soundOn = false;
            this.repaint();
        }
        else{
            soundOn = true;
            this.repaint();
        }
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
}
