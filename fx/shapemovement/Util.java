package shapemovement;

import javafx.scene.Cursor;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Util {
	static double  rotateStartX, rotateStartY, rotateEndX, rotateEndY;
    public static void enableRotate(Circle node, boolean canDragX, boolean canDragY, RotateHandler rotateHandler) {
    	
    	if (rotateHandler != null) {
    		node.setOnMousePressed(mouseEvent -> {	
    			rotateStartX = mouseEvent.getSceneX();
    			rotateStartY = mouseEvent.getSceneY();
    		});
    		
    		node.setOnMouseReleased(mouseEvent -> {
    			rotateEndX = mouseEvent.getSceneX();
    			rotateEndY = mouseEvent.getSceneY();
    			
    			rotateHandler.handle(rotateStartX, rotateStartY, rotateEndX, rotateEndY, true);
                node.getScene().setCursor(Cursor.HAND);
            });
    		node.setOnMouseDragged(mouseEvent -> {
    			rotateEndX = mouseEvent.getSceneX();
    			rotateEndY = mouseEvent.getSceneY();
    			
    			rotateHandler.handle(rotateStartX, rotateStartY, rotateEndX, rotateEndY, false);
    			
    		});
    		
    		 node.setOnMouseExited(mouseEvent -> {
    	            if (!mouseEvent.isPrimaryButtonDown()) {
    	                node.getScene().setCursor(Cursor.DEFAULT);
    	            }
    	        });
    		
		}
    	
       
    }	
	
    // make a targetNode movable by dragging it around with the mouse.
    public static void enableDrag(Circle node, boolean canDragX, boolean canDragY, DragHandler dragHandler) {
        final Delta dragDelta = new Delta();
        //final 
        node.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = node.getCenterX() - mouseEvent.getX();
            dragDelta.y = node.getCenterY() - mouseEvent.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseReleased(mouseEvent -> {
            node.getScene().setCursor(Cursor.HAND);
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
                dragHandler.handle(oldX, oldY, newX, newY);
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

    // make a targetNode movable by dragging it around with the mouse.
    public static void makeDraggable(Rectangle node, DragHandler dragHandler) {
        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.HAND);
            }
        });
        node.setOnMouseExited(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        node.setOnMousePressed(me -> {
            if (me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
            dragDelta.x = me.getX() - node.getX();
            dragDelta.y = me.getY() - node.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseReleased(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        node.setOnMouseDragged(me -> {
            double oldX = node.getX();
            double oldY = node.getY();

            node.setX(me.getX() - dragDelta.x);
            node.setY(me.getY() - dragDelta.y);

            double newX = node.getX();
            double newY = node.getY();

            if (dragHandler != null && (newX != oldX || newY != oldY)) {
                dragHandler.handle(oldX, oldY, newX, newY);
            }
        });
    }

    // records relative x and y co-ordinates.
    private static class Delta {
        double x, y;
    }

}
