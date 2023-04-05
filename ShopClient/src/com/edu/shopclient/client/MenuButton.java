package com.edu.shopclient.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

// 쇼핑몰의 메뉴 기능을 담당할 버튼을 커스텀

public class MenuButton extends JButton implements ActionListener{
	ClientMain clientMain;
	String title;
	int pageNum;
	public MenuButton(ClientMain clientMain, int pageNum, String title) {
		super(title); // 버튼 제목 설정
		this.clientMain = clientMain;
		this.pageNum = pageNum;
		this.title = title;
		this.addActionListener(this); // 버튼과 리스너 연결
		
	}

	public void actionPerformed(ActionEvent e) {
//		System.out.println("???");
		clientMain.showHide(pageNum);
		
//		해당 상품에 알맞는 쿼리요청 : 단, 상품 관련 메뉴에만 적용
		if (pageNum == ClientMain.PRODUCTPAGE) {
//			ProductPage의 selectAll()을 호출하되 title을 넘겨야 '상의', '하의' ... 텍스트가 전달된다
//			ProductPage는 여기에 없다. ClientMain에 있다. 따라서 ClientMain을 통해 접근해야 한다
//			형변환 하는 이유 : page 자식인 PRODUCTPAGE에 있으므로
			ProductPage productPage = (ProductPage)clientMain.pageList[0]; 
			productPage.getProductList(title);
//			패널 새로고침
			clientMain.container.updateUI();
		}
		
	}
}
