
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
public class ColorSelection {
	JButton jButtonApply;
	JTextField jTextFieldRed;
	JTextField jTextFieldGreen;
	JTextField jTextFieldBlue;
	JSlider jSliderRed;
	JSlider jSliderBlue;
	JSlider jSliderGreen;
	public ColorSelection() {
		JFrame jFrame = new JFrame();

		initialize(jFrame);
		jFrame.setTitle("JFrame Placement");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(300, 200);
		jFrame.setVisible(true);
	}
	private void repaintButtonColor(JButton jButton ) {
		int red = jSliderRed.getValue();
		int green = jSliderGreen.getValue();
		int blue = jSliderBlue.getValue();
		jTextFieldRed.setText(Integer.toString(red));
		jTextFieldGreen.setText(Integer.toString(green));
		jTextFieldBlue.setText(Integer.toString(blue));
		Color buttonColor = new Color(red, green, blue);
		jButton.setBackground(buttonColor);
		
		
	}
	private void initialize(JFrame jFrame) {
		
		JPanel mainPanel = new JPanel();
		//
		//mainPanel.setLayout(new GridBagLayout());
		//mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		JLabel jLabelRed = new JLabel("Red :");
		mainPanel.add(jLabelRed);

		jTextFieldRed = new JTextField("255");
		jTextFieldRed.setBounds(15, 15, 15, 15);
		jTextFieldRed.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				//repaintButtonColor(jButtonApply);			
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {

				//repaintButtonColor(jButtonApply);
			}
		});
		mainPanel.add(jTextFieldRed);

		JLabel jLabelGreen = new JLabel("Green :");
		mainPanel.add(jLabelGreen);
		
		jTextFieldGreen = new JTextField("255");
		jTextFieldGreen.setBounds(15, 15, 15, 15);
		jTextFieldGreen.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				//repaintButtonColor(jButtonApply);			
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {

				//repaintButtonColor(jButtonApply);
			}
		});

		mainPanel.add(jTextFieldGreen);

		JLabel jLabelBlue = new JLabel("Blue :");
		mainPanel.add(jLabelBlue);
		
		jTextFieldBlue = new JTextField("255");
		jTextFieldBlue.setBounds(15, 15, 15, 15);
		jTextFieldBlue.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				//repaintButtonColor(jButtonApply);			
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {

				//repaintButtonColor(jButtonApply);
			}
		});

		mainPanel.add(jTextFieldBlue);
		
		jButtonApply = new JButton("Apply");
		jButtonApply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mainPanel.add(jButtonApply);
		
		jSliderRed = new JSlider();
		jSliderRed.setMinimum(0);
		jSliderRed.setMaximum(255);
		jSliderRed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				repaintButtonColor(jButtonApply);
			}
        });
		mainPanel.add(jSliderRed);
		
		jSliderGreen = new JSlider();
		jSliderGreen.setMinimum(0);
		jSliderGreen.setMaximum(255);
		jSliderGreen.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				repaintButtonColor(jButtonApply);
			}
        });
		mainPanel.add(jSliderGreen);
		
		jSliderBlue = new JSlider();
		jSliderBlue.setMinimum(0);
		jSliderBlue.setMaximum(255);
		jSliderBlue.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				repaintButtonColor(jButtonApply);
			}
        });
		mainPanel.add(jSliderBlue);

		jFrame.getContentPane().add(mainPanel);
	}
}
