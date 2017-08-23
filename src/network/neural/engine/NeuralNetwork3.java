package network.neural.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class NeuralNetwork3 {

	private Matrix w1;
	private Matrix w2;
	
	public NeuralNetwork3(int inputSize, int hiddenSize, int outputSize) {
		w1 = new Matrix(new double[inputSize][hiddenSize]);
		w2 = new Matrix(new double[hiddenSize][outputSize]);
		
		for (int i = 0; i < inputSize; i ++) {
			for (int j = 0; j < hiddenSize; j ++) {
				w1.set(i, j, Math.random());
			}
		}
		
		for (int i = 0; i < hiddenSize; i ++) {
			for (int j = 0; j < outputSize; j ++) {
				w2.set(i, j, Math.random());
			}
		}
	}
	
	public Matrix forward(Matrix X) {
		return sigmoid(sigmoid(X.dot(w1)).dot(w2));
	}
	
	public void backPropagate(Matrix X, Matrix Y) {
		Matrix Z2 = X.dot(w1);
		Matrix A2 = sigmoid(Z2);
		Matrix Z3 = A2.dot(w2);
		Matrix yHat = sigmoid(Z3);
		
		Matrix d3 = Y.sub(yHat).mult(-1).mult(sigmoidPrime(Z3));
		Matrix dJdW2 = A2.transpose().dot(d3);
		
		Matrix d2 = d3.dot(w2.transpose()).mult(sigmoidPrime(X.dot(w1)));
		Matrix dJdW1 = X.transpose().dot(d2);
		
		w1 = w1.sub(dJdW1);
		w2 = w2.sub(dJdW2);
	}
	
	public double sigmoid(double k) {
		return 1.0 / (1.0 + Math.exp(- k));
	}
	
	public double sigmoidPrime(double k) {
		return Math.exp(- k) / (1.0 + Math.exp(- k) * (1.0 + Math.exp(- k)));
	}
	
	public Matrix sigmoid(Matrix k) {
		double[][] ret = new double[k.getRows()][k.getColumns()];
		for (int r = 0; r < k.getRows(); r ++) {
			for (int c = 0; c < k.getColumns(); c ++) {
				ret[r][c] = sigmoid(k.get(r, c));
			}
		}
		return new Matrix(ret);
	}
	
	public Matrix sigmoidPrime(Matrix k) {
		double[][] ret = new double[k.getRows()][k.getColumns()];
		for (int r = 0; r < k.getRows(); r ++) {
			for (int c = 0; c < k.getColumns(); c ++) {
				ret[r][c] = sigmoidPrime(k.get(r, c));
			}
		}
		return new Matrix(ret);
	}
	
	public Matrix[] getMatricesFromCSV(File csv, int inputSize, int outputSize) {
		ArrayList<ArrayList<Double>> rows = new ArrayList<>();
		
		try {
			Scanner r = new Scanner(csv);
			while (r.hasNextLine()) {
				ArrayList<Double> column = new ArrayList<>();
				String line = r.nextLine();
				StringTokenizer st = new StringTokenizer(line, ",", false);
				while (st.hasMoreTokens()) {
					column.add(Double.parseDouble(st.nextToken().trim()));
				}
				rows.add(column);
			}
			r.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		double[][] inputData = new double[rows.size()][inputSize];
		for (int i = 0; i < inputData.length; i ++) {
			for (int j = 0; j < inputSize; j ++) {
				inputData[i][j] = rows.get(i).get(j);
			}
		}
		Matrix X = new Matrix(inputData);
		
		double[][] outputData = new double[rows.size()][outputSize];
		for (int i = 0; i < outputData.length; i ++) {
			for (int j = inputSize; j < outputSize + inputSize; j ++) {
				outputData[i][j - inputSize] = rows.get(i).get(j);
			}
		}
		Matrix Y = new Matrix(outputData);
		
		Matrix[] ret = {
				X, Y
		};
		return ret;
	}
}
