package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Matrix {

	private double[][] a;
	
	private int rows;
	private int columns;
	
	public Matrix(double[][] a) {
		this.a = a;
		this.rows = a.length;
		this.columns = a[0].length;
	}
	
	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		
		this.a = new double[rows][columns];
	}
	
	public static Matrix fromFile(String fileName, int rows, int columns) {
		Matrix m = new Matrix(rows, columns);
		
		try {
			Scanner scan = new Scanner(new File(fileName));
			int row = 0;
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				StringTokenizer st = new StringTokenizer(line, ",", false);
				
				int column = 0;
				while (st.hasMoreTokens()) {					
					String token = st.nextToken().trim();
					
					m.a[row][column] = Float.parseFloat(token);
					
					column ++;
				}
				
				row ++;
			}
			
			scan.close();
			return m;
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Matrix Identity(int rows, int columns) {
		double[][] a = new double[rows][columns];
		for (int r = 0; r < rows; r ++) {
			for (int c = 0; c < columns; c ++) {
				if (r == c) {
					a[r][c]	= 1.0;
				} else {
					a[r][c] = 0.0;
				}
			}
		}
		return new Matrix(a);
	}
	
	public static Matrix FromArray(double[] data) {
		double[][] array = {
				data
		};
		Matrix ret = new Matrix(array);
		return ret;
	}
	
	public Matrix transpose() {
		double[][] ret = new double[this.columns][this.rows];
		for (int r = 0; r < this.rows; r ++) {
			for (int c = 0; c < this.columns; c ++) {
				ret[c][r] = this.a[r][c];
			}
		}
		return new Matrix(ret);
	}
	
	public Matrix dot(Matrix other) {
		double[][] ret = new double[this.rows][other.columns];
		for (int r = 0; r < this.rows; r ++) {
			for (int c = 0; c < other.columns; c ++) {
				double sum = 0;
				for (int i = 0; i < this.columns; i ++) {
					sum += this.a[r][i] * other.a[i][c];
				}
				ret[r][c] = sum;
			}
		}
		return new Matrix(ret);
	}
	
	public Matrix mult(Matrix other) {
		double[][] ret = new double[this.rows][this.columns];
		for (int r = 0; r < this.rows; r ++) {
			for (int c = 0; c < this.columns; c ++) {
				ret[r][c] = this.a[r][c] * other.a[r][c];
			}
		}
		return new Matrix(ret);
	}
	
	public Matrix mult(double k) {
		double[][] ret = new double[this.rows][this.columns];
		for (int r = 0; r < this.rows; r ++) {
			for (int c = 0; c < this.columns; c ++) {
				ret[r][c] = a[r][c] * k;
			}
		}
		return new Matrix(ret);
	}
	
	public Matrix add(Matrix other) {
		double[][] ret = new double[this.rows][this.columns];
		for (int r = 0; r < this.rows; r ++) {
			for (int c = 0; c < this.columns; c ++) {
				ret[r][c] = this.a[r][c] + other.a[r][c];
			}
		}
		return new Matrix(ret);
	}
	
	public Matrix sub(Matrix other) {
		double[][] ret = new double[this.rows][this.columns];
		for (int r = 0; r < this.rows; r ++) {
			for (int c = 0; c < this.columns; c ++) {
				ret[r][c] = this.a[r][c] - other.a[r][c];
			}
		}
		return new Matrix(ret);
	}
	
	public double get(int r, int c) {
		return a[r][c];
	}
	
	public void set(int r, int c, double v) {
		a[r][c] = v;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns()	{
		return columns;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < rows; i ++) {
			for (int j = 0; j < columns; j ++) {
				ret += a[i][j] + (j < columns - 1 ? ", " : "");
			}
			ret += "\n";
		}
		ret += "\n";
		
		return ret;
	}
	
	@Override
	public Matrix clone() {
		Matrix m = new Matrix(rows, columns);
		for (int i = 0; i < rows; i ++) {
			for (int j = 0; j < columns; j ++) {
				m.set(i, j, this.get(i, j));
			}
		}
		return m;
	}
}
