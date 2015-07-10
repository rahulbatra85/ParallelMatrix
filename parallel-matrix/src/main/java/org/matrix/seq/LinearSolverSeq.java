package org.matrix.seq;

import org.matrix.common.IfaceLinearSolver;
import org.matrix.common.Matrix;

public class LinearSolverSeq implements IfaceLinearSolver {

	@Override
	public Matrix linearSolver(Matrix A, Matrix B) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

/*public static boolean solveLinearSystem(Matrix A, Matrix X, Matrix B) {
// AX=B, so A^-1B = X...Take the inverse of A and solve for X
Matrix invA = A.invert();
if(multiply(invA,B,X)) {
	System.out.println("The matrix result is: " + X.toString());
} else {
	System.out.println("Problem solving linear equation");
}
return true;
}*/
