import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Joptions extends JPanel implements ActionListener{
	

	
	
	//components
	JSlider stanDev;
	JSlider blemishRemove;
	JSlider areaT;
	JTextField addImage;
	JButton addImageB;
	JButton refresh;
	
	//handler
	Handler handler = new Handler();
	
	//graphics pane
	Jdisplay imageDisplay;
	
	public Joptions(Jdisplay sdf){
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		imageDisplay = sdf;
		
		this.add(new JLabel("Pixel Grouping (0-200)"));
		stanDev = new JSlider(JSlider.HORIZONTAL, 0, 200, 70);
		this.add(stanDev);	
		
		this.add(new JLabel("spanning (1-5)"));
		blemishRemove = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
		this.add(blemishRemove);
		
		
		this.add(new JLabel("area Threshold (0-1000)"));
		areaT = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
		this.add(areaT);
		
		this.add(new JLabel("add Image"));
		addImage = new JTextField(20);
		addImage.setText("eye1");
		this.add(addImage);
		addImageB = new JButton();
		addImageB.addActionListener(this);
		this.add(addImageB);
		
		
		refresh = new JButton("refresh");
		refresh.addActionListener(this);
		this.add(refresh);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == refresh){
			handler.refresh((stanDev.getValue()), blemishRemove.getValue());
			imageDisplay.updateImage(handler.getImg());
			
		}else if(src == addImageB){
			handler.addImage(addImage.getText() + ".jpg", stanDev.getValue(), blemishRemove.getValue(),areaT.getValue());
			imageDisplay.updateImage(handler.getImg());
		}
		
	} 
}
