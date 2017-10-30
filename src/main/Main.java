package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import game.ttt.TicTacToe;
import game.ttt.TicTacToe.Player;
import network.neural.engine.Matrix;
import network.neural.engine.NeuralNetwork3;

public class Main {
	
	public static Scanner r;
	public static PrintWriter pw;
	
	public static void main (String[] args) {
		
		r = new Scanner(System.in);
		try {
			pw = new PrintWriter("gen_data.txt");
			
			Population pop = new Population(20, 0.02f, 0.5f);
			pop.generateInital(19, 15, 9);
			
			int i = 0;
			while(i < 100000) {
				pop.population = pop.newPopulation();
				pop.score = new int[pop.popSize];
				
				for (int j = 0; j < pop.popSize; j ++) {
					NeuralNetwork3 nn1 = DNA.CreateFromDNA(pop.population[j]);
					for (int k = 0; k < pop.popSize; k ++) {
						NeuralNetwork3 nn2 = DNA.CreateFromDNA(pop.population[k]);
						TicTacToe ttt = TicTacToe.StartingBoard(Player.COMPUTER, Player.COMPUTER);
						ttt.loadNeuralNetwork(0, nn1);
						ttt.loadNeuralNetwork(1, nn2);
						ttt.start();
						int winner = ttt.getWinner();
						if (winner == 0) {
							pop.score[j] += 1;
						} else if (winner == 1) {
							pop.score[k] += 1;
						} else {
						}
					}
				}
				if (i % 100 == 0) {
					pop.sortPopulation();
					System.out.printf("Generation: %d\n", i);
					System.out.printf("\tBest: %d\n", pop.score[0]);
					System.out.printf("\tMedian: %d\n", pop.score[pop.popSize / 2]);
					System.out.printf("\tWorst: %d\n", pop.score[pop.popSize - 1]);
					pw.printf("%d,%d,%d,%d\n", i, pop.score[0], pop.score[pop.popSize / 2], pop.score[pop.popSize - 1]);
					pw.flush();
				}
				i++;
			}
			
			pw.close();
			
			while(true) {
				pop.sortPopulation();
				NeuralNetwork3 nn = DNA.CreateFromDNA(pop.population[0]);
				TicTacToe ttt = TicTacToe.StartingBoard(Player.COMPUTER, Player.HUMAN);
				ttt.loadNeuralNetwork(0, nn);
				ttt.start();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}


class Population {
	
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
	
	DNA[] newPopulation() {
		sortPopulation();
		DNA[] newPop = new DNA[popSize];
		
		for (int i = 0; i < popSize; i ++) {
			DNA parent1 = getParent(parentPercentile);
			DNA parent2 = getParent(parentPercentile);
			DNA child = parent1.splice(parent2);
			child.mutate(mutationRate);
			newPop[i] = child;
		}
		
		return newPop;
	}
	
	DNA getParent(float percentile) {
		Random r = new Random();
		return population[r.nextInt((int) (popSize * percentile))];
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
}


class DNA {
	
	Matrix w1;
	Matrix w2;
	
	DNA(){
	}
	
	DNA(int inputSize, int hiddenSize, int outputSize) {
		w1 = new Matrix(inputSize, hiddenSize);
		w2 = new Matrix(hiddenSize, outputSize);
	}
	
	public static DNA createRandom(int inputSize, int hiddenSize, int outputSize) {
		Random r = new Random();
		DNA dna = new DNA(inputSize, hiddenSize, outputSize);
		
		for (int k = 0; k < inputSize; k ++) {
			for (int j = 0; j < hiddenSize; j ++) {
				dna.w1.set(k, j, r.nextGaussian());
			}
		}
		
		for (int k = 0; k < hiddenSize; k ++) {
			for (int j = 0; j < outputSize; j ++) {
				dna.w2.set(k, j, r.nextGaussian());
			}
		}
		
		return dna;
	}
	
	public static DNA CreateDNA(NeuralNetwork3 nn) {
		DNA dna = new DNA();
		dna.w1 = nn.getW1();
		dna.w2 = nn.getW2();
		return dna;
	}
	
	public static NeuralNetwork3 CreateFromDNA(DNA dna) {
		NeuralNetwork3 nn = new NeuralNetwork3(dna.w1, dna.w2);
		return nn;
	}
	
	public void mutate(float mutationRate) {
		Random r = new Random();
		for (int i = 0; i < w1.getRows(); i ++) {
			for (int j = 0; j < w1.getColumns(); j ++) {
				if (r.nextFloat() < mutationRate) {
					w1.set(i, j, w1.get(i, j) + r.nextGaussian());
				}
			}
		}
		
		for (int i = 0; i < w2.getRows(); i ++) {
			for (int j = 0; j < w2.getColumns(); j ++) {
				if (r.nextFloat() < mutationRate) {
					w2.set(i, j, w2.get(i, j) + r.nextGaussian());
				}
			}
		}
	}
	
	public DNA splice(DNA other) {
		DNA ret = new DNA();
		ret.w1 = new Matrix(w1.getRows(), w1.getColumns());
		ret.w2 = new Matrix(w2.getRows(), w2.getColumns());
		
		for (int i = 0; i < w1.getRows(); i ++) {
			for (int j = 0; j < w1.getColumns(); j ++) {
				ret.w1.set(i, j, this.w1.get(i, j));
			}
		}
		
		for (int i = 0; i < w2.getRows(); i ++) {
			for (int j = 0; j < w2.getColumns(); j ++) {
				ret.w2.set(i, j, other.w2.get(i, j));
			}
		}
		return ret;
	}
	
	protected DNA clone() {
		DNA dna = new DNA();
		dna.w1 = this.w1.clone();
		dna.w2 = this.w2.clone();
		return dna;
	}
}
