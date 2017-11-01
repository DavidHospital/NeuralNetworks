package main;

import game.ttt.TicTacToe;
import game.ttt.TicTacToe.Player;
import genetic.DNA;
import network.neural.engine.NeuralNetwork3;

public class PlayAgainstO {
	
	public static void main(String[] args) {
		DNA dna = DNA.createFromFiles("w1O", "w2O");
		NeuralNetwork3 nn = DNA.CreateFromDNA(dna);
		
		TicTacToe ttt = TicTacToe.StartingBoard(Player.HUMAN, Player.COMPUTER);
		ttt.loadNeuralNetwork(1, nn);
		ttt.start();
	}
}
