package shapes;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class MCylinder extends MShape{
	Point[] cylinderTopPoints;
    Point[] cylinderBotPoints;
	
	public MCylinder(int startX, int startY, int width, int height) {
		super(startX, startY, width, height);
		cylinderTopPoints = getTopCylinderPoints();
		cylinderBotPoints = getBotCylinderPoints();
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
	    
	    g2.drawOval(startX, startY, width, height/3);
	    
	    g2.drawOval(startX, startY+height, width, height/3);
	    
	    
	    for (int i = 0; i < 2; i++) {
            g.drawLine(cylinderTopPoints[i].x, cylinderTopPoints[i].y, 
            		cylinderBotPoints[i].x, cylinderBotPoints[i].y);
        }
		
	}
	
	private Point[] getTopCylinderPoints() {
		Point[] points = new Point[2];
        points[0] = new Point(startX, startY+height/6);
        points[1] = new Point(startX + width, startY+height/6);
        return points;
	}

	private Point[] getBotCylinderPoints() {
		Point[] points = new Point[4];
        points[0] = new Point(startX, startY+7*height/6 );
        points[1] = new Point(startX + width, startY+7*height/6);
        return points;
	}
	
}
