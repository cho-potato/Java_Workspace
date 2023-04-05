package network.unicast.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// Unicasting 이상의 서버에서는 다수의 클라이언트가 접속을 하므로,
// 서버측에 생성된 sokcet, BufferefdReader, BufferedWriter 등의 객체는
// 만일 5 클라이언트가 접속하면 이 객체들도 5쌍이 존재하고 보존되어야 한다
// 따라서, 이를 해결하려면 접속한 클라이언트마다 각각 서버 측에 객체를 생성하여
// 해당 인스턴스 안에 보관하여 인스턴스(메세지)를 주고 받는다.
// 또한 이 객체들 간 서로 간섭받지 않고 독립적으로 수행되어야 하므로 쓰레즈 정의하기에 좋다

public class MessegeThread extends Thread {
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;

// 메시지 쓰레드 객체는 태어날 때 서버로부터 소켓을 전달받아 메세지를 주고 받는데에 사용하면 된다
	public MessegeThread(Socket socket) {
		this.socket = socket;
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	듣기
	public void listen() {
		String msg = null;
		try {
			msg = buffr.readLine(); // 듣기
			sendMsg(msg); // 말하기
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//  말하기
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while(true) {
			listen();
		}
	}
}
