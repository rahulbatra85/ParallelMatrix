package org.matrix.parallel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import cern.colt.matrix.tdouble.DoubleMatrix2D;
import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import Jama.Matrix;

public class BenchJama {
	
	public static Matrix readJamaMatrixFromFile(String filename) {
		double[][] thisMatrix = null;
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
		    String line=null;
		    int rows=0;
		    int columns=0;
		    StringTokenizer st = null;
    		try {
			    if((line = br.readLine()) != null) {
			    	st = new StringTokenizer(line, " ");	
			    	rows = Integer.parseInt(st.nextToken());
			    	columns = Integer.parseInt(st.nextToken());
			    }
				thisMatrix = new double[rows][columns];
			    int i=0;
			    while((line = br.readLine()) != null) {
			    	st = new StringTokenizer(line, " ");
			    	int j=0;
			    	while(st.hasMoreTokens() && j < columns) {
			    			thisMatrix[i][j] = Double.parseDouble(st.nextToken().trim());
			    			j++;
			    	}
			    	i++;
			    }
			    br.close();
	    	} catch ( Exception e) {
	    			e.printStackTrace();
	    			br.close();
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return new Matrix(thisMatrix);
	}
	
	public static void main(String[] args) {
		
		//C:\Users\Amy\git\ParallelMatrix\parallel-matrix
		Matrix matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\10_10_A");
		Matrix matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\10_10_B");
		Long start = System.currentTimeMillis();
		Matrix result = matrix.times(matrix2);
		Long end = System.currentTimeMillis();
		Long time = end - start;
		System.out.println("Time for Jama multiply 10x10 is: " + time);

		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\100_100_M1");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\100_100_M2");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 100x100 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\200_200_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\200_200_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 200x200 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\300_300_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\300_300_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 300x300 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\500_500_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\500_500_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 500x500 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\1000_1000_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\1000_1000_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 1000x1000 is: " + time);
	}
	
	

}
