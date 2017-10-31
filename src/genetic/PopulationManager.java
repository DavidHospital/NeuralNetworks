package genetic;

import java.awt.Graphics;

public class PopulationManager {

	Population pop;
	
	public PopulationManager(int popSize, float mutationRate, float parentPercentile) {
		pop = new Population(popSize, mutationRate, parentPercentile);
	}
	
	public void forwardGeneration() {
		pop.sortPopulation();
		for(int i = 0; i < pop.popSize * (1 - pop.parentPercentile); i ++) {
			DNA child = pop.getNewChild();
			pop.population[pop.popSize - 1 - i] = child;
		}
		pop.resetScores();
	}
	
	public void render(Graphics g) {
		
	}
}
