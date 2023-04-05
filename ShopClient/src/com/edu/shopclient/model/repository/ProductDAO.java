package com.edu.shopclient.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shopclient.domain.Product;
import com.edu.shopclient.domain.SubCategory;
import com.edu.shopclient.domain.TopCategory;
import com.edu.shopclient.util.DBManager;

// Product table에 대한 CRUD를 수행할 객체

public class ProductDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "insert into product(product_idx, subcategory_idx, product_name, brand, price, filename)";
		sql += " values(seq_product.nextval, ?,?,?,?,?)";
//		sql+=" values(seq_product.nextval, "+subcategory_idx+", '"+product_name+"', '"+brand+"', "+price+", '"+filename+"')";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
//			쿼리 수행 전 바인드 변수 값을 정하자
			pstmt.setInt(1, product.getSubcategory().getSubcategory_idx());
			pstmt.setString(2, product.getProduct_name());
			pstmt.setString(3, product.getBrand());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFilename());
//			DML 수행
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		System.out.println("ProductDAO" + sql);
		return result;
	}

//	하위 카테고리와 조인한 모든 상품들 가져오기
	public List selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<Product>();
		conn = dbManager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"select s.subcategory_idx as subcategory_idx, subcategory_name, product_idx, product_name, brand, price, filename");
		sb.append(" from subcategory s , product p ");
		sb.append(" where s.subcategory_idx = p.subcategory_idx");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				SubCategory subCategory = new SubCategory();
//				subCategory를 product에 넣어주자
				product.setSubcategory(subCategory);
//				SubCategory subCategory = product.getSubcategory(); // null

				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name((rs.getString("subcategory_name")));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));

//				이미 Product가 가진 SubCategory DTO이므로 Product만 담으면 됨 (Has a 관계)
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

//	선택한 상위 카테고리에 소속된 모든 상품 가져오기
	public List selectAll(String topcategory_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<Product>();
		conn = dbManager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"select s.subcategory_idx as subcategory_idx, subcategory_name, product_idx, product_name, brand, price, filename");
		sb.append(" from topcategory t, subcategory s , product p ");
		sb.append(" where t.topcategory_idx = s.topcategory_idx");
		sb.append(" and s.subcategory_idx = p.subcategory_idx");
		sb.append(" and topcategory_name = ?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, topcategory_name);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				SubCategory subCategory = new SubCategory();
//				subCategory를 product에 넣어주자
				product.setSubcategory(subCategory);
//				SubCategory subCategory = product.getSubcategory(); // null

				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name((rs.getString("subcategory_name")));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));

//				이미 Product가 가진 SubCategory DTO이므로 Product만 담으면 됨 (Has a 관계)
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}


//	상, 하위 카테고리와 join하여 상품 1건 가져오기
	public Product select(int product_idx) {// 3개 테이블을 조인하자
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;// 리턴하기 위해서
		// 레코드가 있는 if문을 만나면 레코드가 있다, null이면 레코드가 없다.

		con = dbManager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("select t.topcategory_idx as topcategory_idx, topcategory_name");
		sb.append(" , s.subcategory_idx as subcategory_idx, subcategory_name");
		sb.append(" , product_idx, product_name, brand, price, filename");
		sb.append(" from topcategory t, subcategory s, product p");
		sb.append(" where t.topcategory_idx = s.topcategory_idx");
		sb.append(" and s.subcategory_idx = p.subcategory_idx");
		sb.append(" and p.product_idx = ?"); // 이 부분(1건 나오는 부분) 때문에 while문이 아니라 if문이 적당
//		int형의 product_idx를 구하기 위한 쿼리문

		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, product_idx);
			rs = pstmt.executeQuery();

			// 레코드가 있다면 커서 이동시 true 반환
			if (rs.next()) {
				product = new Product();
				SubCategory subCategory = new SubCategory();
				TopCategory topCategory = new TopCategory();

				// 연결하기
				subCategory.setTopCategory(topCategory);
				product.setSubcategory(subCategory);

				// 상위 담기
				topCategory.setTopcategory_idx(rs.getInt("topcategory_idx"));
				topCategory.setTopcategory_name(rs.getString("topcategory_name"));

				// 하위 담기
				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));

				// 상품담기
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return product;
	}

//	레코드 1건 삭제
	public int delete(int product_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		conn = dbManager.getConnection();

		String sql = "delete product where product_idx=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_idx);
			result = pstmt.executeUpdate(); // 삭제
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

//	레코드 1건 수정
	public int update(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "update product set subcategory_idx=?";
		sql += " , product_name=?, brand=?, price=?, filename=?";
		sql += " where product_idx=?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product.getSubcategory().getSubcategory_idx());
			pstmt.setString(2, product.getProduct_name());
			pstmt.setString(3, product.getBrand());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFilename());
			pstmt.setInt(6, product.getProduct_idx());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
}
