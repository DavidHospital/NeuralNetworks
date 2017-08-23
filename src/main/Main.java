package main;

import network.neural.engine.Matrix;
import network.neural.engine.NeuralNetwork3;

public class Main {

	
	public static void main (String[] args) {
		double[][] x = {
				{ 0.0, 0.0 },
				{ 0.0, 1.0 },
				{ 1.0, 0.0 },
				{ 1.0, 1.0 }
		};
		Matrix X = new Matrix(x);
		double[][] y = {
				{ 0.0 },
				{ 1.0 },
				{ 1.0 },
				{ 0.0 }
		};
		Matrix Y = new Matrix(y);
		
		NeuralNetwork3 nn = new NeuralNetwork3(2, 3, 1);
		
		System.out.println(nn.forward(X));
		for (int i = 0; i < 100000; i ++) {
			System.out.println(i);
			nn.backPropagate(X, Y);
			System.out.println(nn.forward(X));
		}
		
	}
}
