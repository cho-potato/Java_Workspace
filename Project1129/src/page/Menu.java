package page;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// 어플리케이션에서 사용할 네비게이션 메뉴를 클래스로 정의
// JLabel을 커스터마이징하자
public class Menu extends JLabel {
	int index;
	AppMain appMain; // AppMain을 제어할 것이기 때문에 주소값을 받아오고자 선언

	public Menu(AppMain appMain, ImageIcon icon, int width, int height, int index) {
		this.appMain = appMain;
		this.setIcon(icon); // 라벨에 사용할 아이콘 // this 생략가능
		this.setPreferredSize(new Dimension(width, height)); // 라벨의 크기
		this.index = index;
//		System.out.println();

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				System.out.println("저는"+index+"메뉴에요");
				appMain.checkLogin(index);
//				appMain.showHide(index);
			}
		});
	}
}
