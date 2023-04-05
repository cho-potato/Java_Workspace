package graphic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AniCircle extends JFrame implements ActionListener{
	JButton bt; 
	XCanvas p_center;
	 
	public AniCircle() {
		bt = new JButton("움직이기");
		p_center = new XCanvas();
		
		add(bt, BorderLayout.NORTH);
		add(p_center);
		
		
//		setSize(600, 500);
		setBounds(500, 200, 600, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		bt.addActionListener(this);
	}
	public void move () {
		// 1) 패널이 가진 멤버변수 x,y를 증가시키자
		p_center.x += 20;
		p_center.y += 20;
		// 2) 다시 그리자
		p_center.repaint(); // 잔상이 남게 되는데, 그래픽 처리과정 중에서 페인트 통을 붓고 새로 칠하고 다시 생성해야 함
		
	}
	public void actionPerformed(ActionEvent e) {
		move();
	}
	public static void main(String[] args) {
		new AniCircle();
	}

}
