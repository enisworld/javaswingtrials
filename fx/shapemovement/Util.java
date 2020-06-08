package shapemovement;

import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Util {
	static double  rotateStartX, rotateStartY, rotateEndX, rotateEndY;
    public static void enableRotate(Circle node, boolean canDragX, boolean canDragY, RotateHandler rotateHandler) {
    	
    	if (rotateHandler != null) {
    		node.setOnMousePressed(mouseEvent -> {	
    			rotateStartX = mouseEvent.getSceneX();
    			rotateStartY = mouseEvent.getSceneY();
    			rotateHandler.handle(rotateStartX, rotateStartY, rotateStartX, rotateStartY, false);
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
    			rotateStartX = rotateEndX;
    			rotateStartY = rotateEndY;
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
    
    public static TableView<CustomNode> getNodeProperties(Node node) {
    	TableView<CustomNode> table = new TableView<>();
    	//node.getProperties();
    	
    	table.setEditable(true);
    	TableColumn<CustomNode, String> firstNameCol = new TableColumn<>("Property");
    	firstNameCol.setCellValueFactory(
                new PropertyValueFactory<CustomNode, String>("property"));
    	/*firstNameCol.setOnEditCommit(event -> {
    	    CustomNode row = event.getRowValue();
    	    row.setValue(event.getNewValue());
    	});
    	*/
        TableColumn<CustomNode, String> lastNameCol = new TableColumn<>("Value");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<CustomNode, String>("value"));
        ObservableList<CustomNode> data = getProp(node);
        
        
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);
    	//table.set
    	return table;
    }
    static String roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#,####");
        return Double.toString(Double.valueOf(twoDForm.format(d)));
    }
    private static ObservableList<CustomNode> getProp(Node node){
    	double angle = 0;
    	String startX = "", startY= "",centerX = "", centerY= "", width= "", height= "", radiusX= "", radiusY= "";
    	ObservableList<CustomNode> data = FXCollections.observableArrayList();
    	
    	
    	if (node instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) node;
			angle = ellipse.getRotate();
			centerX = roundTwoDecimals(ellipse.getCenterX());
			centerY = roundTwoDecimals(ellipse.getCenterY());
			radiusX = roundTwoDecimals(ellipse.getRadiusX());
			radiusY = roundTwoDecimals(ellipse.getRadiusY());
			data = FXCollections.observableArrayList(
	    			new CustomNode("Center X", centerX)
	    			,new CustomNode("Center Y", centerY)
	    			,new CustomNode("Angle", roundTwoDecimals(angle))
	    			,new CustomNode("Radius X", radiusX)
	    			,new CustomNode("Radius Y", radiusY));
		}
    	if (node instanceof Rectangle) {
    		Rectangle rectangle = (Rectangle) node;
			angle = rectangle.getRotate();
			startX = roundTwoDecimals(rectangle.getX());
			startY = roundTwoDecimals(rectangle.getY());
			width = roundTwoDecimals(rectangle.getWidth());
			height = roundTwoDecimals(rectangle.getHeight());
			data = FXCollections.observableArrayList(
	    			new CustomNode("Start X", startX)
	    			,new CustomNode("Start Y", startY)
	    			,new CustomNode("Angle", roundTwoDecimals(angle))
	    			,new CustomNode("Width", width)
	    			,new CustomNode("Height", height));
    		
		}
    	if (node instanceof Line) {
    		Line line = (Line) node;
		}
    			
    			
    	return data;
    }
    
    
}
