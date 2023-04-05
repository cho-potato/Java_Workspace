package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class ProjectWorkDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(ProjectWorkDTO projectWorkDTO) {
		System.out.println("호출 후" + projectWorkDTO);
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "insert into projectworkdiary(projectworkdiary_idx, yy, mm, dd, content)";
		sql += " values(seq_projectworkdiary.nextval, ?, ?, ?, ?)";

		int result = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, projectWorkDTO.getYy());
			pstmt.setInt(2, projectWorkDTO.getMm());
			pstmt.setInt(3, projectWorkDTO.getDd());
			pstmt.setString(4, projectWorkDTO.getContent());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	// 한 건 삭제
			public int delete(int projectworkdiary_idx) {
				Connection con = null;
				PreparedStatement pstmt = null;
				int result = 0;

				con = dbManager.getConnection();
				String sql = "delete from projectworkdiary where projectworkdiary_idx=?";

				try {
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, projectworkdiary_idx); // 바인드 변수값 지정
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
		
		ArrayList<ProjectWorkDTO> list = new ArrayList<ProjectWorkDTO>();// rs를 대신함
		
		conn = dbManager.getConnection();
		
		String sql = "select * from projectworkdiary where yy=?";
		sql += " and mm=? order by projectworkdiary_idx asc";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, yy);
			pstmt.setInt(2, mm);
			rs = pstmt.executeQuery();// 쿼리수행 및 테이블 반환

			// 2)그래서 rs정보를 담아서 전달해야함.
			// rs가 없어지기 전에 빼먹고 있음
			while (rs.next()) {// 총레코드 수를 모르기 때문에 for문은 사용 불가
				// 비어있는 DTO 하나 생성하자 /이유? 레코드 한건을 담기 위함.
				ProjectWorkDTO projectWorkDTO = new ProjectWorkDTO();// DTO
				projectWorkDTO.setProjectworkdiary_idx(rs.getInt("projectworkdiary_idx"));
				projectWorkDTO.setYy(rs.getInt("yy"));
				projectWorkDTO.setMm(rs.getInt("mm"));
				projectWorkDTO.setDd(rs.getInt("dd"));
				projectWorkDTO.setContent(rs.getString("content"));

				// 레코드 한줄이 채워진 DTO를 ArrayList에 추가하자
				list.add(projectWorkDTO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	
	public ProjectWorkDTO selectByDates(ProjectWorkDTO projectWorkDTO) {
		ProjectWorkDTO result = null;
		String sql = "select * from projectworkdiary where yy=? and mm=? and dd=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, projectWorkDTO.getYy());
			pstmt.setInt(2, projectWorkDTO.getMm());
			pstmt.setInt(3, projectWorkDTO.getDd());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = new ProjectWorkDTO();
				result.setProjectworkdiary_idx(rs.getInt("projectworkdiary_idx"));
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