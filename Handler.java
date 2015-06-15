import java.awt.image.BufferedImage;


public class Handler {
	Grid grid = new Grid();
	Process p = new Process();
	
	public Handler(){
		
	}
	


	public void refresh(double sd, int br) {
		//grid.makeImg();
	}
	public void addImage(String name, int pixG, int sp, int areaT){
		p.setInt(PictureLoader.loadInt(name));
		//p.createBool(sd);
		p.reiman();
		p.DFS(sp,pixG,areaT);
		
		grid.addImage(p.getBool());
		
		
	}
	public BufferedImage getImg(){
		return grid.makeImg();
	}
}
