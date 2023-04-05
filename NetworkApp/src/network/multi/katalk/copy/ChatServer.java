package network.multi.katalk.copy;

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

public class ChatServer extends JFrame{
	JPanel p_north;
	JTextField t_port; //포트번호 입력
	JButton bt_start; //시작 버튼
	JTextArea area;
	JScrollPane scroll;
	ServerSocket server;
	Thread serverThread;	//접속한 사용자마다 1:1 대응되는 MessageThread를 담을 
	
	Vector <ServerMessageThread> vec = new Vector<ServerMessageThread>();
	
	public ChatServer() {
		p_north = new JPanel();
		t_port = new JTextField("8888", 8);
		bt_start = new JButton("Start");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		
		p_north.add(t_port);
		p_north.add(bt_start);
		
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		setSize(400,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//스레드 정의 
		serverThread = new Thread() {
			public void run() {
				startServer();
			}
		};
		
		//버튼과 리스너 연결
		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start();
			}
		});
	}
	
	//서버 가동 메서드
	 public void startServer() {
	       int port = Integer.parseInt(t_port.getText()); // 포트번호
	       try {
	         server = new ServerSocket(port);
	         area.append("서버 가동\n");
	         Socket socket = server.accept(); // 접속자가 들어올 때까지 대기상태  
	         area.append("접속자 감지\n");
	         ServerMessageThread smt = new ServerMessageThread(this, socket);
	         smt.start();
	         vec.add(smt);//벡터에 담기
	         area.append("현재 채팅 참여자 수:"+vec.size()+"\n");
	         
	       } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	
	public static void main(String[] args) {
		new ChatServer();
	}
}
