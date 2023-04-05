package event;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.IllegalPathStateException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Nandayo extends JFrame {

	JButton bt_create; // 버튼을 한 개씩 생성하는
	JButton bt_bg; // 전체 색상 바뀌는 버튼 // setBackground
	JPanel p, p_center;
	int x = 1; // 버튼 라벨 순서
	JButton[] btn= new JButton[10]; //빈공간 10개 확보
//	배열은 크기가 고정되어 있으므로 동적으로 채워질 버튼들을 담기엔 한계가 있다. 따라서 크기를 자유롭게 동적으로 바꿀 수 있는 Collection Framework가 지원하는 객체 List 중 ArrayList를 사용해본다
	ArrayList<JButton> list = new ArrayList<JButton>();

	public Nandayo() {
		bt_bg = new JButton();
		bt_create = new JButton();
		p = new JPanel();
		p_center = new JPanel();


		p_center.setPreferredSize(new Dimension(500, 600));
		p_center.setBackground(Color.RED);
//		setLayout(new FlowLayout());

		add(p, BorderLayout.NORTH);
		add(p_center);

		
		p.add(bt_create);
		p.add(bt_bg);
		
		
		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 버튼과 리스너 연결
		bt_create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createButton();
			}
		});
		bt_bg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBg();
			}
		});
	}
// 생성된 버튼들을 대상으로 모든 버튼의 색상을 변경한다
// 자바 배열의 단점 : C, C# 등 일반적인 프로그래밍 언어의 배열 특징은, 생성시 그 크기를 정해야 한다는 단점이 있다
//	따라서, 본 예제는 배열로 해결 불가능하고, sun에서는 객체를 모아서 처리하는데에 유용한 api를 제공해주는데
//	이를 가리켜 Collection Framework라 하며, java.util 패키지에서 지원한다
	public void setBg() {
//		  for (int i; i<btn.length; i++) {
//			  btn[i].setBackground(Color.RED);
//		  }
		  for (int i = 0; i<list.size(); i++) {
			  JButton bt = list.get(i);
			  bt.setBackground(Color.RED);
		  }
		}
	public void createButton() {
		JButton bt = new JButton("버튼" + x);
		x++;
		p_center.add(bt); // 배열에 담기
		list.add(bt); // Java Script push()와 동일
		
		// 개발자가 paint()메서드를 재정의한 후 시스템에게 호출하도록 요청할 때는 repaint() 메서드를 호출해야 하지만
		// paint()가 아닌 GUI컴포넌트를 부착한 경우에는 그린 것이 아니므로(paint()를 재정의한 것이 아니므로)
		// repaint()가 아닌 컴포넌트의 갱신을 요청해야 함 -> updateUI();
		SwingUtilities.updateComponentTreeUI(this); // refresh JFrame !!!
	}

	public static void main(String[] args) {
		new Nandayo();
	}
}
