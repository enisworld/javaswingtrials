package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Paint;
import java.awt.Point;

//import java.awt.Shape;
public abstract class MShape /*implements Shape*/{
    //private Paint fillColor = Color.LIGHT_GRAY;
	//private Paint strokeColor = Color.CYAN;
	private Color shapeColor = Color.BLACK;
	private Point startPoint, endPoint;
	private String shapeType = "";
	public int startX, startY, width, height;
	private double roll = 0, pitch = 0,yaw = 0;

	
	public MShape(int startX, int startY, int width, int height) {
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;

	}
	
	public abstract void draw(Graphics g);
	
	public void drag(Graphics g) {
		
	}
	public void resize(Graphics g) {
		
	}
	
	
	
	public void getMShapeLog() {
		System.out.println(shapeType + " is drawn.");
	}

	public double getRoll() {
		return roll;
	}

	public void setRoll(double roll) {
		this.roll = roll;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}
	
	public Color getShapeColor() {
		return shapeColor;
	}

	public void setShapeColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	public void updateEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	
	public String getShapeType() {
		return shapeType;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}
	
    /*public Paint getFillColor() {
		return fillColor;
	}
	public void setFillColor(Paint fillColor) {
		this.fillColor = fillColor;
	}
	public Paint getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(Paint strokeColor) {
		this.strokeColor = strokeColor;
	}*/

}
