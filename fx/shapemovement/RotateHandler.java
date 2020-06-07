package shapemovement;

public interface RotateHandler {
	void handle(double oldX, double oldY, double newX, double newY, boolean isReleased);
}
