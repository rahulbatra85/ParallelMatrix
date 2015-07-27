package org.matrix.parallel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;


class workerThreadSync{
	static final ReentrantLock lock = new ReentrantLock();
	final Condition divideQ  = lock.newCondition(); 
	final Condition msQ  = lock.newCondition(); 
	boolean msActive, dActive;
	
	workerThreadSync(){
		msActive = false;
		dActive = true;
	}
	
	public void waitMS(){
		lock.lock();
		try {
			while(!msActive){
				msQ.await();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
	
	public void signalMS(){		
		lock.lock();
		try {
			msQ.signalAll();
		} finally{
			lock.unlock();
		}
		
	}
	
	public void waitDivide(){
		lock.lock();
		try {
			while(msActive){
				divideQ.await();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
	
	public void signalDivide(){
		lock.lock();
		try {
			divideQ.signalAll();
		} finally{
			lock.unlock();
		}
	}
	
	public void setMsActive(boolean v){
		lock.lock();
		try {
			msActive = v;
		} finally{
			lock.unlock();
		}
	}	
}

class workerThread implements Runnable{
	Matrix A;
	int tid, numT, type;
	workerThreadSync wts;
	CyclicBarrier barrier;
	
	workerThread(Matrix A, int tid, int numT, int type, workerThreadSync wts, CyclicBarrier b){
		this.A = A;
		this.tid = tid;
		this.numT = numT;
		this.type = type;
		this.wts = wts;
		this.barrier = b;
	}
	
	@Override
	public void run() {
		//Divide thread
		if(type == 0){
			divide();
		} else{
			ms();
		}		
	}
	
	public void divide() {
		int k = 0;
		int n = A.getNumRows();
		while (k<n){
			//Do division
			double div = A.getElem(k, k);
			for(int i=k+1; i<n; i++){
				A.setElem(k, i, A.getElem(k, i)/div);
			}
			
			//Barrier(wait for all divide threads to finish)
			//\todo
			
			//Signal MS Threads(if id=1)
			if(tid == 1){
				wts.setMsActive(true);
				wts.signalMS();
			}

			wts.waitDivide();
									
			//Next iteration
			k++;
		}		
	}
	
	public void ms(){
		int k = 0;
		int n = A.getNumRows();
		while (k<n){
			//Wait
			wts.waitMS();
			
			
			//Determine starting row for each thread
			//based on kth iteration and thread-id(tid)
			int b=0;
			if(k==0){
				b = 0;
			} else{
				if(k%numT + 1 > tid){
					b = k/numT + 1;
				} else{
					b = k/numT;
				}
			}
			
			//Do multiply and subtract
			for(int r = ((b*numT) + tid);r<n;r+=numT){
				for(int c=k+1;c<n;c++){
					double val = A.getElem(r, c) - A.getElem(r, k)*A.getElem(k, c);
					A.setElem(r, c, val);
				}
			}
			
			//Barrier(wait for all MS threads to finish)
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			k++;
			
			//Signal Divide Threads(if id=0)
			if(tid == 1){
				wts.setMsActive(false);
				wts.signalDivide();
			}
		}
	}
}

public class LUDecomposeParallel1D implements IfaceLUDecompose {
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	@Override
	public Matrix[] LUDecompose(Matrix A) {
		
		if(!A.isSquare()){
			return null;
		}
				
		workerThreadSync wts = new workerThreadSync();
		CyclicBarrier dBarrier = new CyclicBarrier(1);
		
		//Create Divide threads
		//\todo create more than one
		workerThread d = new workerThread(A, 1, 1, 0, wts, dBarrier);
		Thread td = new Thread(d);
		
		//Create MS threads
		//todo determine how many threads to use
		int n = A.getNumRows();
		int numT = 0;
		if(n<8){
			numT = 1;
		} else{
			numT = 1;
		}
		
		Thread[] ts = new Thread[numT];
		workerThread[] ms = new workerThread[numT];
		CyclicBarrier msBarrier = new CyclicBarrier(numT);		
		for(int i=0; i<numT; i++){
			ms[i] = new workerThread(A, i+1, numT, 1, wts, msBarrier);
			 ts[i] = new Thread(ms[i]);
		}
		
		//Start Divide threads
		td.start();
		
		//Start MS threads
		for(int i=0; i<numT; i++){
			ts[i].start();;
		}
		
		//Join All threads
		try {
			td.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<numT; i++){
			try {
				ts[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
