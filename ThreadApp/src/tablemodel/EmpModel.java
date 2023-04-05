package tablemodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.table.AbstractTableModel;

//	JTable은 유지보수성을 높이기 위해 데이터와 디자인을 분리하기 위한 방법을 제공해주는데,
//	이 때 사용되는 객체가 바로 TableModel이다
// TableModel은 인터페이스로 사용하려면 자식에게 상속 후 자식을 생성해야함
// TableModel의 자식은 AbstractTableModel(추상클래스), DefaultTableModel(일반 클래스)이 있다
// AbstractTableModel(추상클래스) -> 메서드 오버라이딩 해야함

public class EmpModel extends AbstractTableModel {
	AppMain3 appMain3;
	String[] column = { "EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO" };
//	String[][] data = new String[5][column.length];
	String[][] data;
	
//	이하 작성하는 내용에 따라 JTable에 값 반영됨
//	TableModel이 보유한 메서드들은 JTable이 호출하여 화면에 반양한다

//	태어날 때 AppMain3를 넘겨받자
	public EmpModel(AppMain3 appMain3) {
		this.appMain3 = appMain3;
		
		select();
	}
	
//	EMP의 레코드 가져오기
	private void select() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from emp order by empno asc";
		try {
			pstmt = appMain3.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			
			rs.last(); // 스크롤 가능한 rs여야 한다
			int total = rs.getRow(); // 총 레코드 수 반환
			
//			위 정보들을 이용하여 2차원 배열생성
			data = new String[total][column.length];
			
			rs.beforeFirst();
			for (int i = 0; i<total; i++) {
				rs.next(); // 커서 한 칸 전진
				data[i][0] = Integer.toString(rs.getInt("EMPNO"));
				data[i][1] = rs.getString("ENAME");
				data[i][2] = rs.getString("JOB");
				data[i][3] = Integer.toString(rs.getInt("MGR"));
				data[i][4] = rs.getString("HIREDATE");
				data[i][5] = Integer.toString(rs.getInt("SAL"));
				data[i][6] = Integer.toString(rs.getInt("COMM"));
				data[i][7] = Integer.toString(rs.getInt("DEPTNO"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			appMain3.release(pstmt, rs);
		}
	}
//	총 레코드 수
	public int getRowCount() {

		return data.length;
	}
	
//	컬럼 수
	public int getColumnCount() {

		return column.length;
	}
	
//	2차원 배열을 이루는 각 요소들에 들어있는 값 반환(칸에 대한 값)
	public Object getValueAt(int row, int col) { // getRowCount, getColumnCount를 가져옴
		System.out.println("getValueAt("+row+", "+col+") 출출");
		return data[row][col]; // 자동으로 표를 채워줌
	}
//	위의 세 가지 메서드 뿐만 아니라, 추가적으로 필요한 메서드가 있다면 재정의하자

	public String getColumnName(int col) {
		System.out.println(col+"?????");
		return column[col];
		
	}
}
