package org.matrix.seq;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;

public class LUDecomposeSeqRow implements IfaceLUDecompose{

	@Override
	public Matrix[] LUDecompose(Matrix A) {
		// TODO Auto-generated method stub
		if(!A.isSquare()){
			return null;
		}
		int n = A.getNumRows();
		
		for(int k=0; k<n; k++){
			double v = A.getElem(k, k);
			for(int j=k+1; j<n; j++){
				A.setElem(k, j, A.getElem(k, j)/v);
			}
			
			for(int i=k+1; i<n; i++){
				for(int j=k+1; j<n; j++){
					double val = A.getElem(i, j);
					val = val-A.getElem(i, k)*A.getElem(k, j);
					A.setElem(i, j, val);
				}
			}
		}
		
		return null;
	}

}
