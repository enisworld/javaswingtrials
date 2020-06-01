package model;

import views.MainPanel;

public class ShapeModel {
	private static ShapeModel shapeModel;
	public ShapeModel() {
		MainPanel.getInstance().repaint();
	}
	public static ShapeModel getInstance() {
		if (shapeModel == null) {
			shapeModel = new ShapeModel();
		}
		return shapeModel;
	}

}
