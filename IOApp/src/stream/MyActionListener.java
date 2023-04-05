package stream;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

/* 리스너 클래스를 프레임에 두지 않고 별도의 객체로 분리시켜보자 */

public class MyActionListener implements ActionListener {
	JTextArea area; // null 기존의 것을 가져오는 거니까 new하면 안됨

	public MyActionListener(JTextArea area) { // 생성자로 넘겨받자 (새로 area를 생성하면 안됨)
		this.area = area;
	}
	public void actionPerformed(ActionEvent e) {
		// System.out.println("???\n");
		area.append("???\n");
	}
}
