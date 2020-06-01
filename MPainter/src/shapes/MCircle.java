package shapes;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class MCircle extends MShape{
	//private int startX, startY, endX, endY;
	private int xc;
    private int yc;
    private int r;
    
    //private Point startPoint, endPoint;
	
	public MCircle(int startX, int startY, int width, int height) {
		super(startX, startY, width, height);
		setShapeType("Circle");
		/*this.endX = endX;
		this.endY = endY;*/
		this.setStartPoint(new Point(startX,startY));
	}
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(3));
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		calculateCenterPointsandRadius();
		
		int x = xc - r;
		int y = yc - r;
		int size = 2*r;
		g2.setPaint(getShapeColor());
		g2.drawOval(x, y, size, size);
		
		
		/*if (getStartPoint() != null && getEndPoint()!= null) {
		       g2.setPaint(Color.LIGHT_GRAY);
		       //MCircle m = new MCircle(, startPoint.y, , endPoint.y);
		       int width = Math.abs(getStartPoint().x - getEndPoint().x);
		        int height = Math.abs(getStartPoint().y - getEndPoint().y);
		        int d = Math.min(width,height);
		       g2.drawOval(getStartPoint().x, getStartPoint().y, d, d);
		}
		*/
		
		
		//System.out.println("MCircle.draw() --> Circle is drawn.");
	}
	
	private void calculateCenterPointsandRadius() {
        xc = (startX + width/2) ;
        yc = (startY + height/2);
        int d = Math.max(width,height);
        r = d/2;
        //System.out.println("calculateCenterPointsandRadius() --> Center Points and radius calculated.");

	}
	
	/*public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getEndX() {
		return endX;
	}
	public void setEndX(int endX) {
		this.endX = endX;
	}
	public int getEndY() {
		return endY;
	}
	public void setEndY(int endY) {
		this.endY = endY;
	}*/
	
}
