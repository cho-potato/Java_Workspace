package db.diary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class DateCell extends Cell {
	Color color = Color.WHITE;
	DiaryMain diaryMain;

	public DateCell(DiaryMain diaryMain, String title, String content, int fontSize, int x, int y) {
		super(title, content, fontSize, x, y);
		this.diaryMain = diaryMain;

		Border border = new LineBorder(Color.DARK_GRAY);
		setBorder(border);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (color == Color.white) {
					color = Color.YELLOW;
//						콤보박스에 날짜 채워넣기 (콤보박스는 디자인이니까 디자인쪽에서 처리)
					diaryMain.setDateInfo(DateCell.this.title);
				} else {
					color = Color.white;
				}
				repaint();
			}
		});
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, 120, 120);

		// 배경색 적용하기
		g2.setColor(color);
		g2.fillRect(0, 0, 100, 100);

		g2.setColor(Color.DARK_GRAY);
		g2.drawString(title, x, y); // 날짜 그리기
		g2.drawString(content, x - 30, y + 20); // 내용 그리기
	}
}