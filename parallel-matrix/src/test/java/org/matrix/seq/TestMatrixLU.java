package org.matrix.seq;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.matrix.common.Matrix;

public class TestMatrixLU {

	MatrixSeq m1,m2;
	
	@Before
	public void setUp() throws Exception {
		m1 = new MatrixSeq(3,3);
		double[][] row = {{3,2,7},{2,3,1},{3,4,1}};
		m1.setRow(row[0], 0);
		m1.setRow(row[1], 1);
		m1.setRow(row[2], 2);
		
		m2 = new MatrixSeq(3,3);
		double[][] m = {{3,2,5},{7,6,3}, {9,1,7}};
		m2.setRow(m[0], 0);
		m2.setRow(m[1], 1);
		m2.setRow(m[2], 2);
	
	}

	@Test
	public void test() {
		Matrix[] LU = m1.LUDecompose();
		if(LU != null){
			System.out.println(LU[0].toString());
			System.out.println(LU[1].toString());
		}
		/*
		LU = m2.LUDecompose();
		if(LU != null){
			System.out.println(LU[0].toString());
			System.out.println(LU[1].toString());
		}*/
		fail("Not yet implemented");
	}
	
	@Test
	public void testLuRecursive(){ 
		m1.setOpLU(new LUDecomposeSeqRecursive());
		Matrix[] LU = m1.LUDecompose();
		if(LU != null){
			System.out.println(m1.toString());
			System.out.println(LU[1].toString());
		}
	}

}
