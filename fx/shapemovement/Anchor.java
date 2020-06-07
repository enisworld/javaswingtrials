package shapemovement;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class Anchor extends Circle{
	
	public Anchor(Color color, boolean canDragX, boolean canDragY, DragHandler dragHandler, RotateHandler rotateHandler) {
		super(0, 0, 5);
		setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);
        Util.enableDrag(this, canDragX, canDragY, dragHandler);
        Util.enableRotate(this, canDragX, canDragY, rotateHandler);

	}

}
