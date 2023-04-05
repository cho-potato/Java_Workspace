package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

import oracle.net.aso.p;

// EMP table에 대한 CRUD 및 JTable에 정보제공

public class EmpModel extends AbstractTableModel {
	String[] column = { "DEPTNO", "DNAME", "LOC", "EMPNO", "ENAME", "SAL", "JOB" };
	String[][] data;
	EmpMain empMain;

	public EmpModel(EmpMain empMain) {
		this.empMain = empMain;
		select();
	}

//	부서와 사원 테이블을 조인하여 가져오기
	public void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select d.deptno as deptno, dname, loc, empno, ename, sal, job";
		sql += " from emp e inner join dept d";
		sql += " on e.deptno = d.deptno";
		sql += " order by deptno asc";
		System.out.println(sql);

		try {
			pstmt = empMain.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last(); // Scroll이 가능한 옵션을 주어야 가능하다
			int total = rs.getRow();

			data = new String[total][column.length];
			rs.beforeFirst();
			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("DEPTNO")); // 부서번호
				data[i][1] = rs.getString("DNAME"); // 부서명
				data[i][2] = rs.getString("LOC"); // 부서위치
				data[i][3] = Integer.toString(rs.getInt("EMPNO"));// 사원번호
				data[i][4] = rs.getString("ENAME"); // 사원명
				data[i][5] = Integer.toString(rs.getInt("SAL")); // 급여
				data[i][6] = rs.getString("JOB"); // 업무
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs);
			System.out.println("finally Success");
		}

	}

//	부서와 사원 테이블을 조인하여 가져오기
	public int insert(int deptno, String ename, int sal, String job) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		String sql = "insert into emp(empno, deptno, ename, sal, job)";
		sql+= " values(seq_emp.nextval, "+deptno+", '"+ename+"', '"+sal+"', '"+job+"')";
		
		try {
			pstmt = empMain.conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs);
			System.out.println("insert 성공");
		}
		return result;


	}

	public int getRowCount() {
		return data.length;
	}

	public int getColumnCount() {
		return column.length;
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public String getColumnName(int col) {
		return column[col];
	}
}
