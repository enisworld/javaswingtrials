package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import org.jfree.svg.SVGGraphics2D;

import shapes.MCircle;
import shapes.MCylinder;
import shapes.MEllipse;
import shapes.MLine;
import shapes.MRectangle;
import shapes.MShape;

public class PerspectiveViewPanel extends JComponent implements MouseListener, MouseMotionListener{
	ArrayList<MShape> mShapes = new ArrayList<MShape>();
	Point startDrag, endDrag;
	MShape currentShape;
	SVGGraphics2D svgGraphics;
	public PerspectiveViewPanel() {
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		endDrag = new Point(e.getX(), e.getY());
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.print("Perspective View Panel move event ===>");
		System.out.println("Points : X - Y : " + e.getX() + " - "+ e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		int width = Math.abs(start.x - end.x);
	    int height = Math.abs(start.y - end.y); 
		currentShape = new MCircle(Math.min(start.x, end.x), Math.min(start.y, endDrag.y), width, height);
		return currentShape;
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		 for (MShape s : mShapes) {
			 	s.draw(g);
		      }
		 addShapetoSVG();
		 if (startDrag != null && endDrag != null) {
		       g2.setPaint(Color.LIGHT_GRAY);
		       g2.setStroke(new BasicStroke(3));
		       int width = Math.abs(startDrag.x - endDrag.x);
		       int height = Math.abs(startDrag.y - endDrag.y);
		       int d = Math.max(width,height);
		       //Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), Math.abs(x1 - x2), Math.abs(y1 - y2)
		       g2.drawOval(Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), d, d);
		 }
		
	}
	public void addShapetoSVG() {
		svgGraphics = null;
		svgGraphics = new SVGGraphics2D(this.getWidth(), this.getHeight());
		for (MShape s : mShapes) {
		 	if (s instanceof MLine) {
				
			} else if (s instanceof MCircle) {
				svgGraphics.drawOval(s.startX, s.startY, s.width, s.height);
			} else if (s instanceof MRectangle) {
				
			} else if (s instanceof MEllipse) {
				
			} else {

			}
	      }
		System.out.println("Shapes added to svg");
	}
	
}
