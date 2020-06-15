package painter15062020.dragdrop;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class BoundBox extends Group{
	private Parent parentofBoundBox;
	private final BoundRectangle boundary = new BoundRectangle(this, (oldX, oldY, newX, newY, isreleased) ->{
		if (isreleased) {
			updateTranslateBound();
			updateAnchors();
			resizeRelocateTarget();
		}
	});
	private final BoundRectangle translateBound = new BoundRectangle();
	private Node targetNode; 
	private Anchor topLeftAnchor 		= new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY, isreleased) -> {
		double newWidth = boundary.getWidth() - (newX - oldX);
        if (newWidth > 0) {
            boundary.setX(newX);
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() - (newY - oldY);
        if (newHeight > 0) {
            boundary.setY(newY);
            boundary.setHeight(newHeight);
        }
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateTranslateBound();
			updateAnchors();
			resizeRelocateTarget();
		}
	}, null);
	private Anchor topCenterAnchor 		= new Anchor(Color.GHOSTWHITE, false, true, (oldX, oldY, newX, newY, isreleased) -> {
		double newHeight = boundary.getHeight() - (newY - oldY);
        if (newHeight > 0) {
            boundary.setY(newY);
            boundary.setHeight(newHeight);
        }
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateTranslateBound();
			updateAnchors();
			resizeRelocateTarget();
		}
	}, null);
	private Anchor topRightAnchor 		= new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY, isreleased) -> {
		double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() - (newY - oldY);
        if (newHeight > 0) {
            boundary.setY(newY);
            boundary.setHeight(newHeight);
        }
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateTranslateBound();
			updateAnchors();
			resizeRelocateTarget();
		}
	}, null);
	private Anchor bottomLeftAnchor 	= new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY, isreleased) -> {
		double newWidth = boundary.getWidth() - (newX - oldX);
        if (newWidth > 0) {
            boundary.setX(newX);
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateTranslateBound();
			updateAnchors();
			resizeRelocateTarget();
		}
	}, null);
	private Anchor bottomCenterAnchor 	= new Anchor(Color.GHOSTWHITE, false, true, (oldX, oldY, newX, newY, isreleased) -> {
		double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateAnchors();
			resizeRelocateTarget();
		}
	}, null);
	private Anchor bottomRightAnchor 	= new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY, isreleased) -> {
		double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateTranslateBound();
			updateAnchors();
			resizeRelocateTarget();
		}
	}, null);
	private Anchor leftCenterAnchor 	= new Anchor(Color.GHOSTWHITE, true, false, (oldX, oldY, newX, newY, isreleased) -> {
		double newWidth = boundary.getWidth() - (newX - oldX);
        if (newWidth > 0) {
            boundary.setX(newX);
            boundary.setWidth(newWidth);
        }
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateTranslateBound();
			updateAnchors();
			resizeRelocateTarget();
		}
	}, null);
	private Anchor rightCenterAnchor 	= new Anchor(Color.GHOSTWHITE, true, false, (oldX, oldY, newX, newY, isreleased) -> {
		double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateTranslateBound();
			updateAnchors();
			resizeRelocateTarget();
		}
	}, null);
	private Anchor rotateAnchor 		= new Anchor(Color.LAWNGREEN, false, false, null, (oldX, oldY, newX, newY, isreleased) -> {
		double angle = boundary.getRotate();
		
		Bounds boundsParent = getParentBound();
		angle += getAngleAtan2(boundsParent.getMinX() + boundary.getX()+boundary.getWidth()/2
						, boundsParent.getMinY() + boundary.getY()+ boundary.getHeight()/2
						, oldX
						, oldY
						, newX
						, newY);
		boundary.setBoundStyleColor(boundary, Color.DARKGRAY);
		boundary.setRotate(angle);
		if (isreleased) {
			boundary.setBoundStyleColor(boundary, Color.BLACK);
			updateTranslateBound();
			updateAnchors();
			rotateTargetNode(angle);
		}
	});
	
	
	public BoundBox(Parent parent, Node node) {
		
		parentofBoundBox = parent;
		getParentBound();
		targetNode = node;
		double startX = 0, startY = 0, groupWidth = 0, groupHeight = 0;
		if (targetNode != null) {
			for (Node shape : ((Group)targetNode).getChildren()) {
				if (shape instanceof ImageView) {
					ImageView imageView = (ImageView)shape; 
					startX = imageView.getX();
					startY = imageView.getY();
					groupWidth = imageView.getBoundsInParent().getWidth();
					groupHeight = imageView.getBoundsInParent().getHeight();
				}
				else if (shape instanceof Rectangle) {
					Rectangle rectangle = (Rectangle) shape;
					
					startX = rectangle.getX();
					startY = rectangle.getY();
					groupWidth = rectangle.getBoundsInParent().getWidth();
					groupHeight = rectangle.getBoundsInParent().getHeight();
				}
				else if (shape instanceof Ellipse) {
					Ellipse ellipse = (Ellipse) shape;
					startX = ellipse.getBoundsInParent().getMinX();
					startY = ellipse.getBoundsInParent().getMinY();
					groupWidth = ellipse.getBoundsInParent().getWidth();
					groupHeight = ellipse.getBoundsInParent().getHeight();
				}
				
			}
		}
		boundary.setX(startX);
		boundary.setY(startY);
		boundary.setWidth(groupWidth);
		boundary.setHeight(groupHeight);
		boundary.setRotate(targetNode.getRotate());
		
		translateBound.setX(startX);
		translateBound.setY(startY);
		translateBound.setWidth(groupWidth);
		translateBound.setHeight(groupHeight);
		translateBound.setRotate(targetNode.getRotate());
		/*boundary.setTranslateX(startX);
		boundary.setTranslateY(startY);*/
		//translateBound.toBack();
		getChildren().addAll(boundary, translateBound);
		boundary.toFront();
		attachAnchors();
		
	}	
	public Bounds getParentBound(){
		Bounds bounds = ((Parent)((GroupObject)parentofBoundBox).getParentObject()).getBoundsInParent();
		/*System.out.println("Bounds of GroupObject Parent : " + bounds);
		System.out.println("Bounds of parentofBoundBox : " + parentofBoundBox);
		System.out.println("Bounds of (GroupObject)parentofBoundBox).getParentObject() : " + ((GroupObject)parentofBoundBox).getParentObject());*/
		boundary.setBound(bounds);
		return bounds;
	}
	private void updateTranslateBound() {
		translateBound.setX(boundary.getX());
		translateBound.setY(boundary.getY());
		translateBound.setWidth(boundary.getWidth());
		translateBound.setHeight(boundary.getHeight());
		translateBound.setRotate(boundary.getRotate());
		
	}
	/**
	 * Bu metot objenin etrafýndaki anchor larý BoundBox grubuna ekliyor.
	 */
	private void attachAnchors() {
		updateAnchors();
		getChildren().addAll(
				topLeftAnchor
				,topCenterAnchor
				,topRightAnchor
				,bottomLeftAnchor
				,bottomCenterAnchor
				,bottomRightAnchor
				,leftCenterAnchor
				,rightCenterAnchor
				,rotateAnchor
                );
	}
	/**
	 * Bu metot Anchorlarýn yeni pazisyonlarýný set ediyor.
	 */
	private void updateAnchors(){
		topLeftAnchor.setCenterX(boundary.getX());
        topLeftAnchor.setCenterY(boundary.getY());
        topCenterAnchor.setCenterX(boundary.getX() + boundary.getWidth()/2);
        topCenterAnchor.setCenterY(boundary.getY());
        topRightAnchor.setCenterX(boundary.getX() + boundary.getWidth());
        topRightAnchor.setCenterY(boundary.getY());
        bottomLeftAnchor.setCenterX(boundary.getX());
        bottomLeftAnchor.setCenterY(boundary.getY()+ boundary.getHeight());
        bottomCenterAnchor.setCenterX(boundary.getX()+ boundary.getWidth()/2);
        bottomCenterAnchor.setCenterY(boundary.getY()+ boundary.getHeight());
        bottomRightAnchor.setCenterX(boundary.getX()+ boundary.getWidth());
        bottomRightAnchor.setCenterY(boundary.getY()+ boundary.getHeight());
        leftCenterAnchor.setCenterX(boundary.getX());
        leftCenterAnchor.setCenterY(boundary.getY()+ boundary.getHeight()/2);
        rightCenterAnchor.setCenterX(boundary.getX()+ boundary.getWidth());
        rightCenterAnchor.setCenterY(boundary.getY()+ boundary.getHeight()/2);
        rotateAnchor.setCenterX(boundary.getX() + boundary.getWidth()/2);
        rotateAnchor.setCenterY(boundary.getY() - 20);
        
        rotateAnchors(boundary.getRotate());
	}
	private void resizeRelocateTarget() {
		for (Node node : ((Group)targetNode).getChildren()) {
			if (node instanceof ImageView) {
				ImageView imageView = (ImageView)node;
				imageView.setFitWidth(boundary.getWidth());
				imageView.setFitHeight(boundary.getHeight());
				imageView.setX(boundary.getX());
				imageView.setY(boundary.getY());
			}
			else if (node instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) node;
				rectangle.setWidth(boundary.getWidth());
				rectangle.setHeight(boundary.getHeight());
				rectangle.setX(boundary.getX());
				rectangle.setY(boundary.getY());
			}
			else if (node instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) node;
				ellipse.setCenterX(boundary.getCenterRectX());
				ellipse.setCenterY(boundary.getCenterRectY());
				ellipse.setRadiusX(boundary.getWidth()/2);
				ellipse.setRadiusY(boundary.getHeight()/2);
			}
		}
	}
	private void rotateTargetNode(double angle) {
		targetNode.setRotate(angle);
	}
	private void rotateAnchors(double angle) {
		/*Rotate rotate = new Rotate();  
        rotate.setAngle(angle); 
        rotate.setPivotX(boundary.getCenterRectX()); 
        rotate.setPivotY(boundary.getCenterRectY()); */
        
        ((Rotate)leftCenterAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)leftCenterAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)leftCenterAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
        
        ((Rotate)rotateAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)rotateAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)rotateAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
        
        ((Rotate)topLeftAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)topLeftAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)topLeftAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
        
        ((Rotate)topCenterAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)topCenterAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)topCenterAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
        
        ((Rotate)topRightAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)topRightAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)topRightAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
        
        ((Rotate)rightCenterAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)rightCenterAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)rightCenterAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
        
        ((Rotate)bottomRightAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)bottomRightAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)bottomRightAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
        
        ((Rotate)bottomCenterAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)bottomCenterAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)bottomCenterAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
        
        ((Rotate)bottomLeftAnchor.getTransforms().get(0)).setAngle(angle);
        ((Rotate)bottomLeftAnchor.getTransforms().get(0)).setPivotX(boundary.getCenterRectX());
        ((Rotate)bottomLeftAnchor.getTransforms().get(0)).setPivotY(boundary.getCenterRectY()); 
		
	}
	private double getAngleAtan2(double pivotX, double pivotY, double startX, double startY, double endX, double endY) {

		double val = 0;
				
		double startPosinRad = Math.atan2((startY-pivotY), (startX-pivotX));
		double stopPosinRad = Math.atan2((endY-pivotY), (endX-pivotX));
		
		val = stopPosinRad - startPosinRad;
		return Math.toDegrees(val);
	}
}