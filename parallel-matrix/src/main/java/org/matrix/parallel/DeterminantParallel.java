import java.util.concurrent.Callable;
import java.util.concurrent.*;
import java.util.*;
import java.util.Random;

public class DeterminantParallel implements Callable<Integer>{

	int[][] matrix;
	int column;
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	public DeterminantParallel(int[][] matrix, int column){
		this.matrix = matrix;
		this.column = column;
	}

	public Integer call() throws Exception {
		int detVal = BeginDet(matrix, column);
		return detVal;
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

	public static int Det2x2(int[][] matrix){
		return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
	}

	public static int Det3x3(int[][] matrix){
		int[][] aminor = {{matrix[1][1], matrix[1][2]},
						  {matrix[2][1], matrix[2][2]}
						};
		int[][] bminor = {{matrix[0][1], matrix[0][2]},
						  {matrix[2][1], matrix[2][2]}
						};
		int[][] cminor = {{matrix[0][1], matrix[0][2]},
						  {matrix[1][1], matrix[1][2]}
						};
		return matrix[0][0] * Det2x2(aminor) - matrix[1][0] * Det2x2(bminor) + matrix[2][0] * Det2x2(cminor);
	}

	public static int DetNxN(int[][] matrix){
		int det = 0;
		int size = matrix[0].length;
		int[] vals = new int[size];

		for (int i=0; i<size; i++){
			while (size > 3){
				int x = DetNxN(SubMatrix(i,matrix));
				vals[i] = x * matrix[i][0];
				break;
			}
		}
		if (size==3){
			det = Det3x3(matrix);
		}
		for (int j=0; j<vals.length; j++){
			if (j%2 != 0){
				det -= vals[j];
			}
			else {
				det += vals[j];
			}
		}
		return det;
	}

	public static int BeginDet(int[][] matrix, int column){
		//Initializes the matrix we want to recurse through for a given thread
		int[][] newMatrix = SubMatrix(column, matrix);
		int retVal = matrix[column][0] * DetNxN(newMatrix);
		return retVal;
	}

	public static int[][] SubMatrix(int column, int[][] matrix){
		int row = 0;
		int col = 0;
		int size = matrix[0].length - 1;
		int[][] sub = new int[size][size];
		for (int i=0; i<matrix[0].length; i++){
			if (i!=column){
				for (int j=1; j<matrix[0].length; j++){
					sub[col][row] = matrix[i][j];
					row++;
				}
				col++;
			}
			row = 0;
		}
		return sub;
	}

	public static void main(String[] args){

		int[][] x = new int[14][14];
		Random rando = new Random();
		for (int i=0; i<x[0].length; i++){
			for (int j=0; j<x[0].length; j++){
				x[i][j] = rando.nextInt(10);
			}

		int numThreads = 4;
		System.out.println(MatrixToString(x));
		int size = x[0].length;
		long startTime = System.currentTimeMillis();
		if (size == 1){
			System.out.println(x[0][0]);
			System.exit(0);
		}
		if (size == 2){
			System.out.println(Det2x2(x));
			System.exit(0);
		}
		ArrayList<Future<Integer>> vals = new ArrayList<Future<Integer>>();
		for (int i=0; i<size; i++){
			DeterminantParallel d = new DeterminantParallel(x, i);
			Future<Integer> fut = threadPool.submit(d);
			vals.add(fut);
		}
		threadPool.shutdown();
		int det = 0;
		for (int j=0; j<vals.size(); j++){
			try{
				if (j%2==0){
					det += vals.get(j).get();
				}
				else {
					det -= vals.get(j).get();
				}
			} catch (Exception exc) {
				System.err.println(exc);
			}
		}
		long endTime = System.currentTimeMillis();
		long executeTimeMS = endTime - startTime;
		System.out.println(executeTimeMS);
		System.out.println(det);
	}
}