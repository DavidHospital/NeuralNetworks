package tools;

import java.awt.Color;
import java.awt.Graphics;

public class LineGraph extends Graph {

	public LineGraph(Vector2 axis, Vector2 position, Vector2 size, Color color) {
		super(axis, position, size, color);
	}

	@Override
	public void render(Graphics g) {
		drawAxis(g);
		
		g.setColor(color);
		for (int i = 0; i < data.size() - 1; i ++) {
			Vector2 p1 = calcPoint(data.get(i));
			Vector2 p2 = calcPoint(data.get(i + 1));
			g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
		}
	}

}
