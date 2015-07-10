package org.matrix.seq;

import org.matrix.common.IfaceInverse;
import org.matrix.common.Matrix;

public class InverseSeq implements IfaceInverse {

	@Override
	public Matrix inverse(Matrix A) {
		return null;
	}
	
	/*public Matrix invert() {
	Matrix inverse = new Matrix(getNumRows(), getNumColumns(), true, 0);
	double det = 0;
	try {
	    det = determinant();
	} catch (Exception e) {
		System.out.println(e.toString());
	}
	det = 1/det;
	Matrix adjointR = new Matrix(getNumRows(), getNumColumns(), true, 0);
	adjointR = adjoint(this);
	inverse = scalarMultiply(adjointR,det);
	return inverse;
}*/

}
