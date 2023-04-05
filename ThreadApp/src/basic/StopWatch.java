
package basic;
//	초를 카운트 하는 스탑워치 구현하기

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import util.StringUtil;

public class StopWatch extends JFrame {
//	public class StopWatch extends JFrame extends Thread (X) Thread 클래스를 상속받는 방법
//	public class StopWatch extends JFrame Implements Thread (X) Runnable 인터페이스를 구현

//	지금까지 흔히 얘기해왔던 자바의 실행부는 사실 시스템에 의해 제공되는 main thread였다 -> 자바는 Thread 기반 언어다
//	메인 쓰레드는 개발자가 정의하는 객체가 아니라서 동작에 제한이 있다
//	메인 쓰레드의 목적은 프로그램 운영에 있으므로, 절대 하면 안되는 것들이 있는데
//	1) 무한루프에 빠지게 해서는 안된다
//	2) 대기상태에 빠지게 해서는 안된다
//	메인 쓰레드의 역할은 GUI 프로그래밍에서 이벤트를 감지하거나 화면에 그래픽 처리를 담당하는 등의 역할을 수행하므로,
//	개발자가 정의한 쓰레드로 이벤트나 GUI 처리를 하는 것은 다른 언어에서는 금지사항이다 (예, 아이폰 / 안드로이드)

	JButton bt;
	JLabel la;
	Thread thread;
	int sec; // 초를 증가시킬 인스턴스 변수
	int min; // 분을 증가시킬 인스턴스 변수
	Boolean flag=false; // 스탑워치의 동작여부를 결정하는 논리값 // 명시하지 않으면 기본값은 false 

	public StopWatch() {
		bt = new JButton("Start");
		la = new JLabel("00:00", SwingConstants.CENTER);
		thread = new Thread() {

			public void run() {
				while (true) {
					printTime();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start(); // Thread를 Runnable 상태로 만들자

		la.setPreferredSize(new Dimension(480, 250));
		la.setFont(new Font("Verdana", Font.BOLD, 120));

		setLayout(new FlowLayout());
		add(bt);
		add(la);
		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// 버튼과 리스너 연결
		 bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag=!flag;
				// 버튼의 텍스트 전환하기 (Stop / Start)
				String title = (flag)? "Stop" : "Start";
				bt.setText(title);
			}
		});
	}

	private void printTime() {
		if(flag) {
		sec++;
		if (sec >= 60) {
			sec = 0;
			min++; // 분 증가
		}
//		la.setText("00:" + StringUtil.getNumString(sec)); // la.setText("00:"+sec);
		la.setText(StringUtil.getNumString(min) + ":" + StringUtil.getNumString(sec));
		}
	}

	public static void main(String[] args) {
//		int[] arr = new int[2];
//		arr[2] = 5; // Exception in thread "main" : main도 Thread 중 하나였다
		new StopWatch();
	}
}
