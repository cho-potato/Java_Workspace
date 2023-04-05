
/* Thread를 정의한다 */

package basic;

public class StarThread extends Thread { // java.lang 소속이기 때문에 별도의 import가 필요없다
	String star;
	public StarThread(String star) {
		this.star = star;
	}

//	개발자는 독립실행을 원하는 코드를 run()에 작성해놓기만 하면 된다
	public void run() {
		while (true) {
			System.out.println(Thread.currentThread().getName()+star);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
