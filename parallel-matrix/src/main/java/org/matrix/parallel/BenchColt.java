package org.matrix.parallel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.tdouble.DoubleMatrix2D;

public class BenchColt {
	
	public static DoubleMatrix2D readColtMatrixFromFile(String filename) {
		DoubleMatrix2D thisMatrix=null;
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
				thisMatrix = new DenseDoubleMatrix2D(rows,columns);
			    int i=0;
			    while((line = br.readLine()) != null) {
			    	st = new StringTokenizer(line, " ");
			    	int j=0;
			    	while(st.hasMoreTokens() && j < columns) {
			    			thisMatrix.set(i,j,(Double.parseDouble(st.nextToken().trim())));
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
		return thisMatrix;
	}
	
	public static void main(String[] args) {
		
	
		DoubleMatrix2D matrix = readColtMatrixFromFile(".\\src\\test\\resources\\10_10_A");
		DoubleMatrix2D matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\10_10_B");
		DenseDoubleAlgebra algebra = new DenseDoubleAlgebra();
		Long start = System.currentTimeMillis();
		DoubleMatrix2D result = algebra.mult(matrix,matrix2);
		Long end = System.currentTimeMillis();
		Long time = end - start;
		System.out.println("Time for colt multiply 10x10 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\100_100_M1");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\100_100_M2");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 100x100 is: " + time);

		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\200_200_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\200_200_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 200x200 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\300_300_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\300_300_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 300x300 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\500_500_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\500_500_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 500x500 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\1000_1000_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\1000_1000_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 1000x1000 is: " + time);
	}
}
