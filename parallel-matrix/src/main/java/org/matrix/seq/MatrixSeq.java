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

	public static int flipOdd(int i) {
		if((i % 2) == 0) return 1;
		else 
			return -1;
	}
	
	
/*	public void adjoint(Matrix a) {
		a = transpose(cofactor());
	}*/
	    
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
	
	public Matrix createSubMatrix(int excludeRow, int excludeCol) {	   
	    int r = -1;
	    Matrix result = new MatrixSeq((getNumRows()-1),(getNumColumns()-1),true,0);
	    for (int i=0;i<getNumRows();i++) {
	        if (i==excludeRow)
	            continue;
	            r++;
	            int c = -1;
	        for (int j=0;j<getNumColumns();j++) {
	            if (j==excludeCol)
	                continue;
	            result.setElem(r, ++c, matrix[i][j]);
	        }
	    }
	    return result;
	}
	
	/*public Matrix cofactor() {
		MatrixSeq subMat = new MatrixSeq((getNumRows()-1),(getNumColumns()-1),true,0);
		MatrixSeq cofactorResult = new MatrixSeq(getNumRows(),getNumColumns(),true,0);
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				subMat = (MatrixSeq)createSubMatrix(i,j);
				try {
				   cofactorResult.setElem(i, j, (flipOdd(i+j)*subMat.determinant()));
				} catch(Exception e) {
					System.out.println(e.toString());
				}
			}
		}
        return cofactorResult;
	}*/
	
	public static void main(String[] args) {
		System.out.println("test");
		MatrixSeq testSub = new MatrixSeq(3,3,true,0);
		double[] arow = {1,2,3};
		double[] brow = {0,4,5};
		double[] crow = {1,0,6};
		testSub.setRow(arow, 0);
		testSub.setRow(brow, 1);
		testSub.setRow(crow, 2);
		System.out.println(testSub.toString());
		//System.out.println((testSub.cofactor()).toString());
		try {
		   System.out.println("Determinant: " + testSub.determinant());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
