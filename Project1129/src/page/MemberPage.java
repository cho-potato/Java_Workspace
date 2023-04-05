package page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.StringUtil;

//	회원가입 내용을 보여줄 페이지

public class MemberPage extends JPanel {
	JPanel p_form;
	JLabel la_id, la_pass, la_email;
	JTextField t_id;
	JPasswordField t_pass;
	JTextField t_email;
	JButton bt_regist;

	AppMain appMain;
	MailSender mailSender;

	public MemberPage(AppMain appMain) {
		this.appMain = appMain;
		mailSender = new MailSender();
		this.setBackground(Color.GREEN);
		this.setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));

		p_form = new JPanel();
		la_id = new JLabel("ID");
		la_pass = new JLabel("PASSWORD");
		la_email = new JLabel("E-MAIL");
		t_id = new JTextField();
		t_pass = new JPasswordField();
		t_email = new JTextField();
		bt_regist = new JButton("회원가입");

		p_form.setPreferredSize(new Dimension(500, 150));

		Dimension d = new Dimension(230, 25);
		la_id.setPreferredSize(d);
		t_id.setPreferredSize(d);
		la_pass.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		la_email.setPreferredSize(d);
		t_email.setPreferredSize(d);

		p_form.add(la_id);
		p_form.add(t_id);
		p_form.add(la_pass);
		p_form.add(t_pass);
		p_form.add(la_email);
		p_form.add(t_email);
		p_form.add(bt_regist);

		add(p_form);

		bt_regist.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int result = regist();
				if (result > 0) {
					JOptionPane.showConfirmDialog(MemberPage.this, "등록 성공");
//					이메일 발송(유저가 존재하는 메일을 넣었어야 함)
					mailSender.sendMail(t_email.getText());
				}
			}
		});
	}

	public int regist() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		? : 바인드 변수 처리하고 싶을 때 사용하는 기호
		String sql = "insert into member(member_idx, id, pass, email)";
		sql += " values(seq_member.nextval, ?, ?, ?)"; // 첫번째 두번째 세번째 물음표
		int result = 0;
		try {
			pstmt = appMain.conn.prepareStatement(sql);
//			물음표의 값을 먼저 지정해 준 후 쿼리를 수행
			pstmt.setString(1, t_id.getText()); // id
			pstmt.setString(2, StringUtil.getConvetredPassword(new String(t_pass.getPassword()))); // pass
			pstmt.setString(3, t_email.getText()); // email

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			appMain.release(pstmt);
		}
		return result;
	}
}
