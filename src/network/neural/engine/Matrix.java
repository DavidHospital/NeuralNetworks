package network.neural.engine;

public class Matrix {

	private double[][] a;
	
	private int rows;
	private int columns;
	
	public Matrix(double[][] a) {
		this.a = a;
		this.rows = a.length;
		this.columns = a[0].length;
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
			ret += "[";
			for (int j = 0; j < columns; j ++) {
				ret += Math.round(a[i][j] * 100) / 100.0 + (j < columns - 1 ? ", " : "");
			}
			ret += "]\n";
		}
		
		return ret;
	}
}
