package org.matrix.seq;

import org.matrix.common.IfaceDeterminant;
import org.matrix.common.Matrix;

public class DeterminantSeq implements IfaceDeterminant {

	@Override
	public double determinant(Matrix A) {
		return 0.0;
	}
	
	   //To do - this is copied from an example, we want to do our own
	   // but I needed a determinant function
	   //http://www.codeproject.com/Articles/405128/Matrix-operations-in-Java
	/*public static double determinant(Matrix A) throws Exception {
		//public static double determinant(Matrix matrix) throws NoSquareException {
if (!A.isSquare()) {
	throw new Exception("Need square matrix."); //todo define matrix exception
}
		if (A.getNumRows() == 1) {
			return A.getElem(0,0);
		}
		if (A.getNumRows()==2) {
		    return (A.getElem(0, 0) * A.getElem(1, 1)) - ( A.getElem(0, 1) * A.getElem(1, 0));
		}
		double sum = 0.0;
		for (int i=0; i<A.getNumColumns(); i++) {
		    sum += flipOdd(i) * A.getElem(0, i) * determinant(Matrix.createSubMatrix(A, 0, i));
		}
		return sum;
	}*/
}
