package org.matrix.parallel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.matrix.common.Matrix;
import org.matrix.seq.LUDecomposeSeqRecursive;
import org.matrix.seq.MatrixSeq;

public class TestMatrixParallelLU {

	MatrixParallel m1,m2;
	
	@Before
	public void setUp() throws Exception {
		m1 = new MatrixParallel(3,3);
		double[][] row = {{3,2,7},{2,3,1},{3,4,1}};
		m1.setRow(row[0], 0);
		m1.setRow(row[1], 1);
		m1.setRow(row[2], 2);
		
		m2 = new MatrixParallel(3,3);
		double[][] m = {{3,2,5},{7,6,3}, {9,1,7}};
		m2.setRow(m[0], 0);
		m2.setRow(m[1], 1);
		m2.setRow(m[2], 2);
	}

	@Test
	public void test() {
		
		m1.setOpLU(new LUDecomposeParallel1D());
		Matrix[] LU = m1.LUDecompose();
		System.out.println(m1.toString());
		if(LU != null){
			System.out.println(m1.toString());
			System.out.println(LU[1].toString());
		}
	}
	
	@Test
	public void test1() {
		
		m2.setOpLU(new LUDecomposeParallel1D());
		Matrix[] LU = m2.LUDecompose();
		System.out.println(m2.toString());
		if(LU != null){
			System.out.println(m2.toString());
			System.out.println(LU[1].toString());
		}
	}

}
