import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.*;
import java.util.*;

public class StrassenParallel {
	
	public StrassenParallel(){

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

	public static class GenerateQuadrant implements Callable<int[][]>{

		int[][] matrix;
		int subSize;
		int quadrant;

		public GenerateQuadrant(int[][] matrix, int subSize, int quadrant){
			this.matrix = matrix;
			this.subSize = subSize;
			this.quadrant = quadrant;
		}

		public int[][] call(){
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

	public static class MatrixMult implements Callable<int[][]>{

		int rowA;
		int colA;
		int rowB;
		int colB;
		int[][] matrixA;
		int[][] matrixB;
		int[][] matrixC;

		public MatrixMult(int[][] matrixA, int[][] matrixB){
			this.rowA = matrixA[0].length;
			this.colA = matrixA.length;
			this.rowB = matrixB[0].length;
			this.colB = matrixB.length;
			this.matrixA = matrixA;
			this.matrixB = matrixB;
			this.matrixC = new int[matrixA[0].length][matrixA[0].length];
		}

		public int[][] call(){

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
		ExecutorService service = Executors.newCachedThreadPool();
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

		int[][] p1 = new int[subSize][subSize];
		int[][] p2 = new int[subSize][subSize];
		int[][] p3 = new int[subSize][subSize];
		int[][] p4 = new int[subSize][subSize];
		int[][] p5 = new int[subSize][subSize];
		int[][] p6 = new int[subSize][subSize];
		int[][] p7 = new int[subSize][subSize];

		List<Future<int[][]>> quadListA = new ArrayList<Future<int[][]>>();
		List<Future<int[][]>> quadListB = new ArrayList<Future<int[][]>>();
		List<Future<int[][]>> quadListC = new ArrayList<Future<int[][]>>();
		for (int i=0; i<4; i++){
			Future<int[][]> q1 = service.submit(new GenerateQuadrant(initA,subSize,i));
			quadListA.add(q1);
			Future<int[][]> q2 = service.submit(new GenerateQuadrant(initB,subSize,i));
			quadListB.add(q2);
		}
		try{
			a = quadListA.get(0).get();
			//System.out.println("a: " + MatrixToString(a));
			b = quadListA.get(1).get();
			//System.out.println("b: " + MatrixToString(b));
			c = quadListA.get(2).get();
			//System.out.println("c: " + MatrixToString(c));
			d = quadListA.get(3).get();
			//System.out.println("d: " + MatrixToString(d));
			e = quadListB.get(0).get();
			//System.out.println("e: " + MatrixToString(e));
			f = quadListB.get(1).get();
			//System.out.println("f: " + MatrixToString(f));
			g = quadListB.get(2).get();
			//System.out.println("g: " + MatrixToString(g));
			h = quadListB.get(3).get();
			//System.out.println("h: " + MatrixToString(h));
		} catch (Exception exc){
			exc.printStackTrace();
		}

		Future<int[][]> q3;
		q3 = service.submit(new MatrixMult(a,MatrixSubtract(f,h)));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixAdd(a,b),h));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixAdd(c,d),e));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(d,MatrixSubtract(g,e)));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixAdd(a,d),MatrixAdd(e,h)));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixSubtract(b,d),MatrixAdd(g,h)));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixSubtract(a,c),MatrixAdd(e,f)));
		quadListC.add(q3);

		try{
			p1 = quadListC.get(0).get();
			//System.out.println(MatrixToString(p1));
			p2 = quadListC.get(1).get();
			//System.out.println(MatrixToString(p2));
			p3 = quadListC.get(2).get();
			//System.out.println(MatrixToString(p3));
			p4 = quadListC.get(3).get();
			//System.out.println(MatrixToString(p4));
			p5 = quadListC.get(4).get();
			//System.out.println(MatrixToString(p5));
			p6 = quadListC.get(5).get();
			//System.out.println(MatrixToString(p6));
			p7 = quadListC.get(6).get();
			//System.out.println(MatrixToString(p7));
		} catch (Exception exc){
			exc.printStackTrace();
		}

		quad1 = MatrixAdd(MatrixSubtract(MatrixAdd(p5,p4),p2),p6);
		//System.out.println(MatrixToString(quad4));
		quad2 = MatrixAdd(p1,p2);
		//System.out.println(MatrixToString(quad2));
		quad3 = MatrixAdd(p3,p4);
		//System.out.println(MatrixToString(quad3));
		quad4 = MatrixSubtract(MatrixSubtract(MatrixAdd(p1,p5),p3),p7);
		//System.out.println(MatrixToString(quad4));

		service.shutdownNow();
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
		int[][] initA = new int[100][100];
		int[][] initB = new int[100][100];
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