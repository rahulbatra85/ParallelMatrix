package org.matrix.common;

public class Matrix {
	double[][] matrix;
	
	static MatrixAddInteface add;

	public Matrix(int rows, int columns) {
		matrix = new double[rows][columns];
				//Default
		add = new MatrixAdd();
	}
	
	public static void setOPAdd(MatrixAddInteface add){
		Matrix.add = add;
	}
	
	public Matrix(int rows, int columns, boolean zerod, int max) {
		matrix = new double[rows][columns];
		for (int row=0; row<rows; row++) {
			for (int column=0; column < columns; column++) {
				if(zerod) {
					this.matrix[row][column] = 0;
				} else {
					this.matrix[row][column] = (row*column)% max;
				}
			}
		}		
	}
		
	public double getElem(int i, int j) {
		return matrix[i][j];
	}

	public boolean setElem(int i, int j, double value) {
		if(i < getNumRows() && j < getNumColumns()) {
			matrix[i][j] = value;
			return true;
		} else {
			return false;
		}
	}
		
	public double[] getRow(int row) {
		if(row < getNumRows()) 
			return matrix[row];
		else
			return new double[0]; // need to fix this
	}

	public double[] getColumn(int col) {
		return matrix[col];
	}
		
	public int getNumRows() {
		return matrix.length;
	}
		
	public int getNumColumns() {
		return matrix[0].length;
	}
		
	public boolean setRow(double[] inputRow, int rowID) {
		if(inputRow.length == matrix[0].length && rowID < matrix.length) {
			matrix[rowID] = inputRow;
			return true;
		} else {
			return false;
		}
	}
	
	//To do
	public boolean equals(Matrix compareM){
		return true;
	}
		
	public static boolean add(Matrix A, Matrix B, Matrix C) {
		if(Matrix.add != null){
			boolean ret = add.add(A, B, C);
			return ret;
		} else{
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Test");
	}
}
