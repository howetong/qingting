package cn.core.test;

public class Counting {
	static int count;
	public static void main(String[] args) throws InterruptedException {
		
		Thread thread1 = new Thread(){
			public void run(){
				for(int i = 0 ; i < 10000 ; i++){
					count++;			
				}
			}
		};
		Thread thread2 = new Thread(){
			public void run(){
				for(int i = 0 ; i < 10000 ; i++){
					count++;			
				}
			}
		};
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		System.out.println(count);
	}
}
