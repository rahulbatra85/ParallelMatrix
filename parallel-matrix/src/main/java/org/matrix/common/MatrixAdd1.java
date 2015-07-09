package org.matrix.common;

public class MatrixAdd1 implements MatrixAddInteface {
	public boolean add(Matrix A, Matrix B, Matrix C){
		if((A.getNumRows() != B.getNumRows()) || 
			(A.getNumColumns() != B.getNumColumns()) ||
			(C.getNumColumns() != B.getNumColumns()) ||
			(C.getNumRows() != C.getNumRows())) {
			return false;
		}
		for(int i=0; i<A.getNumRows(); i++) {
			for(int j=0; j<A.getNumColumns(); j++) {
				C.setElem(i,j, (A.getElem(i,j)+B.getElem(i,j)));
			} 
		}
	return true;
	}
}