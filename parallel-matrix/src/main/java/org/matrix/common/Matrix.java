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
		
	public boolean setRow(double[] inputRow, int rowID) {
		if(inputRow.length == matrix[0].length && rowID < matrix.length) {
		    matrix[rowID] = inputRow;
		    return true;
		} else {
			return false;
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
	
	//To do
	public boolean equals(Matrix compareM){
		return true;
	}
	
    public static Matrix adjoint(Matrix A) {
    	return (A.cofactor()).transpose();
    }
    
    public static Matrix scalarMultiply(Matrix A, double scalar) {
    	Matrix result = new Matrix(A.getNumRows(),A.getNumColumns(),true,0);
    	for (int i=0; i<A.getNumRows(); i++) {
    		for (int j=0; j<A.getNumColumns(); j++) {
    			result.setElem(i, j, (scalar*A.getElem(i, j)));
    		}
    	}
    	return result;
    }
	
	public static boolean multiply(Matrix matrixA, Matrix matrixB, Matrix matrixResult) {
		if((matrixA.getNumColumns() != matrixB.getNumRows()) ||
				(matrixResult.getNumRows() != matrixA.getNumRows()) ||
				(matrixResult.getNumColumns() != matrixB.getNumColumns()))
			return false;
		for(int i=0; i<matrixA.getNumRows(); i++) {
			for(int j=0; j<matrixB.getNumColumns(); j++) {
			    double value = 0;
			    for(int inner=0; inner < matrixA.getNumColumns();inner++) {
				    value = value + (matrixA.getElem(i, inner) * matrixB.getElem(inner, j));
				}
				matrixResult.setElem(i, j, value);
			}
		}
		return true;
	}
	   //To do - this is copied from an example, we want to do our own
	   // but I needed a determinant function
	   //http://www.codeproject.com/Articles/405128/Matrix-operations-in-Java
	public static double determinant(Matrix A) throws Exception {
		//public static double determinant(Matrix matrix) throws NoSquareException {
        if (!A.isSquare()) {
        	throw new Exception("Need square matrix."); //todo define matrix exception
        }
		if (A.getNumRows() == 1) {
			return A.getElem(0,0);
		}
		if (A.getNumRows()==2) {
		    return (A.getElem(0, 0) * A.getElem(1, 1)) - ( A.getElem(0, 1) * A.getElem(1, 0));
		}
		double sum = 0.0;
		for (int i=0; i<A.getNumColumns(); i++) {
		    sum += flipOdd(i) * A.getElem(0, i) * determinant(Matrix.createSubMatrix(A, 0, i));
		}
		return sum;
	}
	
	public static Matrix createSubMatrix(Matrix a, int x, int y) {
	    Matrix mat = new Matrix(a.getNumRows()-1, a.getNumColumns()-1,true,0);
	    int r = -1;
	    for (int i=0;i<a.getNumRows();i++) {
	        if (i==x)
	            continue;
	            r++;
	            int c = -1;
	        for (int j=0;j<a.getNumColumns();j++) {
	            if (j==y)
	                continue;
	            mat.setElem(r, ++c, a.getElem(i, j));
	        }
	    }
	    return mat;
	}
	
	public Matrix cofactor() {
		Matrix subMat = new Matrix((getNumRows()-1),(getNumColumns()-1),true,0);
		Matrix cofactorResult = new Matrix(getNumRows(),getNumColumns(),true,0);
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				subMat = createSubMatrix(this,i,j);
				try {
				   cofactorResult.setElem(i, j, (flipOdd(i+j)*determinant(subMat)));
				} catch(Exception e) {
					System.out.println(e.toString());
				}
			}
		}
        return cofactorResult;
	}

	public Matrix invert() {
		Matrix inverse = new Matrix(getNumRows(), getNumColumns(), true, 0);
		double det = 0;
		try {
		    det = determinant(this);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		det = 1/det;
		Matrix adjointR = new Matrix(getNumRows(), getNumColumns(), true, 0);
		adjointR = adjoint(this);
		inverse = scalarMultiply(adjointR,det);
		return inverse;
	}
	
	public Matrix transpose() {
		Matrix trans = new Matrix(getNumColumns(),getNumRows(),true,0);
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				trans.setElem(i, j, this.getElem(j, i));
			}
		}
		return trans;
	}
	
	public static boolean solveLinearSystem(Matrix A, Matrix X, Matrix B) {
		// AX=B, so A^-1B = X...Take the inverse of A and solve for X
		Matrix invA = A.invert();
		if(multiply(invA,B,X)) {
			System.out.println("The matrix result is: " + X.toString());
		} else {
			System.out.println("Problem solving linear equation");
		}
		return true;
	}
	
	public boolean isSquare() {
		return (getNumRows() == getNumColumns());
	}
		
	public static boolean add(Matrix A, Matrix B, Matrix C) {
		if(Matrix.add != null){
			boolean ret = add.add(A, B, C);
			return ret;
		} else{
			return false;
		}
	}
	
	public static int flipOdd(int i) {
		if((i % 2) == 0) return 1;
		else 
			return -1;
	}
	
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
	
	public static void main(String[] args) {
		double[] arow = {1,1,1};
		double[] brow = {0,2,5};
		double[] crow = {2,5,-1};
		Matrix linearA = new Matrix(3,3,true,0);
		linearA.setRow(arow, 0);	
		linearA.setRow(brow, 1);
		linearA.setRow(crow, 2);
		Matrix linearB = new Matrix(3,1,true,0);
		linearB.setElem(0, 0, 6);
		linearB.setElem(1, 0, -4);
		linearB.setElem(2, 0, 27);
		Matrix linResult = new Matrix(3,1,true,0);
		solveLinearSystem(linearA,linResult,linearB);
		System.out.println("The linear result is:\n" + linResult.toString());
	}
}
