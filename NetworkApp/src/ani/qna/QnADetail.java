package ani.qna;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// 글 등록 폼

public class QnADetail extends Page {
//	상세보기
	JTextField t_title;
	JTextField t_writer;
	JTextField t_detail;
	JButton bt_edit; // 수정
	JButton bt_del; // 삭제
	JButton bt_list; // 목록
	JButton bt_replyform; // 답변폼 보이기

//	답변달기
	JPanel p_reply; // 답변폼을 보였다안보였다 처리하기 위함
	JTextField t_title2;
	JTextField t_writer2;
	JTextField t_detail2;
	JButton bt_reply; // 답변 등록 버튼
	JButton bt_cancel; // 답변 취소 버튼

	ReBoardDAO reboardDAO;
	ReBoard reboard; // 현재 보고 있는 글을 담는 DTO

	public QnADetail(AppMain appMain) {
		super(appMain);
		reboardDAO = appMain.reBoardDAO;

		t_title = new JTextField();
		t_writer = new JTextField();
		t_detail = new JTextField();

		p_reply = new JPanel();
		t_title2 = new JTextField();
		t_writer2 = new JTextField();
		t_detail2 = new JTextField();

		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");
		bt_list = new JButton("목록");
		bt_replyform = new JButton("답변하기");

		bt_reply = new JButton("답변등록");
		bt_cancel = new JButton("취소");

		// style
		t_title.setPreferredSize(new Dimension(750, 30));
		t_writer.setPreferredSize(new Dimension(750, 30));
		t_detail.setPreferredSize(new Dimension(750, 150));

		p_reply.setPreferredSize(new Dimension(750, 300));
		t_title2.setPreferredSize(new Dimension(750, 30));
		t_writer2.setPreferredSize(new Dimension(750, 30));
		t_detail2.setPreferredSize(new Dimension(750, 150));

		// add
		add(t_title);
		add(t_writer);
		add(t_detail);

		add(bt_edit);
		add(bt_del);
		add(bt_list);
		add(bt_replyform);

		add(p_reply);

		p_reply.add(t_title2);
		p_reply.add(t_writer2);
		p_reply.add(t_detail2);

		p_reply.add(bt_reply);
		p_reply.add(bt_cancel);

		p_reply.setVisible(false); // 답변 폼 숨기기

//		답변 폼 보이기
		bt_replyform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p_reply.setVisible(true);
			}
		});
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				데이터베이스 목록 가져오기 + 화면 전환
				QnAPage qnaPage = (QnAPage) appMain.page[AppMain.QNAPAGE];
				qnaPage.getList(); // DB갱신

				appMain.showHide(AppMain.QNAPAGE); // 화면전환
			}
		});
//		답변 등록
		bt_reply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reply();
			}
		});
	}

//	한 건 가져오기
	public void getDetail(int reboard_idx) {
		System.out.println(reboard_idx);
		reboard = appMain.reBoardDAO.select(reboard_idx); // DAO로부터 레코드를

//		화면에 출력
		t_title.setText(reboard.getTitle());
		t_writer.setText(reboard.getWriter());
		t_detail.setText(reboard.getContent());
	}

//	현재 읽은 글에 대한 답변 등록하기
	public void reply() {
		int maxStep = appMain.reBoardDAO.selectMaxStep(reboard);
		if (maxStep > 0) {
			reboard.setStep(maxStep);
			appMain.reBoardDAO.updateStep(reboard); // 기득권 세력 물러나게 하기
		}
		ReBoard dto = new ReBoard(); // 답변을 채울 DTO
		dto.setTitle(t_title2.getText()); // 답변 제목
		dto.setWriter(t_writer2.getText()); // 답변자 이름
		dto.setContent(t_detail2.getText()); // 답변 내용
		dto.setTeam(reboard.getTeam()); // 내가 본 글의 team
		dto.setStep(maxStep); // 답변에 사용할 step
		dto.setDepth(reboard.getDepth()); // 내가 본 글의 depth
		
		int result = appMain.reBoardDAO.reply(dto); // 답변 등록
		if (result > 0) {
			JOptionPane.showMessageDialog(appMain, "답변등록");
		}
	}
}
