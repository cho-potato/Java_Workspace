package network.multi.katalk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 접속한 클라이언트마다 일대일 대응하여 대화를 주고받는 쓰레드
// 이 쓰레드는 대화를 주고받기 위해서 Stream이 필요하고, 
// Stream을 얻기 위해서는 Socket이 필요하다

public class ServerMessegeThread extends Thread{
	Socket socket;
//	닫는 것에 대한 부담을 개발자가 가져갈 필요가 없으니까 두 줄로 해결
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag = true; // 이 쓰레드의 생사여부를 결정짓는 논리값
	ChatServer chatServer;
	
	public ServerMessegeThread(ChatServer chatServer, Socket socket) {
		this.chatServer = chatServer;
		this.socket = socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void listen() {
		String msg = null;
		try {
			msg = buffr.readLine(); // JSON 문자열로 받아짐, 따라서 분석하려면 파싱해야 한다
//			로그남기기
			chatServer.area.append(msg+"\n");
//			BroeadCasting : 다수의 사용자에 동시에 메세지를 보낸 것
			for (int i = 0; i<chatServer.vec.size(); i++) {
				ServerMessegeThread smt = chatServer.vec.get(i); // 벡터에서 한 요소를 꺼낸다
				smt.sendMsg(msg);
			}
		} catch (IOException e) {
//			사용자가 대화 중단하고 나갔으므로 추후 명단에서 제거할 것이고 현재 쓰레드는 소멸시켜야 한다
			chatServer.vec.remove(this);
			chatServer.area.append("현재 참여중인 회원 수 : "+chatServer.vec.size()+"\n");
			flag = false;
//			e.printStackTrace();
		}

	}
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
//			사용자가 대화 중단하고 나갔으므로 추후 명단에서 제거할 것이고 현재 쓰레드는 소멸시켜야 한다
			flag = false;
//			e.printStackTrace();
		}
	}
//	죽지 않고 지속적으로 듣고 말하고를 반복, 단, 사용자가 접속을 끊으면 이 쓰레드는 죽어야 한다 
//	따라서 while()문 실행여부를 결정짓는 논리값이 필요하다
	public void run() {
		while(flag) {
			listen();
		}
	}
}
