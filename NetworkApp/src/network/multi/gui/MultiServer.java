package network.multi.gui;

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

public class MultiServer extends JFrame {
	JPanel p_north;
	JTextField t_port;
	JButton bt_start; // 서버가동버튼
	JTextArea area;
	JScrollPane scroll;

	int port = 9999;

	ServerSocket server;
	Thread serverThread; // 메인 쓰레드가 대기상태에 빠지는 것을 방지하기 위한 대체 쓰레드
	Socket socket;

//	접속한 클라이언트측 메세지 전용 쓰레드를 모아놓을 컬렉션
	Vector<ServerMessegeThread> vec = new Vector();

	public MultiServer() {
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

//		쓰레드 생성 // 버튼 부르기 전 생성
		serverThread = new Thread() {
			public void run() {
				startServer();
				bt_start.setEnabled(false); // 서버가동 누르고나면 비활성화
			}
		};
		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start();
			}
		});
	}

//	서버를 가동한다는 것은 ServerSocket를 생성하고, accept() 동작시킴
	public void startServer() {
		try {
			server = new ServerSocket(port); // 서버 생성
			System.out.println("서버가동");
			area.append("서버가동\n");
			while(true) {
			socket = server.accept(); // 서버 가동 // 클라이언트가 접속할 때까지 대기상태
//			소켓은 곧 소멸되므로 소멸되기 전에 특정 클래스의 인스턴스를 생성하여 그 안에 보관해둔다
//			특히, 이 인스턴스는 각각 독립적으로 실행되기 위해 쓰레드로 정의한다
			String ip = socket.getInetAddress().getHostAddress();
			System.out.println(ip+"접속자 감지n");
			area.append(ip+" 접속자 감지\n");
			
			ServerMessegeThread smt = new ServerMessegeThread(this, socket);
			smt.start(); // smt.run(); 으로 호출하게 되면 메인이 무한루프에 빠져 멈추기 때문에 쓰면 안된다
			vec.add(smt); // 출생 명부에 기록
			area.append("현재"+vec.size()+"명이 채팅에 참여 중\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
// 채팅 참여자가 나갈 경우 서버측 메세지 전용 쓰레드 또한 소멸시켜야 한다 
//	따라서 별도의 메서드 처리가 필요하다
	public void removeClient(ServerMessegeThread smt) {
//		누구를 죽일지 매개변수로 받을거임 
//		넘겨받은 SMT를 벡터에서 제거
		vec.remove(smt);
		area.append("현재 채팅 참여자 수는" + vec.size()+"\n");
	}
	public static void main(String[] args) {
		new MultiServer();
	}
}