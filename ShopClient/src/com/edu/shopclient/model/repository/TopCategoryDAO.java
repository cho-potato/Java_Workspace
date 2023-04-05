package com.edu.shopclient.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shopclient.domain.TopCategory;
import com.edu.shopclient.util.DBManager;

// 이 클래스는 topCategory 테이블에 대해서 CRUD 수행을 목적으로 정의함
// SELECT문을 AdminMain에서 작성해도 프로그램은 수행될 수 있으나,
// 개발방법적인 측면에서 본다면 유지보수성이 떨어진다
// DAO로 별도로 정의해놓으면 admin 뿐만 아니라 customer 입장에서도 이용 가능하다 (재사용=시간==돈)

public class TopCategoryDAO {
	DBManager dbManager = DBManager.getInstance();

//	모두 가져오기
	public List selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<TopCategory> list = new ArrayList<TopCategory>();

		conn = dbManager.getConnection(); // 기존에 접속되어 있는 것을 얻어온 상태. 접속을 여기서 한 것이 아님
		String sql = "select * from topCategory order by topCategory_idx asc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) { // 레코드가 있는 만큼
				TopCategory topCategory = new TopCategory();
				topCategory.setTopcategory_idx(rs.getInt("topCategory_idx"));
				topCategory.setTopcategory_name(rs.getString("topCategory_name"));
				list.add(topCategory); // DTO 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list; // rs를 대신할 list를 반환한다
	}
//	카테고리 이름으로 Primary Key 반환하기
	public int gettopCategoryIdx(String topCategory_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int topCategory_idx = 0;
		
		String sql = "select topCategory_idx from topCategory";
		sql += " where topCategory_name=?"; // 상의 ,,, 하의 ,,,
		
		conn = dbManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  topCategory_name);
			rs = pstmt.executeQuery();
			if(rs.next() ) { //레코드가 존재한다면
				topCategory_idx = rs.getInt("topCategory_idx");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		} 
		
		return topCategory_idx;
	}
}
