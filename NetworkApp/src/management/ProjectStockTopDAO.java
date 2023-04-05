package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class ProjectStockTopDAO {
	DBManager dbManager = DBManager.getInstance();

//	모두 가져오기
	public List selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProjectStockTopDTO> list = new ArrayList<ProjectStockTopDTO>();

		conn = dbManager.getConnection(); // 기존에 접속되어 있는 것을 얻어온 상태. 접속을 여기서 한 것이 아님
		String sql = "select * from stocktop order by stocktop_idx asc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) { // 레코드가 있는 만큼
				ProjectStockTopDTO projectStockTopDTO = new ProjectStockTopDTO();
				projectStockTopDTO.setStocktop_idx(rs.getInt("stocktop_idx"));
				projectStockTopDTO.setStocktop_name(rs.getString("stocktop_name"));
				list.add(projectStockTopDTO); // DTO 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list; // rs를 대신할 list를 반환한다
	}
//	카테고리 이름으로 Primary Key 반환하기
	public int getStocktop_idx(String stocktop_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int stocktop_idx = 0;
		
		String sql = "select stocktop_idx from stocktop";
		sql += " where stocktop_name=?"; // 상의 ,,, 하의 ,,,
		
		conn = dbManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  stocktop_name);
			rs = pstmt.executeQuery();
			if(rs.next() ) { //레코드가 존재한다면
				stocktop_idx = rs.getInt("stocktop_idx");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		} 
		
		return stocktop_idx;
	}
}

