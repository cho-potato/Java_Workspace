package com.edu.shopclient.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.edu.shopclient.domain.TopCategory;
import com.edu.shopclient.model.repository.TopCategoryDAO;
import com.edu.shopclient.util.DBManager;

public class ClientMain extends JFrame {
	DBManager dbManager = DBManager.getInstance();
	TopCategoryDAO topCategoryDAO = new TopCategoryDAO();

	JPanel p_north; // 메뉴 버튼들이 올 패널
	List<MenuButton> menuList;
//	반복문 대상이 아닌 것들은 직접 작성
	MenuButton m_login;
	MenuButton m_join;
	MenuButton m_cart;

	JPanel container; // 화면전환에 사용될 루트 컨테이너 : 화면 여러 개 부착될
	Page[] pageList = new Page[4];

	public static final int PRODUCTPAGE = 0;
	public static final int LOGINPAGE = 1;
	public static final int JOINPAGE = 2;
	public static final int CARTPAGE = 3;
	
	public ClientMain() {
		p_north = new JPanel();
		menuList = new ArrayList<MenuButton>();
		createMenu(); // 상품 카테고리 수 만큼 버튼 생성

//		로그인, 회원가입, 장바구니		
		m_login = new MenuButton(this, LOGINPAGE, "로그인");
		m_join = new MenuButton(this, JOINPAGE, "회가입원");
		m_cart = new MenuButton(this, CARTPAGE, "카트트트트ㅡㅌㄹ");
		p_north.add(m_login);
		p_north.add(m_join);
		p_north.add(m_cart);

//		모든 페이지들을 품고 있을 패널
		container = new JPanel();
		container.setBackground(Color.BLACK);

//		페이지 생성하여 붙여넣기
		pageList[0] = new ProductPage();
		pageList[1] = new LoginPage();
		pageList[2] = new JoinPage();
		pageList[3] = new CartPage();
		for (int i = 0; i < pageList.length; i++) {
			container.add(pageList[i]);
		}
		add(p_north, BorderLayout.NORTH);
		add(container);

		setSize(900, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});

//		디폴트로 보여주고 싶은 페이지 지정
		showHide(0); // 상품 페이지
	}

	public void createMenu() {
//		String[] menuName = { "상dml", "하dml", "액tptjfl", "신qkf" }; // DB연동하여 가져올 예정
		List<TopCategory> topList = topCategoryDAO.selectAll();
		
		for (int i = 0; i < topList.size(); i++) {
			TopCategory topCategory = topList.get(i);
			MenuButton menu = new MenuButton(this, PRODUCTPAGE, topCategory.getTopcategory_name());
			p_north.add(menu); // 북쪽 패널에 부착
		}

	}

//	보여질 페이지와 가려질 페이지를 처리하여 화면전환 효과
//	n은 보여질 index를 결정 예) 2를 넘기면 3번째 페이지 보여짐

	public void showHide(int n) {
		for (int i = 0; i < pageList.length; i++) {
			if (i == n) { // n과 i가 같을 때 보여짐
				pageList[i].setVisible(true);
			} else {
				pageList[i].setVisible(false);
			}
		}
	}

	public static void main(String[] args) {
		new ClientMain();
	}

}
