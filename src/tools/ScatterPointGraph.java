package tools;

import java.awt.Color;
import java.awt.Graphics;

public class ScatterPointGraph extends Graph {

	public ScatterPointGraph(Vector2 axis, Vector2 position, Vector2 size, Color color) {
		super(axis, position, size, color);
	}

	@Override
	public void render(Graphics g) {
		drawAxis(g);
		
		g.setColor(color);
		for (Vector2 point : data) {
			Vector2 gPoint = calcPoint(point);
			g.fillOval((int)gPoint.x - 2, (int)gPoint.y - 2, 4, 4);
		}
	}

}
