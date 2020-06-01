package shapes;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class MCube extends MShape{
	/*private int startX;//Küpün çizileceði baþlangýç X deðeri
	private int startY;//Küpün çizileceði baþlangýç Y deðeri
	
	//private int size;//Küpün bir kenarýnýn uzunluðu
	private int width;//Küpün bir kenarýnýn uzunluðu
	private int height;//Küpün bir kenarýnýn uzunluðu*/
	private int shift;//Küpün çizileceði sýrada ön karenin ne kadar saðda olacaðý
	Point[] cubeOnePoints;
    Point[] cubeTwoPoints;
	public MCube(int startX, int startY, int width, int height) {
		super(startX, startY, width, height);

		updateShiftValue();
        cubeOnePoints = getCubeOnePoints();
        cubeTwoPoints = getCubeTwoPoints();

	}
	
	@Override
	public void draw(Graphics g) {
		System.out.println("Drawing started  ------------------");
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

	    
	    g2.drawRect(startX, startY, width, height);
        g2.drawRect(startX + shift, startY + shift, width, height);
        // draw connecting lines----
        for (int i = 0; i < 4; i++) {
            g2.drawLine(cubeOnePoints[i].x, cubeOnePoints[i].y, 
                    cubeTwoPoints[i].x, cubeTwoPoints[i].y);
        }
		
	}

	private void updateCubePoints() {
		cubeOnePoints = getCubeOnePoints();
        cubeTwoPoints = getCubeTwoPoints();
	}
	
	private void updateShiftValue() {
		this.shift = this.width/5;
	}
	
    private Point[] getCubeOnePoints() {
        Point[] points = new Point[4];
        points[0] = new Point(startX, startY);
        points[1] = new Point(startX + width, startY);
        points[2] = new Point(startX + width, startY + height);
        points[3] = new Point(startX, startY + height);
        return points;
    }

    private Point[] getCubeTwoPoints() {
        int newX = startX + shift;
        int newY = startY + shift;
        Point[] points = new Point[4];
        points[0] = new Point(newX, newY);
        points[1] = new Point(newX + width, newY);
        points[2] = new Point(newX + width, newY + height);
        points[3] = new Point(newX, newY + height);
        return points;
    }
	

	
	/*public void setSize(int size) {
		//this.size = size;
		updateShiftValue();
	}*/
}
