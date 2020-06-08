package painter;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
import shapemovement.MovementControl;
import shapemovement.Util;

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
	private VBox vBox;
	private String shapeToDraw = "";
	private boolean removeSelectedShape = false;
	private Node selectedNode;
	
	public MainPane() {
		initializeComponent();
	}
	private void initializeComponent() {

		HBox hbox = addHBox();
		
		
		BorderPane border = addBorderPane();

		//mainLayout.setTop(hbox);//Butonların olduğu alan
		//mainLayout.setCenter(null);// 4 tane panelin olduğu alan
		//mainLayout.setRight(null);//özellikler tablosunun ekleneceği alan
		
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
	    	shapeToDraw = "Line";
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
	    });

	    hbox.getChildren().addAll(buttonLine, buttonRectangle, buttonCircle, buttonRemove);

	    return hbox;
	}
	
	public VBox addVBox() {
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
	    vbox.setSpacing(8);
	    vbox.setStyle("-fx-background-color: #336699;");
	    vbox.setPrefWidth(138);
	    
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
		
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(16));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setBorder(black);
		gridPane.setBackground(new Background(new BackgroundFill(
            Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));	
		gridPane.setAlignment(Pos.CENTER); 
		gridPane.setMinSize(paneWidth, paneHeight); 
		
		
		Pane pane1 = new Pane();
		pane1.setBorder(black);
		pane1.setPrefSize(gridPane.getWidth()/2, gridPane.getHeight()/2);
		
		pane1.setOnMouseClicked(event -> {
			System.out.println("Pane 1 : " + pane1.getWidth() + " : " );
			System.out.println("Pane 1 : " + pane1.getBoundsInParent().getMinX() + " : " + pane1.getBoundsInParent().getMinY() );
			if (!shapeToDraw.equals("")) {
				Shape s = getShapeToDraw();
				pane1.getChildren().add(s);
				makeSelectable(pane1, pane1.getChildren());
				shapeToDraw = "";
			}
            final Parent parentNode = ((Node) event.getTarget()).getParent();
            //System.out.println("Center Node clicked. "+ event.getSource());
            if (selectedNode != null && !(parentNode instanceof MovementControl)) {
            	pane1.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
                selectedNode = null;
            }
        });
		
		Pane pane2 = new Pane();
		pane2.setBorder(red);
		pane2.setPrefSize(paneWidth, paneHeight);
		pane2.setOnMouseClicked(event -> {
			System.out.println("Pane 2 : " + pane2.getWidth() + " : " );
			System.out.println("Pane 2 : " + pane2.getBoundsInParent().getMinX() + " : " + pane2.getBoundsInParent().getMinY());
			System.out.println("Event : " + event.getSceneX() + " : " );
			if (!shapeToDraw.equals("")) {
				Shape s = getShapeToDraw();
				pane2.getChildren().add(s);
				makeSelectable(pane2, pane2.getChildren());
				shapeToDraw = "";
			}
            final Parent parentNode = ((Node) event.getTarget()).getParent();
            //System.out.println("Center Node clicked. "+ event.getSource());
            if (selectedNode != null && !(parentNode instanceof MovementControl)) {
            	pane2.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
                selectedNode = null;
            }
        });
		Pane pane3 = new Pane();
		pane3.setBorder(yellow);
		pane3.setPrefSize(paneWidth, paneHeight);
		pane3.setOnMouseClicked(event -> {
			if (!shapeToDraw.equals("")) {
				Shape s = getShapeToDraw();
				pane3.getChildren().add(s);
				makeSelectable(pane3, pane3.getChildren());
				shapeToDraw = "";
			}
            final Parent parentNode = ((Node) event.getTarget()).getParent();
            //System.out.println("Center Node clicked. "+ event.getSource());
            if (selectedNode != null && !(parentNode instanceof MovementControl)) {
            	pane3.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
                selectedNode = null;
            }
        });
		Pane pane4 = new Pane();
		pane4.setBorder(blue);
		pane4.setPrefSize(paneWidth, paneHeight);
		pane4.setOnMouseClicked(event -> {
			if (!shapeToDraw.equals("")) {
				Shape s = getShapeToDraw();
				pane4.getChildren().add(s);
				makeSelectable(pane4, pane4.getChildren());
				shapeToDraw = "";
			}
            final Parent parentNode = ((Node) event.getTarget()).getParent();
            //System.out.println("Center Node clicked. "+ event.getSource());
            if (selectedNode != null && !(parentNode instanceof MovementControl)) {
            	pane4.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
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
	    
	    
		/*System.out.println("Pane 1 : " + pane1.getWidth() + " : " );
		System.out.println("Pane 2 : " + pane2.getBoundsInLocal().getMinX() + " : " + pane2.getBoundsInParent().getMinY());
		System.out.println("Pane 3 : " + pane3.getBoundsInLocal().getMinX() + " : " + pane3.getBoundsInParent().getMinY());
		System.out.println("Pane 4 : " + pane4.getBoundsInLocal().getMinX() + " : " + pane4.getBoundsInParent().getMinY());*/
		
		return gridPane;
	}
	
	private Shape getShapeToDraw() {
		Shape shape = null;
		switch (shapeToDraw) {
		case "Line":
			shape = new Line(50,50,100,100);
			break;
		case "Rectangle":
			shape = new Rectangle(100,100,60,60);
			break;
		case "Ellipse":
			shape = new Ellipse(30,30,30,30);
			break;

		default:
			break;
		}
		
		return shape;
	}
	
	private void makeSelectable(Pane pane, ObservableList<Node> observableList) {
		int index = 0;
        for (Node node: observableList) {
        	System.out.println("index : " + index);
            node.setOnMouseClicked(event -> {
                if (selectedNode != node) {
                    pane.getChildren().removeIf(candidate -> candidate instanceof MovementControl);
                    selectedNode = node;
                    //TODO node seçildiğinde özellikleri eklenmeli
                    //System.out.println("Bounds -> " + bounds.getMinX()+ bounds.getMinY()+ bounds.getWidth()+ bounds.getHeight());
                    if (!removeSelectedShape) {
                    	node.toFront();
                    	//Fill node properties
                    	
                        
                        MovementControl movementControl = new MovementControl(node, vBox);
                        pane.getChildren().add(movementControl);
                    	//pane.getChildren().remove
					}
                    else {
                    	pane.getChildren().remove(node);
                    	removeSelectedShape = false;
                    }
                    
                }

                event.consume();
            });
            index++;
        }
    }
}
