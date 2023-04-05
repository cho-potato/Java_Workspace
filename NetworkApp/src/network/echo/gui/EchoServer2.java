package network.echo.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Telnet과 WebBrowser는 접속까지는 가능하지만 대화를 나누기 위한 전용 툴이 아니므로 자바로 직접 개발해본다

public class EchoServer2 extends JFrame {
	JPanel p_north;
	JTextField t_port;
	JButton bt_start; // 접속 버튼
	JTextArea area;
	JScrollPane scroll;

	int port = 9999;
	ServerSocket server;
	Thread serverThread; // 접속자 대기를 위한 전용 쓰레드
//	이 쓰레드가 없은 경우 메인 쓰레드가 대기상태에 빠지게 됨

	public EchoServer2() {
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
		
//		쓰레드 생성 및 재정의
		serverThread = new Thread() {
			public void run() {
				startServer();
			}
		};

		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				쓰레드 시작, Runnable로 진입
				serverThread.start();
			}
		});
	}
// 클라이언트의 접속을 감지하기 위한 서버소켓을 생성하고 접속자를 기다린다
	public void startServer() { 
		try {
			server = new ServerSocket(port);
			area.append("서버생성\n");
//			메인 쓰레드는 1. 루프 2.대기상태 빠트려서는 안된다
//			메인 쓰레드는 프로그래밍 운영 쓰레드이므로 그래픽처리, 이벤트 감지 등 처리해야 하니까
			
			Socket socket = server.accept(); // 접속자를 기다린다
			InetAddress inet = socket.getInetAddress();
			String ip = inet.getHostAddress();
			
			area.append(ip+" 접속");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new EchoServer2();
	}

}
