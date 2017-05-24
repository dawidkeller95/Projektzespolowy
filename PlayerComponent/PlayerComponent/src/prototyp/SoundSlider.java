/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototyp;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JSlider;

/**
 *
 * @author Janusz
 */
public class SoundSlider extends JSlider {
    
    public SoundSlider(){
        this.setOpaque(false);
        this.setBackground(Color.BLUE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

        super.paintComponent(g);
    }
    
}
