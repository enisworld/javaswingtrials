package JSlider.Example.sliderwithbutton;

import java.awt.Button;
import java.awt.Color;
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
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CustomSlider extends Component {
	private int frameWidth = 800;
	private int frameHeight = 400;
	private int panelWidth = 300;
	private int panelHeight = 20;
	private int panelStart_X_Index = 150;
	private int panelStart_Y_Index= 50;
	//sliderSize degiskeni sliderdaki eleman sayisini ifade eder.
	private int sliderSize = 10;
	//sliderWidth slider'in genisligini ifade eder. burada panelin geniþliðgi ile ayni kabul edilmektedir.
	private int sliderWidth = 300;
	//moveValue herbir adimda ne kadarlýk bir degisim olacagini ifade eder. Burada yatayda bir degisiklik oldugundan x koordinatinda degisim yapilmatadir.
	int movingPart;
	private int selectedValueIndex = 0;
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
	Label labelCount;
	//int dragEnd = 0;

	
	public CustomSlider(int sliderSize) {
		this.sliderSize = sliderSize;
		initializeComponent();
		movingSize();
		//enableInputMethods(true);   
		//addMouseListener(this);
		//repaint();
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
		labelCount = new Label("0");
		labelCount.setBounds(panelStart_X_Index-20, panelStart_Y_Index, 20, panelHeight);
		frame.add(labelCount);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(rightButton.getX()+ rightButton.getWidth(), rightButton.getY(), 150, 150);
		//textArea.Scrol
		frame.add(textArea);
		
		panel = new Panel();
		panel.setBounds(panelStart_X_Index, panelStart_Y_Index+100, panelWidth/2, panelHeight*2);
		panel.setLayout(null);
		//panel.setBackground(Color.GRAY);
		Panel panel2 = new Panel();
		panel2.setBounds(panelStart_X_Index, panelStart_Y_Index+150, panelWidth/2, panelHeight*2);
		//panel2.setBackground(Color.GRAY);
		panel2.setLayout(null);
		
		label = new Label(createString());
		label.setBounds(panelStart_X_Index, panelStart_Y_Index+100, panelWidth, panelHeight);
		label.setBackground(Color.YELLOW);
		panel.add(label);
		
		JPanel container = new JPanel();
		container.add(panel);
		//container.add(panel2);
		JScrollPane jsp = new JScrollPane(container);
		frame.add(jsp);
		
		
		//frame.add(panel);		
		//frame.add(panelSlider);
	}

	private void initializeActionListener() {
        this.actionListenerLeft = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int locationX  = midButton.getX()-movingPart ;
				int locationY  = leftButton.getY();
				if ((leftButton.getX() + leftButton.getWidth()) < locationX) {
					midButton.setLocation(locationX , locationY);
				}
				else {
					midButton.setLocation(leftButton.getX() + leftButton.getWidth() , locationY);
				}
				updateLabelCount();
				moveLabel(label,true);
				
			}

		};
        this.actionListenerRight = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int locationX  = midButton.getX()+movingPart ;
				int locationY  = midButton.getY();
				if (rightButton.getX() > locationX + midButton.getWidth()) {
					midButton.setLocation(locationX , locationY);
				}	
				else {
					midButton.setLocation(rightButton.getX()-midButton.getWidth() , locationY);
				}
				updateLabelCount();
				moveLabel(label,false);
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
	
	private int movingSize() {
		// Bu metot ile sliderdaki her bir adimin ne kadar mesafede yapilacagini hesaplar.
		movingPart = (this.sliderWidth-this.leftButton.getWidth()*2)/this.sliderSize;
		return  movingPart;
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
	private void moveLabel(Label label, boolean direction/*true= left, false = right*/) {
		if (direction) {
			label.setBounds(label.getX()-movingPart, label.getY(), label.getWidth(), label.getHeight());			
		}
		else {
			label.setBounds(label.getX()+movingPart, label.getY(), label.getWidth(), label.getHeight());
			
		}
	}
	public int getSliderSize() {
		return sliderSize;
	}
	public void setSliderSize(int sliderSize) {
		this.sliderSize = sliderSize;
	}

	public int getSelectedValueIndex() {
		this.selectedValueIndex = (midButton.getX()-leftButton.getX()-leftButton.getWidth())/movingSize();
		return this.selectedValueIndex;
	}

	public void setSelectedValueIndex(int selectedValueIndex) {
		this.selectedValueIndex = selectedValueIndex;
	}
	private void updateLabelCount() {
		this.labelCount.setText(Integer.toString(getSelectedValueIndex()));
	}
	
}
