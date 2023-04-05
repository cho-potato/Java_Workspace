package ani.qna;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// 글 등록 폼

public class QnARegist extends Page {
	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	JButton bt_regist;
	JButton bt_list;

	AppMain appMain;

	ReBoardDAO reboardDAO;
	
	public QnARegist(AppMain appMain) {
		super(appMain);
		this.appMain = appMain;
		reboardDAO = appMain.reBoardDAO;
		
		t_title = new JTextField();
		t_writer = new JTextField();
		t_detail = new JTextField();

		bt_regist = new JButton("등록");
		bt_list = new JButton("목록");
		// this.setBackground(Color.GREEN);

		// style
		t_title.setPreferredSize(new Dimension(750, 30));
		t_writer.setPreferredSize(new Dimension(750, 30));
		t_detail.setPreferredSize(new Dimension(750, 450));

//		add
		add(t_title);
		add(t_writer);
		add(t_detail);
		add(bt_regist);
		add(bt_list);

//		등록 버튼과 리스너 연결
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
		bt_list.addActionListener(new  ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				데이터베이스 목록 가져오기 + 화면 전환
				QnAPage qnaPage = (QnAPage)appMain.page[AppMain.QNAPAGE];
				qnaPage.getList(); // DB갱신
				
				appMain.showHide(AppMain.QNAPAGE); // 화면전환
			}
		});
	}
	public void regist() {
		ReBoard reboard = new ReBoard(); // Empty DTO;
		reboard.setTitle(t_title.getText());
		reboard.setWriter(t_writer.getText());
		reboard.setContent(t_detail.getText());
		
		int result = reboardDAO.insert(reboard); // DTO
		System.out.println("result" + result);
		if(result > 0) {
			JOptionPane.showMessageDialog(appMain, "원글 등록 성공");
		}
	}
}
