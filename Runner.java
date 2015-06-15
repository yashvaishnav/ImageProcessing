import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Runner {
	public static void main(String[] args){
		JFrame overall = new JFrame("eye processing");
		overall.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		overall.setSize(1000, 700);
		overall.setResizable(false);
		
		
		Jdisplay sd = new Jdisplay();
		sd.setPreferredSize(new Dimension(700,700));
		Joptions  a = new Joptions(sd);
		a.setPreferredSize(new Dimension(300,700));
		
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
		
		main.add(a);
		main.add(sd);
		overall.add(main);
		
		overall.setVisible(true);
	}
}
