package painter15062020.dragdrop;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;

public class Anchor extends Circle{
	public Anchor(Color color, boolean canDragX, boolean canDragY, DragHandler dragHandler, RotateHandler rotateHandler) {
		super(0, 0, 3);
		setFill(color.deriveColor(1, 1, 1, 1));
        setStroke(color.deriveColor(0, 0, 0, 0.5)/*Color.BLACK*//*color*/);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);
        getTransforms().add(new Rotate());
        enableDragEvents(canDragX, canDragY, dragHandler);
        enableRotateEvents(rotateHandler);
	}
	
	private void enableDragEvents(boolean canDragX, boolean canDragY, DragHandler dragHandler) {
		final Delta dragDelta = new Delta();
        //final 
		Circle node = this;
        node.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = node.getCenterX() - mouseEvent.getX();
            dragDelta.y = node.getCenterY() - mouseEvent.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseReleased(mouseEvent -> {
            node.getScene().setCursor(Cursor.HAND);
            double oldX = node.getCenterX();
            double oldY = node.getCenterY();
            if (dragHandler != null /*&& (newX != oldX || newY != oldY)*/) {
                dragHandler.handle(oldX, oldY,oldX, oldY, true);
            }
        });
        node.setOnMouseDragged(mouseEvent -> {
        	
            double oldX = node.getCenterX();
            double oldY = node.getCenterY();

            double newX = mouseEvent.getX() + dragDelta.x;
            if (canDragX && newX > 0 && newX < node.getScene().getWidth()) {
                node.setCenterX(newX);
            }

            double newY = mouseEvent.getY() + dragDelta.y;
            if (canDragY && newY > 0 && newY < node.getScene().getHeight()) {
                node.setCenterY(newY);
            }
            
            newX = node.getCenterX();
            newY = node.getCenterY();

            if (dragHandler != null && (newX != oldX || newY != oldY)) {
                dragHandler.handle(oldX, oldY, newX, newY, false);
            }
        });
        node.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.HAND);
            }
        });
        node.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
	}
	
	private void enableRotateEvents(RotateHandler rotateHandler) {
		Circle node = this;
		final Delta  rotateDelta = new Delta();
		if (rotateHandler != null) {
    		node.setOnMousePressed(mouseEvent -> {	
    			rotateDelta.x = mouseEvent.getSceneX();
    			rotateDelta.y = mouseEvent.getSceneY();
    			System.out.println("Press ----> Relative X : " + mouseEvent.getX()+" Relative Y : " + mouseEvent.getY()+" Absolute X : " + mouseEvent.getSceneX()+" Absolute Y : " + mouseEvent.getSceneY());
    			//rotateHandler.handle(rotateStartX, rotateStartY, rotateStartX, rotateStartY, false);
    		});
    		
    		node.setOnMouseReleased(mouseEvent -> {
    			double rotateEndX = mouseEvent.getSceneX();
    			double rotateEndY = mouseEvent.getSceneY();
    			//mouseEvent.getX()
    			System.out.println("Relase ----> Relative X : " + mouseEvent.getX()+" Relative Y : " + mouseEvent.getY()+" Absolute X : " + mouseEvent.getSceneX()+" Absolute Y : " + mouseEvent.getSceneY());
    			rotateHandler.handle(rotateDelta.x, rotateDelta.y, rotateEndX, rotateEndY, true);
                node.getScene().setCursor(Cursor.HAND);
            });
    		node.setOnMouseDragged(mouseEvent -> {
    			double rotateEndX = mouseEvent.getSceneX();
    			double rotateEndY = mouseEvent.getSceneY();
    			
    			rotateHandler.handle(rotateDelta.x, rotateDelta.y, rotateEndX, rotateEndY, false);
    			rotateDelta.x = rotateEndX;
    			rotateDelta.y = rotateEndY;
    		});
    		
    		 node.setOnMouseExited(mouseEvent -> {
    	            if (!mouseEvent.isPrimaryButtonDown()) {
    	                node.getScene().setCursor(Cursor.DEFAULT);
    	            }
    	        });
    		
		}		
	}
	final class Delta{
		double x,y;
	}

}