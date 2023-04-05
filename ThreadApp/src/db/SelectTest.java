package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Oracle에 접속하여  emp 테이블의 레코드를 가죠와보자

public class SelectTest {
	public static void main(String[] args) {
//		Java DataBase Connectivity : 자바 기반의 데이터베이스 연동 기술(개념), java.spl 패키지에서 지원
//		(JDBC기반) DB연동 절차
//		1) 드라이버 로드(사용하려는 DBMS 제품에 맞는) : (드라이버?) java 언어가 해당 DB제품을 핸들링하기 위한 라이브러리
//		2) 접속하기
//		3) 쿼리실행
//		4) DB관련 자원해제(Stream과 DB는 개발자가 반드시 닫아야 함)
//		인스턴스를 올리는 것이 아니라 staic 영역으로 지정한 드라이버 클래스를 Load라 한다 (=forName())
//		개발자가 해당 라인에서 예외처리를 하고 싶지 않다면 해당 메서드를 호출하는 것에 책임을 전가하는 것(해당 메서드 이름 throws ClassNotFoundException)
//		throws란, try ~ catch 블록으로 해당 예외를 개발자가 처리하기 싫을 때, 이 예외가 발생한 메서드를 호출한 호출자에게 예외처리를 전가시키는 것 

		Connection conn = null; // java.sql 패키지
		PreparedStatement pstmt = null; // 쿼리 수행문 객체
		ResultSet rs = null; // java.sql 결과표를 표현한 객체
		try {
//			1) 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

//			2) 접속시도
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "java";
			String pass = "1234";
//			Resultset : 원격지에 있는 emp 테이블 레코드 결과를 복사하여 개발자의 PC로 들어옴
//			-> 쿼리문을 날리면 원격지에 생성된 결과물을 개발자의 PC에 담는 객체

			conn = DriverManager.getConnection(url, user, pass);
//			접속이 성공되면 Connection 객체가 채워진다 (따라서 conn이 null이면 접속 실패임)
//			Connection안에는 접속 정보가 들어있으며, 접속을 끊을 때 중요한 역할을 한다

			if (conn != null) {
				System.out.println("Connected");

			} else {
				System.out.println("Connect Failed");
			}

//			3) 쿼리실행

			String sql = "select * from emp order by empno"; // 쿼리문 작성
			pstmt = conn.prepareStatement(sql); // 쿼리수행 객체 생성
			rs = pstmt.executeQuery(); // 쿼리 수행 및 표 생성 rs : emp 테이블 자체

//			rs의 레코드를 사용하기 위해서는 커서를 이동해야해 하는데, 
//			rs가 생성된 초기에는 그 어떤 레코드도 가리키지 않고 제일 위로 올라와 있다
//			rs.next();
//			rs.next();
//			rs.next();
//					
			while (rs.next()) { // next()가 true일 때
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				System.out.println(empno + "\t" + ename + "\t" + job);
			}

//			rs가 메모리에 올라오면 pointer로 접근, pointer가 접근하는 곳만 사용 가능
//			pointer가 원하는 항목으로 갈 때 쓰는 것 = next 
//			next = 커서 이동
//			Schemas 데이터 베이스의 모든 단위
//			데이터베이스 : 사용자, 테이블 등 모든 것의 집합체

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) { // 2) 다중캐치문(DriverManager.getConnection(url, user, pass);
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
