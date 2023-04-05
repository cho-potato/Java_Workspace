package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;


public class ProjectStockSubDAO {
//	상위 카테고리 중 하나를 선택하면 해당 하위 카테고리를 조회
	DBManager dbManager = DBManager.getInstance();

	public List selectByTopCategory(int stocktop_idx ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProjectStockSubDTO> list = new ArrayList<ProjectStockSubDTO>();

		String sql = "select * from stocksub";
//		sql += " where topcategory_idx="+topcategory_idx;
		sql += " where stocktop_idx=?";

//		새롭게 접속이 발생하는게 아니라, 싱글턴 객체가 이미 보유한 Connection을 얻어오는 것
//		즉, 이미 접속된 상태의 Connection 객체를 얻어오는 것
		conn = dbManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stocktop_idx);
			rs = pstmt.executeQuery();
//			rs의 값들을 DTO에 담아서 list에 모으자
//			이렇게 하면 rs는 더이상 필요없고 close()가능
			while(rs.next()) {
				ProjectStockSubDTO projectStockSubDTO = new ProjectStockSubDTO();
//				has a 관계로 보유한 TopCategory DTO도 생성하자
				ProjectStockTopDTO projectStockTopDTO = new ProjectStockTopDTO();
				
				projectStockSubDTO.setProjectStockTopDTO(projectStockTopDTO);  // 연결				
				projectStockSubDTO.setStocksub_idx(rs.getInt("stocksub_idx")); // PK
//             subCategory.setTopcategory_idx(topCategory_idx);// FK rs.getInt("topcategory_idx")
				projectStockTopDTO.setStocktop_idx(stocktop_idx); // 연결했기 때문에 가능한 것 
                projectStockSubDTO.setStocksub_name(rs.getString("stocksub_name"));
                list.add(projectStockSubDTO);//리스트에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbManager.release(pstmt, rs);
        }
        return list;
    }
}

