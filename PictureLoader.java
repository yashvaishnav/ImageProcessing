import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PictureLoader {
	
	//loads an array of Color objects
	public static Color[][] loadColor(String name){
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Image loaded: x = " + img.getWidth() + ", y = " + img.getHeight());
		Color[][] colors = new Color[img.getWidth()][img.getHeight()];
		for(int x = 0; x < colors.length; x ++){
			for(int y = 0; y < colors[0].length; y ++){
				Color c = new Color(img.getRGB(x, y), true);
			    colors[x][y] = c;
			}
		}
		return colors;
	}
	
	
	//loads and array of int 0-765
	public static int[][] loadInt(String name){
		Color[][] c = loadColor(name);
		int[][] a = new int[c.length][c[0].length];
		for(int x = 0; x < c.length; x ++){
			for(int y = 0; y < c[0].length; y ++){	
				a[x][y] = c[x][y].getBlue() + c[x][y].getGreen() + c[x][y].getRed();
			}
		}
		return a;
	}
	
	public static BufferedImage loadImg(String name){
		try {
		    return ImageIO.read(new File(name));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
