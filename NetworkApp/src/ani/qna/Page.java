package ani.qna;

import java.awt.Dimension;

import javax.swing.JPanel;

// 앱에 사용할 모든 페이지들의 최상위 클래스

public class Page extends JPanel {
	AppMain appMain; // 모든 페이지는 메인 프레임을 통해 서로 접근할 수 있도록
	
	public Page(AppMain appMain) {
		this.appMain = appMain;
		this.setPreferredSize(new Dimension(800, 700));
	}
}
