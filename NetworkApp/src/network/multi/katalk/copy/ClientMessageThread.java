package network.multi.katalk.copy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import netscape.javascript.JSObject;

/*나 아닌 다른 사용자들이 보낸 메시지를 실시간 청취하려면 listen() 메서드가 무한루프로 동작해야 하 
 이 동작을 메인스레드에서 하면 프로그램이 멈춘다.
 따라서 메인 스레드 대신 무한루프로 청취 할 스레드를 정의한다 */
public class ClientMessageThread extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	ChatPage chatPage;
	boolean flag = true;  //이 스레드의 생사 여부를 결정짓는 논리값 
	
	
	public ClientMessageThread(ChatPage chatPage, Socket socket) {
		this.chatPage = chatPage;
		this.socket=socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//듣기 
	public void listen() {
		String msg=null;
		try {
			msg = buffr.readLine();
			//json 문자열 파싱하기
			JSONParser jsonParser = new JSONParser();//파서 생성
			try {
				JSONObject json=(JSONObject)jsonParser.parse(msg);
				String data=(String)json.get("data");
				System.out.println("클라이언트에 전송된 대화내용은 "+data);
				
				//data의 길이가 0보다 크다면, 이모티콘 사용이 아닌 대화메시지 요청이므로 
				System.out.println(data.length());
				if(data.length()>0) {
					chatPage.addString(json);//메시지가 전송되	
				}else {
					chatPage.addIcon(json);// 이모티콘을 전송한 경우
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
	
		} catch (IOException e) {
			flag=false;
		}
	}
	//말하
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("스레드동자 ");
		while(flag) {
			listen();// 끝나는 시점 : 사용자가 나감 -> 캐치문에 걸려 무한루프에 걸리니까 캐치문에서 제동걸기 
		}
	}
}
















