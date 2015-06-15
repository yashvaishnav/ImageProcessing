import java.awt.image.BufferedImage;


public class Grid {
	boolean[][] myFirstPic;
	public void addImage(boolean[][] sdf){
		myFirstPic = sdf;
	}
	
	public BufferedImage makeImg(){
		BufferedImage asdf = new BufferedImage(myFirstPic.length, myFirstPic[0].length,BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < myFirstPic.length; x ++){
			for(int y = 0; y < myFirstPic[0].length; y ++){
				if(myFirstPic[x][y])
					asdf.setRGB(x, y, 16777215);
				else
					asdf.setRGB(x, y, 0);
			}
		}
		return asdf;
	}
}
