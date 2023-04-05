package managementSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage extends Page{
	JPanel border;
	JPanel p_north, p_center, p_south;
	JPanel p;
	JPanel northPanel, cenPanel, southPanel, eastPanel, westPanel;
	
	JLabel la_ment1, la_ment2, la_ment3, la_ment4, la_id, la_pass;
	JTextField idField;
	JPasswordField passField;
	JButton bt_west, bt_login;
	ImageIcon id_icon, pass_icon, west_image, login_image;
	
	ClientMain clientMain;
	
	public LoginPage(ClientMain clientMain) {
		super(clientMain);
		
		Dimension d1 = new Dimension(640, 360);
		Dimension d2 = new Dimension(175, 175);
		Dimension d3 = new Dimension(50, 20);
		Dimension d4 = new Dimension(50, 50);
		Dimension d5 = new Dimension(300, 300);
		
		border = new JPanel();
		p_north = new  JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		
		p = new JPanel();
		northPanel = new JPanel();
		cenPanel = new JPanel();
		southPanel = new JPanel();
		eastPanel = new JPanel();
		westPanel = new JPanel();
		
		la_ment1 = new JLabel("감자맛커피 영업정보시스템입니다. ");
		la_ment2 = new JLabel("아이디와 비밀번호를 입력해주세요.");
		la_ment3 = new JLabel("　　　　　　　　　　　　　　");
		la_ment4 = new JLabel("COPYRIGHT 2022 POTATO F&B Co., Ltd. All Rights Reserved");
		
		id_icon = new ImageIcon("src/res/image/user.png");
		la_id = new JLabel(id_icon);
		idField = new JTextField(20);
		
		pass_icon = new ImageIcon("src/res/image/lock.png");
		la_pass = new JLabel(pass_icon);
		passField = new JPasswordField(20);
		
		west_image = new ImageIcon("src/res/image/west.jpg");
		bt_west = new JButton(west_image);
		login_image = new ImageIcon("src/res/image/loginButton.png");
		bt_login = new JButton(login_image);
		
//		style
		p_north.setPreferredSize(d1);
		p_north.setBackground(Color.LIGHT_GRAY);
		
		p_center.setPreferredSize(new Dimension(1920, 360));
		p_center.setBackground(Color.LIGHT_GRAY);
		
		p_south.setPreferredSize(d1);
		p_south.setBackground(Color.LIGHT_GRAY);
		
		p.setPreferredSize(new Dimension(1100, 360));
		p.setBackground(Color.RED);
		cenPanel.setLayout(new BorderLayout());
		
//		northPanel.setPreferredSize(new Dimension(10, 10));
//		northPanel.setBackground(Color.GREEN);
		cenPanel.setPreferredSize(new Dimension(600, 300));
		cenPanel.setBackground(Color.RED);
//		southPanel.setPreferredSize(new Dimension(10, 10));
//		southPanel.setBackground(Color.WHITE);
		eastPanel.setPreferredSize(d2);
		eastPanel.setBackground(Color.RED);
		westPanel.setPreferredSize(d5);
//		westPanel.setBackground(Color.BLUE);
		
		la_ment1.setFont(new Font("고딕", Font.BOLD, 20));
		la_ment1.setForeground(Color.WHITE);
		la_ment1.setPreferredSize(new Dimension(500, 50));
		la_ment1.setHorizontalAlignment(la_ment4.CENTER);
		
		la_ment2.setFont(new Font("고딕", Font.BOLD, 20));
		la_ment2.setForeground(Color.WHITE);
		la_ment2.setPreferredSize(new Dimension(500, 50));
		la_ment2.setHorizontalAlignment(la_ment4.CENTER);
		
		la_ment3.setPreferredSize(new Dimension(600, 15));
		la_ment3.setForeground(Color.WHITE);
		la_ment4.setPreferredSize(new Dimension(500, 150));
		la_ment4.setForeground(Color.WHITE);
		la_ment4.setHorizontalAlignment(la_ment4.CENTER);
		
//		Dimension d1 = new Dimension(640, 360);
//		Dimension d2 = new Dimension(175, 175);
//		Dimension d3 = new Dimension(50, 20);
//		Dimension d4 = new Dimension(50, 50);
//		Dimension d5 = new Dimension(300, 300);
		
		la_id.setPreferredSize(d4);
		la_pass.setPreferredSize(d4);
		idField.setPreferredSize(d3);
		passField.setPreferredSize(d3);
		bt_west.setPreferredSize(d5);
		bt_login.setPreferredSize(d2);
		
		bt_west.setBorderPainted(false);
		bt_login.setBorderPainted(false);
		
//		add
		add(border);
		border.setLayout(new BorderLayout());
		
		border.add(p_north, BorderLayout.NORTH);
		border.add(p_center);
		border.add(p_south, BorderLayout.SOUTH);
		
		p_center.add(p);
		p_center.add(cenPanel);
		

//		p.add(northPanel, BorderLayout.NORTH);
//		p.add(southPanel, BorderLayout.SOUTH);
		p.add(westPanel, BorderLayout.WEST);
		p.add(cenPanel);
		p.add(eastPanel, BorderLayout.EAST);
		
		cenPanel.setLayout(new FlowLayout());
//		cenPanel.setLayout(new GridBagLayout());
		
		cenPanel.add(la_ment1);
		cenPanel.add(la_ment2);
		cenPanel.add(la_ment3);
		cenPanel.add(la_id);		
		cenPanel.add(idField);
		cenPanel.add(la_pass);
		cenPanel.add(passField);
		cenPanel.add(la_ment4);
		
		westPanel.add(bt_west);
		eastPanel.add(bt_login);
	}
	

}
