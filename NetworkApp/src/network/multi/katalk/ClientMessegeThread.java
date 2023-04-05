package network.multi.katalk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// 나 아닌 다른 사용자들이 보낸 메세지를 실시간 청취하려면 listen() 메서드가 무한루프로 동작해야 하므로
// 이 동작을 메인 쓰레드에서 하면 프로그램이 멈추기 때문에 메인 쓰레드 대신 무한루프 할 청취를 쓰레드로 정의한다

public class ClientMessegeThread extends Thread {
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	ChatPage chatPage;
	boolean flag = true; // 쓰레드 소명여부를 결정 짓는 논리값
	public ClientMessegeThread(ChatPage chatPage, Socket socket) {
		this.chatPage = chatPage;
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
			msg = buffr.readLine();
			
//			JSON 문자열 파싱하기
			JSONParser jsonPaerser = new JSONParser(); // 파서 생성
			try {
				JSONObject json = (JSONObject)jsonPaerser.parse(msg);
				String data = (String) json.get("data");
//				System.out.println("클라이언트에 전송된 대화내용은 : "+ data);
//				data의 길이가 0보다 크다면 이모티콘 사용이 아닌 메세지 요청이므로
				if(data.length()>0) {
					chatPage.addString(json); // 메세지가 전송된 경우
				} else {
					chatPage.addIcon(json); // 이모티콘을 전송한 경우
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

//			로그남기기
			chatPage.area.append(msg+"\n");
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
	}
//	말하기
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while (flag) {
			listen();
		}
	}
}
