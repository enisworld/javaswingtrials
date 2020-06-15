package painter15062020.view;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import painter15062020.dragdrop.GroupObject;
//import painter12062020.dragdrop.GroupObject;
import shapemovement.MovementControl;
//import shapemovement.utils.CustomNode;
//import shapemovement.utils.PaneUpdateHelper;
//import shapemovement.utils.Util;

public class MainPane extends BorderPane{
	private static final Border black = new Border(new BorderStroke(Color.BLACK,
	        BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2)));
	private static final Border red = new Border(new BorderStroke(Color.RED,
	        BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2)));
    private static final Border blue = new Border(new BorderStroke(Color.BLUE,
	        BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2)));
    private static final Border yellow = new Border(new BorderStroke(Color.YELLOW,
	        BorderStrokeStyle.DOTTED, new CornerRadii(8), new BorderWidths(2)));
    //private static final Color yellow = Color.YELLOW.deriveColor(0, .9, 1, 1);
	public double paneWidth = 400, paneHeight = 300; 
	private static VBox vBox;
	private String shapeToDraw = "";
	private boolean removeSelectedShape = false;
	private static Node selectedNode;
	private Pane selectedNodePane;
	public MainPane() {
		initializeComponent();
	}
	private void initializeComponent() {

		HBox hbox = addHBox();
		
		
		BorderPane border = addBorderPane();

		//mainLayout.setTop(hbox);//Butonlarýn olduðu alan
		//mainLayout.setCenter(null);// 4 tane panelin olduðu alan
		//mainLayout.setRight(null);//özellikler tablosunun ekleneceði alan
		
		this.setTop(hbox);
		this.setCenter(border);
		//this
	}
	
	public HBox addHBox() {
	    HBox hbox = new HBox();
	    
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #336699;");

	    Button buttonLine = new Button("Line");
	    buttonLine.setPrefSize(75, 20);
	    buttonLine.setOnMouseClicked(e -> {
	    	shapeToDraw = "direk.jpg"/*"Line"*/;
	    });
	    Button buttonRectangle = new Button("Rectangle");
	    buttonRectangle.setPrefSize(75, 20);
	    buttonRectangle.setOnMouseClicked(e -> {
	    	shapeToDraw = "Rectangle";
	    });
	    Button buttonCircle = new Button("Ellipse");
	    buttonCircle.setPrefSize(75, 20);
	    buttonCircle.setOnMouseClicked(e -> {
	    	shapeToDraw = "Ellipse";
	    });
	    Button buttonRemove = new Button("Remove");
	    buttonRemove.setPrefSize(75, 20);
	    buttonRemove.setOnMouseClicked(e -> {
	    	shapeToDraw = "";
	    	removeSelectedShape = true;
	    	//System.out.println("selectedNode : " + selectedNode);
	    	if (selectedNode != null) {
	    		/*Pane pane=  ((Pane)selectedNode.getParent());*/
	    		//System.out.println("Pane" + pane);
	    		/*pane.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
	    		pane.getChildren().remove(selectedNode);*/
	    		//Util.deleteProperties((TableView<CustomNode>)vBox.getChildren().get(0));
	    		//TODO after removed selectedNode, properties must remove
	    		///System.out.println("Selected node removed");
	    		selectedNode = null;
	    		
			}
	    });

	    hbox.getChildren().addAll(buttonLine, buttonRectangle, buttonCircle, buttonRemove);

	    return hbox;
	}
	
	public VBox addVBox() {
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
	    vbox.setSpacing(8);
	    vbox.setStyle("-fx-background-color: #336699;");
	    //vbox.setPrefWidth(138);
	    vbox.setMaxWidth(210);
	    return vbox;
	}
	
	private BorderPane addBorderPane() {
		BorderPane borderPane = new BorderPane();
		GridPane gridPane = addGridPane();
		vBox = addVBox();
		borderPane.setCenter(gridPane);
		borderPane.setRight(vBox);
		return borderPane;
	}

	private GridPane addGridPane() {
		Pane pane1, pane2, pane3, pane4;
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(16));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setBorder(black);
		gridPane.setBackground(new Background(new BackgroundFill(
            Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));	
		gridPane.setAlignment(Pos.CENTER); 
		gridPane.setMinSize(paneWidth, paneHeight); 
		
		
		pane1 = new Pane();
		pane1.setBorder(black);
		pane1.setPrefSize(gridPane.getWidth()/2, gridPane.getHeight()/2);
		
		pane1.setOnMouseClicked(event -> {
			//System.out.println("Pane 1 : " + pane1.getWidth() + " : " );
			//System.out.println("Pane 1 : " + pane1.getBoundsInParent().getMinX() + " : " + pane1.getBoundsInParent().getMinY() );
			if (!shapeToDraw.equals("")) {
				//Shape s = getShapeToDraw(pane1);
				GroupObject obj = new GroupObject(pane1, shapeToDraw);
				pane1.getChildren().add(obj);
				//PaneUpdateHelper.updatePanes(pane1, pane1);
				makeSelectable(pane1);
				shapeToDraw = "";
			}
			//final Parent parentNode = ((Node) event.getTarget()).getParent();
            
            if (selectedNode != null /*&& !(parentNode instanceof DrawableObject)*/) {
            	((GroupObject)selectedNode).showBoundBox(false);
            	selectedNode = null;
            }
        });
		
		pane2 = new Pane();
		pane2.setBorder(red);
		pane2.setPrefSize(paneWidth, paneHeight);
		pane2.setOnMouseClicked(event -> {
			if (!shapeToDraw.equals("")) {
				GroupObject obj = new GroupObject(pane2, shapeToDraw);
				pane2.getChildren().add(obj);
				makeSelectable(pane2);
				shapeToDraw = "";
			}
			//final Parent parentNode = ((Node) event.getTarget()).getParent();
            
            if (selectedNode != null /*&& !(parentNode instanceof DrawableObject)*/) {
            	((GroupObject)selectedNode).showBoundBox(false);
            	selectedNode = null;
            }
        });
		pane3 = new Pane();
		pane3.setBorder(yellow);
		pane3.setPrefSize(paneWidth, paneHeight);
		pane3.setOnMouseClicked(event -> {
			if (!shapeToDraw.equals("")) {
				GroupObject obj = new GroupObject(pane3, shapeToDraw);

				pane3.getChildren().add(obj);
				makeSelectable(pane3);
				shapeToDraw = "";
			}
			//final Parent parentNode = ((Node) event.getTarget()).getParent();
            
            if (selectedNode != null /*&& !(parentNode instanceof DrawableObject)*/) {
            	((GroupObject)selectedNode).showBoundBox(false);
            	selectedNode = null;
            }
        });
		pane4 = new Pane();
		pane4.setBorder(blue);
		pane4.setPrefSize(paneWidth, paneHeight);
		pane4.setOnMouseClicked(event -> {
			if (!shapeToDraw.equals("")) {
				GroupObject obj = new GroupObject(pane4, shapeToDraw);
				pane4.getChildren().add(obj);
				makeSelectable(pane4);
				shapeToDraw = "";
			}
			//final Parent parentNode = ((Node) event.getTarget()).getParent();
            
            if (selectedNode != null /*&& !(parentNode instanceof DrawableObject)*/) {
            	((GroupObject)selectedNode).showBoundBox(false);
            	selectedNode = null;
            }
        });
		gridPane.add(pane1, 0, 0);
		gridPane.add(pane2, 1, 0);
		gridPane.add(pane3, 0, 1);
		gridPane.add(pane4, 1, 1);
		
	
		RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(50);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(50);
	    
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(50);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(50);
	    
	    gridPane.getRowConstraints().addAll(row1, row2);
	    gridPane.getColumnConstraints().addAll(column1, column2);
	    		
		return gridPane;
	}
	
	private GroupObject getGroupObject(){
		Shape shape = null;
		switch (shapeToDraw) {
		case "Line":
			/*shape = new Line(paneCenterPointX-30,paneCenterPointY,paneCenterPointX+30,paneCenterPointY);
			shape.setStrokeWidth(2);*/
			break;
		case "Rectangle":
			
			//shape = new Rectangle(paneCenterPointX-40,paneCenterPointY-40,80,80);
			break;
		case "Ellipse":
			//shape = new Ellipse(paneCenterPointX,paneCenterPointY,40,40);
			break;
		case "Image":
			//shape = new Ellipse(paneCenterPointX,paneCenterPointY,40,40);
			break;

		default:
			break;
		}
		GroupObject obj = new GroupObject(null, null);

		return obj;
	}
	
	
	private Shape getShapeToDraw(Pane pane) {
		Shape shape = null;
		double paneCenterPointX  = pane.getWidth()/2;
		double paneCenterPointY  = pane.getHeight()/2;
		
		switch (shapeToDraw) {
		case "Line":
			shape = new Line(paneCenterPointX-30,paneCenterPointY,paneCenterPointX+30,paneCenterPointY);
			shape.setStrokeWidth(2);
			break;
		case "Rectangle":
			shape = new Rectangle(paneCenterPointX-40,paneCenterPointY-40,80,80);
			break;
		case "Ellipse":
			shape = new Ellipse(paneCenterPointX,paneCenterPointY,40,40);
			break;

		default:
			break;
		}
		
		return shape;
	}
	
	private void makeSelectable(Pane pane) {
		ObservableList<Node> observableList = pane.getChildren();
		for (Node node : observableList) {
			node.setOnMouseClicked(event -> {
				if (selectedNode != node) {		
                    selectedNode = node;
                    ((GroupObject)node).showBoundBox(true);                }
                event.consume();
			});
		}
    }
	
	public static void updateNodeFromTable(/*CustomNode customNode*/) {
		/*Pane pane = ((Pane)selectedNode.getParent());
		//Set new value from table view
		Node node = Util.setNewValueCurrentNode(selectedNode, customNode);
		
		pane.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
        //selectedNode = node;
        
    	//Fill node properties
    	node.toFront();
        MovementControl movementControl = new MovementControl(node, vBox);
        pane.getChildren().add(movementControl); */
	}
}