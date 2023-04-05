package page;

//Java 언어에서 DBMS를 연동해보는 실습
//	1) 드라이버 로드 (원하는 제품에 대한 드라이버  jar)
//	2) 접속하기
//	3) 퀴리수행
//	4) 자원해제

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLTest {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			System.out.println("mysql용 드라이버 로드 성공");

//			접속 시도
//			jdbc에 있는 : mysql 기종을 통해 : 나 자신이 호스트니까 localhost쓸거고 : 포트번호는 3306이고 / 쓸 DB이름
			String url = "jdbc:mysql://localhost:3306/java";
			String user = "root";
			String pass = "1234";
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			conn = DriverManager.getConnection(url, user, pass);
			if(conn !=null) {
				System.out.println("접속 성공");
			}
			String sql = "insert into emp2(empno, ename) values(1, 'zino')";
			pstmt = conn.prepareStatement(sql);
			int result = pstmt.executeUpdate(); // 쿼리 수행
			if(result > 0) {
				System.out.println("성공");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
