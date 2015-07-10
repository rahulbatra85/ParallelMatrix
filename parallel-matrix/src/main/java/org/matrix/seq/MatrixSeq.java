package org.matrix.seq;

import org.matrix.common.*;

//This is concrete class for MatrixSequential Algorithms that extens abstact class Matrix
public class MatrixSeq extends Matrix {

	public MatrixSeq(int rows, int columns) {
		super(rows, columns);
	
		this.mOpAdd = new AddSeq();
		this.mOpDeterminant = new DeterminantSeq();
		this.mOpInverse = new InverseSeq();
		this.mOpLinearSolver = new LinearSolverSeq();
		this.mOpLUDecompose = new LUDecomposeSeq();
		this.mOpMult = new MultSeq();
	}
	
	public MatrixSeq(int rows, int columns, boolean zerod, int max){
		super(rows,columns,zerod,max);
	}

	/*public static int flipOdd(int i) {
		if((i % 2) == 0) return 1;
		else 
			return -1;
	}*/
	
	/*
	public void adjoint(Matrix a) {
		a = transpose(cofactor());
	}
	    
	public void scalarMultiply(Matrix result, double scalar) {	    
		for (int i=0; i<getNumRows(); i++) {
			for (int j=0; j<getNumColumns(); j++) {
	    		result.setElem(i, j, scalar*matrix[i][j]);
	    	}
	    }	    
	}
	
	public void transpose(Matrix t) {
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				t.setElem(i, j, matrix[j][i]);
			}
		}
	}
	
	public void createSubMatrix(Matrix s, int x, int y) {	   
	    int r = -1;
	    for (int i=0;i<getNumRows();i++) {
	        if (i==x)
	            continue;
	            r++;
	            int c = -1;
	        for (int j=0;j<getNumColumns();j++) {
	            if (j==y)
	                continue;
	            s.setElem(r, ++c, matrix[x][y]);
	        }
	    }
	}*/
	
	/*public Matrix cofactor() {
		Matrix subMat = new Matrix((getNumRows()-1),(getNumColumns()-1),true,0);
		Matrix cofactorResult = new Matrix(getNumRows(),getNumColumns(),true,0);
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				subMat = createSubMatrix(this,i,j);
				try {
				   cofactorResult.setElem(i, j, (flipOdd(i+j)*subMat.determinant()));
				} catch(Exception e) {
					System.out.println(e.toString());
				}
			}
		}
        return cofactorResult;
	}*/
	
}
