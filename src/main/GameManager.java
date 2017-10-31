package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import genetic.PopulationManager;
import tools.LineGraph;
import tools.Vector2;

public class GameManager {
	
	PopulationManager pm;
	LineGraph genData;
	
	public GameManager() {
		pm = new PopulationManager(20, 0.005f, 0.5f);
		genData = new LineGraph(
				new Vector2(100, 100),
				new Vector2(50, 50),
				new Vector2(400, 200),
				Color.BLUE);
	}
	
	void update() {
	}
	
	void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Game.windowWidth, Game.windowHeight);
		
		genData.render(g);
	}
	
	public void keyPressedEvent(KeyEvent e) {
		
	}
	
	public void keyReleasedEvent(KeyEvent e) {
		
	}
}
