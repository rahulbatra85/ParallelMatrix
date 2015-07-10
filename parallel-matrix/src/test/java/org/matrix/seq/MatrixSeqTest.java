package org.matrix.seq;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.matrix.common.Matrix;


public class MatrixSeqTest {
	MatrixSeq m1;
	MatrixSeq m2;
	MatrixSeq m3;
	
	@Before
	public void setUp() throws Exception {
		m1 = new MatrixSeq(4,4,false,10);
		m2 = new MatrixSeq(4,4,false,5);
		m3 = new MatrixSeq(3,4,false,5);
	}
	
	@Test
	public void testToString() {
		String expected = "0.0 0.0 0.0 0.0 \n0.0 1.0 2.0 3.0 \n0.0 2.0 4.0 6.0 \n0.0 3.0 6.0 9.0 \n";
		assertEquals(m1.toString(), expected);
	}
	
	@Test
	public void testSetRow() {
		Matrix replaceRow = new MatrixSeq(4,4,false,10);
		double[] row = {5,5,5,5};
		replaceRow.setRow(row, 3);
		String expected = "0.0 0.0 0.0 0.0 \n0.0 1.0 2.0 3.0 \n0.0 2.0 4.0 6.0 \n5.0 5.0 5.0 5.0 \n";
		assertEquals(expected,replaceRow.toString());
		
	}
	@Test
	public void testSetElem(){
		Matrix thisMatrix = new MatrixSeq(4,4,true,0);
		for(int i=0;i<thisMatrix.getNumRows();i++) {
			for(int j=0; j<thisMatrix.getNumColumns();j++) {
				thisMatrix.setElem(i, j, ((i*j)%10));
			}
		}
		String expected = "0.0 0.0 0.0 0.0 \n0.0 1.0 2.0 3.0 \n0.0 2.0 4.0 6.0 \n0.0 3.0 6.0 9.0 \n";
		assertEquals(expected,thisMatrix.toString());
	}
	
	@Test
	public void testGetNumRows() {
		Matrix newMat = new MatrixSeq(7,3,true,0);
		assertEquals(newMat.getNumRows(),7);
	}
	@Test
	public void testGetNumColumns() {
		Matrix newMat = new MatrixSeq(7,3,true,0);
		assertEquals(newMat.getNumColumns(),3);
	}
	
	@Test
	public void testGetRow() {
		double[] rowSample = {0,2,4,6};
		double[] temp = m1.getRow(2);
		for(int i=0; i<temp.length; i++) {
			assert(temp[i] == rowSample[i]);
		}
	}
	@Test
	public void testGetColumn() {
		double[] colSample = {0,2,4,6};
		double[] temp = m1.getRow(2);
		for(int i=0; i<temp.length; i++) {
			assert(temp[i] == colSample[i]);
		}
	}

	@Test
	public void testGetElem() {
		assert(m1.getElem(2, 2)== 4);
	}
	
	@Test
	public void testIsSquare() {
		assert(m1.isSquare());
	}
	
	@Test 
	public void testIsNotSquare() {
		assert(!m3.isSquare());
	}
	
	/*@Test
	public void testMultiply() {
		Matrix matrixResult = new Matrix(4,4,true,0);
		Matrix.multiply(m1, m2, matrixResult);
		String expected = "0.0 0.0 0.0 0.0 \n0.0 14.0 13.0 17.0 \n0.0 28.0 26.0 34.0 \n0.0 42.0 39.0 51.0 \n";
		assertEquals(matrixResult.toString(),expected);
	}*/
	
}
