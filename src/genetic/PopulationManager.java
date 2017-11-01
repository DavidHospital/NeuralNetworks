package genetic;

import game.ttt.TicTacToe;
import game.ttt.TicTacToe.Player;
import network.neural.engine.NeuralNetwork3;
import tools.Graph;
import tools.Vector2;

public class PopulationManager {

	Population popX, popO;
	int generation;
	
	public PopulationManager(int popSize, float mutationRate, float parentPercentile) {
		popX = new Population(popSize, mutationRate, parentPercentile);
		popX.generateInital(18, 9, 9);
		
		popO = new Population(popSize, mutationRate, parentPercentile);
		popO.generateInital(18, 9, 9);
		
		generation = 0;
	}
	
	public void roundRobin() {
		for (int i = 0; i < popX.popSize; i ++) {
			NeuralNetwork3 nn1 = DNA.CreateFromDNA(popX.population[i]);
			
			for (int j = 0; j < popO.popSize; j ++) {
				NeuralNetwork3 nn2 = DNA.CreateFromDNA(popO.population[j]);
				
				TicTacToe ttt = TicTacToe.StartingBoard(Player.COMPUTER, Player.COMPUTER);
				ttt.loadNeuralNetwork(0, nn1);
				ttt.loadNeuralNetwork(1, nn2);
				ttt.start();
				
				switch (ttt.getWinner()) {
				case 0: 
					popO.score[j] += 1;
					break;
				case 1:
					popX.score[i] += 1;
					break;
				case 2:
					break;
				}
			}
		}
	}
	
	public void forwardGeneration() {
		generation ++;
		forwardGen(popX);
		forwardGen(popO);
	}
	
	void forwardGen(Population pop) {
		pop.sortPopulation();
		DNA[] newPop = new DNA[pop.popSize];
		for(int i = 0; i < pop.popSize; i ++) {
			if (i < pop.popSize * pop.parentPercentile) {
				newPop[i] = pop.population[i].clone();
			} else {
				DNA child = pop.getNewChild();
				newPop[i] = child;
			}
		}
		pop.population = newPop;
		pop.resetScores();
	}
	
	public void updateGraphX(Graph graph) {
		float avg = 0;
		for (int i = 0; i < popX.popSize; i ++) {
			avg += popX.score[i];
		}
		avg /= popX.popSize;
		graph.addPoint(new Vector2(generation, avg));
	}
	
	public void updateGraphO(Graph graph) {
		float avg = 0;
		for (int i = 0; i < popO.popSize; i ++) {
			avg += popO.score[i];
		}
		avg /= popO.popSize;
		graph.addPoint(new Vector2(generation, avg));
	}
	
	public DNA getBestX() {
		popX.sortPopulation();
		return popX.population[0];
	}
	
	public DNA getBestO() {
		popO.sortPopulation();
		return popO.population[0];
	}
}
