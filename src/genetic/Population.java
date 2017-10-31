package genetic;

import java.util.Random;

public class Population {
	
	int popSize;
	float mutationRate;
	float parentPercentile;
	
	DNA[] population;
	int[] score;
	
	Population(int popSize, float mutationRate, float parentPercentile) {
		this.popSize = popSize;
		this.mutationRate = mutationRate;
		this.parentPercentile = parentPercentile;
		
		this.population = new DNA[popSize];
		this.score = new int[popSize];
	}
	
	void generateInital(int inputSize, int hiddenSize, int outputSize) {
		for (int i = 0; i < popSize; i ++) {
			DNA dna = DNA.createRandom(inputSize, hiddenSize, outputSize);
			
			population[i] = dna;
			score[i] = 0;
		}
	}
	
	DNA getParent(float percentile) {
		Random r = new Random();
		return population[r.nextInt((int) (popSize * percentile))];
	}
	
	DNA getNewChild() {
		DNA parent1 = getParent(parentPercentile);
		DNA parent2 = getParent(parentPercentile);
		DNA child = parent1.splice(parent2);
		child.mutate(mutationRate);
		return child;
	}
	
	void sortPopulation() {
		int i = 1;
		while (i < popSize) {
			int j = i;
			while (j > 0 && score[j - 1] < score[j]) {
				int temp = score[j];
				score[j] = score[j-1];
				score[j-1] = temp;
				
				DNA temp2 = population[j];
				population[j] = population[j-1];
				population[j-1] = temp2;
				
				j --;
			}
			i ++;
		}
	}
	
	void resetScores() {
		this.score = new int[popSize];
	}
}
