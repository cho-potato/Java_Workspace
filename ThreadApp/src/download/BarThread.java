package download;
//	 하나의 프로그램 내에서 각각 다른 방식으로 동작할 바를 구현하기 위해

//	재사용 가능성이 예상되므로 별도의 .java로 정의함

import javax.swing.JProgressBar;

public class BarThread extends Thread {
	JProgressBar jPrograssbar;
	boolean flag = true; // Thread 종료여부를 결정짓는 변수
	int n;
	int velX; // bar의 증가속도를 결정짓는 변수
	int time; // Thread의 sleep()속도를 결정짓는 변수
	
	// 생성시 barThread가 얼마의 속도로 진행할 지 여부를 결정짓는 매개변수
	public BarThread(JProgressBar jPrograssbar, int velX, int time) {
		this.jPrograssbar = jPrograssbar;
		this.velX = velX;
		this.time = time;
	}

	public void printValue() {
		n += velX;
		jPrograssbar.setValue(n);
		if(n>=100) {
			flag = false; // Thread 사망
		}
	}
//	Bar를 증가시키자
	public void run() {
		while (flag) {
			printValue();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
