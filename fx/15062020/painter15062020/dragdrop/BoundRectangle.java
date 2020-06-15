package painter15062020.dragdrop;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoundRectangle extends Rectangle{
	private double centerRectX, centerRectY;	
	private double rotateAngle;
	public Group parentO;
	private Bounds bound;
	
	public Bounds getBound() {
		return bound;
	}
	public void setBound(Bounds bound) {
		this.bound = bound;
	}
	public BoundRectangle() {
		this.setStyle(
                "-fx-stroke: black; " +
                "-fx-stroke-width: 2px; " +
                "-fx-stroke-dash-array: 12 2 4 2; " +
                "-fx-stroke-dash-offset: 6; " +
                "-fx-stroke-line-cap: butt; " +
                "-fx-fill: rgba(135,206,250, .5);"/*255, 228, 118*/
        );
		//makeDraggable(this, DragHandler dragHandler)
	}
	public BoundRectangle(Group parent, DragHandler dragHandler) {
		System.out.println("wwwwwwwww : " +  parent);
		this.parentO = parent;
		//bound = ((BoundBox)parentO).getParentBound();;
		this.setStyle(
                "-fx-stroke: black; " +
                "-fx-stroke-width: 2px; " +
                "-fx-stroke-dash-array: 12 2 4 2; " +
                "-fx-stroke-dash-offset: 6; " +
                "-fx-stroke-line-cap: butt; " +
                "-fx-fill: rgba(135,206,250, .5);"/*255, 228, 118*/
        );
		makeDraggable(this, dragHandler);
	}
	public BoundRectangle(double width, double height) {
		super(width, height);
		this.setStyle(
                "-fx-stroke: black; " +
                "-fx-stroke-width: 2px; " +
                "-fx-stroke-dash-array: 12 2 4 2; " +
                "-fx-stroke-dash-offset: 6; " +
                "-fx-stroke-line-cap: butt; " +
                "-fx-fill: rgba(135,206,250, .5);"/*255, 228, 118*/
        );
	}
	public BoundRectangle(double startX, double startY, double width, double height) {
		super(startX, startY, width, height);
		this.setStyle(
                "-fx-stroke: black; " +
                "-fx-stroke-width: 2px; " +
                "-fx-stroke-dash-array: 12 2 4 2; " +
                "-fx-stroke-dash-offset: 6; " +
                "-fx-stroke-line-cap: butt; " +
                "-fx-fill: rgba(135,206,250, .5);"/*255, 228, 118*/
        );
	}
	public void setBoundStyleColor(Rectangle node, Color color) {
		String s = "";
		if (color == Color.DARKGRAY) {
			s = "darkgray";
		} else if(color == Color.BLACK){
			s = "black";
		}
		node.setStyle(
                "-fx-stroke: "+ s +"; " +
                "-fx-stroke-width: 1px; " +
                "-fx-stroke-dash-array: 12 2 4 2; " +
                "-fx-stroke-dash-offset: 6; " +
                "-fx-stroke-line-cap: butt; " +
                "-fx-fill: rgba(135,206,250, .5);"/*255, 228, 118*/
        );
	}

	public double getCenterRectY() {
		this.centerRectY = getY() + getHeight()/2;
		return centerRectY;
	}


	public void setCenterRectY(double centerRectY) {
		this.centerRectY = centerRectY;
	}


	public double getCenterRectX() {
		this.centerRectX = getX() + getWidth()/2;
		return centerRectX;
	}


	public void setCenterRectX(double centerRectX) {
		this.centerRectX = centerRectX;
	}
	
	public void makeDraggable(BoundRectangle node, DragHandler dragHandler) {
		final Delta dragDelta = new Delta();
		node.setOnMouseMoved(me->{
		});
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
            dragDelta.x = me.getSceneX()/* - node.getX()*/;
            dragDelta.y = me.getSceneY()/* - node.getY()*/;
            node.getScene().setCursor(Cursor.MOVE);
        });
        
        node.setOnMouseDragged(me -> {
        	//bound objenin bulunduðu panelin sýnýrlarýný belirtir.
        	
        	System.out.println(bound);
            double oldX = ((Rectangle)node).getX() ;
            double oldY = ((Rectangle)node).getY();
            
            setBoundStyleColor(node, Color.DARKGRAY);
            //System.out.println("me.getSceneX()" + me.getSceneX() + "me.getSceneY()" + me.getSceneY());
            //Burada topPanein height ý kadar sýnýrlama yapýlmalý. þimdilik elle  50 veriyorum.
            if (me.getSceneX() >= bound.getMinX() && me.getSceneY() > bound.getMinY()+50 && me.getSceneX()<= bound.getMaxX() && me.getSceneY() <= bound.getMaxY() ) {
            	
	            node.setX(oldX + (me.getSceneX() - dragDelta.x));
	            node.setY(oldY + (me.getSceneY() - dragDelta.y));
	            
	            dragDelta.x = me.getSceneX();
	            dragDelta.y = me.getSceneY();
	            double newX = ((Rectangle)node).getX();
	            double newY = ((Rectangle)node).getY();
	            
	            	if (dragHandler != null && (newX != oldX || newY != oldY) ) {
	            		
	            			dragHandler.handle(oldX, oldY, newX, newY, false);
	            	/*}
	            		else {
	            			//System.out.println("oldddddd");
	            			node.setX(oldX);
	                        node.setY(oldY);
	            			dragHandler.handle(oldX, oldY, oldX, oldY, false);
						}*/
	            	}
            }
            
        });
        node.setOnMouseReleased(me -> {
            if (!me.isPrimaryButtonDown()) {
                setBoundStyleColor(node, Color.BLACK);
            	double oldX = node.getX();
                double oldY = node.getY();
            	if (dragHandler != null /*&& (newX != oldX || newY != oldY)*/) {
            		 dragHandler.handle(oldX, oldY, oldX, oldY, true);
                }
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }
	private static class Delta {
        double x, y;
    }
	
}