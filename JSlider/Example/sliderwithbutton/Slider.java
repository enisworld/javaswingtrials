package sliderwithbutton;

import java.awt.Button;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Slider extends Component {
	private int frameWidth = 800;
	private int frameHeight = 400;
	private int panelWidth = 300;
	private int panelHeight = 20;
	private int panelStart_X_Index = 150;
	private int panelStart_Y_Index= 50;
	Frame frame;
	Panel panel;
	Button leftButton;
	Button rightButton;
	Button midButton;
    ActionListener actionListenerLeft;
    ActionListener actionListenerRight;
    ActionListener actionListenerMid;
	MouseMotionListener mouseMotionListener;
	Label label;
	int moveValue = 3;
	//int dragEnd = 0;

	
	public Slider() {
		initializeComponent();
		
		//enableInputMethods(true);   
		//addMouseListener(this);
		//repaint();
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		g.drawLine(panelStart_X_Index,panelStart_Y_Index+panelHeight, panelStart_X_Index+panelWidth, panelHeight);
		//g.drawLine(panelStart_X_Index,panelStart_Y_Index+panelHeight+25, panelWidth, panelHeight);
	}

	private void initializeComponent() {
		frame = new Frame();
		frame.setTitle("Frame Slider");
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);

		//Panel panelSlider = new Panel();
		//panelSlider.setBounds(panelStart_X_Index, panelStart_Y_Index, panelWidth, panelHeight);
		//panelSlider.setLayout(null);		
		

		
		
		initializeActionListener();
		
		leftButton = new Button("<");
		leftButton.setBounds(panelStart_X_Index, panelStart_Y_Index, panelWidth/8, panelHeight);
		leftButton.addActionListener(this.actionListenerLeft);
		//panelSlider.add(leftButton);
		frame.add(leftButton);

		rightButton = new Button(">");
		rightButton.setBounds(panelStart_X_Index+panelWidth-panelWidth/5, panelStart_Y_Index, panelWidth/8, panelHeight);
		rightButton.addActionListener(this.actionListenerRight);
		//panelSlider.add(rightButton);
		frame.add(rightButton);
		
		midButton = new Button("|||");
		midButton.setFont(new Font("Arial", Font.BOLD, 8));
		midButton.setBounds(panelStart_X_Index+panelWidth/8, panelStart_Y_Index, panelWidth/8, panelHeight);
		midButton.addActionListener(this.actionListenerMid);
		initializeMouseMotionListener();
		midButton.addMouseMotionListener(mouseMotionListener);
		//panelSlider.add(midButton );
		frame.add(midButton);
		panel = new Panel();
		panel.setBounds(panelStart_X_Index, panelStart_Y_Index+100, panelWidth/2, panelHeight*2);
		panel.setLayout(null);
		frame.add(panel);
		
		label = new Label(createString());
		label.setBounds(panelStart_X_Index, panelStart_Y_Index+100, panelWidth, panelHeight*2);
		panel.add(label);
		
		//frame.add(panelSlider);
	}

	private void initializeActionListener() {
        this.actionListenerLeft = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				int locationX  = midButton.getX()-moveValue ;
//				int locationY  = leftButton.getY();
//				if ((leftButton.getX() + leftButton.getWidth()) < locationX) {
//					midButton.setLocation(locationX , locationY);
//				}
//				else {
//					midButton.setLocation(leftButton.getX() + leftButton.getWidth() , locationY);
//				}
				moveLabel(label);
				
			}

		};
        this.actionListenerRight = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int locationX  = midButton.getX()+moveValue ;
				int locationY  = midButton.getY();
				if (rightButton.getX() > midButton.getX()+midButton.getWidth()) {
					midButton.setLocation(locationX , locationY);
				}	
				else {
					midButton.setLocation(rightButton.getX()-midButton.getWidth() , locationY);
				}
			}
		};
        this.actionListenerMid = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked");
			}
		};
	}
	
	private void initializeMouseMotionListener() {

		this.mouseMotionListener = new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {

				Point getloc = e.getLocationOnScreen();
				int x = getloc.x-midButton.getWidth()/2;
				int y = getloc.y - midButton.getHeight()*2;
				Rectangle tect = frame.getBounds();
				if (x<tect.x) {
					x= tect.x;
				}
				else if (x>tect.x+tect.width-midButton.getWidth()*2) {
					x = tect.x+tect.width-midButton.getWidth()*3/2;
				}
				if (y<tect.y) {
					y= tect.y;
				}
				else if (y>tect.y+ tect.height- midButton.getHeight()*3) {
					y = tect.y+ tect.height- midButton.getHeight()*3;
				}
				midButton.setLocation(x,y);

			}
		};
	}
	
	private int movingSize(ActionEvent e) {
		return 0;
	}
	
	private void editFrameWidthHeight() {
		Rectangle r = frame.getBounds();
		frameHeight = r.height;
		frameWidth = r.width;
	}
		
	private void updateButtonLocation() {
		
	}

	private String createString() {
		String str = "";
		for (int i = 0; i < 300; i++) {
			str = str + i;
		}
		return str;
	}
	private void moveLabel(Label label) {
		label.setBounds(label.getX()-moveValue, label.getY(), label.getWidth(), label.getHeight());
	}
	
}
