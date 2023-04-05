package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class ProjectSalesDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(ProjectSalesDTO projectSalesDTO) {
		System.out.println("호출 후" + projectSalesDTO);
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "insert into projectsalesdiary(projectsalesdiary_idx, yy, mm, dd, content)";
		sql += " values(seq_projectsalesdiary.nextval, ?, ?, ?, ?)";

		int result = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, projectSalesDTO.getYy());
			pstmt.setInt(2, projectSalesDTO.getMm());
			pstmt.setInt(3, projectSalesDTO.getDd());
			pstmt.setString(4, projectSalesDTO.getContent());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	// 한 건 삭제
			public int delete(int projectsalesdiary_idx) {
				Connection con = null;
				PreparedStatement pstmt = null;
				int result = 0;

				con = dbManager.getConnection();
				String sql = "delete from projectsalesdiary where projectsalesdiary_idx=?";

				try {
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, projectsalesdiary_idx); // 바인드 변수값 지정
					result = pstmt.executeUpdate(); // DML수행 후 반영된 레코드 수 반환

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					dbManager.release(pstmt);
				}
				return result;
			}

	// 해당월에 등록된 다이어리 가져오기
	// 1부터 말일까지와 비교
	public List selectAll(int yy, int mm) {
//		List list = new ArrayList();
		// 1)반환값을 주면 디자인 쪽에서 처리해줘야함(디자인 영역에서 닫아야함)
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<ProjectSalesDTO> list = new ArrayList<ProjectSalesDTO>();// rs를 대신함
		
		conn = dbManager.getConnection();
		
		String sql = "select * from projectsalesdiary where yy=?";
		sql += " and mm=? order by projectsalesdiary_idx asc";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, yy);
			pstmt.setInt(2, mm);
			rs = pstmt.executeQuery();// 쿼리수행 및 테이블 반환

			// 2)그래서 rs정보를 담아서 전달해야함.
			// rs가 없어지기 전에 빼먹고 있음
			while (rs.next()) {// 총레코드 수를 모르기 때문에 for문은 사용 불가
				// 비어있는 DTO 하나 생성하자 /이유? 레코드 한건을 담기 위함.
				ProjectSalesDTO projectSalesDTO = new ProjectSalesDTO();// DTO
				projectSalesDTO.setProjectsalesdiary_idx(rs.getInt("projectsalesdiary_idx"));
				projectSalesDTO.setYy(rs.getInt("yy"));
				projectSalesDTO.setMm(rs.getInt("mm"));
				projectSalesDTO.setDd(rs.getInt("dd"));
				projectSalesDTO.setContent(rs.getString("content"));

				// 레코드 한줄이 채워진 DTO를 ArrayList에 추가하자
				list.add(projectSalesDTO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	
	public ProjectSalesDTO selectByDates(ProjectSalesDTO projectSalesDTO) {
		ProjectSalesDTO result = null;
		String sql = "select * from projectsalesdiary where yy=? and mm=? and dd=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, projectSalesDTO.getYy());
			pstmt.setInt(2, projectSalesDTO.getMm());
			pstmt.setInt(3, projectSalesDTO.getDd());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = new ProjectSalesDTO();
				result.setProjectsalesdiary_idx(rs.getInt("projectsalesdiary_idx"));
				result.setYy(rs.getInt("yy"));
				result.setMm(rs.getInt("mm"));
				result.setDd(rs.getInt("dd"));
				result.setContent(rs.getString("content"));
				result.setIcon(rs.getString("icon"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return result;
	}
}