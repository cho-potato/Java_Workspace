package network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 네트워크 : 전구의 소켓과 같은 역할
 * 소켓(Socket) : 개발자가 네트워크에 전문적 지식이 없더라도 네트워크를 제어할 수 있도록 하부 기능들을 추상화 시켜놓은 객체
 * - 추상화 : 복잡한 것들을 간략화 해놓은 개념
 * - 접속을 받는 쪽 : 서버 / 접속을 하는 쪽 : 클라이언트
 * 
 * 1) 에코시스템 : 서버와 1명의 클라이언트가 메세지를 주고 받은 가장 기초적인 단계 
 * 2) 유니캐스팅 : 서버와 다수의 클라이언트가 메세지를 주고 받는 단계
 * 3) 멀티캐스팅
 */
//------------------------------------------------------------------------------------------------------------
// Echo Server를 정의한다
// 클라이언트가 소켓으로 접속을 시도하면, 서버는 클라이언트의 접속을 감지해야 대화할 수 있는 소켓을 얻을 수 있는데,
// 이와 같이 접속 감지용 소켓을 가리켜 ServerSocket이라 한다

public class EchoServer {
//	포트번호? (서버가 결정)
//	현재 컴퓨터에서 실행되고 있는 모든 네트워크 프로그램 간 교섭이 발생하지 않도록 구분해주는 네트워크 구분 번호이다
//	개발자는 1 ~ 1024번은 사용할 수 없다(=이미 시스템이 점유하고 있다) 따라서 아무도 안쓰는 포트번호인 9999를 쓸 예정
// 서버는 포트번호만 명시하면 되는거고 클라이언트는 아이피와 포트번호를 알아야 함
	
	int port = 9999;
	ServerSocket server; // 접속자를 감지하는 객체
	public EchoServer() {
		try {
			server = new ServerSocket(port); // server가 있는 이유는 접속자를 감지하기 위함
			System.out.println("서버가동");
			
//			접속자를 감지해보자
			Socket socket = server.accept(); // 접속자가 들어올 때까지 무한 대기상태 // 따라서 syso로 뭔가를 적어도 accept에서 접속자가 감지되어야만 syso가 출력된다
			InetAddress inet = socket.getInetAddress();
			String ip = inet.getHostAddress(); // 접속자 IP 우리에게 접속하려는 클라이언트
			System.out.println(ip+"접속자 감지");
//			telnet : 원격 접속 프로그램이지만 보안이 약해 쓰이지 않음
			
//			현재 이 접속과 관련된 정보를 가진 socket 객체로부터 Stream을 "뽑자"
			
//			바이트기반 입력 스트림
			InputStream is = socket.getInputStream();
//			문자기반 입력 스트림
			InputStreamReader reader = new InputStreamReader(is);
//			버퍼 처리된 문자기반 입력 스트림
			BufferedReader buffr = new BufferedReader(reader);
	
			String msg =null;
			
			msg = buffr.readLine();
			System.out.println(msg);

//			인터넷 주소창에 본인아이피주소:포트번호 -> 접속자 감지 가능 

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoServer();
	}
}
