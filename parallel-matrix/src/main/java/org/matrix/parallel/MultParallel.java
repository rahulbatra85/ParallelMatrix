package org.matrix.parallel;

import org.matrix.common.IfaceMult;

import java.util.ArrayList;
import java.util.concurrent.*;

import org.matrix.common.Matrix;
import org.matrix.seq.MatrixSeq;

public class MultParallel implements IfaceMult,Callable<Matrix>{
	
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	int numThreads;
	Matrix A;
	Matrix B;
	
    public MultParallel(int numThreads) {
		this.numThreads = numThreads;
	}
    
    public MultParallel(Matrix A, Matrix B) {
    	this.A = A;
    	this.B = B;
    }

	
	public Matrix call() {
		
		if(A.getNumColumns() != B.getNumRows()){
			return null;
		}
		
		int numAR = A.getNumRows();
		int numBC = B.getNumColumns();
		int numAC = A.getNumColumns();
		MatrixParallel C = new MatrixParallel(numAR,numBC);
		String expected = "6.0 0.0 \n0.0 0.0 \n";
		//if(expected.equals(A.toString())) {
		//    System.out.println("Starting multiply on A: \n" + A.toString() + " and B:\n" + B.toString());
		//}
		for(int i=0; i<numAR; i++) {
			for(int j=0; j<numBC; j++) {
			    double value = 0;
			    for(int k=0; k < numAC; k++) {
			    	
				    value = value + (A.getElem(i, k) * B.getElem(k, j));
				    //if(expected.equals(A.toString())) {
				    //	System.out.println("Result of mult A:" + A.getElem(i, k) + " x B: " + B.getElem(k, j));
			    	//    System.out.println("Value: " + value);
				    //}
				}
				C.setElem(i, j, value);
			}
		}
		if(expected.equals(A.toString())) {
		    System.out.println("Finishing multiply on A: \n" + C.toString());
		}
		return C;
	}
	
	@Override
	public Matrix multiply(Matrix A, Matrix B) {
		/*
		if(A.getNumColumns() != B.getNumRows()){
			return null;
		}
		
		int numAR = A.getNumRows();
		int numBC = B.getNumColumns();
		int numAC = A.getNumColumns();
		int numBR = B.getNumRows();
		MatrixParallel C = new MatrixParallel(numAR,numBC);
		ArrayList<Future<Matrix>> threadVals = new ArrayList<Future<Matrix>>();
		//int b = (int) Math.ceil(a / 100);
		int divisions1= (int) Math.ceil((double)numAC/numThreads);
		int remainder1= numAC%numThreads;
		int divisions2= (int) Math.ceil((double)numAR/numThreads);
		int remainder2=numAR%numThreads;
		int divisions3= (int) Math.ceil((double)numBC/numThreads);
		int remainder3=numBC%numThreads;
		System.out.println("numThreads: " + numThreads + "num Rows A: " + numAR + "section div2" + divisions2 + " with remaind2: " + remainder2);
		System.out.println("numThreads: " + numThreads + "num Cols A: " + numAC + "section div1" + divisions1 + " with remaind1: " + remainder1);
		System.out.println("numThreads: " + numThreads + "num Rows B: " + numBR + "section div1" + divisions1 + " with remaind1: " + remainder1);
		System.out.println("numThreads: " + numThreads + "num Cols B: " + numBC + "section div3" + divisions3 + " with remaind3: " + remainder3);
		System.out.println("A\n" + A.toString() + "B\n" + B.toString());
		for(int i=0; i<(numThreads); i++) {
			for(int j=0; j<(numThreads); j++) {
				System.out.println("Sub Matrix i:" + i + " j:" + j + " rowsA: " + (i * divisions2) + " colsA: " + (i*divisions1) + " rowsB: " + (i*divisions1) + " colsB: " + (i*divisions3));
					Matrix newA = ((MatrixParallel)A).createSubMatrixRange((i*divisions2), A.getNumRows(),(j*divisions1), ((j*divisions1)+(divisions1-1)));
					Matrix newB = ((MatrixParallel)B).createSubMatrixRange((i*divisions1), ((i*divisions1)+(divisions1-1)),(j*divisions3), B.getNumColumns());
					System.out.println("new A: \n" + newA.toString() + "new B: \n" + newB.toString());
					MultParallel s = new MultParallel(newA,newB);
					Future<Matrix> f1 = threadPool.submit(s);
					threadVals.add(f1);
			}
		}
		int element=0;
		for(int i=0; i<(numThreads); i++) {
			for(int j=0; j<(numThreads); j++) {
		        try {
		        	System.out.println("Setting submatrix elements i: " + (i*divisions1) + " j: " + (j*divisions1));
		        	MatrixParallel temp = (MatrixParallel)(threadVals.get(element)).get();
		        	System.out.println("subresult:\n" + temp.toString());
		        	C.setSubMatrix((i*divisions1), (j*divisions1), temp);
		        	element++;
		        }
		        catch(Exception exc) {
		          System.err.println(exc);
		        }
			}
	    }
		System.out.println("Final: \n" + C.toString());
	    threadPool.shutdown();
		return C;
		*/
		return new MatrixParallel(0,0);
	}

}


