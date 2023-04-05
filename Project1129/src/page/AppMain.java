package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.ImageManager;

public class AppMain extends JFrame {
	JPanel p_north;
	Menu[] menu = new Menu[4];
	ImageManager imageManager;
	String[] path = { "res/app/introduce.png", "res/app/member.png", "res/app/login.png", "res/app/notice.png" };
	JPanel p_center;

	JPanel[] pageList = new JPanel[4];


	public static final int PAGE_WIDTH = 800;
	public static final int PAGE_HEIGHT = 400;

//	DB관련 정보
	FileInputStream fis; // File을 대상으로 한 입력 스트림. 실행중인 파일에 접근할 수 있는 자원은 Stream뿐임
	Properties props; // Map의 손자격, key-value
	String driver;
	String url2;
	String user;
	String pass;
	Connection conn;
	boolean loginFlag; // flase가 디폴트임 (최초에 로그인 안했으므로)
	
	public AppMain() {
		loadInfo();
		connect();
		
		p_north = new JPanel();
		imageManager = new ImageManager();

		p_north.setBackground(Color.RED);


		for (int i = 0; i < menu.length; i++) {
			menu[i] = new Menu(this, imageManager.getIcon(path[i], 50, 50), 50, 50, i); 
			p_north.add(menu[i]);
		}


		p_center = new JPanel();
		p_center.setBackground(Color.GRAY);


		pageList[0] = new CompanyPage();
		pageList[1] = new MemberPage(this);
		pageList[2] = new LoginPage(this);
		pageList[3] = new NoticePage();

		for (int i = 0; i < pageList.length; i++) {
			p_center.add(pageList[i]);
		}

		add(p_center);
		add(p_north, BorderLayout.NORTH);

		setSize(800, 500);
		setVisible(true);
		setLocationRelativeTo(null);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);

		showHide(0);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(conn);
				System.exit(0);
			}
		});
		
	}
//	페이지 보여주기 전에 로그인 완료 여부 확인하기
	public void checkLogin(int currentPage) {
//		회원가입과 로그인의 경우 검사하지 않는다
		if(currentPage ==1 || currentPage==2) {
			showHide(currentPage);
		} else {
//			검증이 필요한 나머지 것들
			if (loginFlag == false) {
				JOptionPane.showConfirmDialog(this, "로그인이 필요한 서비스입니다");
			}else {
				showHide(currentPage);
			}
		}
	}


	public void showHide(int currentPage) {
		for (int i = 0; i < pageList.length; i++) {
			if (currentPage == i) {
				pageList[i].setVisible(true);
			} else {
				pageList[i].setVisible(false);
			}
		}

	}

	public void loadInfo() {
//		Stream을 생성할 자원이 Package 경로에 있으므로 
		URL url = this.getClass().getClassLoader().getResource("res/db/db.properties");
		try {
			URI uri = url.toURI();
			File file = new File(uri);
			fis = new FileInputStream(file);
			props = new Properties(); // Map의 손자 생성
			props.load(fis); // properties와 fis 연결 -> 파일을 인식한 맵이 되는 순간
			driver = props.getProperty("driver");
			url2 = props.getProperty("url");
			user = props.getProperty("user");
			pass = props.getProperty("pass");

//			
//			int data = -1;
//			
//			while (true) {
//				data = fis.read();
//				if(data == -1) break;
//				System.out.println((char)data);
//			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void connect() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url2, user, pass);
			if (conn != null) {
				this.setTitle("접속 성공");
			} else {
				this.setTitle("접속 실패");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void release(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt, ResultSet rs) { // select의 경우
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new AppMain();
	}
}
