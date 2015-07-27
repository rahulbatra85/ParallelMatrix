package org.matrix.parallel;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.matrix.common.Matrix;

public class TestMatrixParallelMult {

	@Test
	public void testMultiplyBlock() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\10_10_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\10_10_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\10_10_MULT_A_B");
		Matrix matrixResult = large1.multiply(large2);
        assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	@Test
	public void timeMultiplyBlocks500x500Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\500_500_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\500_500_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\500_500_A_B_MULT");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		matrixResult.writeMatrixToFile("A_B_MULT_500");
		System.out.println("The time for parallel 500x500 block multiply is: " + timer);
		assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	
	@Test
	public void timeMultiplyBlocks10x10Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\10_10_A");
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\10_10_B");
		large1.setOpMult(new MultParallel2());
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\10_10_MULT_A_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 10x10 block multiply is: " + timer);
		assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	
	@Test
	public void timeMultiplyBlocks200x200Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\200_200_A");
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\200_200_B");
		large1.setOpMult(new MultParallel2());
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\200_200_A_B_MULT");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		matrixResult.writeMatrixToFile("new2002000");
		long timer = end-start;
		System.out.println("The time for parallel 200x200 block multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void timeMultiplyDNS100x100Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\100_100_M1");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\100_100_M2");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\100_100_M1_M2_MULT_Format");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 100x100 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	
	@Test
	public void timeMultiplyDNS10x10Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\10_10_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\10_10_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\10_10_MULT_A_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 10x10 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void timeMultiplyDNS200x200Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\200_200_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\200_200_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\200_200_A_B_MULT_Format");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		matrixResult.writeMatrixToFile("200_200");
		long timer = end-start;
		System.out.println("The time for parallel 200x200 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
}
