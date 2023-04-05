package network.echo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

// Telnet과 WebBrowser는 접속까지는 가능하지만 대화를 나누기 위한 전용 툴이 아니므로 자바로 직접 개발해본다

public class EchoClient extends JFrame {
	JPanel p_north;
	JComboBox<String> box_ip;
	JTextField t_port;
	JButton bt_connect; // 접속 버튼
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south;
	JTextField t_input; // 메시지 입력창
	JButton bt_send; // 전송 버튼

	Socket socket; // 대화용 소켓, 서버에 접속할 수 있다

	BufferedReader buffr = null;
	InputStreamReader reader = null;
	InputStream is = null;
	BufferedWriter buffw = null;
	OutputStreamWriter writer = null;
	OutputStream os = null;

	public EchoClient() {
		p_north = new JPanel();
		box_ip = new JComboBox<String>();
		t_port = new JTextField("9999", 6);
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
		area.setBackground(Color.ORANGE);

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
			}
		});
		bt_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMsg(); // 서버에 메시지 보내기
			}
		});
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
						if(key==KeyEvent.VK_ENTER) {
							sendMsg();
						}
				
			};
		});
	}
	public void createIp() {
		for (int i = 3; i < 100; i++) {
			box_ip.addItem("172.30.1." + i);
		}
	}

// 소켓 객체의 인스턴스를 생성하는 순간 지정한 서버에 접속할 수 있다 
	public void connect() {
		String ip = (String) box_ip.getSelectedItem();
		int port = Integer.parseInt(t_port.getText());
		try {
			socket = new Socket(ip, port);
//			현재 소켓으로부터 대화에 사용할 스트림 얻기

			is = socket.getInputStream();
			reader = new InputStreamReader(is);
			buffr = new BufferedReader(reader);

			os = socket.getOutputStream();
			writer = new OutputStreamWriter(os);
			buffw = new BufferedWriter(writer);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		String msg = null;
		try {
			msg = buffr.readLine(); // 한 줄 읽기
			area.append(msg+"\n"); // 로그에 쌓기
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMsg() {
//		메시지 보내기 
		String msg = t_input.getText();
		try {
//			버퍼처리 된 문자열 스트림의 경우 한 줄의 끝임을 알려줄 수 있는 개행을 반드시 해줘야 함
//			안넣으면 문자열의 끝이 없는 상태로 인식하고 무한대기상태에 빠짐
			buffw.write(msg + "\n");
//			버퍼처리된 출력스트림만  flush
			buffw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		t_input.setText(""); // 입력했던 내용 초기화
		listen();
	}

	public static void main(String[] args) {
		new EchoClient();
	}

}
