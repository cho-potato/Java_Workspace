package management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.StringUtil;

public class ProjectLoginPage extends ProjectPage {
	JPanel border;
	JPanel border_north, border_center, border_south;
	JPanel innerP;
	JPanel innerP_center, innerP_west, innerP_east;

	JLabel la_ment1, la_ment2, la_ment3, la_ment4, la_id, la_pass;
	JTextField idField;
	JPasswordField passField;
	JButton bt_westJoin, bt_eastLogin;
	ImageIcon idIcon, passIcon, joinImage, loginImage;

	ProjectMemberDAO projectMemberDAO;
	ProjectDateCheck projectDateCheck;

	public ProjectLoginPage(ProjectClientMain projectClientMain) {
		super(projectClientMain);
		projectMemberDAO = new ProjectOracleDAO();
		projectDateCheck = new ProjectDateCheck();
		
//		Memory
		border = new JPanel();
		border_north = new JPanel();
		border_center = new JPanel();
		border_south = new JPanel();

		innerP = new JPanel();

		innerP_center = new JPanel();
		innerP_west = new JPanel();
		innerP_east = new JPanel();

		la_ment1 = new JLabel("감자맛커피 영업정보시스템입니다. ");
		la_ment2 = new JLabel("아이디와 비밀번호를 입력해주세요.");
		la_ment3 = new JLabel("　　　　　　　　　　　　　　");
		la_ment4 = new JLabel("COPYRIGHT 2022 POTATO F&B Co., Ltd. All Rights Reserved");

		idIcon = new ImageIcon("src/res/image/user.png");
		la_id = new JLabel(idIcon);
		idField = new JTextField(20);

		passIcon = new ImageIcon("src/res/image/lock.png");
		la_pass = new JLabel(passIcon);
		passField = new JPasswordField(20);

		joinImage = new ImageIcon("src/res/image/westJoin.png");
		bt_westJoin = new JButton(joinImage);

		loginImage = new ImageIcon("src/res/image/eastLogin.jpg");
		bt_eastLogin = new JButton(loginImage);

//		add + style
//		Layout
		add(border);
		border.setLayout(new BorderLayout());

		border.add(border_north, BorderLayout.NORTH);
		border_north.setPreferredSize(new Dimension(1920, 360));
		border_north.setBackground(Color.LIGHT_GRAY);
		border.add(border_center);
		border_center.setPreferredSize(new Dimension(1920, 360));
		border_center.setBackground(Color.LIGHT_GRAY);
		border.add(border_south, BorderLayout.SOUTH);
		border_south.setPreferredSize(new Dimension(1920, 360));
		border_south.setBackground(Color.LIGHT_GRAY);

		border_center.add(innerP);

		innerP.setLayout(new BorderLayout());
		innerP.setPreferredSize(new Dimension(1100, 360));
		innerP.setBackground(Color.RED);

		innerP.add(innerP_west, BorderLayout.WEST);
		innerP_west.setPreferredSize(new Dimension(300, 360));
		innerP_west.setBackground(Color.RED);
		innerP.add(innerP_center);
		innerP_center.setPreferredSize(new Dimension(600, 300));
		innerP_center.setBackground(Color.RED);
		innerP.add(innerP_east, BorderLayout.EAST);
		innerP_east.setPreferredSize(new Dimension(175, 300));
		innerP_east.setBackground(Color.RED);

		innerP_center.setLayout(new FlowLayout());

		innerP_center.add(la_ment1);
		la_ment1.setPreferredSize(new Dimension(500, 50));
		la_ment1.setFont(new Font("고딕", Font.BOLD, 20));
		la_ment1.setForeground(Color.WHITE);
		la_ment1.setHorizontalAlignment(la_ment1.CENTER);

		innerP_center.add(la_ment2);
		la_ment2.setPreferredSize(new Dimension(500, 50));
		la_ment2.setFont(new Font("고딕", Font.BOLD, 20));
		la_ment2.setForeground(Color.WHITE);
		la_ment2.setHorizontalAlignment(la_ment2.CENTER);

		innerP_center.add(la_ment3);
		la_ment3.setPreferredSize(new Dimension(600, 15));

		innerP_center.add(la_id);
		la_id.setPreferredSize(new Dimension(50, 50));

		innerP_center.add(idField);
		idField.setPreferredSize(new Dimension(50, 20));

		innerP_center.add(la_pass);
		la_pass.setPreferredSize(new Dimension(50, 50));

		innerP_center.add(passField);
		passField.setPreferredSize(new Dimension(50, 20));

		innerP_center.add(la_ment4);
		la_ment4.setPreferredSize(new Dimension(500, 150));
		la_ment4.setForeground(Color.WHITE);
		la_ment4.setHorizontalAlignment(la_ment4.CENTER);

		innerP_west.add(bt_westJoin);
		bt_westJoin.setPreferredSize(new Dimension(300, 355));
		bt_westJoin.setBorderPainted(false);

		innerP_east.add(bt_eastLogin);
//		bt_eastLogin.setBounds(60, 60, 175, 175);
		bt_eastLogin.setPreferredSize(new Dimension(175, 310));
		bt_eastLogin.setBorderPainted(false);

//		가입버튼
		bt_westJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectClientMain.projectShowHide(ProjectClientMain.PROJECTJOINPAGE);
			}
		});
//		로그인버튼
		bt_eastLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberCheck();
			}
		});
	}

	public void memberCheck() {
		ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO();
		projectMemberDTO.setId(idField.getText());
		projectMemberDTO.setPass(StringUtil.getConvertedPassword(new String(passField.getPassword())));

		projectMemberDTO = projectMemberDAO.select(projectMemberDTO);
		if (projectMemberDTO == null) {
			JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호를 확인해주세요");
		} else {
			JOptionPane.showMessageDialog(this, projectMemberDTO.getId() + " 님 " + projectDateCheck.formatedNow + " 에 로그인 하셨습니다");

			projectClientMain.projectMemberDTO = projectMemberDTO;
			projectClientMain.projectShowHide(ProjectClientMain.PROJECTMAINPAGE);
			ProjectMainPage projectMainPage = (ProjectMainPage) projectClientMain.projectPage[ProjectClientMain.PROJECTMAINPAGE];
		}
	}
}
