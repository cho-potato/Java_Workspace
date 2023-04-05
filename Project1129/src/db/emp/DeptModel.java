package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

// Dept table에 대한 CRUD 및 JTable에 정보제공

public class DeptModel extends AbstractTableModel {
	String[] column = { "DEPTNO", "DNAME", "LOC" };
	String[][] data;
	EmpMain empMain; // EmpMain에 Connection이 있으므로

	public DeptModel(EmpMain empMain) {
		this.empMain = empMain;
		selectAll();
	}

//	CRUD

	public void selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from dept order by deptno asc";
		try {
			pstmt = empMain.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 순서대로 작성
			rs = pstmt.executeQuery(); // select 수행 및 테이블 반환

			rs.last(); // Scroll이 가능해야 메서드 호출이 가능함
			int total = rs.getRow();

			data = new String[total][column.length];
//			- - - - - - - - - - - - - - - - - - - - - - - - - - - 2차원 배열 생성
			rs.beforeFirst();
			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("DEPTNO"));
				data[i][0] = rs.getString("DNAME");
				data[i][0] = rs.getString("LOC");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs);
		}
	}

	public int insert(String dname, String loc) {
		PreparedStatement pstmt = null; // 쿼리문이 두 개면 pstmt도 두 개여야 함
		ResultSet rs = null; // 시퀀스 담기 위한

		String sql = "insert into dept(deptno, dname, loc)";
		sql += "values(seq_dept.nextval, 'dname', 'loc')";
		int deptno = 0;

		try {
			pstmt = empMain.conn.prepareStatement(sql);
			int result = pstmt.executeUpdate(); // 1건 등록 설정
			if (result > 0) {
				sql = "select seq_dept.currval as deptno from dual";
//				PreparedStatement, ResultSet은 쿼리문마다 일대일 대응한다(=생성하여 사용한다)
				pstmt = empMain.conn.prepareStatement(sql); // 별도의 try - catch문 필요없음
				rs = pstmt.executeQuery(); // 별도의 try - catch문 필요없음
				rs.next(); // 커서 한 칸 전진(스크롤 필요없음)
				deptno = rs.getInt("deptno"); // seq_dept.currval를 as 통해 deptno로 받음

			}
			System.out.println("DeptModel insert 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs);
		}
		return deptno;

	}

	public int update() {
		return 0;
	}

	public int delete() {
		return 0;
	}

	public int getRowCount() {

		return data.length; // 층수
	}

	public int getColumnCount() {

		return column.length; // 호수
	}

	public Object getValueAt(int row, int col) {

		return data[row][col];
	}

	public String getColumnName(int col) {

		return column[col];
	}

}
