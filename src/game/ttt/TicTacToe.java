package game.ttt;

import main.Game;
import network.neural.engine.Matrix;
import network.neural.engine.NeuralNetwork3;


public class TicTacToe {

	public enum Player {
		HUMAN,
		COMPUTER
	}

	private Player[] players;
	private int[] board;
	private int turn;
	private int state;
	
	private NeuralNetwork3[] nn;
	
	public static TicTacToe StartingBoard(Player p1, Player p2) {
		int[] board = new int[9];
		for (int i = 0; i < board.length; i ++) {
			board[i] = -1;
		}
		return new TicTacToe(board, p1, p2);
	}
	
	public TicTacToe(int[] board, Player p1, Player p2) {
		this.board = board;
		this.players = new Player[2];
		this.players[0] = p1;
		this.players[1] = p2;
		
		this.nn = new NeuralNetwork3[2];
	}
	
	public void loadNeuralNetwork(int index, NeuralNetwork3 nn) {
		this.nn[index] = nn;
	}
	
	public void start() {
		gameLoop();
	}
	
	private void gameLoop() {
		turn = 0;
		state = -1;
		while (state == -1) {
			switch (players[turn]) {
			case HUMAN:
				humanTurn();
				break;
			case COMPUTER:
				computerTurn();
				break;
			}
			
			state = evaluateBoard();
			turn = turn == 0 ? 1 : 0;
		}
		end();
	}
	
	private void humanTurn() {
		System.out.println(this + "\n");
		int input;
		do {
			input = Main.r.nextInt();
		} while(board[input] != -1);
		board[input] = turn;
	}
	
	private void computerTurn() {
		double[] data = new double[19];
		for (int i = 0; i < board.length; i ++) {
			if (board[i] == -1) {
				data[i] = 0;
				data[i + 1] = 0;
			} else if (board[i] == 0) {
				data[i] = 0;
				data[i + 1] = 1;
			} else if (board[i] == 1) {
				data[i] = 1;
				data[i + 1] = 0;
			}
		}
		data[18] = turn;
		Matrix X = Matrix.FromArray(data);
		Matrix out = nn[turn].forward(X);
		
		int largestIndex;
		double largest;
		do {
			largestIndex = 0;
			largest = 0;
			for (int i = 0; i < out.getColumns(); i ++) {
				if (out.get(0, i) > largest) {
					largestIndex = i;
					largest = out.get(0, i);
				}
			}
			out.set(0, largestIndex, 0);
		} while (board[largestIndex] != -1);
		board[largestIndex] = turn;
	}
	
	private void end() {
		//System.out.println(this);
		//if (state == 2) {
		//	System.out.println("It's a draw!");
		//} else {
		//	System.out.println( (state == 0 ? "X" : "O") + " is the winner!");
		//}
	}
	
	public int getWinner() {
		return state;
	}
	
	/**
	 * 
	 * @return 0 for X victory, 1 for O Victory, -1 for no Victory, 2 for Draw
	 */
	private int evaluateBoard() {
		/*		X|X|X
		 * 		 | |			 
		 * 		 | | 
		 */
		
		if (board[0] == board[1] && board[1] == board[2]) {
			return board[0];
		}
		
		/*		 | | 
		 * 		X|X|X		 
		 * 		 | | 
		 */
		
		else if (board[3] == board[4] && board[4] == board[5]) {
			return board[3];
		}
		
		/*		 | | 
		 * 		 | |		 
		 * 		X|X|X
		 */
		
		else if (board[6] == board[7] && board[7] == board[8]) {
			return board[6];
		}
		
		/*		X| | 
		 * 		X| | 		 
		 * 		X| | 
		 */
		
		else if (board[0] == board[3] && board[3] == board[6]) {
			return board[0];
		}
		
		/*		 |X| 
		 * 		 |X|		 
		 * 		 |X| 
		 */
		
		else if (board[1] == board[4] && board[4] == board[7]) {
			return board[1];
		}
		
		/*		 | |X
		 * 		 | |X		 
		 * 		 | |X
		 */
		
		else if (board[2] == board[5] && board[5] == board[8]) {
			return board[2];
		}
		
		/*		X| | 
		 * 		 |X|		 
		 * 		 | |X 
		 */
		
		else if (board[0] == board[4] && board[4] == board[8]) {
			return board[0];
		}
		
		/*		 | |X
		 * 		 |X| 		 
		 * 		X| | 
		 */
		
		else if (board[2] == board[4] && board[4] == board[6]) {
			return board[2];
		}
		
		else {
			boolean fullBoard = true;
			for (int i = 0; i < board.length; i ++) {
				if (board[i] == -1) {
					fullBoard = false;
				}
			}
			if (fullBoard) {
				return 2;
			}
		}
		
		return -1;
	}
	
	public String toString() {
		String[] b = new String[9];
		for (int i = 0; i < 9; i ++) {
			switch(board[i]) {
			case -1:
				b[i] = " ";
				break;
			case 0:
				b[i] = "X";
				break;
			case 1:
				b[i] = "O";
				break;
			}
		}
		String ret =  b[0] + " | " + b[1] + " | " + b[2] + "\n"
					+ "--+---+--\n"
					+ b[3] + " | " + b[4] + " | " + b[5] + "\n"
					+ "--+---+--\n"
					+ b[6] + " | " + b[7] + " | " + b[8];
		return ret;
	}
	
}
