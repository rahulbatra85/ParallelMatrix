package org.matrix.parallel;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.matrix.common.IfaceMult;
import org.matrix.common.Matrix;

public class MultParallel2 implements IfaceMult,Callable<Matrix> {
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	int numThreads;
	Matrix A;
	Matrix B;
	
    public MultParallel2(int numThreads) {
		this.numThreads = numThreads;
	}
    
    public MultParallel2(Matrix A, Matrix B) {
    	this.A = A;
    	this.B = B;
    }

	
	public Matrix call() {
		

		
		int numAR = A.matrix.length;
		int numBC = B.matrix[0].length;
		int numAC = A.matrix[0].length;
		if(numAC != B.matrix.length){
			return null;
		}
		MatrixParallel C = new MatrixParallel(numAR,numBC);
		for(int i=0; i<numAR; i++) {
			for(int j=0; j<numBC; j++) {
			    double value = 0;
			    for(int k=0; k < numAC; k++) {
				    value = value + (A.matrix[i][k] * B.matrix[k][j]);
				}
				C.matrix[i][j] = value;
			}
		}
		return C;
	}
	
	@Override
	public Matrix multiply(Matrix A, Matrix B) {
	
		if(A.getNumColumns() != B.getNumRows()){
			return null;
		}
		//System.out.println("A:\n" + A.toString() + "B:\n" + B.toString());
		
		int numAR = A.getNumRows();
		int numBC = B.getNumColumns();
		int numAC = A.getNumColumns();
		int numBR = B.getNumRows();
		//System.out.println("The final matrix is: " + numAR + " rows and: " + numBC + " columns");
		int divisions = (int)Math.sqrt(numThreads);
//		System.out.println("Divisions: " + divisions + " Num threads is: " + numThreads);
		int partAcolsBrows = (int)Math.ceil((double)numAC/divisions);
//		System.out.println("NumAR: " + numAR);
		int partArows = (int)Math.ceil((double)numAR/divisions);
		int partBcols = (int)Math.ceil((double)numBC/divisions);
//		System.out.println("PartARows: " + partArows + " PartAcolsBrows: " + partAcolsBrows + " PartBCols: " + partBcols);
		int numARupdated = numAR;
		int numBCupdated = numBC;
		int numACBRupdated = numAC;
		if(numAR%partArows != 0) {
			numARupdated = divisions*partArows;
			numACBRupdated = divisions*partAcolsBrows;
			numBCupdated = divisions*partBcols;
		}
		MatrixParallel C = new MatrixParallel(Math.max(numARupdated,partAcolsBrows),Math.max(numBCupdated,numACBRupdated));
		Matrix[][] subMatA = new MatrixParallel[divisions][divisions];
		Matrix[][] subMatB = new MatrixParallel[divisions][divisions];
		for(int i=0; i<divisions; i++) {
			for(int j=0; j<divisions; j++) { 
				subMatA[i][j] = (MatrixParallel)((MatrixParallel)A).createSubMatrixRange((i*partArows), ((i*partArows)+(partArows-1)), (j*partAcolsBrows), ((j*partAcolsBrows)+(partAcolsBrows-1)));
				subMatB[i][j] = ((MatrixParallel)B).createSubMatrixRange((i*partAcolsBrows), ((i*partAcolsBrows)+(partAcolsBrows-1)), (j*partBcols),((j*partBcols)+(partBcols-1)));
			}
		}
		ArrayList<Future<Matrix>> threadVals = new ArrayList<Future<Matrix>>();
		int numMultiplies = 0;
		for(int i=0; i<divisions; i++) {
			for(int j=0; j<divisions; j++) { 
				for(int k=0; k<divisions; k++) {
//		            System.out.println("Multiply A: \n" + subMatA[i][k].toString());
//		            System.out.println("Multiply B: \n" + subMatB[k][j].toString());
					MultParallel2 s = new MultParallel2(subMatA[i][k],subMatB[k][j]);
					Future<Matrix> f1 = threadPool.submit(s);
					threadVals.add(f1);
					numMultiplies++;
				}
			}
		}
		int element=0;
		int divrows = 0;
		int divcols = 0;
		for(int i=0; i<divisions; i++) {
			for(int j=0; j<divisions; j++) {
		        try {
		        	//System.out.println("Setting submatrix elements i: " + (i*divisions1) + " j: " + (j*divisions1));
		        	if(element<threadVals.size()) {
		        	    subMatA[i][j] = (MatrixParallel)(threadVals.get(element)).get();
		        	    element++;
		        	    subMatB[i][j] = (MatrixParallel)(threadVals.get(element)).get();
		        	    //System.out.println("subresultA:\n" + subMatA[i][j].toString());
		                //System.out.println("subresultB:\n" + subMatB[i][j].toString());
		        		int row = 0;
		                for(int k=(partArows*i);k<((partArows*i)+partArows);k++) {
			        		int col = 0;
		                	for(int l=(partBcols*j);l<((partBcols*j)+partBcols);l++) {
				                //System.out.println("row: " + row + "\ncol: " + col + "\n\nk: " + k + "\nl: " + l + "\ni: " + i + "\nj: " + j + "\n\n\n");
		                    	//System.out.println("NumAR: " + numAR + " k: " + k + " numBC: " + numBC + " l: " + l);
			                    //C.addToElem(k,l, subMatA[i][j].getElem(row, col) + subMatB[i][j].getElem(row, col));
		                		C.matrix[k][l] = C.matrix[k][l] + subMatA[i][j].matrix[row][col] + subMatB[i][j].matrix[row][col];
				                col++;
				                if(col>(partBcols-1)) {
				                	row++;
				                	col=0;
				                }
		                	}
		                }
		        	    element++;
		        	}
		        	divcols++;
		        }
		        catch(Exception exc) {
		          System.err.println(exc);
		        }
			}
			divrows++;
	    }
        //System.out.println("Final\n" + (C.createSubMatrixRange(0,numAR-1,0,numBC-1)).toString());
        //System.out.println("Rows final: " + (numAR-1) + " Columns final: " + (numBC-1));
		//return C.createSubMatrixRange(0,numAR-1,0,numBC-1);
	    int row = -1;
	    //System.out.println("This matrix: \n" + this.toString());
	    //public Matrix createSubMatrixRange(int includeStartRow,int includeEndRow, int includeStartCol, int includeEndCol) {	
	    Matrix result = new MatrixParallel(numAR,numBC);
	    for (int i=0;i<C.matrix.length;i++) {
	        if (i<0 || i>numAR) {
	        	//System.out.println("Skipping row: " + i);
	            continue;
	        }
	            row++;
	            int col = -1;
	        for (int j=0;j<C.matrix[0].length;j++) {
	            if (j<0 || j>numBC)	{
	            	//System.out.println("Skipping row: " + i + " col: " + j);
	                continue;
	            }
	            result.setElem(row,++col, C.matrix[i][j]);
	        }
	    }
	    //System.out.println("Result:\n" + result.toString());
	    return result;
	}

}
