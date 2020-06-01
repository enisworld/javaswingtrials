package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import shapes.MCircle;
import shapes.MShape;

public class DrawCircleDemo extends JFrame {
	public static void main(String[] args) {
		new DrawCircleDemo();
	}
	public DrawCircleDemo() {
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(new PaintCircle(),BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	private class PaintCircle extends JComponent{
		ArrayList<MShape> mShapes = new ArrayList<MShape>();
		Point startDrag, endDrag;
		MShape currentShape;
		public PaintCircle() {
			this.addMouseListener(new MouseAdapter() {
				
				public void mousePressed(MouseEvent e) {
					startDrag = new Point(e.getX(), e.getY());
			          endDrag = startDrag;
			          //createCurrentMShape(startDrag,endDrag);
			          repaint();
				}
				
				public void mouseReleased(MouseEvent e) {
					MShape m = createCurrentMShape(startDrag, new Point( e.getX(), e.getY()));
					mShapes.add(m);
					startDrag = null;
					endDrag = null;
					repaint();
				}
				
				
			});
			
			this.addMouseMotionListener(new MouseMotionAdapter() {
		        public void mouseDragged(MouseEvent e) {
		          endDrag = new Point(e.getX(), e.getY());
		          //currentShape.setEndPoint(endDrag);
		          repaint();
		        }
		      });
		}
		
		public MShape createCurrentMShape(Point start, Point end) {
			currentShape = new MCircle(start.x, start.y, end.x, end.y);
			return currentShape;
		}
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			 for (MShape s : mShapes) {
				 	s.draw(g);
			      }
			 if (startDrag != null && endDrag != null) {
			       g2.setPaint(Color.LIGHT_GRAY);
			       int width = Math.abs(startDrag.x - endDrag.x);
			       int height = Math.abs(startDrag.y - endDrag.y);
			       int d = Math.max(width,height);
			       //Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), Math.abs(x1 - x2), Math.abs(y1 - y2)
			       g2.drawOval(Math.min(startDrag.x, endDrag.x), Math.min(startDrag.y, endDrag.y), d, d);
			 }
			
		}
		
		
		
		
		
	}
}
