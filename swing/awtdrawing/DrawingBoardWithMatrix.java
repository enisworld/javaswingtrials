package java3dworks;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawingBoardWithMatrix extends JFrame {
	PaintSurface paintSurface; 
  public static void main(String[] args) {
    new DrawingBoardWithMatrix();
  }

  public DrawingBoardWithMatrix() {
    this.setSize(300, 300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponents(this);  
    paintSurface = new PaintSurface();
    this.add(paintSurface, BorderLayout.CENTER);
    this.setVisible(true);
  }
  
  public void addComponents(DrawingBoardWithMatrix frame) {
	  JPanel northPanel = new JPanel();
	  northPanel.setLayout(new GridBagLayout());
	  JButton btnLine = new JButton("Line");
	  btnLine.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			paintSurface.shapeType = "line";
		}
  	  });
	  JButton btnRect = new JButton("Rect");
	  btnRect.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			paintSurface.shapeType = "rect";
		}
	  });
	  JButton btnPlus = new JButton("+");
	  btnPlus.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			double ratio = 1.5;
			ArrayList<Shape> newShapes = new ArrayList<Shape>();
			for (Shape s : paintSurface.shapes) {
				if (s instanceof Line2D) {
					Line2D l = (Line2D)s;
					l.setLine(l.getX1()*ratio, l.getY1()*ratio, l.getX2()*ratio, l.getY2()*ratio);
					//newShapes.add(l);
				}
				else {
					Rectangle r = s.getBounds();
					int dx = (int)Math.floor(r.getWidth()*ratio);
					int dy = (int)Math.floor(r.getHeight()*ratio);
					int originX = getWidth() / 2;
			        int originY = getHeight() / 2;
					int x = originX - (dx / 2);
			        int y = originY - (dy / 2);
			        r.setBounds(originX, originY, dx, dy);
					//r.setSize((int)Math.floor(r.getWidth()*ratio), (int)Math.floor(r.getHeight()*ratio));
//					r.setSize(Integer.parseInt(Double.toString(Math.floor(r.getWidth()*ratio))), Integer.parseInt(Double.toString(Math.floor(r.getHeight()*ratio))));
					//newShapes.add(r);
					
				}
				
		        //g2.setPaint(colors[(colorIndex) % 6]);
		        //g2.draw(s);
		        //g2.setPaint(colors[(colorIndex++) % 6]);
		        //g2.fill(s);
		      }
			
			paintSurface.shapes = newShapes;
			repaint();
		}
  	  });
	  JButton btnMinus = new JButton("-");
	  btnMinus.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			for (Shape s : paintSurface.shapes) {
		        //g2.setPaint(colors[(colorIndex) % 6]);
		        //g2.draw(s);
		        //g2.setPaint(colors[(colorIndex++) % 6]);
		        //g2.fill(s);
		      }
		}
	  });
	  northPanel.add(btnLine);
	  northPanel.add(btnRect);
	  northPanel.add(btnPlus);
	  northPanel.add(btnMinus);
	  frame.add(northPanel,BorderLayout.NORTH);  
  }
  
  public class PaintSurface extends JComponent {
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    public String shapeType = "line";
    Point startDrag, endDrag;

    public PaintSurface() {
      this.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
        	System.out.println("shape type = " + shapeType);
          startDrag = new Point(e.getX(), e.getY());
          endDrag = startDrag;
          repaint();
        }

        public void mouseReleased(MouseEvent e) {
        	
        	Shape r = null;
            if (startDrag != null && endDrag != null) {
	          	if (shapeType == "line") {
	      			r = new Line2D.Float(startDrag.x, startDrag.y, e.getX(), e.getY());
	      		}else if (shapeType == "rect") {
	      			r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
	      		}
            }
          //Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
          shapes.add(r);
          startDrag = null;
          endDrag = null;
          repaint();
        }
      });

      this.addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseDragged(MouseEvent e) {
          endDrag = new Point(e.getX(), e.getY());
          repaint();
        }
      });
    }
    private void paintBackground(Graphics2D g2){
      g2.setPaint(Color.LIGHT_GRAY);
      for (int i = 0; i < getSize().width; i += 10) {
        Shape line = new Line2D.Float(i, 0, i, getSize().height);
        g2.draw(line);
      }

      for (int i = 0; i < getSize().height; i += 10) {
        Shape line = new Line2D.Float(0, i, getSize().width, i);
        g2.draw(line);
      }

      
    }
    public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      paintBackground(g2);
      Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN , Color.RED, Color.BLUE, Color.PINK};
      int colorIndex = 0;
      int w = 300, h = 100;
      
      g2.setStroke(new BasicStroke(2));
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
      /*AffineTransform affT = g2.getTransform();
      g2.scale(0.5, 0.5);
      super.paint(g2);
      g2.setTransform(affT);*/
      for (Shape s : shapes) {
        g2.setPaint(colors[(colorIndex) % 6]);
        g2.draw(s);
        g2.setPaint(colors[(colorIndex++) % 6]);
        g2.fill(s);
      }

      if (startDrag != null && endDrag != null) {
    	  g2.setPaint(Color.LIGHT_GRAY);
    	  Shape r = null;
    	if (shapeType == "line") {
			r = new Line2D.Float(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
		}else if (shapeType == "rect") {
			r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
		}
        g2.draw(r);
      }
    }

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
      return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }
  }
}
