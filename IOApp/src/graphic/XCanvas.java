package graphic;

import java.awt.Graphics;

import javax.swing.JPanel;

// 패널을 상속받아 개발자 주도 하에 그림을 그리자

public class XCanvas extends JPanel {
	int x = 50;
	int y = 50;
	
	// 개발자가 원하는 그래픽처리를 코드작성해놓으면, 시스템이 적절한 시점에 아래의 메서드를 호줄해준다
	// update() -> paint()
	// 개발자가 repaint() 호출 -> update() -> paint()
	public void paint(Graphics g) {
		g.clearRect(0, 0, 600, 470); // Clears the specified rectangle by filling it with the background color of the current drawing surface.
		g.drawOval(x, y, 50, 50);
	}
}
