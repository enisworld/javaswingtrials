package painter15062020.dragdrop;

public interface DragHandler {
	void handle(double oldX, double oldY, double newX, double newY, boolean isReleased);
}
