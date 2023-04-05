package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class StockProductDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(StockProductDTO stockProductDTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "insert into stockproduct(stockproduct_idx, stocksub_idx, stockproduct_name, brand, price, filename)";
		sql += " values(seq_stockproduct.nextval, ?,?,?,?,?)";
//		sql+=" values(seq_product.nextval, "+subcategory_idx+", '"+product_name+"', '"+brand+"', "+price+", '"+filename+"')";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
//			쿼리 수행 전 바인드 변수 값을 정하자
			pstmt.setInt(1, stockProductDTO.getProjectStockSubDTO().getStocksub_idx());
			pstmt.setString(2, stockProductDTO.getStockproduct_name());
			pstmt.setString(3, stockProductDTO.getBrand());
			pstmt.setInt(4, stockProductDTO.getPrice());
			pstmt.setString(5, stockProductDTO.getFilename());
//			DML 수행
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		System.out.println("StockProductDAO" + sql);
		return result;
	}

//	하위 카테고리와 조인한 모든 상품들 가져오기
	public List selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<StockProductDTO> list = new ArrayList<StockProductDTO>();
		conn = dbManager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"select s.stocksub_idx as stocksub_idx, stocksub_name, stockproduct_idx, stockproduct_name, brand, price, filename");
		sb.append(" from stocksub s , stockproduct p ");
		sb.append(" where s.stocksub_idx = p.stocksub_idx");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				StockProductDTO stockProductDTO = new StockProductDTO();
				ProjectStockSubDTO projectStockSubDTO = new ProjectStockSubDTO();
//				subCategory를 product에 넣어주자
				stockProductDTO.setProjectStockSubDTO(projectStockSubDTO);
//				SubCategory subCategory = product.getSubcategory(); // null

				projectStockSubDTO.setStocksub_idx(rs.getInt("stocksub_idx"));
				projectStockSubDTO.setStocksub_name((rs.getString("stocksub_name")));
				stockProductDTO.setStockproduct_idx(rs.getInt("stockproduct_idx"));
				stockProductDTO.setStockproduct_name(rs.getString("stockproduct_name"));
				stockProductDTO.setBrand(rs.getString("brand"));
				stockProductDTO.setPrice(rs.getInt("price"));
				stockProductDTO.setFilename(rs.getString("filename"));

//				이미 Product가 가진 SubCategory DTO이므로 Product만 담으면 됨 (Has a 관계)
				list.add(stockProductDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

//	상, 하위 카테고리와 join하여 상품 1건 가져오기
	public StockProductDTO select(int stockproduct_idx) {// 3개 테이블을 조인하자
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StockProductDTO stockProductDTO = null;// 리턴하기 위해서
		// 레코드가 있는 if문을 만나면 레코드가 있다, null이면 레코드가 없다.

		con = dbManager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("select t.stocktop_idx as stocktop_idx, stocktop_name");
		sb.append(" , s.stocksub_idx as stocksub_idx, stocksub_name");
		sb.append(" , stockproduct_idx, stockproduct_name, brand, price, filename");
		sb.append(" from stocktop t, stocksub s, stockproduct p");
		sb.append(" where t.stocktop_idx = s.stocktop_idx");
		sb.append(" and s.stocksub_idx = p.stocksub_idx");
		sb.append(" and p.stockproduct_idx = ?"); // 이 부분(1건 나오는 부분) 때문에 while문이 아니라 if문이 적당
//		int형의 product_idx를 구하기 위한 쿼리문

		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, stockproduct_idx);
			rs = pstmt.executeQuery();

			// 레코드가 있다면 커서 이동시 true 반환
			if (rs.next()) {
				stockProductDTO = new StockProductDTO();
				ProjectStockSubDTO projectStockSubDTO = new ProjectStockSubDTO();
				ProjectStockTopDTO projectStockTopDTO = new ProjectStockTopDTO();

				// 연결하기
				projectStockSubDTO.setProjectStockTopDTO(projectStockTopDTO);
				stockProductDTO.setProjectStockSubDTO(projectStockSubDTO);

				// 상위 담기
				projectStockTopDTO.setStocktop_idx(rs.getInt("stocktop_idx"));
				projectStockTopDTO.setStocktop_name(rs.getString("stocktop_name"));

				// 하위 담기
				projectStockSubDTO.setStocksub_idx(rs.getInt("stocksub_idx"));
				projectStockSubDTO.setStocksub_name(rs.getString("stocksub_name"));

				// 상품담기
				stockProductDTO.setStockproduct_idx(rs.getInt("stockproduct_idx"));
				stockProductDTO.setStockproduct_name(rs.getString("stockproduct_name"));
				stockProductDTO.setBrand(rs.getString("brand"));
				stockProductDTO.setPrice(rs.getInt("price"));
				stockProductDTO.setFilename(rs.getString("filename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return stockProductDTO;
	}

//	레코드 1건 삭제
	public int delete(int stockproduct_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		conn = dbManager.getConnection();

		String sql = "delete stockproduct where stockproduct_idx=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stockproduct_idx);
			result = pstmt.executeUpdate(); // 삭제
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

//	레코드 1건 수정
	public int update(StockProductDTO stockProductDTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "update stockproduct set stocksub_idx=?";
		sql += " , stockproduct_name=?, brand=?, price=?, filename=?";
		sql += " where stockproduct_idx=?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stockProductDTO.getProjectStockSubDTO().getStocksub_idx());
			pstmt.setString(2, stockProductDTO.getStockproduct_name());
			pstmt.setString(3, stockProductDTO.getBrand());
			pstmt.setInt(4, stockProductDTO.getPrice());
			pstmt.setString(5, stockProductDTO.getFilename());
			pstmt.setInt(6, stockProductDTO.getStockproduct_idx());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
}