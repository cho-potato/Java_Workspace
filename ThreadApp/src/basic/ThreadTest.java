
/*
 
 쓰레드(Thread)?
 하나의 프로세스(자바 프로그램) 안에서 독집적으로 동작할 수 있는 하위 실행단위
 
 쓰레드 프로그래밍 순서
 1) 쓰레드 클래스를 정의
 	a. Thread 클래스를 상속받는 방법
 	b. 특정 클래스를 상속받은 경우 다중상속이 불가능하므로 Runnable 인터페이스를 구현(상속)한다
 	c. 내부 익명 클래스로 Thread를 사용
 2) 원하는 로직을 run()에 작성해야 JVM이 실행
  - - - - - - - - - - - - - - - - - - - - - - - - - - - - 여기까지 개발자의 역할
 3) 시스템(JVM)에게 수행을 맡긴다( start()호출)
  
 */

package basic;

// 카운터 증가시키기
public class ThreadTest extends Thread { // 1) - a. Thread 클래스를 상속받는 방법
	int count;

//	Thread로 실행하고 싶은 코드를 run()에 작성해놓으면 JVM이 알아서 실행여부를 결정
//	Thread의 생명주기에서 run()의 닫는 브레이스를 만나면 Thread는 소멸하며, 이 때 내부적으로 destroy()를 호출하면서 죽는다
//	Sleep : 1/1000초 단위 (만약, 1000이면 1초를 쉬는 것)
//	쉬는 동안 Non-Runnable 영역으로 들어갔다가 나옴
	public void run() {
//		count++;
//		System.out.println("count");
//		위처럼 해놓으면 생명주기가 끝나기 떄문에 while문으로 묶음
		while (true) {
			count++;
			System.out.println(count);
			try {
				Thread.sleep(1000); // 1초 동안 쉬다가 1초가 지나면 다시 복귀해라
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	public static void main(String[] args) {
//		new ThreadTest(); // 생성
		Thread t = new ThreadTest();
		t.start(); // JVM에게 맡기기(Runnable 영역으로 진입)
	}
}
