import java.util.ArrayList;


public class Vein {
	int[] start;
	int[] end;
	ArrayList<Vein> cont;
	public Vein(int startX, int startY, int endX, int endY, int width){
		start = new int[2];
		end = new int[2];
		start[0] = startX;
		start[1] = startY;
		end[0] = endX;
		end[1] = endY;
		cont = new ArrayList<Vein>();
	}
}

