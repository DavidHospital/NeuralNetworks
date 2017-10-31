package genetic;

import java.util.Random;

import network.neural.engine.NeuralNetwork3;
import tools.Matrix;

public class DNA {
	public Matrix w1;
	public Matrix w2;
	
	DNA(){
	}
	
	DNA(int inputSize, int hiddenSize, int outputSize) {
		w1 = new Matrix(inputSize, hiddenSize);
		w2 = new Matrix(hiddenSize, outputSize);
	}
	
	public static DNA createFromFiles(String w1File, String w2File) {
		DNA dna = new DNA();
		dna.w1 = Matrix.fromFile(w1File, 19, 9);
		dna.w2 = Matrix.fromFile(w2File, 9, 9);
		return dna;
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

	@Override
	public String toString() {
		String str = "";
		str += w1.toString();
		str += w2.toString();
		return str;
	}
}
