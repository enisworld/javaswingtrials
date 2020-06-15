package painter15062020.dragdrop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class GroupObject extends Group{
	Parent parentObject;
	
	BoundBox boundBox;
	//Rectangle rectangle;//Bunun içerisine resim, þekil eklenecek
	Group shapeView;
	public GroupObject(Parent parent, String imagePath) {
		this.parentObject = parent;
		System.out.println("Parent Object " + parentObject);
		this.shapeView = new Group();
		setImagetoImageView(imagePath);
		createBoundBox();
		boundBox.setVisible(false);
		//printPanes();
		getChildren().addAll(boundBox,shapeView);
	}
	/**
	 * eklenen þekil/resim'in boundunu oluþturmak için bu metot kullanýlmaktadýr. 
	 */
	private void createBoundBox() {
			//bu grubu panelin merkez noktasýna yerleþtiriyoruz
			boundBox = new BoundBox(this, shapeView
									);
	}
	/**
	 * Verilen parametredeki resmin yüklenmesini saðlamak için bu metot kullanýlmaktadýr.
	 * @param imagePath
	 */
	private void setImagetoImageView(String imagePath) {
		Image image;
		try {
			switch (imagePath) {
			case "Rectangle":
				Rectangle rectangle = new Rectangle(50,50);
				rectangle.setX(200); 
				rectangle.setY(25); 
				this.shapeView.getChildren().add(rectangle);
				break;
			case "Ellipse":
				Ellipse ellipse = new Ellipse(100,100,30,40);
				this.shapeView.getChildren().add(ellipse);
				break;

			default:
				image = new Image(new FileInputStream(imagePath));
				
				//Setting the image view 
				ImageView imageView = new ImageView(image); 
				//System.out.println("Image Width : " + imageView.getBoundsInParent().getWidth() + "Image Height : " + imageView.getBoundsInParent().getHeight());
				;
				imageView.setX(200); 
				imageView.setY(25); 
				this.shapeView.getChildren().add(imageView);
				break;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}
	
	public void showBoundBox(boolean isVisible) {
		if (isVisible) {	
			
			createBoundBox();
			getChildren().clear();
			getChildren().addAll(boundBox,shapeView);
			boundBox.toFront();
		}
		boundBox.setVisible(isVisible);
	}
	
	
	public void updateShapeViewLocation() {
		
	}
	
	private void printPanes() {
		for (Node node : ((GridPane)((Pane)parentObject).getParent()).getChildren()) {
			
			System.out.println("Node : " + node);
			System.out.println("Node Bounds : " + node.getBoundsInParent());
			
		}
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