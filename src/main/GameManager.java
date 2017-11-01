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
	LineGraph genDataX;
	LineGraph genDataO;
	
	boolean stopSim = false;
	
	public GameManager() {
		pm = new PopulationManager(100, 0.005f, 0.2f);
		genDataX = new LineGraph(
				new Vector2(50, 50),
				new Vector2(50, 50),
				new Vector2(700, 200),
				Color.BLUE);
		genDataO = new LineGraph(
				new Vector2(50, 50),
				new Vector2(50, 300),
				new Vector2(700, 200),
				Color.RED);
	}
	
	void update() {
		if (!stopSim) {
			pm.roundRobin();
			pm.updateGraphX(genDataX);
			pm.updateGraphO(genDataO);
			pm.forwardGeneration();
		} else {
			try {
				
				// X
				FileWriter fw = new FileWriter("w1X");
				DNA best = pm.getBestX();
				fw.write(best.w1.toString());
				fw.close();
				
				fw = new FileWriter("w2X");
				fw.write(best.w2.toString());
				fw.close();
				
				// O
				fw = new FileWriter("w1O");
				best = pm.getBestO();
				fw.write(best.w1.toString());
				fw.close();
				
				fw = new FileWriter("w2O");
				fw.write(best.w2.toString());
				fw.close();				
			} catch (IOException e) {
				e.printStackTrace();
			}
			stopSim = false;
		}
	}
	
	void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Game.windowWidth, Game.windowHeight);
		
		genDataX.render(g);
		genDataO.render(g);
	}
	
	public void keyPressedEvent(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			stopSim = true;
		}
	}
	
	public void keyReleasedEvent(KeyEvent e) {
		
	}
}
