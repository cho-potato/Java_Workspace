package emp;

import java.sql.Connection;
import java.sql.SQLException;

import network.util.DBManager;

public class TestApp {

	public static void main(String[] args) {
//		두 개의 dao가 보유한 커넥션이 같은지 알아보기
		DeptDAO deptDAO = new DeptDAO();
		EmpDAO empDAO = new EmpDAO();
		
//		JDBC는 개발자가 명시하지 않아도 DML에서 자동 commit 처리되어 있따
//		현재 사용 중인 Connection에서 트랜잭션 처리
		DBManager dbManager = DBManager.getInstance();
		Connection conn = dbManager.getConnection();
		
		try {
			conn.setAutoCommit(false); // 자동 commit 막기
			
//		부서지우기
			deptDAO.delete(20);
			
//		사원지우기
			empDAO.delete(20);
			
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("바깥쪽 도달");
			try {
				conn.rollback(); // 확정짓되 돌려놓기(처음부터 없었던 것으로 = 무효처리)
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		
	}
}
