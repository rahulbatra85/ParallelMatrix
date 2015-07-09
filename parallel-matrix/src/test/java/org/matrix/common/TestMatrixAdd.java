package org.matrix.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.matrix.common.Matrix;
import org.matrix.common.MatrixAdd1;

public class TestMatrixAdd {

	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAdd() {
		Matrix A = new Matrix(2,2);
		Matrix B = new Matrix(2,2);
		Matrix C = new Matrix(2,2);
	
		boolean ret = Matrix.add(A, B, C);
		if(!ret){
			fail("Matrix add returned false");
		}
		
	}

	@Test
	public void testAdd1() {
		Matrix A = new Matrix(2,2);
		Matrix B = new Matrix(2,2);
		Matrix C = new Matrix(2,2);
		
		Matrix.setOPAdd(new MatrixAdd1());
		
		boolean ret = Matrix.add(A, B, C);
		if(!ret){
			fail("Matrix add returned false");
		}
	}
}
