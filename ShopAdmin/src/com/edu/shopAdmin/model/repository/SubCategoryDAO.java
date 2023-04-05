package com.edu.shopAdmin.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shopAdmin.domain.SubCategory;
import com.edu.shopAdmin.domain.TopCategory;
import com.edu.shopAdmin.util.DBManager;

// 이 클래스는 오직 SubCategory 테이블에 대한 CRUD만을 담당

public class SubCategoryDAO {
//	상위 카테고리 중 하나를 선택하면 해당 하위 카테고리를 조회
	DBManager dbManager = DBManager.getInstance();

	public List selectByTopCategory(int topCategory_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<SubCategory> list = new ArrayList<SubCategory>();

		String sql = "select * from subcategory";
//		sql += " where topcategory_idx="+topcategory_idx;
		sql += " where topcategory_idx=?";

//		새롭게 접속이 발생하는게 아니라, 싱글턴 객체가 이미 보유한 Connection을 얻어오는 것
//		즉, 이미 접속된 상태의 Connection 객체를 얻어오는 것
		conn = dbManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topCategory_idx);
			rs = pstmt.executeQuery();
//			rs의 값들을 DTO에 담아서 list에 모으자
//			이렇게 하면 rs는 더이상 필요없고 close()가능
			while(rs.next()) {
				SubCategory subCategory=new SubCategory();
//				has a 관계로 보유한 TopCategory DTO도 생성하자
				TopCategory topCategory = new TopCategory();
				
				subCategory.setTopCategory(topCategory); // 연결				
                subCategory.setSubcategory_idx(rs.getInt("subcategory_idx")); // PK
//             subCategory.setTopcategory_idx(topCategory_idx);// FK rs.getInt("topcategory_idx")
                topCategory.setTopcategory_idx(topCategory_idx); // 연결했기 때문에 가능한 것 
                subCategory.setSubcategory_name(rs.getString("subcategory_name"));
                list.add(subCategory);//리스트에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbManager.release(pstmt, rs);
        }
        return list;
    }
}
