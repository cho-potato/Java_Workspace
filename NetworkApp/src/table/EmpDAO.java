package table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class EmpDAO {
	DBManager dbManager = DBManager.getInstance();
	
	public List selectAll() {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		conn = dbManager.getConnection();
		
		String sql = "select * from emp";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Emp emp = new Emp();
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setMgr(rs.getInt("mgr"));
				emp.setHiredate(rs.getString("hiredate"));
				emp.setSal(rs.getInt("sal"));
				emp.setComm(rs.getInt("comm"));
				emp.setDeptno(rs.getInt("deptno"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		
		return list;
	}
	public int update(Emp emp) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		conn = dbManager.getConnection();
		
		String sql = "update emp set ename=?, job=?, hiredate=?, sal=?, comm=? where empno=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emp.getEname());
			pstmt.setString(2, emp.getJob());
			pstmt.setString(3, emp.getHiredate());
			pstmt.setInt(4, emp.getSal());
			pstmt.setInt(5, emp.getComm());
			pstmt.setInt(6, emp.getEmpno());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
}
