package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JComponent{
	
	public MainPanel() {
		initilazeComponent();
	}
	private void initilazeComponent() {
		this.setLayout(new BorderLayout());
		
		JPanel shapesPanel = new JPanel();
		shapesPanel.setLayout(new FlowLayout());
		shapesPanel.add(new JButton("Line"));
		shapesPanel.add(new JButton("Rectangle"));
		shapesPanel.add(new JButton("Circle"));
		shapesPanel.add(new JButton("Cube"));
		shapesPanel.add(new JButton("Cylnder"));
		shapesPanel.setBorder(BorderFactory.createTitledBorder("Shapes Panel"));
		this.add(shapesPanel, BorderLayout.NORTH);
		

		JPanel functionalityPanel = new JPanel();
		functionalityPanel.setLayout(new BoxLayout(functionalityPanel,BoxLayout.PAGE_AXIS));
		
		JButton btnDrag = new JButton("Drag Item");
		btnDrag.setSize(70, 25);
		functionalityPanel.add(btnDrag);		
		
		JButton btnResize = new JButton("Resize Item");
		btnResize.setSize(70, 25);
		functionalityPanel.add(btnResize);
		functionalityPanel.setBorder(BorderFactory.createTitledBorder("Func. Panel"));
		this.add(functionalityPanel, BorderLayout.WEST);
		

		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel,BoxLayout.PAGE_AXIS));
		JButton btnProp = new JButton("Propertiesssss");
		btnProp.setSize(70, 25);
		propertiesPanel.add(btnProp);	
		propertiesPanel.setBorder(BorderFactory.createTitledBorder("Properties Panel"));
		this.add(propertiesPanel, BorderLayout.EAST);
		
		/*
		 * mainPanel.setBorder(BorderFactory.createTitledBorder("Main Panel"));
    	mainPanel.setLayout(new GridLayout(1,2));*/
		JPanel viewsPanel = new JPanel();
		GridLayout gL = new GridLayout(2, 2);
		viewsPanel.setLayout(gL);
		/*GridBagLayout gbl = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();
		*/
		//viewsPanel.setBackground(Color.PINK);
		viewsPanel.setBorder(BorderFactory.createTitledBorder("Main Panel"));
		//viewsPanel.setName("Main View");
		
		TopViewPanel topView = new TopViewPanel();
		topView.setBorder(BorderFactory.createTitledBorder("Top Panel"));

		SideViewPanel sideView = new SideViewPanel();
		sideView.setBorder(BorderFactory.createTitledBorder("Side Panel"));
		
		PerspectiveViewPanel perspectiveView = new PerspectiveViewPanel();
		perspectiveView.setBorder(BorderFactory.createTitledBorder("Perspective Panel"));
		
		FrontViewPanel frontView = new FrontViewPanel();
		frontView.setBorder(BorderFactory.createTitledBorder("Front Panel"));
		
		viewsPanel.add(topView);
		viewsPanel.add(sideView);
		viewsPanel.add(frontView);
		viewsPanel.add(perspectiveView);
		
		
		//viewsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(viewsPanel, BorderLayout.CENTER);
		
		
		
	}

}
