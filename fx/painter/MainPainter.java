package painter;

import java.util.Iterator;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainPainter extends Application{
	MainPane mpane;
	private double previousWidth = 0, previousHeight = 0;
	@Override
	public void start(Stage primaryStage) throws Exception {
		mpane = new MainPane();
		
		Scene scene = new Scene(mpane);
		
		primaryStage.setScene(scene);
		
		/*ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->{
	    	//System.out.println("Height: " + primaryStage.getHeight() + " Width: " + primaryStage.getWidth());
			//System.out.println("Old Value : " + oldValue + " NewValue : " + newValue);
			
			resizePanes(primaryStage.getWidth() - previousWidth, primaryStage.getHeight()- previousHeight);
			previousWidth = primaryStage.getWidth();
			previousHeight = primaryStage.getHeight();
		};

	    primaryStage.widthProperty().addListener(stageSizeListener);
	    primaryStage.heightProperty().addListener(stageSizeListener); 
		previousWidth = primaryStage.getWidth();
		previousHeight = primaryStage.getHeight();*/
		primaryStage.show();
		
	}
	/****
	 * bu kod grid pane içerisindeki panellerin yeniden boyutlandırılması için yazılmıştı.
	 * ROW ve COLOMN constraintler ile pencerenin büyütülüp küçütülmesi sırasında grid pane resize edildiğinden buna gerek kalmadı. 
	 */	
	private void resizePanes(double resizeWidth, double resizeHeight) {
		/*for (Node node : mpane.getChildren()) {
			if (node instanceof BorderPane) {
				for (Node innerNode : ((BorderPane) node).getChildren()) {
					if (innerNode instanceof GridPane) {
						for (Node pane : ((GridPane) innerNode).getChildren()) {
							if (pane instanceof Pane) {
								mpane.paneWidth = mpane.paneWidth+ resizeWidth;
								mpane.paneHeight = mpane.paneHeight+resizeHeight;
								pane.resize(mpane.paneWidth, mpane.paneHeight);
								
								//System.out.println("resized");
							}
						}
						break;
					}		
				}
				
				break;
			}
		}*/
		
		
	}
	public static void main(String[] args){
		launch(args);
	}
}
