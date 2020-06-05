package examples001;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class ResizingSample extends Application {
    private Pane root;
    private Node selectedNode;
    private double rotateAngle = 0;
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        Ellipse ellipse = new Ellipse(100, 100, 50, 50);
        ellipse.setFill(Color.AQUAMARINE);
        
        Ellipse ellipse2 = new Ellipse(150, 100, 50, 50);
        ellipse2.setFill(Color.BLUE);

        Rectangle rectangle = new Rectangle(200, 250, 150, 100);
        rectangle.setFill(Color.PALEGREEN);
        rectangle.setRotate(rotateAngle);
        root = new Pane(
                ellipse,
                rectangle,
                ellipse2
        );
        stage.setScene(
                new Scene(
                        root,
                        400, 400, Color.ALICEBLUE
                )
        );
        stage.show();

        root.setOnMouseClicked(event -> {
            final Parent parentNode = ((Node) event.getTarget()).getParent();
            if (selectedNode != null && !(parentNode instanceof ResizingControl)) {
                root.getChildren().removeIf(candidate -> candidate instanceof ResizingControl);
                selectedNode = null;
            }
        });

        makeSelectable(ellipse, rectangle, ellipse2);
    }

    private void makeSelectable(Node... nodes) {
        for (Node node: nodes) {
            node.setOnMouseClicked(event -> {
                if (selectedNode != node) {
                    root.getChildren().removeIf(candidate -> candidate instanceof ResizingControl);
                    selectedNode = node;

                    node.toFront();
                    ResizingControl resizingControl = new ResizingControl(node);
                    root.getChildren().add(resizingControl);
                }

                event.consume();
            });
        }
    }
}

class ResizingControl extends Group {
    private Node targetNode = null;
    private final Rectangle boundary = new Rectangle();
    private double angle = 0, boundaryCenterX, boundaryCenterY;//degree
    private Anchor rotateAnchor = new Anchor(Color.BLACK, false, false, null, (oldX, oldY, newX, newY) -> {
    	double value2 = getDirectionAtan2(boundaryCenterX
    									, boundaryCenterY
    									, oldX
    									, oldY
    									, newX
    									, newY);
    	angle = Math.toDegrees(value2);//Math.toDegrees(value2);
    	Rotate rotate = new Rotate(); 
        
        //Setting the angle for the rotation 
        rotate.setAngle(angle); 
        
        //Setting pivot points for the rotation 
        rotate.setPivotX(boundaryCenterX); 
        rotate.setPivotY(boundaryCenterY); 
          
        //Adding the transformation to rectangle2 
        boundary.getTransforms().addAll(rotate); 
    	//boundary.setRotate(angle);
    	updateAnchorPositions();
    	rotateTargetNode();
    	/*System.out.println("rotateAnchor handle");*/
    });

    private Anchor topLeft = new Anchor(Color.GOLD, true, true, (oldX, oldY, newX, newY) -> {
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
    private Anchor topCenter = new Anchor(Color.GOLD, false, true, (oldX, oldY, newX, newY) -> {
        double newHeight = boundary.getHeight() - (newY - oldY);
        if (newHeight > 0) {
            boundary.setY(newY);
            boundary.setHeight(newHeight);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor topRight = new Anchor(Color.GOLD, true, true, (oldX, oldY, newX, newY) -> {
        
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
    private Anchor rightCenter = new Anchor(Color.GOLD, true, false, (oldX, oldY, newX, newY) -> {
        double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor bottomRight = new Anchor(Color.GOLD, true, true, (oldX, oldY, newX, newY) -> {
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
    private Anchor bottomCenter = new Anchor(Color.GOLD, false, true, (oldX, oldY, newX, newY) -> {
        double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);
    private Anchor bottomLeft = new Anchor(Color.GOLD, true, true, (oldX, oldY, newX, newY) -> {
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
    private Anchor leftCenter = new Anchor(Color.GOLD, true, false, (oldX, oldY, newX, newY) -> {
        double newWidth = boundary.getWidth() - (newX - oldX);
        if (newWidth > 0) {
            boundary.setX(newX);
            boundary.setWidth(newWidth);
        }

        updateAnchorPositions();
        resizeTargetNode();
    }, null);

    ResizingControl(Node targetNode) {
        this.targetNode = targetNode;

        attachBoundingRectangle(targetNode);
        attachAnchors();

        boundary.toBack();
    }

    private void attachBoundingRectangle(Node node) {
        Bounds bounds = node.getBoundsInParent();

        boundary.setStyle(
                "-fx-stroke: forestgreen; " +
                "-fx-stroke-width: 2px; " +
                "-fx-stroke-dash-array: 12 2 4 2; " +
                "-fx-stroke-dash-offset: 6; " +
                "-fx-stroke-line-cap: butt; " +
                "-fx-fill: rgba(255, 228, 118, .5);"
        );

        boundary.setX(bounds.getMinX());
        boundary.setY(bounds.getMinY());
        boundary.setWidth(bounds.getWidth());
        boundary.setHeight(bounds.getHeight());
        boundaryCenterX = (bounds.getMinX() + bounds.getMaxX())/2;
        boundaryCenterY = (bounds.getMinY() + bounds.getMaxY())/2;
        System.out.println("bounds.getMinX() : " + bounds.getMinX() + " bounds.getMinY() : " + bounds.getMinY() + "bounds.getWidth() : " + bounds.getWidth() + " bounds.getHeight() : " + bounds.getHeight());
        
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
        }
    }
    private void rotateTargetNode() {
    	if (targetNode instanceof Ellipse) {
            Ellipse ellipse = (Ellipse) targetNode;
            ellipse.setRotate(angle);
            //relocateTargetNode(boundary.getX(), boundary.getY());
        } else if (targetNode instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) targetNode;;
            rectangle.setRotate(angle);
            //relocateTargetNode(boundary.getX(), boundary.getY());
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
    
    private void updateAnchorPositions() {
    	
    	Rotate rotate = new Rotate(); 
        //Setting the angle for the rotation 
        rotate.setAngle(angle); 
        //Setting pivot points for the rotation 
        rotate.setPivotX(boundaryCenterX); 
        rotate.setPivotY(boundaryCenterY); 
        
    	rotateAnchor.setCenterX(boundaryCenterX);
    	rotateAnchor.setCenterY(boundaryCenterY-(boundary.getHeight()/2 + boundary.getHeight()/6));
        rotateAnchor.getTransforms().addAll(rotate); 
    	//rotateAnchor.setR;;
        topLeft.setCenterX(boundary.getX());
        topLeft.setCenterY(boundary.getY());
        topLeft.getTransforms().addAll(rotate); 
        topCenter.setCenterX(boundary.getX() + boundary.getWidth() / 2);
        topCenter.setCenterY(boundary.getY());
        topCenter.getTransforms().addAll(rotate); 
        topRight.setCenterX(boundary.getX() + boundary.getWidth());
        topRight.setCenterY(boundary.getY());
        topRight.getTransforms().addAll(rotate); 
        rightCenter.setCenterX(boundary.getX() + boundary.getWidth());
        rightCenter.setCenterY(boundary.getY() + boundary.getHeight() / 2);
        rightCenter.getTransforms().addAll(rotate); 
        bottomRight.setCenterX(boundary.getX() + boundary.getWidth());
        bottomRight.setCenterY(boundary.getY() + boundary.getHeight());
        bottomRight.getTransforms().addAll(rotate); 
        bottomCenter.setCenterX(boundary.getX() + boundary.getWidth() / 2);
        bottomCenter.setCenterY(boundary.getY() + boundary.getHeight());
        bottomCenter.getTransforms().addAll(rotate); 
        bottomLeft.setCenterX(boundary.getX());
        bottomLeft.setCenterY(boundary.getY() + boundary.getHeight());
        bottomLeft.getTransforms().addAll(rotate); 
        leftCenter.setCenterX(boundary.getX());
        leftCenter.setCenterY(boundary.getY() + boundary.getHeight() / 2);
        leftCenter.getTransforms().addAll(rotate); 
    }
}

interface DragHandler {
    void handle(double oldX, double oldY, double newX, double newY);
}
interface RotateHandler {
    void handle(double oldX, double oldY, double newX, double newY);
}

// a draggable anchor displayed around a point.
class Anchor extends Circle {
    Anchor(Color color, boolean canDragX, boolean canDragY, DragHandler dragHandler, RotateHandler rotateHandler) {
        super(0, 0, 5);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);
        Util.enableDrag(this, canDragX, canDragY, dragHandler);
        Util.enableRotate(this, canDragX, canDragY, rotateHandler);
    }
}

class Util {
	static double  rotateStartX, rotateStartY, rotateEndX, rotateEndY;
    static void enableRotate(Circle node, boolean canDragX, boolean canDragY, RotateHandler rotateHandler) {
    	
    	if (rotateHandler != null) {
    		node.setOnMousePressed(mouseEvent -> {	
    			rotateStartX = mouseEvent.getSceneX();
    			rotateStartY = mouseEvent.getSceneY();
    		});
    		
    		node.setOnMouseReleased(mouseEvent -> {
                node.getScene().setCursor(Cursor.HAND);
            });
    		node.setOnMouseDragged(mouseEvent -> {
    			rotateEndX = mouseEvent.getSceneX();
    			rotateEndY = mouseEvent.getSceneY();
    			
    			rotateHandler.handle(rotateStartX, rotateStartY, rotateEndX, rotateEndY);
    			
    		});
    		
    		 node.setOnMouseExited(mouseEvent -> {
    	            if (!mouseEvent.isPrimaryButtonDown()) {
    	                node.getScene().setCursor(Cursor.DEFAULT);
    	            }
    	        });
    		
		}
    	
       
    }	
	
    // make a targetNode movable by dragging it around with the mouse.
    static void enableDrag(Circle node, boolean canDragX, boolean canDragY, DragHandler dragHandler) {
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
    static void makeDraggable(Rectangle node, DragHandler dragHandler) {
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
