import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

 
public class Jdisplay extends JPanel{
	BufferedImage img;
	public Jdisplay(){
		this.setVisible(true);
		this.setBackground(new Color(0,0,0));
	}
	 public void paintComponent(Graphics g)
     { 
		 super.paintComponent(g);
		 g.fillRect(0, 0, 100, 100);
         if(img != null){
        	 g.drawImage(img,0,0,this);
         }
     }
	 public void updateImage(BufferedImage sdsf){
		 img = sdsf;
		 paintComponent(this.getGraphics());
	 }
	 
	 
}
