package genetic;

import game.ttt.TicTacToe;
import game.ttt.TicTacToe.Player;
import network.neural.engine.NeuralNetwork3;
import tools.Graph;
import tools.Vector2;

public class PopulationManager {

	Population pop;
	int generation;
	
	public PopulationManager(int popSize, float mutationRate, float parentPercentile) {
		pop = new Population(popSize, mutationRate, parentPercentile);
		pop.generateInital(19, 9, 9);
		generation = 0;
	}
	
	public void roundRobin() {
		for (int i = 0; i < pop.popSize; i ++) {
			NeuralNetwork3 nn1 = DNA.CreateFromDNA(pop.population[i]);
			
			for (int j = 0; j < pop.popSize; j ++) {
				NeuralNetwork3 nn2 = DNA.CreateFromDNA(pop.population[j]);
				
				TicTacToe ttt = TicTacToe.StartingBoard(Player.COMPUTER, Player.COMPUTER);
				ttt.loadNeuralNetwork(0, nn1);
				ttt.loadNeuralNetwork(1, nn2);
				ttt.start();
				
				switch (ttt.getWinner()) {
				case 0: 
					pop.score[j] ++;
					break;
				case 1:
					pop.score[i] ++;
					break;
				}
			}
		}
	}
	
	public void forwardGeneration() {
		generation ++;
		pop.sortPopulation();
		DNA[] newPop = new DNA[pop.popSize];
		for(int i = 0; i < pop.popSize; i ++) {
			DNA child = pop.getNewChild();
			newPop[i] = child;
		}
		pop.population = newPop;
		pop.resetScores();
	}
	
	public void updateGraph(Graph graph) {
		float avg = 0;
		for (int i = 0; i < pop.popSize; i ++) {
			avg += pop.score[i];
		}
		avg /= pop.popSize;
		graph.addPoint(new Vector2(generation, avg));
	}
	
	public DNA getBest() {
		pop.sortPopulation();
		return pop.population[0];
	}
}
