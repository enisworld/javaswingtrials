package demo;

import javax.swing.JFrame;

import views.MainPanel;

public class Demo1 {

	public static void main(String[] args) {
		JFrame fr = new JFrame();
		fr.setSize(400, 400);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.add(MainPanel.getInstance());
		fr.setVisible(true);

	}

}
