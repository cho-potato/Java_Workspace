package network.multi.katalk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatServer extends JFrame {

	JPanel p_north;
	JTextField t_port;
	JButton bt_start;
	JTextArea area;
	JScrollPane scroll;
	ServerSocket server; // 접속자를 감지하기 위한 서버 소켓
	Thread serverThread; // 메인 쓰레드 대신 대기상태를 처리하기 위한 개발자 정의 쓰레드
	Socket socket;
//	접속한 사용자마다 일대응 대응되는 MessegeThread를 담을 컬렉션
	Vector<ServerMessegeThread> vec = new Vector<ServerMessegeThread>();

	public ChatServer() {
		p_north = new JPanel();
		t_port = new JTextField("8888");
		bt_start = new JButton("Activate");
		area = new JTextArea();
		scroll = new JScrollPane(area);

		p_north.add(t_port);
		p_north.add(bt_start);

		add(p_north, BorderLayout.NORTH);

		add(scroll);
		setSize(400, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

//		 쓰레드 정의
		serverThread = new Thread() {
			public void run() {
				startServer();
			}
		};
//		 버튼과 리스너 연결
		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				쓰레드를 runnable로 진입시키자
				serverThread.start();
			}
		});
	}

//	 서버 가동 메서드
	public void startServer() {
		int port = Integer.parseInt(t_port.getText()); // 포트번호
		try {
			server = new ServerSocket(port);
			area.append("서버 가동\n");
			while (true) {
				socket = server.accept(); // 접속자가 들어올 때까지 대기상태
				area.append("접속자 감지\n");
				ServerMessegeThread smt = new ServerMessegeThread(this, socket);
				smt.start(); // 쓰레드 시작 (Runnable 상태로 진입)
//				생성된 쓰레드를 명단에 추가
				vec.add(smt);
				area.append("현재 참여 중인 회원 수"+vec.size()+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ChatServer();
	}
}
