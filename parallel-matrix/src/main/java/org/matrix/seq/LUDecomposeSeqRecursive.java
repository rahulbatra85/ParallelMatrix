package org.matrix.seq;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;

public class LUDecomposeSeqRecursive implements IfaceLUDecompose {

	@Override
	public Matrix[] LUDecompose(Matrix A) {
		if(!A.isSquare()){
			return null;
		}
		int n = A.getNumRows();
		
		//Create L and U  matrix
		MatrixSeq L = new MatrixSeq(n,n);
		MatrixSeq U = new MatrixSeq(n,n);
		
		//Permutation Matrix
		MatrixSeq p = new MatrixSeq(n,n);
		for(int i=0; i<n; i++){
			p.setElem(i, i, 1);
		}
		
		//Recursive loop k=1 to n
		for(int k=0; k<n; k++){
			//Find max pivot
			int idx = k;
			double max = 0;
			for(int i=k; i<n; i++){
				if(Math.abs(A.getElem(i, k)) > max){
					idx = i;
					max = A.getElem(i, k);
				}
			}
			//If pivot is zero, then matrix is singular
			if(max == 0){
				return null;
			}
			
			p.setElem(k, k, 0);
			p.setElem(k, idx, 1);
			p.setElem(idx, idx, 0);
			p.setElem(idx, k, 1);
			
			//Exchange row
			for(int i=0; i<n; i++){
				double tmp = A.getElem(k, i);
				double tmp1 = A.getElem(idx, i);
				A.setElem(k, i, tmp1);
				A.setElem(idx, i, tmp);
			}
			//Compute V
			for(int i=k+1; i<n; i++){
				A.setElem(i, k,  A.getElem(i, k)/A.getElem(k, k));
				//for j=k+1 to n
				for(int j=k+1; j<n; j++){
					//Aij = Aij - AikAkj
					double val = A.getElem(i, j) - A.getElem(i, k)*A.getElem(k, j);
					A.setElem(i, j, val);
				}
			}
		}
		MatrixSeq[] ret = new MatrixSeq[2];
		ret[0] = L;
		ret[1] = p;
		return ret;
	}

}
