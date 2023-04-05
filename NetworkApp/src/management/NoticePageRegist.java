package management;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ani.qna.AppMain;
import ani.qna.QnAPage;
import ani.qna.ReBoard;

public class NoticePageRegist extends ProjectInnerPage {
	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	JButton bt_regist;
	JButton bt_list;

	ProjectMainPage projectMainPage;
	ProjectNoticeBoardDAO projectNoticeBoardDAO;
	
	public NoticePageRegist(ProjectMainPage projectMainPage) {
		super(projectMainPage);
		this.projectMainPage = projectMainPage;
		projectNoticeBoardDAO = projectMainPage.projectNoticeBoardDAO;
		
		t_title = new JTextField();
		t_title.setPreferredSize(new Dimension(1800, 50));
		t_writer = new JTextField();
		t_writer.setPreferredSize(new Dimension(1800, 50));
		t_detail = new JTextField();
		t_detail.setPreferredSize(new Dimension(1800, 500));

		bt_regist = new JButton("등록");
		bt_regist.setPreferredSize(new Dimension(150, 40));
		bt_regist.setBackground(Color.RED);
		bt_regist.setForeground(Color.WHITE);
		bt_regist.setFont(new Font("고딕", Font.BOLD, 15));
		bt_regist.setBorderPainted(false);
		bt_regist.setFocusPainted(false);
		
		bt_list = new JButton("목록");
		bt_list.setPreferredSize(new Dimension(150, 40));
		bt_list.setBackground(Color.RED);
		bt_list.setForeground(Color.WHITE);
		bt_list.setFont(new Font("고딕", Font.BOLD, 15));
		bt_list.setBorderPainted(false);
		bt_list.setFocusPainted(false);
	
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
				InnerNoticePage innerNoticePage = (InnerNoticePage)projectMainPage.projectInnerPage[ProjectMainPage.INNERNOTICEPAGE];
				innerNoticePage.getList(); // DB갱신
				
				projectMainPage.mainPageShowHide(projectMainPage.INNERNOTICEPAGE);; // 화면전환
			}
		});
	}
	public void regist() {
		ProjectNoticeBoardDTO projectNoticeBoardDTO = new ProjectNoticeBoardDTO(); // Empty DTO;
		projectNoticeBoardDTO.setTitle(t_title.getText());
		projectNoticeBoardDTO.setWriter(t_writer.getText());
		projectNoticeBoardDTO.setContent(t_detail.getText());
		
		int result = projectNoticeBoardDAO.insert(projectNoticeBoardDTO); // DTO
		System.out.println("result" + result);
		if(result > 0) {
			JOptionPane.showMessageDialog(projectMainPage, "원글 등록 성공");
			// 목록 갱신
			InnerNoticePage innerNoticePage = (InnerNoticePage) projectMainPage.projectInnerPage[projectMainPage.INNERNOTICEPAGE];
			innerNoticePage.getList();
			// 목록 페이지 보여주기
			projectMainPage.mainPageShowHide(projectMainPage.INNERNOTICEPAGE);
		}
	}
}

