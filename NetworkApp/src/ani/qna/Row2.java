package ani.qna;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

// 페이징 처리를 위해 JTable을 이용하지 않고 손수 Row를 재정의하여 사용해보자
public class Row2 extends JPanel {
	int no; // 게시물 순번
	ReBoard reboard;

	public Row2(int no, ReBoard reboard) {
		this.reboard = reboard;

//		테두리를 영어로 : Border
		Border border = new LineBorder(Color.GRAY);
		this.setPreferredSize(new Dimension(750, 40));
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
//		순번, 제목, 작성자, 등록일, 조회수
		g2.drawString(Integer.toString(no), 5, 10); // 순번
		g2.drawString(reboard.getTitle(), 100, 10); // 제목
		g2.drawString(reboard.getWriter(), 300, 10); // 작성자
		g2.drawString(reboard.getRegdate(), 500, 10); // 등록일
		g2.drawString(Integer.toString(reboard.getHit()), 700, 10); // 조회수
	}

}
