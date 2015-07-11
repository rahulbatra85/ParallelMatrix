package org.matrix.common;

//This is just an abstact class
//See concrete class like MatrixSeq
public abstract class Matrix {
	protected double[][] matrix;
	
	protected IfaceAdd mOpAdd;
	protected IfaceMult mOpMult;
	protected IfaceDeterminant mOpDeterminant;
	protected IfaceInverse mOpInverse;
	protected IfaceLinearSolver mOpLinearSolver;
	protected IfaceLUDecompose mOpLUDecompose;
	
	//
	//Constructor
	//
	public Matrix(int rows, int columns) {
		matrix = new double[rows][columns];
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
	
	//Java overrides
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				sb.append(matrix[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Matrix))return false;
	    Matrix otherMatrix = (Matrix)other;
	    
	    //todo
	    return true;
	}

	
	//
	//Operations
	//
	//Adds this matrix with B and returns the results
	//Returns If matrix B dimensions don't match, then returns null
	public Matrix add(Matrix B){
		return this.mOpAdd.add(this, B);
	}
	
	//Multiplies this matrix with B 
	//Returns null if number of colums in B don't match number of rows in this matrix
	//Otherwise, returns multiplied matrix
	public Matrix multiply(Matrix B){		
		return this.mOpMult.multiply(this, B);
	}
	
	//Finds the inverse of this matrix 
	//Returns null if matrix is not square or invertible 
	//Otherwise, returns inverse
	public Matrix inverse(){
		return this.mOpInverse.inverse(this);
	}
	
	//Finds the determinant of this matrix 
	//Returns throws exception if matrix is not square
	//Otherwise, returns the determinant
	public double determinant() throws Exception {	
		if (!this.isSquare()) {
			System.out.println("The not square matrix is: \n" + this.toString());
			throw new Exception("Need square matrix."); //todo define matrix exception
		} else{
			return this.mOpDeterminant.determinant(this);
		}
	}
	
	//Solves the linear system consisting of this matrix and vector B
	//Returns null if no solution is found
	//Otherwise, returns vector solution x in equation(Ax=B) 
	public Matrix linearSolve(Matrix B){
		return this.mOpLinearSolver.linearSolver(this,B);
	}
	
	//Decomposes the matrix into Lower-Triangular and Upper Triangular
	//Returns null if no decomposition if found
	//Otherwise, returns a pair of matrix objects L and U
	public Matrix[] LUDecompose(){
		return this.mOpLUDecompose.LUDecompose(this);
	}
	
	
	//
	//Setters
	//
	public void setOpAdd(IfaceAdd op){
		this.mOpAdd = op;
	}
	
	public void setOpMult(IfaceMult op){
		this.mOpMult = op;
	}
	
	public void setOpDeterminant(IfaceDeterminant op){
		this.mOpDeterminant = op;
	}
	
	public void setOpInverse(IfaceInverse op){
		this.mOpInverse = op;
	}
	
	public void setOpLinearSolver(IfaceLinearSolver op){
		this.mOpLinearSolver = op;
	}
	
	public void setOpLU(IfaceLUDecompose op){
		this.mOpLUDecompose = op;
	}
	
	public boolean setRow(double[] inputRow, int rowID) {
		if(inputRow.length == matrix[0].length && rowID < matrix.length) {
		    matrix[rowID] = inputRow;
		    return true;
		} else {
			return false;
		}
	}	
	public boolean setElem(int i, int j, double value) {
		if(i < getNumRows() && j < getNumColumns()) {
			matrix[i][j] = value;
			return true;
		} else {
			return false;
		}
	}

	//
	//Getters
	//
	public int getNumRows() {
		return matrix.length;
	}
	
	public int getNumColumns() {
		return matrix[0].length;
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
	
	public double getElem(int i, int j) {
		return matrix[i][j];
	}
		
	public boolean isSquare() {
		return (getNumRows() == getNumColumns());
	}
	
}
