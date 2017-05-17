/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototyp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;

/**
 *
 * @author Janusz
 */
public class MoveBackwardButton extends JButton{
    
    private Color stopColor;
    private Color background;
    
    public MoveBackwardButton(){
        this.setPreferredSize(new Dimension(32,32));
        this.stopColor = Color.BLACK;
        this.background = Color.LIGHT_GRAY;
    }
    
    @Override
    public synchronized void paint(Graphics g){
        Dimension d = getSize();
        
        int width = d.width;
        int height = d.height ;
        
        g.setColor(background);
        g.fillRect(0, 0, width, height);
        g.setColor(stopColor);
        int[] x1 = { 10,   width/2 , width/2 };
        int[] y1 = { height/2 , 10 , height - 10 };
        
        int[] x2 = { width/2, width - 10  ,width - 10  };
        int[] y2 = { height/2, 10 , height - 10  };
        
        g.fillPolygon(x1, y1, 3);
        g.fillPolygon(x2, y2, 3);
        
        
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
