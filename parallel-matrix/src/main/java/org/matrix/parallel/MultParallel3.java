package org.matrix.parallel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import org.matrix.common.Matrix;
import org.matrix.common.IfaceMult;


public class MultParallel3 extends Thread implements IfaceMult {
	public static ExecutorService threadPool = Executors.newFixedThreadPool(8);
	public ReentrantLock resultLock;
	Double[][][] cA;
	Double[][][] cB;
	int m;
	int n;
	int o;
	Matrix endResult;
	
	public MultParallel3() {
		super();
		
	}
	
	public MultParallel3(Double[][][] cA,Double[][][]cB,int k, int i, int j,ReentrantLock lock,Matrix result) {
		super();
		this.cA = cA;
		this.cB = cB;
		this.m = i;
		this.n = j;
		this.o = k;
		this.resultLock = lock;
		this.endResult = result;
		
	}
	
	public void run() {
		Double thisValue = 0.0;
		for(int p=0; p<o; p++) {
			thisValue  = thisValue + cA[m][n][p] * cB[m][n][p];
		}
		resultLock.lock();
		endResult.setElem(m, n, thisValue);
		resultLock.unlock();
		
	}


	@Override
	public Matrix multiply(Matrix A, Matrix B) {
		if(A.matrix.length != B.matrix[0].length && A.matrix[0].length != B.matrix.length 
				&& A.matrix.length != A.matrix[0].length) 
			return null;
		int length = A.matrix.length;
		resultLock = new ReentrantLock();
		endResult = new MatrixParallel(length,length,true, 1);
		Double[][][] cubeA = new Double[length][length][length];
		Double[][][] cubeB = new Double[length][length][length];
		ArrayList<Thread> threadlist = new ArrayList<Thread>();
		for (int i=0; i<length; i++) {
			for(int j=0; j<length; j++) {
				for(int k=0; k<length; k++) {
					cubeA[i][j][k] = A.matrix[i][k];
					cubeB[i][j][k] = B.matrix[k][j];
				}
				Thread s = new MultParallel3(cubeA,cubeB,length,i,j,resultLock,endResult);
				threadPool.submit(s);
				threadlist.add(s);
			}
		}
		try {
			for(int i=0; i<threadlist.size(); i++) {
				threadlist.get(i).join();
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		return endResult;
	}
}
