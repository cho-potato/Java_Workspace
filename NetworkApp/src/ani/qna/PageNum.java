package ani.qna;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

// 페이징 처리시 한 페이지를 표현하는 라벨(클릭 가능하게+해당 페이지 번호 보유)

public class PageNum extends JLabel {
	String n;
	QnAPage qnaPage;

	public PageNum(String n, QnAPage qnaPage) {
		super(n);
		this.n = n;
		this.qnaPage = qnaPage;

		this.setFont(new Font("Arial black", Font.BOLD, 30));

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				현재 페이지를 눌렀을 때 화면갱신, PagingManager의 correntPage 값을 현재 라벨로 대체
				System.out.println("현재 당신이 보려는 페이지는 " + n + "입니다");
				qnaPage.pagingManager.setCurrentPage(Integer.parseInt(n));
				
				qnaPage.getList(); // 화면 갱신
				qnaPage.activePage(n);
			}
		});
	}
}
