package org.halk.demo;

class ThreadTrain10 implements Runnable {
	// 定义火车票总数
	static int trainCount = 100;
	private Object oj = new Object();
	public boolean flag = true;

	@Override
	public void run() {
		if (flag) {
			while (trainCount > 0) {

				synchronized (ThreadTrain10.class) {
					if (trainCount > 0) {
						try {
							Thread.sleep(40);
						} catch (Exception e) {

						}
						System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount + 1) + "票");
						trainCount--;
					}
				}

			}
		} else {
			while (trainCount > 0) {
				show();
			}
		}

	}

	/**
	 * 同步方法用的是 this 这把锁 （对象的引用，实例）
	 * static 如果修饰方法，通过类名.方法调用，这时在加this就不行了。被修饰的方法只有当字节码被加载时才会被初始化
	 *
	 */
	public static   synchronized void show() {
		//
		// synchronized (oj) {
		if (trainCount > 0) {
			try {
				Thread.sleep(40);
			} catch (Exception e) {

			}
			System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount + 1) + "票");
			trainCount--;
		}
		// }
	}

}

/**
 *
 * @classDesc: 功能描述:()
 * @author: 余胜军
 * @createTime: 2017年8月19日 下午6:41:58
 * @version: v1.0
 * @copyright:上海每特教育科技有限公司
 */
public class ThreadDemo10 {
	public static void main(String[] args) throws InterruptedException {
		// 定义一个实例
		ThreadTrain10 threadTrain10 = new ThreadTrain10();
		Thread thread1 = new Thread(threadTrain10, "一号窗口");
		Thread thread2 = new Thread(threadTrain10, "二号窗口");
		thread1.start();
		Thread.sleep(40);
		threadTrain10.flag = false;
		thread2.start();
	}
}
