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
import javax.swing.AbstractButton;
import javax.swing.JButton;

/**
 *
 * @author Janusz
 */
public class StopButton extends JButton {
        
    private Color stopColor;
    private Color background;
    
    public StopButton(){
        this.setPreferredSize(new Dimension(32,32));
        this.stopColor = Color.BLACK;
        this.background = Color.BLUE;
    }
    
    @Override
    public synchronized void paint(Graphics g){
        Dimension d = getSize();
        this.setBackground(getBackground());
        int width = d.width ;
        int height = d.height;
        g.setColor(background);
        g.fillRect(0, 0, width, height);
        g.setColor(stopColor);
        g.fillRect(10, 10, width- 20, height- 20);
        
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return stopColor;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.stopColor = color;
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
