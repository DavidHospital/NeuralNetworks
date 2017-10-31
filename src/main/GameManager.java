package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;

import genetic.DNA;
import genetic.PopulationManager;
import tools.LineGraph;
import tools.Vector2;

public class GameManager {
	
	PopulationManager pm;
	LineGraph genData;
	
	boolean stopSim = false;
	
	public GameManager() {
		pm = new PopulationManager(100, 0.005f, 0.5f);
		genData = new LineGraph(
				new Vector2(50, 50),
				new Vector2(50, 50),
				new Vector2(700, 500),
				Color.BLUE);
	}
	
	void update() {
		if (!stopSim) {
			pm.roundRobin();
			pm.updateGraph(genData);
			pm.forwardGeneration();
		} else {
			try {
				FileWriter fw = new FileWriter("w1");
				DNA best = pm.getBest();
				fw.write(best.w1.toString());
				fw.close();
				
				fw = new FileWriter("w2");
				fw.write(best.w2.toString());
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	
	void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Game.windowWidth, Game.windowHeight);
		
		genData.render(g);
	}
	
	public void keyPressedEvent(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			stopSim = true;
		}
	}
	
	public void keyReleasedEvent(KeyEvent e) {
		
	}
}
