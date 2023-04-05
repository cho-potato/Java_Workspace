package emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import network.util.DBManager;

// Dept 테이블에 대한 CRUD객체

public class DeptDAO {

	DBManager dbManager = DBManager.getInstance();

	public DeptDAO() {
		System.out.println("dept" + dbManager.getConnection());
	}

	// 삭제
	public int delete(int deptno) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();
		String sql = "delete dept where deptno = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			pstmt.executeUpdate(); // 자동 커밋

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}

		return result;
	}

}
