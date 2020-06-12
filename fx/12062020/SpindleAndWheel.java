package examples001;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
public class SpindleAndWheel extends Application
{
	private Circle circle;
	private Circle oppositeCircle;
	private Circle pivot;
	private Line spindle;

	private Group group;

	private Rotate rotateGroup;
	double dragInitTheta;
	double theta1,theta2;
	double angleOfRotation;

	private Translate translateGroup;
	private double dragTranslateGroupBackupX, dragTranslateGroupBackupY;  
	private double dragPivotInitX,dragPivotInitY,systemDragOffsetX,systemDragOffsetY;
	
	
	public static void main(String[] args) {
        launch(args);
    }
	@Override
	public void start(Stage stage){
	    circle = new Circle(150, 150, 30);
	    oppositeCircle = new Circle(350, 350, 30);
	    pivot = new Circle(250, 250, 5);
	    spindle = new Line(150, 150, 350, 350);

	    group = new Group(circle,oppositeCircle,pivot,spindle);

	/**************** Initializing Shapes **********/
	    circle.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.35));
	    circle.setStroke(Color.GREEN);
	    oppositeCircle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.35));
	    oppositeCircle.setStroke(Color.BLUE);
	    pivot.setFill(Color.RED.deriveColor(1, 1, 1, 0.35));
	    pivot.setStroke(Color.RED);
	    spindle.setStrokeWidth(3);
	    spindle.setStroke(Color.BLACK.deriveColor(1, 1, 1, 0.35));
	/****************************************************/

	/**************** Initializing Transforms **********/
	    rotateGroup = new Rotate(0, 0, 0);
	    translateGroup = new Translate(0, 0);
	/****************************************************/

	/**************** Adding Transforms **************/
	    group.getTransforms().addAll(translateGroup,rotateGroup);
	/****************************************************/
	    Pane root = new Pane(group);
	    Scene scene = new Scene(root,500,500);

	    stage.setScene(scene);
	    stage.show();

	/** Get angle of point of click with
	 * point of rotation, before drag begins **/
	    circle.setOnMousePressed(event -> {
	        dragInitTheta = angleOfRotation + dragInitTheta;
	        double pointOfRotationX = pivot.getCenterX() + translateGroup.getX();
	        double pointOfRotationY = pivot.getCenterY() + translateGroup.getY();


	        double initialSlope = Math.atan((event.getSceneY() - pointOfRotationY) / (event.getSceneX() - pointOfRotationX));
	        theta1 = Math.toDegrees(initialSlope);

	        if (event.getSceneX() < pointOfRotationX)
	            theta1 = (360 + theta1) % 360;
	        else
	            theta1 = 180 + theta1;

	    });
	/********************************************/

	/** Get angle of point of drags with
	* point of rotation, when the drag is on **/
	   circle.setOnMouseDragged(event -> {
	        double pointOfRotationX = pivot.getCenterX() + translateGroup.getX();
	        double pointOfRotationY = pivot.getCenterY() + translateGroup.getY();
	        double finalSlope = Math.atan((event.getSceneY() - pointOfRotationY) / (event.getSceneX() - pointOfRotationX));
	        theta2 = Math.toDegrees(finalSlope);

	        if (event.getSceneX() < pointOfRotationX)
	            theta2 = (360 + theta2) % 360;
	        else
	            theta2 = 180 + theta2;

	        angleOfRotation = theta2 - theta1;
	        rotateGroup.setPivotX(pivot.getCenterX());
	        rotateGroup.setPivotY(pivot.getCenterY());
	        rotateGroup.setAngle(angleOfRotation + dragInitTheta);
	   });
	/********************************************/

	/** Relocate the whole system when the point of
	 * rotation is dragged **/
	    pivot.setOnMousePressed(event -> {
	        dragPivotInitX = event.getSceneX();
	        dragPivotInitY = event.getSceneY();

	        dragTranslateGroupBackupX = translateGroup.getX();
	        dragTranslateGroupBackupY = translateGroup.getY();
	    });

	    pivot.setOnMouseDragged(event -> {
	        systemDragOffsetX = event.getSceneX() - dragPivotInitX;
	        systemDragOffsetY = event.getSceneY() - dragPivotInitY;

	        translateGroup.setX(dragTranslateGroupBackupX + systemDragOffsetX);
	        translateGroup.setY(dragTranslateGroupBackupY + systemDragOffsetY);

	    });
	/********************************************/

	}
}
