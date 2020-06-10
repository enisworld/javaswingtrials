package painter3d;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import shapemovement.MovementControl;

public class DrawableObject extends Group{
	//private MovementControl boundGroup;//bu objenin etrafındaki kutucuk ve daireleri temsil edecek
	private ControlBox controlBox;
	private Object3D obj3d; //
	private Group groupImage; //çizilmek istenen şeklin, resmin tutulacağı değişken
	
	/**
	 * 
	 * @param obj3d
	 * @param str - bu değişken şeklin hangi panele çizildiğini tutacak. String yerine panel değişkeninin kendisi de parametre olarak verilebilir.
	 */
	public DrawableObject(Object3D obj3d, String str) {
		//this.boundGroup = new MovementControl(null, null);
		this.obj3d = obj3d;
		if (str == "Front") {
			getGroupImage();
			getObjectXY();
		}
		else if (str == "Side") {
			getObjectYZ();
		}
		else if (str == "Top") {
			getObjectXZ();
		}
		controlBox.setVisible(false);
		getChildren().addAll(controlBox, groupImage);
	}
	
	public void getObjectXY() {
		if (obj3d != null) {
			this.controlBox = new ControlBox(obj3d.getX(), obj3d.getY(), obj3d.getWidth(), obj3d.getHeight());
		}
	}
	public void getObjectYZ() {
		if (obj3d != null) {
			this.controlBox = new ControlBox(obj3d.getZ() + 2 * obj3d.getDepth(), obj3d.getY(), obj3d.getDepth(),
					obj3d.getHeight());
		}
	}
	public void getObjectXZ() {
		if (obj3d != null) {
			this.controlBox = new ControlBox(obj3d.getX() + obj3d.getWidth() * 2, obj3d.getZ(), obj3d.getWidth(),
					obj3d.getDepth());
		}
	}

	public Group getGroupImage() {
		this.groupImage = new Group();
		Rectangle rect = new Rectangle(obj3d.getX(), obj3d.getY(),15, 15);
		Rectangle rect2 = new Rectangle(obj3d.getX()+20, obj3d.getY(), 5, 15);
		rect2.setFill(Color.BLUE);
		this.groupImage.getChildren().addAll(rect,rect2);
		return groupImage;
	}

	public ControlBox getControlBox() {
		return controlBox;
	}

	public void setControlBox(ControlBox controlBox) {
		this.controlBox = controlBox;
	}

	public void setGroupImage(Group groupImage) {
		
		
		this.groupImage = groupImage;
	}

	public Object3D getObj3d() {
		return obj3d;
	}

	public void setObj3d(Object3D obj3d) {
		this.obj3d = obj3d;
	}
	
}
