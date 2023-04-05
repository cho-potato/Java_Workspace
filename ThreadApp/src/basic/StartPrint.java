
/* Thread를 두 개 이상 생성하여 실행하는 프로그램 작성하기 */
//	2개 이상의 Thread를 정의한다면, .java로 만들 필요가 있다
//	따라서 별도의 클래스로 Thread를 정의한다

package basic;

public class StartPrint {
	public static void main(String[] args) {
		StarThread st1 = new StarThread("★"); // Thread 1개 생성
		StarThread st2 = new StarThread("☆"); // Thread 2개 생성
		st1.start(); // Runnable로 진입시키기 start()
		st2.start(); // st1, st2 중 어떤 것이 먼저 실행되는가는 개발자가 신경쓸 필요 없음(우선순위가 중요한 게 아니라 동시수행이 중요한 것)
	}
}
