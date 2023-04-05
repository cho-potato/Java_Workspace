package management;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ani.qna.AppMain;
import ani.qna.QnAPage;

public class NoticePageDetail extends ProjectInnerPage {
//	상세보기
	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	JButton bt_edit; // 수정
	JButton bt_del; // 삭제
	JButton bt_list; // 목록

	ProjectNoticeBoardDAO projectNoticeBoardDAO;
	ProjectNoticeBoardDTO projectNoticeBoardDTO;

	public NoticePageDetail(ProjectMainPage projectMainPage) {
		super(projectMainPage);
		projectNoticeBoardDAO = projectMainPage.projectNoticeBoardDAO;

		t_title = new JTextField();
		t_title.setPreferredSize(new Dimension(1800, 30));
		t_writer = new JTextField();
		t_writer.setPreferredSize(new Dimension(1800, 30));
		t_detail = new JTextField();
		t_detail.setPreferredSize(new Dimension(1800, 150));

		bt_edit = new JButton("수정");
		bt_edit.setPreferredSize(new Dimension(150, 40));
		bt_edit.setBackground(Color.RED);
		bt_edit.setForeground(Color.WHITE);
		bt_edit.setFont(new Font("고딕", Font.BOLD, 15));
		bt_edit.setBorderPainted(false);
		bt_edit.setFocusPainted(false);

		bt_del = new JButton("삭제");
		bt_del.setPreferredSize(new Dimension(150, 40));
		bt_del.setBackground(Color.RED);
		bt_del.setForeground(Color.WHITE);
		bt_del.setFont(new Font("고딕", Font.BOLD, 15));
		bt_del.setBorderPainted(false);
		bt_del.setFocusPainted(false);

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

		add(bt_edit);
		add(bt_del);
		add(bt_list);

		// bt_edit
		bt_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(projectMainPage, "수정하시겠습니까?") == JOptionPane.OK_OPTION) {
					// 보고있던 DTO를 변경 InnerNoticePage innerNoticePage
					projectNoticeBoardDTO.setTitle(t_title.getText()); // 수정한 값을 다시 dto로 넣어줘야지...
					projectNoticeBoardDTO.setWriter(t_writer.getText());
					projectNoticeBoardDTO.setContent(t_detail.getText());

					int result = projectMainPage.projectNoticeBoardDAO.update(projectNoticeBoardDTO);

					if (result > 0) {
						JOptionPane.showMessageDialog(projectMainPage, "수정완료");
						// 목록 다시 갱신...
						InnerNoticePage innerNoticePage = (InnerNoticePage) projectMainPage.projectInnerPage[projectMainPage.INNERNOTICEPAGE];;
						innerNoticePage.getList();

						// 목록 페이지 보여주기
						projectMainPage.mainPageShowHide(projectMainPage.INNERNOTICEPAGE);
					}
				}
			}
		});

		// bt_delete
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(projectMainPage, "삭제하시겠습니까?") == JOptionPane.OK_OPTION) {

					int result = projectMainPage.projectNoticeBoardDAO.delete(projectNoticeBoardDTO.getProjectNoticeBoard_idx());

					if (result > 0) { // 삭제 성공
						JOptionPane.showMessageDialog(projectMainPage, "삭제완료");

						// 목록 갱신
						InnerNoticePage innerNoticePage = (InnerNoticePage) projectMainPage.projectInnerPage[projectMainPage.INNERNOTICEPAGE];
						innerNoticePage.getList();

						// 목록 페이지 보여주기
						projectMainPage.mainPageShowHide(projectMainPage.INNERNOTICEPAGE);
					}
				}
			}
		});

		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				데이터베이스 목록 가져오기 + 화면 전환
				InnerNoticePage innerNoticePage = (InnerNoticePage) projectMainPage.projectInnerPage[ProjectMainPage.INNERNOTICEPAGE];
				innerNoticePage.getList(); // DB갱신

//				화면전환
				projectMainPage.mainPageShowHide(projectMainPage.INNERNOTICEPAGE);
			}
		});
	}

//	한 건 가져오기
	public void getDetail(int projectNoticeBoard_idx) {
		System.out.println(projectNoticeBoard_idx);
		projectNoticeBoardDTO = projectMainPage.projectNoticeBoardDAO.select(projectNoticeBoard_idx); // DAO로부터 레코드를

//		화면에 출력
		t_title.setText(projectNoticeBoardDTO.getTitle());
		t_writer.setText(projectNoticeBoardDTO.getWriter());
		t_detail.setText(projectNoticeBoardDTO.getContent());
	}
}
