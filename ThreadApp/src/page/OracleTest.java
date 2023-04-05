package page;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Java 언어에서 DBMS를 연동해보는 실습
//	1) 드라이버 로드 (원하는 제품에 대한 드라이버  jar)
//	2) 접속하기
//	3) 퀴리수행
//	4) 자원해제

public class OracleTest {

	public static void main(String[] args) {

//		알맞는 드라이버를 메모리에 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 지정한 문자열 경로의 클래스를 메모리에 로드
															
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e1) {
			System.out.println("드라이버가 존재하지 않습니다");
			e1.printStackTrace();
		}
//		켜져 있는 오라클 서버에 접속해보자
//		접속 URL은 정해져 있는 규칙을 따른다
//		jdbc를 통해 들어갈거고 : 기종은 오라클로 접속할거고 : 방식은 thin이고  : 호스트는 나니까 : 오라클 포트 번호는 1521 : 오라클 설치할 때 이름
		String url = "jdbc:oracle:thin:@localhost:1521:XE"; // jdbc : Java Database Connectivity // 127.0.0.1
		String user = "java";
		String pass = "1234";
		Connection conn = null; // finally를 닫기 위해
		PreparedStatement pstmt = null; // finally를 닫기 위해

		try {
//			접속 시도 후 성공일 경우만 Connection이라는 인터페이스 객체가 반환된다
//			따라서 Connection 객체가 만일  null이라면 접속 실패
			conn = DriverManager.getConnection(url, user, pass); // 대문
			if (conn == null) {
				System.out.println("접속실패");
			} else {
				System.out.println("접속성공");
			}
//			접속을 성공했으니 insert문 실행해보자
//			쿼리문을 수행하기 위해서는 쿼리담당 객체를 사용해야 한다
//			이 때 사용되는 객체명은 PreparedStatement
			String sql = "insert into emp2(ename, job, sal) values('xman', 'none', 4000)"; // Dbeaver에 옮길 땐 쌍따옴표("") 안에 있는 문구를 복사+붙여넣기
																							
			pstmt = conn.prepareStatement(sql); // new가 아닌 Connection 자료형으로부터 얻어온다 // 방문
			int result = pstmt.executeUpdate(); // 쿼리 실행
			if (result > 0) { // 반영된 레코드 수가 0이 아니면 성공
				System.out.println("입력성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			DB관련 모든 자원 해제 (여기서는 이 두 개 : Connection, PreparedStatement)
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
