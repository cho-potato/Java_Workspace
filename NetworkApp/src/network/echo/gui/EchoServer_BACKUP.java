package network.echo.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Telnet과 WebBrowser는 접속까지는 가능하지만 대화를 나누기 위한 전용 툴이 아니므로 자바로 직접 개발해본다

public class EchoServer_BACKUP extends JFrame {
	JPanel p_north;
	JTextField t_port;
	JButton bt_start; // 서버가동버튼
	JTextArea area;
	JScrollPane scroll;

	int port = 9999;
	
	public EchoServer_BACKUP() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 6);
		bt_start = new JButton("Activate"); // 서버가동버튼
		area = new JTextArea();
		scroll = new JScrollPane(area);

		p_north.add(t_port);
		p_north.add(bt_start);
		add(p_north, BorderLayout.NORTH);

		add(scroll);

		setSize(300, 400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new EchoServer_BACKUP();
	}

}
