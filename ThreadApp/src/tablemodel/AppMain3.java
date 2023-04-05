package tablemodel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

//	JTable에서 TableModel 사용이 상당히 큰 비중을 차지하는데, 왜 사용해야 하느지를 이해하기 위한 예제
//디자인과 데이터 및 데이터 연동코드는 분리되어야 한다
//	1) 사용했을 경우와 2) 사용하지 않았을 경우를 비교

//	1. 드라이버 로드
//	2. 접속
//	3. 쿼리문
//	4. 자원해제

public class AppMain3 extends JFrame { // 1) 사용했을 경우
	JTable table;
	JScrollPane scroll; // 스크롤을 이용해야 컬럼 제목이 노출된다
	String[][] data; // emp 연동 예정
	String[] column = { "EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO" };
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java";
	String pass = "1234";
	Connection conn; // 멤버변수로 선언한 이유 : 원할 때 접속을 끊기 위해
	TableModel model;// EmpModel model; // EMP 테이블에 대한 데이터를 가진 객체 

	public AppMain3() {
//		오라클 연동하기
		connect(); // 오라클 접속
//		select(); // EMP 레코드 가져오기

		table = new JTable(model = new DeptModel(this));
		scroll = new JScrollPane(table);

		add(scroll);

		setSize(600, 400);
		setVisible(true);
		setLocationRelativeTo(null);
//		setDefaultCloseOperation(EXIT_ON_CLOSE); // 무조건 프로세스를 끄는 것. DB에서는 지양 

//		윈도우와 리스너 연결
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				release(conn); // 오라클 연결 해제
				System.exit(0); // 프로세스 종료 // 하고 싶을 떄 할 수 있는 것
			}
		});
	}

	public void connect() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pass);
			if (conn == null) {
				this.setTitle("접속 실패");
			} else {
				this.setTitle(user + "계정으로 접속 됨");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	접속해제
	public void release(Connection con) { // 매개변수로 받으면 외부에 있는 것도 받을 수 있는 유동성이 있다
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new AppMain3();
	}
}

