package painter15062020.view;

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
		
		primaryStage.show();
		
	}
		
	public static void main(String[] args){
		launch(args);
	}
}