package network.multi.katalk.copy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import network.domain.ChatMember;

public class ClientMain extends JFrame{
	JPanel container;//전환될 페이지들을 안고 있을 최상위 패널
	
	//프로그램에서 사용할 페이지들 생성 
	Page[] page=new Page[3];
	public static final int LOGINPAGE=0;
	public static final int JOINPAGE=1;
	public static final int CHATPAGE=2;
	
	//로그인 성공시 담아둘 사용자 정보, 이 클래스에 멤버변수로 선언한 이유는 모든~~ 페이지들이 이 클래스를 접근할 수 있기 때문이다.
	//즉 공통 클래스이기 때
	ChatMember chatMember;
			
	public ClientMain() {
		container = new JPanel();
		page[0]=new LoginPage(this);
		page[1]=new JoinPage(this);
		page[2]=new ChatPage(this);

		for(int i=0;i<page.length;i++) {
			container.add(page[i]);//루트 컨테이너에 페이지들 부착!!
		}
		
		add(container);
		
		//원하는 페이지 번호
		showHide(LOGINPAGE);
		
		setSize(400,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//보여주고 싶은 페이지 넘버를 넘기면 된다!!
	public void showHide(int n) {
		for(int i=0;i<page.length;i++) {
			if(n==i) {
				page[i].setVisible(true);
			}else {
				page[i].setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new ClientMain();
	}
}
