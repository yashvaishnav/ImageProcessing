import java.awt.Color;
import java.util.ArrayList;


public class Process {
	
	//array of pixels
	boolean binary[][];//used for displaying, processing and determining pixel location
	int colors [][]; //storing raw data regarding pixels
	
	//averages of locals in an pixels area
	int pixel = 20; //20 pixel box that it takes the average color in; compares to local average to differentiate white from black
	int localAve[][];
	int localSD[][];
	
	
	//for DFS
	boolean processed[][];
	int SPAN;
	int maxX;
	int maxY;
	int minY;
	int minX;
	
	//for assembling Veins
	ArrayList<int[]> startPlaces;
	
	// old algorithm - checks if pixel brightness is greater than certain threshold
	public void createBool(double sd1) {
		binary = new boolean[colors.length][colors[0].length];
		initLocal();
		

				
		for(int x  = 0; x < binary.length; x ++){
			for(int y = 0; y < binary[0].length; y ++){

				if(colors[x][y] > localAve[x][y] + sd1 *localSD[x][y]){
					binary[x][y] = true;
				}
			}
		}
	}
	//initializes local averages and local SD
	public void initLocal(){
		localAve = new int[colors.length][colors[0].length];
		localSD = new int[colors.length][colors[0].length];
		// go thru each 20px box and average each pixel in that box
		// requires 3 for loops to calc avg and SD (first need avg)
		for(int x = 0; x < colors.length; x += pixel){
			for(int y = 0; y < colors[0].length; y += pixel){
				int sum = 0;
				int cnt = 0;
				for(int x1 = x; x1 < x+pixel; x1 ++){
					for(int y1 = y; y1 < y + pixel; y1 ++){
						if(x1 < colors.length && y1 < colors[0].length){
							cnt ++;
							sum += colors[x1][y1];
						}
					}
				}
				sum /= cnt;
				int sqSum = 0;
				cnt = 0;
				for(int x1 = x; x1 < x+pixel; x1 ++){
					for(int y1 = y; y1 < y + pixel; y1 ++){
						if(x1 < colors.length && y1 < colors[0].length){
							sqSum  += Math.pow((sum - colors[x1][y1]),2);
							cnt ++;
							localAve[x1][y1] = sum;
						}
					}
				}
				int sd = (int)Math.sqrt(sqSum /(cnt-1));
				for(int x1 = x; x1 < x+pixel; x1 ++){
					for(int y1 = y; y1 < y + pixel; y1 ++){
						if(x1 < colors.length && y1 < colors[0].length){
							localSD[x1][y1] = sd;
						}
					}
				}
				
			}
		}
	}
	
	//old algorithm - method removes blemishes in photo
	public void purify(int br){
		for(int k = 0; k < br; k ++){
			for(int x = 0; x < binary.length; x ++){
				for(int y = 0; y < binary[0].length; y ++){
					int[] posX = {-1,1,0,0};
					int[] posY = {0,0,1,-1};
					int simScore = 0;
					int cnt = 0;
					for(int i = 0; i < posX.length; i ++){
						int cX = x + posX[i];
						int cY = y + posY[i];
						if(cX < binary.length && cX >= 0 && cY < binary[0].length && cY >= 0 ){
							cnt ++;
							if(binary[x][y] == binary[cX][cY]){
								simScore ++;
							}
						}
					}
					if(simScore < cnt-3){
						binary[x][y] = !binary[x][y];
					}
				}
			}
		}
	}
	//  reiman sum (grid with averaging surrounding pixels)
	public void reiman(){
		binary = new boolean[colors.length][colors[0].length];
		initLocal();
		//create arrays that check out reiman sum as a table, use 10-4-2 to determine pixel contiunity
		// some random bright pixels are eliminated because you check 3x3 grid around it
		// noise is 1 or 2 pixels while lines are 3 pixels
		int posX[] = {1,1,1,0,0,0,-1,-1,-1};
		int posY[] = {-1,0,1,-1,0,1,-1,1,1};
		int ratings[] = {2,4,2,4,10,4,2,4,2};
		
		for(int x = 0; x < colors.length; x ++){
			for(int y = 0; y < colors[0].length; y ++){
				for(int i = 0; i < ratings.length; i ++){
					int XNew = x + posX[i];
					int YNew = y + posY[i];
					int scoreCnt = 0;
					int cnt = 0;
					if(XNew < colors.length && XNew >= 0 && YNew < colors[0].length && YNew >= 0){

						int colDif = colors[XNew][YNew] - localAve[XNew][YNew];
						if(colDif > 0){
							scoreCnt += colDif * ratings[i];
						}
						cnt ++;	
					}
					
					//find the local average
					if(cnt != 0 && scoreCnt/cnt > 100){
						binary[x][y] = true;
					}else{
						binary[x][y] = false;
					}
				}
			}
		}
	}
	// outer flooding algorithm, sets stuff up
	public void DFS(int span, int pixelClump, int areaTreshold){
		//System.out.println(areaThreshold);
		SPAN = span;
		processed  = new boolean[colors.length][colors[0].length];
		
		//save the coordinates of each connect segment to later assemble veins
		startPlaces = new ArrayList<int[]>();
		
		for(int x = 0; x < colors.length; x ++){
			for(int y = 0; y < colors[0].length; y ++){
				if(binary[x][y] && !processed[x][y]){
					startPlaces.add(new int[] {x,y});
					minX = Integer.MAX_VALUE;
					minY = Integer.MAX_VALUE;
					maxX = Integer.MIN_VALUE;
					maxY = Integer.MIN_VALUE;
					
					ArrayList<int[]> coords = new ArrayList<int[]>();
					boolean destroy = false;
			
					System.out.println("maxx = " + maxX + " min x: " + minX);
					if(DFSRecursive(x,y,coords,SPAN) < pixelClump || areaTreshold > (maxY - minY)*(maxX-minX) ) {
						destroy = true;
					}
					for(int i = 0; i < coords.size(); i ++){
						if(destroy){
							binary[coords.get(i)[0]][coords.get(i)[1]] = false;
						}else{
							binary[coords.get(i)[0]][coords.get(i)[1]] = true;
						}
					}
					
				}
			}
		}	
	}
	// recursive flooding algorithm that calls on a pixel and tries to flood pixels around it
	public int DFSRecursive(int x, int y, ArrayList<int[]> coords, int span){
		//used to find the spread of clump
		if(x < minX) minX = x;
		if(y < minY) minY = y;
		if(x > maxX) maxX = x;
		if(y > maxY) maxY = y;
		
		processed[x][y] = true;
		coords.add(new int[] {x,y});
		
		//deal with spanning over gaps
		int sumSize;
		if(binary[x][y]){
			span = SPAN;
			sumSize = 1;//initialized at 1 to add itself
		}
		else{
			sumSize = 0;
		}
		
		//end recursion if you have already spanned over two
		if(span == 0){
			return sumSize;
		}
		
		int directionsX [] = {1,1,1,0,0,-1,-1,-1};
		int directionsY[] = {-1,0,1,-1,1,-1,0,1};

		
		for(int i = 0; i < directionsX.length; i ++ ){
			int XNew = x + directionsX[i];
			int YNew = y + directionsY[i];
			if(XNew < processed.length && XNew >= 0 && YNew < processed[0].length && YNew >= 0){
				if(!processed[XNew][YNew] && binary[XNew][YNew]){
					sumSize += DFSRecursive(XNew,YNew,coords,span);
				}
				if(!processed[XNew][YNew]){
					sumSize += DFSRecursive(XNew,YNew,coords,span-1);
				}
			}
		}
		
		return sumSize;
	}
	
	public void setInt(int[][] asdf){
		colors = asdf;
	}
	public boolean[][] getBool(){
		return binary;
	}
	
	public ArrayList<Vein> getVeins(){
		VeinMaker vm = new VeinMaker(binary);
		vm.assemble();
		vm.getVeinMap();
	}
}
