package org.matrix.parallel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.jblas.DoubleMatrix;

import Jama.Matrix;

public class BenchJBLAS {
	
	public static DoubleMatrix readJamaMatrixFromFile(String filename) {
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
		return new DoubleMatrix(thisMatrix);
	}

	  public static void main(String[] args) {
		    DoubleMatrix A = readJamaMatrixFromFile(".\\src\\test\\resources\\10_10_A");
		    DoubleMatrix B = readJamaMatrixFromFile(".\\src\\test\\resources\\10_10_B");
			Long start = System.currentTimeMillis();
			DoubleMatrix result = A.mmul(B);
			Long end = System.currentTimeMillis();
			Long time = end - start;
			System.out.println("Time for JBLAS multiply 10x10 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\100_100_M1");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\100_100_M2");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 100x100 is: " + time);

			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\200_200_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\200_200_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 200x200 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\300_300_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\300_300_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 300x300 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\500_500_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\500_500_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 500x500 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\1000_1000_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\1000_1000_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 1000x1000 is: " + time);
		  }
}
