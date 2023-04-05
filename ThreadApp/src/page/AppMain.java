package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ImageManager;

public class AppMain extends JFrame {
	JPanel p_north; // 네비게이션 버튼을 올려놓을 북쪽 패널
//	JButton[] menu = new JButton[4];  // Java에서의 배열은 크기를 명시
	Menu[] menu = new Menu[4]; // JLabel[] menu = new JLabel[4];
	ImageManager imageManager;
	String[] path = { "res/app/introduce.png", "res/app/login.png", "res/app/member.png", "res/app/notice.png" };
	JPanel p_center; // 사용할 페이지들이 공존할 수 있도록 FLowLayout을 적용할 영역 -> CENTER에 부착할 예정

//	사용할 페이지들 
//	CompanyPage companyPage; 
//	MemberPage memberPage; 
//	LoginPage loginPage; 
//	NoticePage noticePage;
	
//	규칙을 만들면 활용도가 높으므로, 모든 페이지들을 상위 자료형인(extends한) JPanel로 묶는다
	JPanel[] pageList = new JPanel[4];

//	모든 페이지의 너비와 높이의 기준을 정한 상수를 정의
	public static final int PAGE_WIDTH = 800;
	public static final int PAGE_HEIGHT = 400;

	public AppMain() {
		p_north = new JPanel();
		imageManager = new ImageManager();

		p_north.setBackground(Color.RED);

//		아이콘이 있는 버튼들을 만들자
		for (int i = 0; i < menu.length; i++) {
//			menu[i] = new JButton(getIcon("res/app/introduce.png")); // 테두리 있는, 마우스 클릭 효과 X 
//			menu[i] = new JLabel(getIcon("res/app/introduce.png")); // 이미지만 출력, 마우스 클릭 효과 X
			menu[i] = new Menu(this, imageManager.getIcon(path[i], 50, 50), 50, 50, i); // menu[i] = new JLabel(imageManager.getIcon(path[i], 50, 50)); 
			p_north.add(menu[i]);
//			내부 익명 클래스는 외부 클래스의 멤버변수를 자기것처럼 쓸 수 있다는 장점이 있으나, 메서드의 지역변수는 쓰지 못하고 읽기만 가능하다(final로 선언된 변수)

//			menu[i].addMouseListener(new MouseAdapter() {
//				public void mouseClicked(MouseEvent e) {
//					System.out.println(i+"???");
//				}
//			});
		}

//		센터에 붙을 패널 생성
		p_center = new JPanel();
		p_center.setBackground(Color.GRAY);

//		네 장의 페이지를 미리 화면에 생성하여 붙이기
		pageList[0] = new CompanyPage(); // companyPage = new CompanyPage();
		pageList[1] = new MemberPage(); // memberPage = new MemberPage();
		pageList[2] = new LoginPage(); // loginPage = new LoginPage();
		pageList[3] = new NoticePage(); // noticePage = new NoticePage();

//		생성된 페이지들을 p_center에 붙이기
//		 p_center.add(companyPage);
//		 p_center.add(memberPage);
//		 p_center.add(loginPage);
//		 p_center.add(noticePage);

		for(int i = 0; i<pageList.length; i++) {
			p_center.add(pageList[i]);			
		}

		add(p_center);
		add(p_north, BorderLayout.NORTH);

		setSize(800, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		showHide(0);
	}

//	메뉴를 눌렀을 때 해당 페이지 보여주기 
//	showHide(2);
	public void showHide(int currentPage) {
		for (int i = 0; i<pageList.length; i++) {
//			pageList[i].setVisible(true);
			if (currentPage==i) {
				pageList[i].setVisible(true);
			} else {
				pageList[i].setVisible(false);
			}
		}
/*
		companyPage.setVisible(false);
		memberPage.setVisible(true);
		loginPage.setVisible(false);
		noticePage.setVisible(false);
*/
	}

	public static void main(String[] args) {
		new AppMain();
	}
}
