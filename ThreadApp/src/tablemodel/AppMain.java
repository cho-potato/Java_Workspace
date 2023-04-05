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

//	JTable에서 TableModel 사용이 상당히 큰 비중을 차지하는데, 왜 사용해야 하느지를 이해하기 위한 예제
//	1) 사용했을 경우와 2) 사용하지 않았을 경우를 비교

//	1. 드라이버 로드
//	2. 접속
//	3. 쿼리문
//	4. 자원해제

public class AppMain extends JFrame {
	JTable table;
	JScrollPane scroll; // 스크롤을 이용해야 컬럼 제목이 노출된다
	String[][] data; // emp 연동 예정
	String[] column = { "EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO" };
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java";
	String pass = "1234";
	Connection conn; // 멤버변수로 선언한 이유 : 원할 때 접속을 끊기 위해

	public AppMain() {
//		오라클 연동하기
		connect(); // 오라클 접속
		select(); // 레코드 가져오기

		table = new JTable(data, column);
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

//	emp에서 레코드 가져오기
	private void select() {
//		PreparedStatement, ResultSet 두 객체는 쿼리문마다 일대일 대응하여 생성해야 하므로 쿼리문 수행 후 객체를 닫는다. 따라서 지역변수로 선언
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from emp order by empno asc";
		try {
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // pstmt = conn.prepareStatement(sql, 스크롤, 읽기 전용); // 쿼리수행 객체 생성
			rs = pstmt.executeQuery(); // select문 수행시 사용할 메서드
//			현재 rs에는 emp테이블이 들어있다. 따라서 커서를 이동하면서 원하는 레코드에 접속할 수 있다.
//			JTable의 생성자가 rs가 아닌 2차원 배열을 원하므로 rs의 데이터를 2차원 배열로 전환한다
//			- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			rs.last(); // 2차원 배열로 만들기 위함
			int total = rs.getRow();
			System.out.println(total);
			data = new String[total][column.length];

//			커서를 다시 복귀시킨다
			rs.beforeFirst(); // 첫 번째 레코드보다 위 쪽(아무 것도 가리키지 않음)
			
//			String[] column = {"EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO"};
			for (int i = 0; i < total; i++) {
				rs.next(); // 커서 한 칸 전진
				data[i][0] = Integer.toString(rs.getInt("EMPNO"));
				data[i][1] = rs.getString("ENAME");
				data[i][2] = rs.getString("JOB");
				data[i][3] = Integer.toString(rs.getInt("MGR"));
				data[i][4] = rs.getString("HIREDATE");
				data[i][5] = Integer.toString(rs.getInt("SAL"));
				data[i][6] = Integer.toString(rs.getInt("COMM"));
				data[i][7] = Integer.toString(rs.getInt("DEPTNO"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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

	}

//	접속해제
	private void release(Connection con) { // 매개변수로 받으면 외부에 있는 것도 받을 수 있는 유동성이 있다
		if (conn != null) {
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AppMain();
	}
}
