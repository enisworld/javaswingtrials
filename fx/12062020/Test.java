package painter12062020;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import painter3d.DrawableObject;

public class Test  extends Application{
	private Node selectedNode;
	public static void main(String[] args) {
		launch(args);

	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		
		
		GroupObject  obj1 = new GroupObject(pane, "imageTest.jpg");
		
		pane.getChildren().addAll(obj1);
		pane.setOnMouseClicked(event ->{
			final Parent parentNode = ((Node) event.getTarget()).getParent();
            System.out.println("pane clicked. "+ event.getSource());
            if (selectedNode != null /*&& !(parentNode instanceof DrawableObject)*/) {
            	((GroupObject)selectedNode).getBoundBox().setVisible(false);
            	selectedNode = null;
            }
		});
		makeSelectable(pane);
		System.out.println("pane " + pane.getWidth()+ pane.getHeight());
		Scene scene = new Scene(pane, 1000,800);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private void makeSelectable(Pane pane) {
		ObservableList<Node> observableList = pane.getChildren();
		for (Node node : observableList) {
			node.setOnMouseClicked(event -> {
				if (selectedNode != node) {				
                    selectedNode = node;
                    ((GroupObject)node).getBoundBox().setVisible(true);
                    ((GroupObject)node).getBoundBox().toFront();
                }
                event.consume();
			});
		}
		
	}

}


-----------------------------------------------------

package painter12062020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class GroupObject extends Group{
	Parent parentObject;
	
	BoundBox boundBox;
	//Rectangle rectangle;//Bunun içerisine resim, şekil eklenecek
	Group shapeView;
	public GroupObject(Parent parent, String imagePath) {
		this.parentObject = parent;
		this.shapeView = new Group();
		setImagetoImageView(imagePath);
		createBoundBox();
		getChildren().addAll(boundBox,shapeView);
	}
	/**
	 * eklenen şekil/resim'in boundunu oluşturmak için bu metot kullanılmaktadır. 
	 */
	private void createBoundBox() {
			Bounds boundShape = shapeView.getBoundsInParent();// bulunduğu panelin bound bilgileri	

			//bu grubu panelin merkez noktasına yerleştiriyoruz
			boundBox = new BoundBox(this
									, boundShape.getMinX()
									, boundShape.getMinY()
									, boundShape.getWidth()
									, boundShape.getHeight()); 
			boundBox.setVisible(false);
	}
	/**
	 * Verilen parametredeki resmin yüklenmesini sağlamak için bu metot kullanılmaktadır.
	 * @param imagePath
	 */
	private void setImagetoImageView(String imagePath) {
		Image image;
		try {
			image = new Image(new FileInputStream(imagePath));
			
			//Setting the image view 
			ImageView imageView = new ImageView(image); 
			System.out.println("Image Width : " + imageView.getBoundsInParent().getWidth() + "Image Height : " + imageView.getBoundsInParent().getHeight());
			;
			imageView.setX(50); 
			imageView.setY(25); 
			
			this.shapeView.getChildren().add(imageView);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}

	
	
	public void updateShapeViewLocation() {
		
	}
	public Parent getParentObject() {
		return parentObject;
	}
	public void setParentObject(Parent parentObject) {
		this.parentObject = parentObject;
	}
	public BoundBox getBoundBox() {
		return boundBox;
	}
	public void setBoundBox(BoundBox boundBox) {
		this.boundBox = boundBox;
	}
	public Group getShapeView() {
		return shapeView;
	}
	public void setShapeView(Group shapeView) {
		this.shapeView = shapeView;
	}
	
}


-------------------------------------------------------------

package painter12062020;

import javafx.scene.Cursor;
import javafx.scene.shape.Rectangle;
import shapemovement.DragHandler;

public class BoundRectangle extends Rectangle{
	private double centerRectX, centerRectY;	
	private double rotateAngle;
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
	
	public void makeDraggable(Rectangle node, DragHandler dragHandler) {
		final Delta dragDelta = new Delta();
		node.setOnMouseMoved(me->{
			System.out.println("setOnMouseMoved ----> " + me.getX() + " : "+ me.getY());
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
            if (node.getParent() instanceof BoundBox) {
				System.out.println("YESSSSS");
				BoundBox boundBox = (BoundBox)node.getParent();
				boundBox.getBoundsInParent().getMinX();
				boundBox.getBoundsInParent().getMinY();
			}
            /*System.out.println("setOnMousePressed --> node.getX() : " + node.getX()+ " node.getY() : " + node.getY());
            System.out.println("setOnMousePressed --> me.getX() : " + me.getX()+ " me.getY() : " + me.getY());
            System.out.println("setOnMousePressed --> me.getSceneX() : " + me.getSceneX()+ " me.getSceneY() : " + me.getSceneY());*/
            dragDelta.x = me.getX() - node.getX();
            dragDelta.y = me.getY() - node.getY();
            System.out.println("setOnMousePressed --> dragDelta.x : " + dragDelta.x+ " dragDelta.y : " + dragDelta.y);
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseReleased(me -> {
            if (!me.isPrimaryButtonDown()) {
            	double oldX = node.getX();
                double oldY = node.getY();
            	if (dragHandler != null /*&& (newX != oldX || newY != oldY)*/) {
                    dragHandler.handle(oldX, oldY, oldX, oldY, true);
                }
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        node.setOnMouseDragged(me -> {
        	//node.getTransforms().clear();
            double oldX = ((Rectangle)node).getX() ;
            double oldY = ((Rectangle)node).getY();
            System.out.println(" setOnMouseDragged ---> node.getX() : " + node.getX()+ " node.getY() : " + node.getY());
            System.out.println("setOnMouseDragged --> me.getX() : " + me.getX()+ " me.getY() : " + me.getY());
            System.out.println("setOnMousePressed --> dragDelta.x : " + dragDelta.x+ " dragDelta.y : " + dragDelta.y);
            System.out.println("---------------------------------------");
            //node.setX(me.getX() - dragDelta.x);
            //node.setY(me.getY() - dragDelta.y);
            node.setX(((Rectangle)node).getX());
            node.setY(((Rectangle)node).getY());
            double newX = ((Rectangle)node).getX() ;
            double newY = ((Rectangle)node).getY();

            if (dragHandler != null && (newX != oldX || newY != oldY)) {
                dragHandler.handle(oldX, oldY, newX, newY, false);
            }
        });
    }
	private static class Delta {
        double x, y;
    }
	
}
----------------------------------------------------
package painter12062020;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class BoundBox extends Group{
	private GroupObject parentBoundBox;
	private final BoundRectangle boundary = new BoundRectangle();
	
	private Anchor topLeftAnchor = new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY, isreleased) -> {
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
		if (isreleased) {
			updateAnchors();
			//resizeTarget();
		}
	}, null);

	private Anchor topCenterAnchor = new Anchor(Color.GHOSTWHITE, false, true, (oldX, oldY, newX, newY, isreleased) -> {		
		double newHeight = boundary.getHeight() - (newY - oldY);
        if (newHeight > 0) {
            boundary.setY(newY);
            boundary.setHeight(newHeight);
        }
		if (isreleased) {
			updateAnchors();
			//resizeTarget();
		}
	}, null);
	private Anchor topRightAnchor = new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY, isreleased) -> {		
		double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() - (newY - oldY);
        if (newHeight > 0) {
            boundary.setY(newY);
            boundary.setHeight(newHeight);
        }
		if (isreleased) {
			updateAnchors();
			//resizeTarget();
		}
	}, null);
	private Anchor bottomLeftAnchor = new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY, isreleased) -> {		
		double newWidth = boundary.getWidth() - (newX - oldX);
        if (newWidth > 0) {
            boundary.setX(newX);
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }
		if (isreleased) {
			updateAnchors();
			//resizeTarget();
		}
	}, null);
	private Anchor bottomCenterAnchor = new Anchor(Color.GHOSTWHITE, false, true, (oldX, oldY, newX, newY, isreleased) -> {		
		double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }
		if (isreleased) {
			updateAnchors();
			//resizeTarget();
		}
	}, null);
	private Anchor bottomRightAnchor = new Anchor(Color.GHOSTWHITE, true, true, (oldX, oldY, newX, newY, isreleased) -> {		
		double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }
        double newHeight = boundary.getHeight() + (newY - oldY);
        if (newHeight > 0) {
            boundary.setHeight(newHeight);
        }
		if (isreleased) {
			updateAnchors();
		}
	}, null);
	private Anchor leftCenterAnchor = new Anchor(Color.GHOSTWHITE, true, false, (oldX, oldY, newX, newY, isreleased) -> {		
		double newWidth = boundary.getWidth() - (newX - oldX);
        if (newWidth > 0) {
            boundary.setX(newX);
            boundary.setWidth(newWidth);
        }
		if (isreleased) {
			updateAnchors();
			//resizeTarget();
		}
	}, null);
	private Anchor rightCenterAnchor = new Anchor(Color.GHOSTWHITE, true, false, (oldX, oldY, newX, newY, isreleased) -> {		
		double newWidth = boundary.getWidth() + (newX - oldX);
        if (newWidth > 0) {
            boundary.setWidth(newWidth);
        }
		if (isreleased) {
			updateAnchors();
			//resizeTarget();
		}
	}, null);
	private Anchor rotateAnchor = new Anchor(Color.LAWNGREEN, false, false, null, (oldX, oldY, newX, newY, isreleased) -> {
		double angle = boundary.getRotate();
		angle += getAtan2(boundary.getX()+boundary.getWidth()/2
						, boundary.getY()+ boundary.getHeight()/2
						, oldX
						, oldY
						, newX
						, newY);
		
		boundary.setRotate(angle);
		if (isreleased) {
			updateAnchors();
			//resizeTarget();
		}
	});
	
	
	
	public BoundBox(Parent parent, double x, double y, double width, double height) {
		parentBoundBox = (GroupObject)parent;
		boundary.setX(x);
		boundary.setY(y);
		boundary.setWidth(width);
		boundary.setHeight(height);
		boundary.makeDraggable(boundary, (oldX, oldY, newX, newY, isreleased) ->{
			if (isreleased) {
				boundary.setX(newX);
				boundary.setY(newY);
				updateAnchors();
			}
		});
		getChildren().add(boundary);
		attachAnchors();
		
	}
	/**
	 * Bu metot objenin etrafındaki anchor ları BoundBox grubuna ekliyor.
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
	 * Bu metot Anchorların yeni pazisyonlarını set ediyor.
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
	private double getAtan2(double pivotX, double pivotY, double startX, double startY, double endX, double endY) {

		double val = 0;
				
		double startPosinRad = Math.atan2((startY-pivotY), (startX-pivotX));
		double stopPosinRad = Math.atan2((endY-pivotY), (endX-pivotX));
		
		val = stopPosinRad - startPosinRad;
		return Math.toDegrees(val);
	}
}
------------------------------------------------------
package painter12062020;

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
----------------------------------------------------

