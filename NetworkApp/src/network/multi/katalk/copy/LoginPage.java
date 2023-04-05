package network.multi.katalk.copy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import network.domain.ChatMember;

public class LoginPage extends Page{
	JLabel la_title;
	JTextField t_id;
	JTextField t_pass;
	JButton bt_login;
	JButton bt_join;
	ChatMemberDAO chatMemberDAO;

	public LoginPage(ClientMain clientMain) {
		super(clientMain);
		chatMemberDAO = new OracleChatMemberDAO();
		
		la_title = new JLabel("KaKaoTalk");
		t_id = new JTextField();
		t_pass = new JTextField();
		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");
		
		la_title.setPreferredSize(new Dimension(350, 200));
		la_title.setFont(new Font("Verdana", Font.BOLD, 45));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);
		
		Dimension d = new Dimension(350, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		
		add(la_title);
		add(t_id);
		add(t_pass);
		
		add(bt_login);
		add(bt_join);

		setBackground(Color.YELLOW);
		
		//로그인버
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
			}
		});
		
//		회원가입 버튼과 리스너 연결
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientMain.showHide(ClientMain.JOINPAGE);
			}
		});
	}
	public void loginCheck() {
		//id, pass를 하나의 DTO에 담아서 전달하기
		ChatMember chatMember=new ChatMember();//empty 상태
		chatMember.setId(t_id.getText());
		chatMember.setPass(t_pass.getText());
		
		chatMember = chatMemberDAO.select(chatMember);//위에서 다 써서 덮어씀
		if(chatMember==null) {
			JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다");
		}else {
			JOptionPane.showMessageDialog(this, "로그인 성공");
			//ClientMain에 사용자 정보 보관해두기!
			clientMain.chatMember=chatMember;
			//채팅창 보여주기
			clientMain.showHide(ClientMain.CHATPAGE);
			ChatPage chatPage=(ChatPage)clientMain.page[ClientMain.CHATPAGE];
			chatPage.connect();
		}
	}
}