package main;

import game.ttt.TicTacToe;
import game.ttt.TicTacToe.Player;

public class Main {

	
	public static void main (String[] args) {
		TicTacToe ttt = TicTacToe.StartingBoard(Player.HUMAN, Player.HUMAN);
		ttt.start();
	}
}
