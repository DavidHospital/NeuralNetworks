package game.ttt;

import java.util.Scanner;


public class TicTacToe {

	public enum Player {
		HUMAN,
		COMPUTER
	}
	

	private Scanner r;
	
	private int[] board;
	private int turn;
	private int state;
	private Player[] players;
	
	public static TicTacToe StartingBoard(Player p1, Player p2) {
		int[] board = new int[9];
		for (int i = 0; i < board.length; i ++) {
			board[i] = -1;
		}
		return new TicTacToe(board, p1, p2);
	}
	
	public TicTacToe(int[] board, Player p1, Player p2) {
		this.board = board;
		players = new Player[2];
		players[0] = p1;
		players[1] = p2;
		
		r = new Scanner(System.in);
	}
	
	public void start() {
		gameLoop();
	}
	
	private void gameLoop() {
		turn = 1;
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
			input = r.nextInt();
		} while(board[input] != -1);
		board[input] = turn;
	}
	
	private void computerTurn() {
		
	}
	
	private void end() {
		System.out.println(this);
		System.out.println( (state == 0 ? "O" : "X") + " is the winner!");
		r.close();
	}
	
	
	/**
	 * 
	 * @return 1 for X victory, 0 for O Victory, -1 for no Victory, 2 for Draw
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
				b[i] = "O";
				break;
			case 1:
				b[i] = "X";
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
