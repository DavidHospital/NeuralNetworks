package main;

import game.ttt.TicTacToe;
import game.ttt.TicTacToe.Player;
import genetic.DNA;
import network.neural.engine.NeuralNetwork3;

public class PlayAgainstAI {

	public static void main(String[] args) {
		DNA dna = DNA.createFromFiles("w1", "w2");
		NeuralNetwork3 nn = DNA.CreateFromDNA(dna);
		
		TicTacToe ttt = TicTacToe.StartingBoard(Player.COMPUTER, Player.HUMAN);
		ttt.loadNeuralNetwork(0, nn);
		ttt.start();
	}
}
