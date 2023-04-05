package network.multi.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiClient extends JFrame {
	JPanel p_north;
	JComboBox<String> box_ip;
	JTextField t_port;
	JButton bt_connect; // 접속 버튼
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south;
	JTextField t_input; // 메시지 입력창
	JButton bt_send; // 전송 버튼

	int port = 9999;
	ClientMessegeThread cmt;

	public MultiClient() {
		p_north = new JPanel();
		box_ip = new JComboBox<String>();
		t_port = new JTextField(Integer.toString(port), 6);
		bt_connect = new JButton("CONN"); // 접속 버튼
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_south = new JPanel();
		t_input = new JTextField(15); // 메시지 입력창
		bt_send = new JButton("SEND"); // 전송 버튼

		p_north.add(box_ip);
		p_north.add(t_port);
		p_north.add(bt_connect);
		add(p_north, BorderLayout.NORTH);

		add(scroll);

		p_south.add(t_input);
		p_south.add(bt_send);
		add(p_south, BorderLayout.SOUTH);

		createIp();

		setSize(300, 400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
//				아래와 같이 메인 쓰레드로 무한 루프를 돌리면, 메인 실행부가 동작할 수 없게 된다
//				따라서 대체 쓰레드가 필요하다
//				while (true) {
//					listen();
//				}
			}
		});
//		입력창과 리스너 연결
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cmt.sendMsg(t_input.getText());
					t_input.setText(""); // 입력값 없애기 (초기화)
				}
			}
		});
	}

	public void createIp() {
		for (int i = 3; i < 100; i++) {
			box_ip.addItem("172.30.1." + i);
		}
	}

//	서버에 접속하기
	public void connect() {
		String ip = (String) box_ip.getSelectedItem();
		int port = Integer.parseInt(t_port.getText());
		try {
			Socket socket = new Socket(ip, port);
//			클라이언트 측에서의 실시간 메세지 청취 및 전송용 쓰레드 생성
			cmt = new ClientMessegeThread(this, socket);
			cmt.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MultiClient();
	}

}