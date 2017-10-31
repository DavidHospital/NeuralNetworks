package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Graph {

	Vector2 axis;
	Vector2 position;
	Vector2 size;
	Color color;
	
	ArrayList<Vector2> data;
	
	public Graph(Vector2 axis, Vector2 position, Vector2 size, Color color) {
		this.axis = axis;
		this.position = position;
		this.size = size;
		this.color = color;
		
		data = new ArrayList<Vector2>();
	}
	
	public abstract void render(Graphics g);
	
	protected void drawAxis(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int)position.x, (int)position.y, (int)position.x, (int)(position.y + size.y));
		g.drawLine((int)position.x, (int)(position.y + size.y), (int)(position.x + size.x), (int)(position.y + size.y));
		
		for (int i = 0; i <= axis.y; i += axis.y / 5) {
			g.drawLine((int)(position.x - 10), (int)(position.y + i / axis.y * size.y), 
					(int)position.x, (int)(position.y + i / axis.y * size.y));
			g.drawString((int)(axis.y - i) + "", (int)(position.x - 30), (int)(position.y + i / axis.y * size.y));
		}
		
		for (int i = 0; i <= axis.x; i += axis.x / 5) {
			g.drawLine((int)(position.x + i / axis.x * size.x), (int)(position.y + size.y), 
					(int)(position.x + i / axis.x * size.x), (int)(position.y + size.y + 10));
			g.drawString((int)(i) + "", (int)(position.x + i / axis.x * size.x), (int)(position.y + size.y + 30));
		}
	}
	
	public void sortData() {
		int i = 1;
		while (i < data.size()) {
			int j = i;
			while (j > 0 && data.get(j-1).x > data.get(j).x) {
				Vector2 temp = data.get(j);
				data.set(j, data.get(j-1));
				data.set(j-1, temp);
				j --;
			}
			i ++;
		}
	}
	
	public void addPoint(Vector2 point) {
		data.add(point);
	}
	
	protected Vector2 calcPoint(Vector2 point) {
		Vector2 a = point.div(axis).mult(size);
		a.y = a.y * -1 + size.y;
		return a.add(position);
	}
}
