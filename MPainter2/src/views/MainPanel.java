package views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

import shapes.Ellipse;
import shapes.Shape;

public class MainPanel extends JComponent implements MouseListener, MouseMotionListener{
    private static final int RESIZE = 1;
    private static final int MOVE = 2;
    private static final int MOVE_GROUP = 3;
    private static final int RESIZE_GROUP = 4;
    private static final int NONE = -1;
	private static MainPanel mainPanel;
    protected Shape selectedShape;  
    private int selectedShapeId = -1;
    
	Point startPt,endPt;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	Shape newShape;
	private int dragFlag;
	private Point dragPos;
	
	private MainPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		newShape = new Ellipse();
	}
	
	public static MainPanel getInstance() {
		if (mainPanel == null) {
			mainPanel = new MainPanel();
		}
		return mainPanel;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).draw(g);
        }
        if (newShape != null) {
            newShape.draw(g);
        }
        /*if (marquee != null) {
            marquee.draw(g);
        }*/
		/*for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).draw(g);
        }
        if (newShape != null) {
        	newShape.setBottomRightCorner(endPt);
    		newShape.setTopLeftCorner(startPt);
            newShape.draw(g);
        }*/
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startPt = e.getPoint();
		endPt = startPt;
		dragFlag = NONE;
        dragPos = startPt;
        
        
            //addChange = true;
        
        if (selectedShape != null) {
            if (!selectedShape.inBounds(startPt)) {
                selectedShape.setSelected(false);
                selectedShape = null;
            } else {
                dragFlag = MOVE;
            }
        }

        if (newShape != null) {

            if (newShape.getTopLeftCorner() == null) {
            	System.out.println("left corner setted");
                newShape.setTopLeftCorner(startPt);
            } else {
                newShape.setBottomRightCorner(startPt);
                System.out.println("right corner setted");
                shapes.add(newShape);
                //ShapesManager.getInstance().addToHistory();
                //newShape = null;
            }
        } else if (selectedShape != null && selectedShape.getAnchorForResize(startPt) != null) {
            dragFlag = RESIZE;
            dragPos = selectedShape.getAnchorForResize(startPt);
        } else {
            dragPos = startPt;
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (shapes.get(i).intersect(startPt)) {
                    if (selectedShape != null) {
                        selectedShape.setSelected(false);
                    }

                    shapes.get(i).setSelected(true);
                    selectedShapeId = i;
                    selectedShape = shapes.get(i);
                    dragFlag = MOVE;
                    break;
                }
            }
        }	
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int width = Math.abs(startPt.x - e.getX());
	    int height = Math.abs(startPt.y - e.getY()); 
	    
	    //Ellipse el = new Ellipse();
	    if (newShape != null) {
		    newShape.setBottomRightCorner(e.getPoint());
		    newShape.setTopLeftCorner(startPt);
		    shapes.add(newShape);			
		}
	    newShape = null;
		startPt = null;
		endPt = null;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//endPt = e.getPoint();
		Point curPt = e.getPoint();
		if (selectedShape != null && dragFlag != NONE) {
            if (dragFlag == MOVE) {
                selectedShape.translate(curPt.x - dragPos.x, curPt.y - dragPos.y);
                shapes.set(selectedShapeId, selectedShape); 
                dragPos = curPt;

            } else if (dragFlag == RESIZE) {
                selectedShape.resize(dragPos, curPt);
            }
        }
		if (newShape != null) {
            newShape.setBottomRightCorner(curPt);
            System.out.println("not null");
            
        }
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//endPt = e.getPoint();
		 Point curPt = e.getPoint();
         if (newShape != null) {
             newShape.setBottomRightCorner(curPt);
             //System.out.println("not null");
             repaint();
         }
	}

}
