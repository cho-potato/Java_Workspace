package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

//	공지사항 내용을 보여줄 페이지

public class NoticePage extends JPanel{
	public NoticePage() {
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));
	}
}
