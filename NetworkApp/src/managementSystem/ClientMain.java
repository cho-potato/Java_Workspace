package managementSystem;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import network.multi.katalk.ChatPage;
import network.multi.katalk.JoinPage;
import network.multi.katalk.LoginPage;

public class ClientMain extends JFrame {
	JPanel container; // 최상위 패널
	
//	사용할 페이지 생성
	Page[] page = new Page[2];
	public static final int LOGINPAGE = 0;
	public static final int MAINPAGE = 1;
//	public static final int MASTERPAGE = 2;
//	public static final int RECEIVEPAGE = 3;
	
	public ClientMain() {
		container = new JPanel();
		
//		page[0] = new LoginPage(this);
		page[1] = new MainPage(this);
//		page[1] = new MasterPage(this);
//		page[2] = new ReceivePage(this);
		
		this.setBackground(Color.BLACK);
		
		 for (int i = 0; i<page.length; i++) {
			 container.add(page[i]); // 루트 컨테이너에 페이지들 부착
		 }
		 
		add(container);
		
//		페이지
		showHide(LOGINPAGE);
		
		setSize(1920,  1080);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	public void showHide(int n) {
		for (int i = 0; i < page.length; i++) {
			if (n == i) {
				page[i].setVisible(true);
			} else {
				page[i].setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new ClientMain();
	}
}
