  package examples001.bounds;

import java.awt.Point;

import examples001.bounds.BoundsDisplay;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class MoveRotate extends Application{
	double orgSceneX, orgSceneY;
	Circle circle1,circle2;
	Rectangle rect; 
	double prevAngle = 0; 
	final ObservableList<Shape>     shapes        = FXCollections.observableArrayList();
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		
		circle1 = new Circle(400, 250, 5);
		circle2 = new Circle(400, 50, 5);
		
		Line line1 = connect(circle1, circle2);
		rect = new Rectangle(375,300, 50,25);
		Line line2 = new Line(400, 265, 500, 265);
		Stop[] stop6 = new Stop[]{new Stop(0, Color.RED),
		        new Stop(0.25, Color.GREEN),
		        new Stop(0.50, Color.BLUE),
		        new Stop(0.75, Color.ORANGE),
		        new Stop(1, Color.YELLOW)};
		 
		LinearGradient gradient6 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stop6);
		
		rect.setFill(gradient6);
		
		circle1.setOnMousePressed(mousePressedEventHandler);
		circle1.setOnMouseDragged(mouseDraggedEventHandler);
		circle2.setOnMousePressed(mousePressedEventHandler2);
		circle2.setOnMouseDragged(mouseDraggedEventHandler2);
		Group shapeGroup = new Group();
		shapeGroup.getChildren().addAll(circle1, circle2, rect, line1, line2);
		
		for (Node node : shapeGroup.getChildrenUnmodifiable()) {
		      if (node instanceof Shape) {
		        shapes.add((Shape) node);
		      }
		    }
		
		Group layoutBoundsOverlay = new Group();
	    layoutBoundsOverlay.setMouseTransparent(true);
	    
	    for (Shape shape : shapes) {
	        //if (!(shape instanceof Anchor)) {
	          layoutBoundsOverlay.getChildren().add(new BoundsDisplay(shape, BoundsType.LAYOUT_BOUNDS));
	        //}
	      } 
	    
	    
		root.getChildren().addAll(shapeGroup, layoutBoundsOverlay);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Line connect(Circle c1, Circle c2)
	{
		Line line = new Line();
		 
		line.startXProperty().bind(c1.centerXProperty());
		line.startYProperty().bind(c1.centerYProperty());
		 
		line.endXProperty().bind(c2.centerXProperty());
		line.endYProperty().bind(c2.centerYProperty());
		 
		line.setStrokeWidth(1);
		line.setStrokeLineCap(StrokeLineCap.BUTT);
		line.getStrokeDashArray().setAll(1.0, 4.0);
	 
	return line;
	}
	
	private EventHandler<MouseEvent> mousePressedEventHandler = (t) ->
	{
		orgSceneX = t.getSceneX();
		orgSceneY = t.getSceneY();
		 
		// bring the clicked circle to the front
		 
		/*Circle c = (Circle) (t.getSource());
		c.toFront();*/
	};
	private EventHandler<MouseEvent> mouseDraggedEventHandler = (t) ->
	{
		double offsetX = t.getSceneX() - orgSceneX;
		double offsetY = t.getSceneY() - orgSceneY;
		System.out.println(t.getSceneY());
		Circle c = (Circle) (t.getSource());
		 
		c.setCenterX(c.getCenterX() + offsetX);
		c.setCenterY(c.getCenterY() + offsetY);
		
		circle2.setCenterX(circle2.getCenterX() + offsetX);
		circle2.setCenterY(circle2.getCenterY() + offsetY);
		
		orgSceneX = t.getSceneX();
		orgSceneY = t.getSceneY();
	};
	
	private EventHandler<MouseEvent> mousePressedEventHandler2 = (t) ->
	{
		orgSceneX = t.getSceneX();
		orgSceneY = t.getSceneY();
		 
		// bring the clicked circle to the front
		 
		/*Circle c = (Circle) (t.getSource());
		c.toFront();*/
	};
	private EventHandler<MouseEvent> mouseDraggedEventHandler2 = (t) ->
	{
		double offsetX = t.getSceneX() - orgSceneX;
		double offsetY = t.getSceneY() - orgSceneY;
		 
		Circle c = (Circle) (t.getSource());
		
		c.setCenterX(c.getCenterX() + offsetX);
		c.setCenterY(c.getCenterY() + offsetY);
		System.out.println(c.getCenterX() + " "+ c.getCenterY());
		/*double originAngle = calcRotationAngleInDegrees(orgSceneY-circle1.getCenterY(), orgSceneX-circle1.getCenterX()); 
		
		
		double newangle = calcRotationAngleInDegrees(t.getSceneY()-circle1.getCenterY(), t.getSceneX()-circle1.getCenterX());
		double angle = Math.abs((newangle-originAngle)/(1+newangle*originAngle));
		
		
		x1 -= x2; 
		y1 -= y2;
		x3 -= x2;
		y3 -= y2;

		double angle1 = atan2(y1, x1);
		double angle2 = atan2(y3, x3);

		if( originAngle < 0.0 ) originAngle += 2*Math.PI;
		if( newangle < 0.0 ) newangle += 2*Math.PI;*/
		
		
		double value = calculateAngleCosinusFormula(circle1.getCenterX(), circle1.getCenterY(), orgSceneX, orgSceneY, t.getSceneX(), t.getSceneY());
		boolean direction = getDirection(circle1.getCenterX(), circle1.getCenterY(), orgSceneX, orgSceneY, t.getSceneX(), t.getSceneY());
		double value2 = getDirectionAtan2(circle1.getCenterX(), circle1.getCenterY(), orgSceneX, orgSceneY, t.getSceneX(), t.getSceneY());
		
		//value = direction ? value:-1*value;
		
		//System.out.println("angle = " + value);
		//rect.setRotate((originAngle-newangle)*180/2*Math.PI);
		//System.out.println("rect.getRotate()  : " + rect.getRotate() +" prevAngle : " + Math.toDegrees(prevAngle) + " Equals : " + (rect.getRotate() == Math.toDegrees(prevAngle)));;
		rect.setRotate(rect.getRotate() + Math.toDegrees(value2));
		//System.out.println("rect.getRotate()  : " + rect.getRotate() + " Equality : " + (Math.toDegrees(prevAngle) + Math.toDegrees(value)));
		//prevAngle += value;
		orgSceneX = t.getSceneX();
		orgSceneY = t.getSceneY();
	};
	
	private double calculateAngleCosinusFormula(double x1,double y1,double x2,double y2,double x3,double y3) {
		
		//center : x1, y1;
		//press point : x2, y2;
		//lastp point : x3, y3;
		
		double cosQ = 0;
		try {
			double l1 = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));//p-c
			
			double l2 = Math.sqrt((x3-x1)*(x3-x1) + (y3-y1)*(y3-y1));//l-c
			
			double l3 = Math.sqrt((x2-x3)*(x2-x3) + (y2-y3)*(y2-y3));//p-l
			
			cosQ = (l1*l1 + l2*l2 - l3*l3)/(2*l1*l2);
			//System.out.println(cosQ);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error occured");
		}

		double val = Math.acos(cosQ);
		//System.out.println(val);
		return val;
	}
	
	private boolean getDirection(double x0, double y0, double x1, double y1, double x2, double y2) {
		//x0,y0 center coordinate
		//x1,y1 start pos coordinate
		//x2,y2 end pos coordinate		
		//return true  ---> pozitif
		//return false  ---> negatif
		boolean val = true;
		if ((x1-x0)> 0) {
			if ((y1-y0)> 0) {
				//1. bölge x, y pozitif
					if ((y2-y1)>0) {
						//return pozitif
					}
					else {
						//return negatif
						val = false;
					}
			}
			else {
				//y1-y0 <0
				//4. bölge x pozitif , y negatif
					if ((y2-y1)>0) {
						//return pozitif
					}
					else {
						//return negatif
						val = false;
					}
			}
		}else {
			if ((y1-y0)> 0) {
				//2. bölge x negatif, y pozitif
				if ((y2-y1)>0) {
					//return negatif
					val = false;
				}
				else {
					//return pozitif
				}

			}
			else {
				//y1-y0 <0
				//3. bölge x negatif , y negatif
				if ((y2-y1)>0) {
					//return negatif
					val = false;
				}
				else {
					//return pozitif
				}
			}
		}
		
		
		return val;
	}
	
	private double getDirectionAtan2(double x0, double y0, double x1, double y1, double x2, double y2) {
		//x0,y0 center coordinate
		//x1,y1 start pos coordinate
		//x2,y2 end pos coordinate		
		//return true  ---> pozitif
		//return false  ---> negatif
		double val = 0;
		
		
		double startPosinRad = Math.atan2((y1-y0), (x1-x0));
		double stopPosinRad = Math.atan2((y2-y0), (x2-x0));
		
		val = stopPosinRad - startPosinRad;
		return val;
	}
	public static double calcRotationAngleInDegrees(double x, double y)
	{
	    // calculate the angle theta from the deltaY and deltaX values
	    // (atan2 returns radians values from [-PI,PI])
	    // 0 currently points EAST.  
	    // NOTE: By preserving Y and X param order to atan2,  we are expecting 
	    // a CLOCKWISE angle direction.  
	    double theta = Math.atan2(y, x);

	    // rotate the theta angle clockwise by 90 degrees 
	    // (this makes 0 point NORTH)
	    // NOTE: adding to an angle rotates it clockwise.  
	    // subtracting would rotate it counter-clockwise
	    theta += Math.PI/2.0;

	    // convert from radians to degrees
	    // this will give you an angle from [0->270],[-180,0]
	    double angle = Math.toDegrees(theta);

	    // convert to positive range [0-360)
	    // since we want to prevent negative angles, adjust them now.
	    // we can assume that atan2 will not return a negative value
	    // greater than one partial rotation
	   /* if (angle < 0) {
	        angle += 360;
	    }*/

	    return theta;
	}
	
	
}
