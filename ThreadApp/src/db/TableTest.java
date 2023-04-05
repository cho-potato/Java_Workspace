package db;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import util.ImageManager;

//스윙 컴포넌트 중 DB를 가장 잘 표현할 수 있는 컴포넌트 - JTable

public class TableTest extends JFrame implements ActionListener {
	JPanel p_west; // 서쪽 영역에 붙일 패널
	JTextField t_id, t_name;
	JPasswordField t_pass; // 비밀번호는 보안상 가려져야 하므로
	ImageManager imageManager;
	JButton bt;
	JTable table;
	JScrollPane scroll;
	int rows;

//	JTable에 사용할 데이터 : JTable(Object[][] rowData, Object[] columnNames)
//	String[] d1 = {"안도경", "010", "고구마"};
//	String[] d2 = {"고구마", "7963", "안도경"};
//	String[] d3 = {"안구마", "3077", "고도경"};
//	String[][] data = { { "안도경", "010", "고구마" }, { "고구마", "7963", "안도경" }, { "안구마", "3077", "고도경" } };

//	JTable에 사용할 컬럼
//	String[] column = { "ID", "PASS", "NAME" };
	String[] column = { "EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO" };

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java";
	String pass = "1234";
	Connection conn; // 접속 정보를 가진 객체
//	PreparedStatement pstmt; // 쿼리 수행 객체
//	ResultSet rs; // 결과 테이블을 담는 객체

	public TableTest() {
		p_west = new JPanel();
		t_id = new JTextField();
		t_name = new JTextField();
		t_pass = new JPasswordField();
		imageManager = new ImageManager();
		bt = new JButton(imageManager.getIcon("res/app/notice.png", 50, 30));

//		스타일 설정
		p_west.setPreferredSize(new Dimension(150, 500));
		Dimension d = new Dimension(130, 30);
		t_name.setPreferredSize(d);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		bt.setPreferredSize(d);

//		서쪽 조립
		p_west.add(t_name);
		p_west.add(t_id);
		p_west.add(t_pass);
		p_west.add(bt);
		add(p_west, BorderLayout.WEST); // 패널 서쪽에 붙이기

//		table = new JTable(data, column); // 10층 5호수 // JTable(int numRows, int numColumns) : Constructs a JTable with numRows and numColumns of empty cells using DefaultTableModel.
//		scroll = new JScrollPane(table);

//		add(scroll); // add(table);
		setSize(600, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

//		버튼과 리스너 연결
		bt.addActionListener(this);

//		윈도우(JFrame)와 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release();
				System.exit(0); // 프로세스 종료
			}
		});
		connect();
		getData();
	}

//	Oracle 접속
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.setTitle("Oracle Connected");

//			2) 접속
			conn = DriverManager.getConnection(url, user, pass);
			if (conn != null) {
				System.out.println("Connected");
			} else {
				System.out.println("Oracle Connect Failed");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	사원 테이블 가져오기 (Select)
	public void getData() {
		PreparedStatement pstmt = null; // 쿼리 수행 객체
		ResultSet rs = null; // 결과 테이블을 담는 객체

		String sql = "select * from emp order by empno asc";
		try {
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // Select문 실행시
			int row = 0;
			while (rs.next()) {
				row = rs.getRow(); // int row = rs.getRow();
				System.out.println(row);
			}
			String[][] data = new String[row][8];
//			rs를 다시 복귀시키자
//			에러가 나는 이유(전방향 전용 결과 집합에 부적합한 작업이 수행되었습니다 : beforeFirst)
//			ResultSet은 생성시 옵션을 부여하지 않으면 전방향 전용 레코드 셋이다. 즉, forward 방향으로만 진행할 수 있다
			rs.beforeFirst(); // 첫번째 레코드보다 이전, 처음 탄생 위치

			for (int i = 0; i < row; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("empno")); // EMPNO
				data[i][1] = rs.getString("ename"); // ENAME
				data[i][2] = rs.getString("job"); // JOB
				data[i][3] = Integer.toString(rs.getInt("mgr")); // MGR
				data[i][4] = rs.getString("hiredate"); // HIREDATE
				data[i][5] = Integer.toString(rs.getInt("sal")); // SAL
				data[i][6] = Integer.toString(rs.getInt("comm")); // COMM
				data[i][7] = Integer.toString(rs.getInt("deptno")); // DEPTNO
			}
			System.out.println("두둥탁" + data.length);

			table = new JTable(data, column);
			scroll = new JScrollPane(table);
			add(scroll);
			SwingUtilities.updateComponentTreeUI(this); // 그림이라면 repaint를 쓰겠지만 프레임이기 때문에

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

//	Oracle 접속해제
	public void release() {
		System.out.println("Database Released");
		if (conn != null) {
			try {
				conn.close(); // SQL플러스 창 닫기와 같다
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	데이터 등록하기
	public void regist() {
//		table.setValueAt(bt, 2, 1); // Sets the value for the cell in the table model at row and column.
		System.out.println("??????");
//		입력한 값 얻기
		String id = t_id.getText(); // 입력한 아이디
		String pass = new String(t_pass.getPassword()); // 입력한 비밀번호
		String name = t_name.getText(); // 입력한 이름

//		하나의 레코드 넣기 
		table.setValueAt(id, rows, 0);
		table.setValueAt(pass, rows, 1);
		table.setValueAt(name, rows, 2);
		rows++;
	}

//	Oracle의 Select문을 수행하여 JTable이 사용할 2차원 배열을 생성해보자

	public void actionPerformed(ActionEvent e) {
		regist();
	}

	public static void main(String[] args) {
		new TableTest();
	}

}
