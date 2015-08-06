package org.matrix.seq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.StringTokenizer;

public class StrassenMultiply {
	
	public StrassenMultiply(){

	}

	public static String MatrixToString(int[][] matrix){
		String x = "";
		for (int i=0; i<matrix[0].length; i++){
			for (int j=0; j<matrix[i].length; j++){
				x = x + matrix[j][i] + "  ";
			}
			x = x + "\n";
		}
		return x;
	}

	public static int[][] GenerateQuadrant(int[][] matrix, int subSize, int quadrant){
		int x;
		int y;
		int[][] quad = new int[subSize][subSize];
		if (quadrant==1){
			x = 0;
			y = 0;
		}
		else if (quadrant==2){
			x = subSize;
			y = 0;
		}
		else if (quadrant==3){
			x = 0;
			y = subSize;
		}
		else {
			x = subSize;
			y = subSize;
		}

		for (int i=0+x; i<subSize+x; i++){
			for (int j=0+y; j<subSize+y; j++){
				quad[i-x][j-y] = matrix[i][j];
			}
		}
		return quad;
	}

	public static int[][] CombineQuadrants(int size, int[][] quad1, int[][] quad2, int[][] quad3, int[][] quad4){
		int[][] combinedMatrix = new int[size][size];
		for (int i=0; i<size/2; i++){
			for (int j=0; j<size/2; j++){
				combinedMatrix[i][j] = quad1[i][j];
			}
		}
		for (int i=size/2; i<size; i++){
			for (int j=0; j<size/2; j++){
				combinedMatrix[i][j] = quad2[i-size/2][j];
			}
		}
		for (int i=0; i<size/2; i++){
			for (int j=size/2; j<size; j++){
				combinedMatrix[i][j] = quad3[i][j-size/2];
			}
		}
		for (int i=size/2; i<size; i++){
			for (int j=size/2; j<size; j++){
				combinedMatrix[i][j] = quad4[i-size/2][j-size/2];
			}
		}
		return combinedMatrix;
	}

	public static int[][] MatrixMult(int[][] matrixA, int[][] matrixB){
		int rowA = matrixA[0].length;
		int colA = matrixA.length;
		int rowB = matrixB[0].length;
		int colB = matrixB.length;
		int[][] matrixC = new int[matrixA[0].length][matrixA[0].length];

		for (int rA=0; rA<rowA; rA++){
    		for (int cA=0; cA<colA; cA++){
      			for (int cB=0; cB<colB; cB++){
        			matrixC[rA][cA] = matrixC[rA][cA] + matrixA[cB][cA] * matrixB[rA][cB];
        			int q = matrixC[rA][cA];
      			}
    		}
  		}
  		return matrixC;
	}

	public static int[][] MatrixAdd(int[][] matrixA, int[][] matrixB){
		int[][] matrixC = new int[matrixA.length][matrixA[0].length];
		for (int i=0; i<matrixA.length; i++){
			for (int j=0; j<matrixA[0].length; j++){
				matrixC[i][j] = matrixA[i][j] + matrixB[i][j];
			}
		}
		return matrixC;
	}

	public static int[][] MatrixSubtract(int[][] matrixA, int[][] matrixB){
		int[][] matrixC = new int[matrixA.length][matrixA[0].length];
		for (int i=0; i<matrixA.length; i++){
			for (int j=0; j<matrixA[0].length; j++){
				matrixC[i][j] = matrixA[i][j] - matrixB[i][j];
			}
		}
		return matrixC;
	}

	public static int[][] Multiply(int[][] initA, int[][] initB){
		int[][] prod;
		int size = initA.length;

		int subSize = size/2;
		int[][] quad1 = new int[subSize][subSize];
		int[][] quad2 = new int[subSize][subSize];
		int[][] quad3 = new int[subSize][subSize];
		int[][] quad4 = new int[subSize][subSize];

		int[][] a = new int[subSize][subSize];
		int[][] b = new int[subSize][subSize];
		int[][] c = new int[subSize][subSize];
		int[][] d = new int[subSize][subSize];
		int[][] e = new int[subSize][subSize];
		int[][] f = new int[subSize][subSize];
		int[][] g = new int[subSize][subSize];
		int[][] h = new int[subSize][subSize];

		a = GenerateQuadrant(initA,subSize,1);
		b = GenerateQuadrant(initA,subSize,2);
		c = GenerateQuadrant(initA,subSize,3);
		d = GenerateQuadrant(initA,subSize,4);
		e = GenerateQuadrant(initB,subSize,1);
		f = GenerateQuadrant(initB,subSize,2);
		g = GenerateQuadrant(initB,subSize,3);
		h = GenerateQuadrant(initB,subSize,4);

		int[][] p1 = MatrixMult(a,MatrixSubtract(f,h));
		int[][] p2 = MatrixMult(MatrixAdd(a,b),h);
		int[][] p3 = MatrixMult(MatrixAdd(c,d),e);
		int[][] p4 = MatrixMult(d,MatrixSubtract(g,e));
		int[][] p5 = MatrixMult(MatrixAdd(a,d),MatrixAdd(e,h));
		int[][] p6 = MatrixMult(MatrixSubtract(b,d),MatrixAdd(g,h));
		int[][] p7 = MatrixMult(MatrixSubtract(a,c),MatrixAdd(e,f));

		quad1 = MatrixAdd(MatrixSubtract(MatrixAdd(p5,p4),p2),p6);
		quad2 = MatrixAdd(p1,p2);
		quad3 = MatrixAdd(p3,p4);
		quad4 = MatrixSubtract(MatrixSubtract(MatrixAdd(p1,p5),p3),p7);

		prod = CombineQuadrants(size,quad1,quad2,quad3,quad4);
		return prod;
	}

	public static int[][] readStrassenMatrixFromFile(String filename) {
		int[][] thisMatrix = null;
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
				thisMatrix = new int[rows][columns];
			    int i=0;
			    while((line = br.readLine()) != null) {
			    	st = new StringTokenizer(line, " ");
			    	int j=0;
			    	while(st.hasMoreTokens() && j < columns) {
			    			thisMatrix[i][j] = Integer.parseInt(st.nextToken().trim());
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
	
	public static void main(String[] args){
		long executeTimeMS = 0;
		Random rand = new Random();
		int[][] initA = new int[32][32];
		int[][] initB = new int[32][32];
		for (int i=0; i<initA.length; i++){
			for (int j=0; j<initA.length; j++){
				initA[i][j] = rand.nextInt(10);
				initB[i][j] = rand.nextInt(10);
			}
		}
		long startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		long endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println(executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\8_8_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 8x8: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\16_16_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 16x16: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\32_32_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 32x32: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\64_64_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 64x64: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\128_128_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 128x128: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\256_256_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 256x256: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\512_512_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 512x512: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 1024x1024: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 2048x2048: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 4096x4096: " + executeTimeMS);
	}
}