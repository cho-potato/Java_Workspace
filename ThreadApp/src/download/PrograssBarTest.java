package download;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class PrograssBarTest extends JFrame {
	JProgressBar bar1;
	JProgressBar bar2;
	JButton bt;
	int n;
	BarThread t1;
	BarThread t2; // 개발자가 정의한 Thread 2개 선언

	public PrograssBarTest() {
		bar1 = new JProgressBar();
		bar2 = new JProgressBar();
		bt = new JButton("DOWNLOAD");

		Dimension d = new Dimension(680, 80);
		bar1.setPreferredSize(d);
		bar2.setPreferredSize(d);
		bar1.setStringPainted(true);
		bar2.setStringPainted(true);
		bar1.setFont(new Font("Verdana", Font.BOLD, 30));
		bar2.setFont(new Font("Verdana", Font.BOLD, 30));

		setLayout(new FlowLayout());

		add(bar1);
		add(bar2);
		add(bt);

		setSize(700, 250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

//		bt.addActionListener(new ActionListener() {
//			
//			public void actionPerformed(ActionEvent e) {
//				bar1.setValue(n); // 매개변수 n은 n%를 의미
//				n++;
//				bar1.setString(Integer.toString(n)+"%"); // 이거 없어도 % 잘 나옴 setStringPainted 땜에
//			}
//		});

		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t1 = new BarThread(bar1, 1, 10); // 생성
				t2 = new BarThread(bar2, 5, 100); // 생성
				t1.start();
				t2.start();
			}
		});
	}

	public static void main(String[] args) {
		new PrograssBarTest();
	}
}
