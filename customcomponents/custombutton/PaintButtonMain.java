package customcomponents.custombutton;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class PaintButtonMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        PaintButton paintButton = new PaintButton();
        
        frame.add(paintButton);
        frame.setVisible(true);

	}

}
