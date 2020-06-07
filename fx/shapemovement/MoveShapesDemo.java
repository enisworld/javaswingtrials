package shapemovement;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MoveShapesDemo extends Application {
	private BorderPane root;
	Pane topPane;
	Pane centerPane;
    private Node selectedNode;
    private double rotateAngle = 0;
    public static void main(String[] args) throws Exception {
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		
		root = new BorderPane();
		/**
		 * Top Pane
		 * includes some buttons
		 */
		topPane = new Pane();
		centerPane = new Pane();
		HBox hbox = new HBox();
		
		Button addRect = new Button("Rectangle");
		
		addRect.setOnMouseClicked(e -> {
			Rectangle newRectangle = new Rectangle(30,30,30, 30);
			//newRectangle.setRotate(45);
			centerPane.getChildren().add(newRectangle);
			makeSelectable(centerPane.getChildren());		
		});
		Button addEllipse = new Button("Ellipse");
		
		addEllipse.setOnMouseClicked(e -> {
			Ellipse newEllipse = new Ellipse(30,30,30, 30);
			centerPane.getChildren().add(newEllipse);
			makeSelectable(centerPane.getChildren());
			
            //System.out.println("addEllipse Node clicked. "+ e.getSource());			
		});
		Button addLine = new Button("Line");
		
		addLine.setOnMouseClicked(e -> {
            System.out.println("addLine Node clicked. "+ e.getSource());			
		});
		//butona event eklenmeli
		hbox.getChildren().addAll(addRect, addEllipse, addLine);
		topPane.getChildren().addAll(hbox);
		
		/**
		 * Center Pane
		 * Includes 4 pane to draw shapes
		 */
		
		
		
		root.setTop(topPane);
		root.setCenter(centerPane);
		
		Scene scene = new Scene(root,400, 400, Color.ALICEBLUE);
		
		stage.setScene(scene);
        stage.show();
        centerPane.setOnMouseClicked(event -> {
            final Parent parentNode = ((Node) event.getTarget()).getParent();
            //System.out.println("Center Node clicked. "+ event.getSource());
            if (selectedNode != null && !(parentNode instanceof MovementControl)) {
            	centerPane.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
                selectedNode = null;
            }
        });
       
        
	}

	/*private void makeSelectable(Node... nodes) {
		for (Node node: nodes) {
		}
		
	}*/
	
	
    private void makeSelectable(ObservableList<Node> observableList) {
        for (Node node: observableList) {
        	//System.out.println("Size : " + observableList.size());
            node.setOnMouseClicked(event -> {
                if (selectedNode != node) {
                	//System.out.println("Selected node is not null");
                    centerPane.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
                    selectedNode = node;
                    Bounds bounds = node.getBoundsInParent();
                    
                    //System.out.println("Bounds -> " + bounds.getMinX()+ bounds.getMinY()+ bounds.getWidth()+ bounds.getHeight());
                    
                    node.toFront();
                    
                    MovementControl movementControl = new MovementControl(node);
                    centerPane.getChildren().add(movementControl);
                }

                event.consume();
            });
        }
    }


}
