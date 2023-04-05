package management;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ProjectWorkDayCell extends ProjectWorkCell{
//	DayCell생성시 요일을 넘겨받아 보관해놓자!
//	paintComponent메서드가 그려야 하므로, 변수는 멤버변수로 존재해야 한다.
	
	public ProjectWorkDayCell(String title, String content, int fontSize, int x, int y) {
		super(title, content, fontSize, x, y);
	}
	
//	입력된 정보를 반영하기 위해서 필요
	protected void paintComponent(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, 200, 50);
		
		g2.setColor(Color.WHITE);
		Font font= new Font("고딕", Font.BOLD, 25);
		
		g2.setFont(font);
		g2.drawString(title, 85, 33); // 글씨 그리는 것 
	}
}
