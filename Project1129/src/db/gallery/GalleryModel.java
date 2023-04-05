package db.gallery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

// Gallery 테이블과 관련된 CRUD를 담당하고  JTable에게 정보를 제공해주는 역할의 객체

public class GalleryModel extends AbstractTableModel{
	GalleryMain galleryMain;
	
	String[] column={"gallery_id", "title", "writer", "content", "fileName", "regdate"}; // 1차원 배열
	String[][] data; // 2차원 배열
	
	public GalleryModel(GalleryMain galleryMain) {
		this.galleryMain = galleryMain;
		selectAll();
	}
//	모든 데이터 가져오기
	public void selectAll() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null; 
		sql = "select * from gallery order by gallery_id desc";
		System.out.println(sql);
		
		try {
			pstmt = galleryMain.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			- - - - - - - - - - - - - - - - 쿼리문 수행객체 생성(준비)
			rs = pstmt.executeQuery();
//			- - - - - - - - - - - - - - - - 결과 반환
			
			rs.last();
			int total = rs.getRow();
			data = new String[total][column.length];
//			- - - - - - - - - - - - - - - - 2차원 배열 생성
			rs.beforeFirst(); // 커서 원상복귀
			for (int i = 0; i<total; i++) {
//			String[] column={"gallery_id", "title", "writer", "content", "fileName", "regdate"}; // 1차원 배열
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("gallery_id")); // primary key가 들어있기 때문에
				data[i][1] = rs.getString("title"); 
				data[i][2] = rs.getString("writer"); 
				data[i][3] = rs.getString("content"); 
				data[i][4] = rs.getString("fileName"); 
				data[i][5] = rs.getString("regdate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			galleryMain.release(pstmt, rs);
		}
		
	}
//	1건 가져오기
	public void select() {
		
	}
//	insert
	public int insert(String title,String writer, String content, String fileName) {
		PreparedStatement pstmt = null;
		
		String sql = "insert into gallery(gallery_id, title, writer, content, fileName)";
		sql += "values(seq_gallery.nextval,'"+title+"', '"+writer+"', '"+content+"', '"+fileName+"')";
		int result = 0;
		
		try {
			pstmt = galleryMain.conn.prepareStatement(sql);
			result = pstmt.executeUpdate(); // 쿼리수행
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			galleryMain.release(pstmt);
		}
		return result;
	}
//	update
	public void update() {
		
	}
//	delete
	public void delete() {
		
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
