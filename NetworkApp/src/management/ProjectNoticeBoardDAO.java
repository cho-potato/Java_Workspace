package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

public class ProjectNoticeBoardDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(ProjectNoticeBoardDTO projectNoticeBoardDTO) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "insert into projectnoticeboard(projectNoticeBoard_idx, title, writer, content, team)";
		sql += " values(seq_projectnoticeboard.nextval, ?, ?, ?, seq_projectnoticeboard.nextval)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectNoticeBoardDTO.getTitle());
			pstmt.setString(2, projectNoticeBoardDTO.getWriter());
			pstmt.setString(3, projectNoticeBoardDTO.getContent());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	// 한 건 수정하기
	public int update(ProjectNoticeBoardDTO projectNoticeBoardDTO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0; // 반환 값으로 사용해야해서 try문 밖으로 빼줌

		con = dbManager.getConnection();
		String sql = "update projectNoticeBoard set title=?, content=? where projectNoticeBoard_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			// 바인드 변수 주기,, 4개니까 낱개로 얻어오지 말고 DTO로 전달받자
			pstmt.setString(1, projectNoticeBoardDTO.getTitle());
			pstmt.setString(2, projectNoticeBoardDTO.getContent());
			pstmt.setInt(3, projectNoticeBoardDTO.getProjectNoticeBoard_idx());

			// DML문이므로 executeUpdate() 메서드 호출. 이때 이 메서드 호출 후 반환되는 값은
			// 이 쿼리 수행시 영향을 받은 레코드 수가 반환되므로, 반환값이 0인 경우, 수정된 레코드가 없다는 뜻.

			result = pstmt.executeUpdate(); // 쿼리 수행

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	// 한 건 삭제
		public int delete(int projectNoticeBoard_idx) {
			Connection con = null;
			PreparedStatement pstmt = null;
			int result = 0;

			con = dbManager.getConnection();
			String sql = "delete from projectNoticeBoard where projectNoticeBoard_idx=?";

			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, projectNoticeBoard_idx); // 바인드 변수값 지정
				result = pstmt.executeUpdate(); // DML수행 후 반영된 레코드 수 반환

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbManager.release(pstmt);
			}
			return result;
		}

	public List selectAll() {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = dbManager.getConnection();

		String sql = "select * from projectnoticeboard order by team desc, step desc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProjectNoticeBoardDTO projectNoticeBoardDTO = new ProjectNoticeBoardDTO();
				projectNoticeBoardDTO.setProjectNoticeBoard_idx(rs.getInt("projectNoticeBoard_idx"));
				projectNoticeBoardDTO.setTitle(rs.getString("title"));
				projectNoticeBoardDTO.setWriter(rs.getString("writer"));
				projectNoticeBoardDTO.setContent(rs.getString("content"));
				projectNoticeBoardDTO.setRegdate(rs.getString("regdate"));
				projectNoticeBoardDTO.setHit(rs.getInt("hit"));
				projectNoticeBoardDTO.setTeam(rs.getInt("team"));
				projectNoticeBoardDTO.setStep(rs.getInt("step"));
				projectNoticeBoardDTO.setDepth(rs.getInt("depth"));

				list.add(projectNoticeBoardDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	public ProjectNoticeBoardDTO select(int projectNoticeBoard_idx) {
		ProjectNoticeBoardDTO projectNoticeBoardDTO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = dbManager.getConnection();

		String sql = "select * from projectnoticeboard where projectnoticeboard_idx=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, projectNoticeBoard_idx);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				projectNoticeBoardDTO = new ProjectNoticeBoardDTO();
				projectNoticeBoardDTO.setProjectNoticeBoard_idx(rs.getInt("projectNoticeBoard_idx"));
				projectNoticeBoardDTO.setTitle(rs.getString("title"));
				projectNoticeBoardDTO.setWriter(rs.getString("writer"));
				projectNoticeBoardDTO.setContent(rs.getString("content"));
				projectNoticeBoardDTO.setRegdate(rs.getString("regdate"));
				projectNoticeBoardDTO.setHit(rs.getInt("hit"));
				projectNoticeBoardDTO.setTeam(rs.getInt("team"));
				projectNoticeBoardDTO.setStep(rs.getInt("step"));
				projectNoticeBoardDTO.setDepth(rs.getInt("depth"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return projectNoticeBoardDTO;
	}
}
