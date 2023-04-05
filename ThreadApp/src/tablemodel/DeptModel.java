package tablemodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

// JTable이 사용할 Dept 테이블에 대한 정보를 가진 객체

public class DeptModel extends AbstractTableModel{
	AppMain3 appMain3;
	String[][] data;
	String[] column={"DEPTNO", "DNAME", "LOC"};

	public DeptModel(AppMain3 appMain3) {
		this.appMain3 = appMain3;
		
		select();
		
	}
	
	public void select () { // 부서 테이블 가져오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from dept order by deptno asc";
		
		try {
			pstmt = appMain3.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // select문 수행 후 표 반환
			
			rs.last();
			int total = rs.getRow(); //  총 레코드 수 얻기
			
//			 2차원 배열 생성하기
			
			data = new String[total][column.length];
			
//			rs 커서 원상복귀
			rs.beforeFirst();
			
			for(int i = 0; i<total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("DEPTNO"));
				data[i][1] = rs.getString("DNAME");
				data[i][2] = rs.getString("LOC");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			appMain3.release(pstmt, rs);
		}
	}
	public int getRowCount() {

		return data.length;
	}

	public int getColumnCount() {

		return column.length;
	}

	public Object getValueAt(int row, int col) { // 각 표에 들어갈 값을 반환하는 메서드

		return data[row][col];
	}
	public String getColumnName(int col) {
		return column[col];
	}
}
