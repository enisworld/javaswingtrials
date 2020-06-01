package views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import shapes.MCircle;
import shapes.MCube;
import shapes.MShape;

public class FrontViewPanel extends JComponent implements MouseListener, MouseMotionListener{
	ArrayList<MShape> mShapes = new ArrayList<MShape>();
	Point startDrag, endDrag;
	MShape currentShape;
	
	public FrontViewPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		/*System.out.println("Side View Panel");
		System.out.println("Points : X - Y : " + e.getX() + " - "+ e.getY());
		
*/		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.print("Front View Panel Move event ===>");
		System.out.println("Points : X - Y : " + e.getX() + " - "+ e.getY());
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method st
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startDrag = new Point(e.getX(), e.getY());
        endDrag = startDrag;
        //createCurrentMShape(startDrag,endDrag);
        repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		MShape m = createCurrentMShape(startDrag, new Point( e.getX(), e.getY()));
		mShapes.add(m);
		startDrag = null;
		endDrag = null;
		repaint();
		
	}
	
	public MShape createCurrentMShape(Point start, Point end) {
		currentShape = new MCube(start.x, start.y, Math.abs((start.x - end.x)), Math.abs((start.y - end.y)));
		return currentShape;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		 for (MShape s : mShapes) {
			 	s.draw(g);
		      }
		 /*if (startDrag != null && endDrag != null) {
		       g2.setPaint(Color.LIGHT_GRAY);
		       int width = Math.abs(startDrag.x - endDrag.x);
		       int height = Math.abs(startDrag.y - endDrag.y);
		       int d = Math.max(width,height);
		       //Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), Math.abs(x1 - x2), Math.abs(y1 - y2)
		       g2.drawOval(Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), d, d);
		 }*/
		
	}

}
