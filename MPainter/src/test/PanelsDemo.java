package test;

import javax.swing.JFrame;

import views.MainPanel;

public class PanelsDemo {

	public static void main(String[] args) {
		JFrame fr = new JFrame();
		fr.setSize(1500, 900);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.add(new MainPanel());
		fr.setVisible(true);
	}

}
