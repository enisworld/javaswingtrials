package shapemovement;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class MovementControl extends Group{
	private Node targetNode = null;
	private VBox tableviewParent;
    private final Rectangle boundary = new Rectangle();
	//private double angle = 0;
    private Anchor rotateAnchor = new Anchor(Color.AQUA, false, false, null, (oldX, oldY, newX, newY, isReleased) -> {
    	double topHeightDiff =  ((BorderPane)boundary.getParent().getParent().getParent().getParent().getParent()).getTop().getBoundsInParent().getHeight();
    	//System.out.println("Height : " + ((HBox)((BorderPane)boundary.getParent().getParent().getParent().getParent()).getTop()).getHeight());
    	/*System.out.println("==============================");*/
    	double angle = boundary.getRotate();
    	//boundary.setRotate(0);
    	angle += getDirectionAtan2( ((Pane)boundary.getParent().getParent()).getBoundsInParent().getMinX() + boundary.getX() + boundary.getWidth()/2
    									, topHeightDiff + ((Pane)boundary.getParent().getParent()).getBoundsInParent().getMinY()+ boundary.getY() + boundary.getHeight()/2
    									, oldX
    									, oldY
    									, newX
    									, newY);
        boundary.setRotate(angle); 
        updateAnchorPositions();
    	rotateTargetNode(angle);

    });
    private Anchor topLeft = new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY) -> {
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

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor topCenter = new Anchor(Color.GHOSTWHITE, false, true, (oldX, oldY, newX, newY) -> {
        double newHeight = boundary.getHeight() - (newY - oldY);
        if (newHeight > 0) {
            boundary.setY(newY);
            boundary.setHeight(newHeight);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor topRight = new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY) -> {
        
    	double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() - (newY - oldY);
        if (newHeight > 0) {
            boundary.setY(newY);
            boundary.setHeight(newHeight);
        }
        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor rightCenter = new Anchor(Color.GHOSTWHITE, true, false, (oldX, oldY, newX, newY) -> {
        double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor bottomRight = new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY) -> {
        double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor bottomCenter = new Anchor(Color.GHOSTWHITE, false, true, (oldX, oldY, newX, newY) -> {
        double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor bottomLeft = new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY) -> {
        double newWidth = boundary.getWidth() - (newX - oldX);
        if (newWidth > 0) {
            boundary.setX(newX);
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor leftCenter = new Anchor(Color.GHOSTWHITE, true, false, (oldX, oldY, newX, newY) -> {
        double newWidth = boundary.getWidth() - (newX - oldX);
        if (newWidth > 0) {
            boundary.setX(newX);
            boundary.setWidth(newWidth);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    
	public MovementControl(Node targetNode, VBox vBox) {
        this.targetNode = targetNode;
        tableviewParent = vBox;
        attachBoundingRectangle(targetNode);
        attachAnchors();

        boundary.toBack();
    }
	
	
	/*private void updateAnchorPositions() {
		
	}*/

	
    private void attachBoundingRectangle(Node node) {
        Bounds bounds = node.getBoundsInParent();
        System.out.println("Rotate angle : " + targetNode.getRotate());
        double nodeStartX = 0, nodeStartY = 0, nodeWidth = 0, nodeHeight = 0, centerXNode = 0, centerYNode = 0;;
        if (node instanceof Rectangle) {
        	Rectangle rectangle = (Rectangle) targetNode;
        	nodeStartX = rectangle.getX();
        	nodeStartY = rectangle.getY();
        	nodeWidth = rectangle.getWidth();
        	nodeHeight = rectangle.getHeight();
        	centerXNode = rectangle.getX() + rectangle.getWidth()/2;
        	centerYNode = rectangle.getY() + rectangle.getHeight()/2;
		}
        else if (node instanceof Ellipse) {
        	//System.out.println("ellipse------");
        	Ellipse ellipse = (Ellipse) targetNode;
        	bounds = node.getBoundsInParent();
         	nodeStartX = ellipse.getCenterX()-ellipse.getRadiusX();
         	nodeStartY = ellipse.getCenterY()-ellipse.getRadiusY();
         	nodeWidth = ellipse.getRadiusX()*2;
         	nodeHeight = ellipse.getRadiusY()*2;
        	centerXNode = ellipse.getCenterX();
        	centerYNode = ellipse.getCenterY();
		}
        else if (node instanceof Line) {
			Line line = (Line) targetNode;
			bounds = node.getBoundsInParent();
			nodeStartX = bounds.getMinX();
         	nodeStartY = bounds.getMinY();
         	nodeWidth = bounds.getWidth();
         	nodeHeight = bounds.getHeight();
		}
        
        boundary.setStyle(
                "-fx-stroke: black; " +
                "-fx-stroke-width: 2px; " +
                "-fx-stroke-dash-array: 12 2 4 2; " +
                "-fx-stroke-dash-offset: 6; " +
                "-fx-stroke-line-cap: butt; " +
                "-fx-fill: rgba(135,206,250, .5);"/*255, 228, 118*/
        );

        boundary.setX(nodeStartX);
        boundary.setY(nodeStartY);
        boundary.setWidth(nodeWidth);
        boundary.setHeight(nodeHeight);
        boundary.setRotate(node.getRotate());
        
        Util.makeDraggable(boundary, (oldX, oldY, newX, newY) -> {
            updateAnchorPositions();

            relocateTargetNode(newX, newY);
        });

        getChildren().add(boundary);
    }

    private void relocateTargetNode(double newX, double newY) {
        if (targetNode instanceof Ellipse) {
            Ellipse ellipse = (Ellipse) targetNode;
            ellipse.setCenterX(newX + ellipse.getRadiusX());
            ellipse.setCenterY(newY + ellipse.getRadiusY());
        } else if (targetNode instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) targetNode;
            rectangle.setX(newX);
            rectangle.setY(newY);
        } else if (targetNode instanceof Line) {
			Line line = (Line)targetNode;
			line.setStartX(boundary.getX());
			line.setStartY(boundary.getY());
			line.setEndX(boundary.getX() + boundary.getWidth());
			line.setEndY(boundary.getY() + boundary.getHeight());
        }
    }

    private void resizeTargetNode() {
        if (targetNode instanceof Ellipse) {
            Ellipse ellipse = (Ellipse) targetNode;
            ellipse.setRadiusX(boundary.getWidth() / 2);
            ellipse.setRadiusY(boundary.getHeight() / 2);
            //ellipse.setRotate(angle);
            relocateTargetNode(boundary.getX(), boundary.getY());
        } else if (targetNode instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) targetNode;
            rectangle.setWidth(boundary.getWidth());
            rectangle.setHeight(boundary.getHeight());
            //rectangle.setRotate(angle);
            relocateTargetNode(boundary.getX(), boundary.getY());
        } else if (targetNode instanceof Line) {
			Line line = (Line)targetNode;
			line.setEndX(boundary.getX() + boundary.getWidth());
			line.setEndY(boundary.getY() + boundary.getHeight());
			relocateTargetNode(boundary.getX(), boundary.getY());
			
		}
    }

    private void attachAnchors() {
        updateAnchorPositions();

        getChildren().addAll(
                topLeft,
                topCenter,
                topRight,
                rightCenter,
                bottomRight,
                bottomCenter,
                bottomLeft,
                leftCenter
                ,rotateAnchor
        );
    }

    private void rotateAnchors(double angle) {

    	Rotate rotate = new Rotate(); 
        //Setting the angle for the rotation 
    	
        rotate.setAngle(angle); 
        //Setting pivot points for the rotation 
        rotate.setPivotX(boundary.getX() + boundary.getWidth() / 2); 
        rotate.setPivotY(boundary.getY() + boundary.getHeight() / 2); 
        rotateAnchor.getTransforms().clear();
        topLeft.getTransforms().clear();
        topCenter.getTransforms().clear();
        topRight.getTransforms().clear();
        rightCenter.getTransforms().clear();
        bottomRight.getTransforms().clear();
        bottomCenter.getTransforms().clear();
        bottomLeft.getTransforms().clear();
        leftCenter.getTransforms().clear();

        rotateAnchor.getTransforms().addAll(rotate);
        topLeft.getTransforms().addAll(rotate);
        topCenter.getTransforms().addAll(rotate);
        topRight.getTransforms().addAll(rotate);
        rightCenter.getTransforms().addAll(rotate);
        bottomRight.getTransforms().addAll(rotate);
        bottomCenter.getTransforms().addAll(rotate);
        bottomLeft.getTransforms().addAll(rotate);
        leftCenter.getTransforms().addAll(rotate); 
        //boundary.setRotate(rotate.getAngle());
    
}

    private void updateAnchorPositions() {
    	if (tableviewParent != null) {
    		tableviewParent.getChildren().clear();
    		tableviewParent.getChildren().addAll(Util.getNodeProperties(targetNode));
		}
    	double angle = targetNode.getRotate();	
	        
	    	rotateAnchor.setCenterX(boundary.getX() + boundary.getWidth() / 2);
	    	rotateAnchor.setCenterY(boundary.getY()-boundary.getHeight()/6);

	        topLeft.setCenterX(boundary.getX());
	        topLeft.setCenterY(boundary.getY());
	        
	        topCenter.setCenterX(boundary.getX() + boundary.getWidth() / 2);
	        topCenter.setCenterY(boundary.getY());
	        
	        topRight.setCenterX(boundary.getX() + boundary.getWidth());
	        topRight.setCenterY(boundary.getY());
	         
	        rightCenter.setCenterX(boundary.getX() + boundary.getWidth());
	        rightCenter.setCenterY(boundary.getY() + boundary.getHeight() / 2);
	       
	        bottomRight.setCenterX(boundary.getX() + boundary.getWidth());
	        bottomRight.setCenterY(boundary.getY() + boundary.getHeight());
	        
	        bottomCenter.setCenterX(boundary.getX() + boundary.getWidth() / 2);
	        bottomCenter.setCenterY(boundary.getY() + boundary.getHeight());
	         
	        bottomLeft.setCenterX(boundary.getX());
	        bottomLeft.setCenterY(boundary.getY() + boundary.getHeight());
	         
	        leftCenter.setCenterX(boundary.getX());
	        leftCenter.setCenterY(boundary.getY() + boundary.getHeight() / 2);
	         
			rotateAnchors(angle);
		//}
    }
    /**
     * 
     * @param x0 center coordinate X
     * @param y0 center coordinate Y
     * @param x1 start pos coordinate X
     * @param y1 start pos coordinate Y
     * @param x2 end pos coordinate X
     * @param y2 end pos coordinate Y
     * @return rotation angle in degree
     */
    private double getDirectionAtan2(double x0, double y0, double x1, double y1, double x2, double y2) {

		double val = 0;
				
		double startPosinRad = Math.atan2((y1-y0), (x1-x0));
		double stopPosinRad = Math.atan2((y2-y0), (x2-x0));
		
		val = stopPosinRad - startPosinRad;
		return Math.toDegrees(val);
	}

    private void rotateTargetNode(double angle) {
    	if (targetNode instanceof Ellipse) {
            Ellipse ellipse = (Ellipse) targetNode;
            ellipse.setRotate(angle);
            //relocateTargetNode(boundary.getX(), boundary.getY());
        } else if (targetNode instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) targetNode;;
            rectangle.setRotate(angle);
            //relocateTargetNode(boundary.getX(), boundary.getY());
        } else if (targetNode instanceof Line) {
        	Line line = (Line) targetNode;;
        	line.setRotate(angle);
            //relocateTargetNode(boundary.getX(), boundary.getY());
        }
    }

}
