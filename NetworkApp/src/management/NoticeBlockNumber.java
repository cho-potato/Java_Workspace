package management;

import java.awt.Font;

import javax.swing.JLabel;

import ani.qna.QnAPage;

public class NoticeBlockNumber extends JLabel {
	String n;
	InnerNoticePage innerNoticePage;
	public NoticeBlockNumber(String n, InnerNoticePage innerNoticePage) {
		this.n = n;
		this.innerNoticePage = innerNoticePage;
		this.setFont(new Font("Verdana", Font.BOLD, 30));
	}
}

	

	
