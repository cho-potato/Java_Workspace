
/* 귀 달아주는 법을 연습해보자 */

// 2. 내가 Implements를 하는 법
//  implements ActionListener
// 		bt.addActionListener(this);
//	}
//	3. 내부 익명 클래스로 넣는 법
//	bt.addActionListener(new ActionListener() { // 내부 익명 클래스 3) 
//		public void actionPerformed(ActionEvent e) {
//		System.out.println("aslkdjajdwka");
//		}
//	});

package event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font; // 
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; //
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener; //

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EventTest extends JFrame {
	JButton bt;
	JTextField t;
	JPanel p;
	int x;
	int y;

	public EventTest() {
		bt = new JButton("팀버튼");
		t = new JTextField(20);
		p = new JPanel() {
			public void paint(Graphics g) {
				g.clearRect(0, 0, 290, 300);
				g.drawOval(x, y, 100, 100);
			}
		};

		p.setPreferredSize(new Dimension(290, 300));
		p.setBackground(Color.YELLOW);

		setLayout(new FlowLayout());
		add(bt);
		add(t);
		add(p);

		setSize(300, 400);
		setVisible(true);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);

//		 버튼과 리스너 연결
//		 bt.addActionListener(new MyActionListener()); // bt.addActionListener(new MyActionListener()); 1) .java로 별도로 만들어 쓰는 법
//		 bt.addActionListener(this); // bt.addActionListener(이벤트 처리코드); 2) 직접 Implements를 하는 법 
		bt.addActionListener(new ActionListener() { // 3) 내부 익명 클래스

			// 내부 익명 클래스는 외부 클래스의 멤버들을 자기 것처럼 쓴다
			public void actionPerformed(ActionEvent e) {
				System.out.println("aslkdjajdwka");
				x += 2;
				y += 2;
				p.repaint(); // 다시 그리기
			}
		});

//		 텍스트필드와 리스너 연결
//		 이벤트 구현시 개발자가 재정의해야 할 메서드가 3개 이상 넘어가면 sun에서 이미 해당 인터페이스를 구현해 놓은, 재정의 해놓은 객체를 지원해주는데 이 객체들을 가리켜 adapter라 한다
//		 KeyListener를 개발자 대신 구현한 어댑터 -> KeyAdapter 클래스
//		 WindowListner를 개발자 대신 구현한 어댑터 -> WindowAdapter 클래스

//		t.addKeyListener(new KeyListener() {
//			public void keyTyped(KeyEvent e) {
//			}
//			public void keyReleased(KeyEvent e) {
//				System.out.println("?????????????");
//			}
//			public void keyPressed(KeyEvent e) {
//			}
//		});

//		 개발자들이 짊어질 재정의 의무를 Adapter들이 재정의했으므로 개발자는 필요한 메서드만 재정의하면 된다
		t.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				System.out.println("!!!!!!");
			}
		});

//		 윈도우와 리스너와의 연결
//		1) Adapter를 쓰지 않은 버전
//		this.addWindowListener(new WindowListener() {
//			public void windowOpened(WindowEvent e) {
//			}
//			public void windowIconified(WindowEvent e) {
//			}
//			public void windowDeiconified(WindowEvent e) {
//			}
//			public void windowDeactivated(WindowEvent e) {
//			}
//			public void windowClosing(WindowEvent e) {
//			}
//			public void windowClosed(WindowEvent e) {
//			}
//			public void windowActivated(WindowEvent e) {
//			}
//		});

		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
//				 윈도우 x버튼 누를 때
				System.exit(0); // 프로세스 종료 (=setDefaultCloseOperation(EXIT_ON_CLOSE);)
			}
		});
	}

	public static void main(String[] args) {
		new EventTest();
	}
}
