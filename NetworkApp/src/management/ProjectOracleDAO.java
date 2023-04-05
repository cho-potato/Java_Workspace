package management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import network.util.DBManager;

public class ProjectOracleDAO implements ProjectMemberDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(ProjectMemberDTO projectMemberDTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "insert into projectmember(projectmember_idx, id, pass, name)";
		sql += " values(seq_projectmember.nextval, ?, ?, ?)";

		int result = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectMemberDTO.getId());
			pstmt.setString(2, projectMemberDTO.getPass());
			pstmt.setString(3, projectMemberDTO.getName());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int update(ProjectMemberDTO projectMemberDTO) {

		return 0;
	}

	public int delete(int projectmember_idx) {

		return 0;
	}

	public List selectAll() {

		return null;
	}

	public ProjectMemberDTO select(int projectmember_idx) {

		return null;
	}

	public ProjectMemberDTO select(ProjectMemberDTO projectMemberDTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProjectMemberDTO obj = null;
		
		conn = dbManager.getConnection();
		String sql = "select * from projectmember where id=? and pass=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectMemberDTO.getId());
			pstmt.setString(2, projectMemberDTO.getPass());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				obj = new ProjectMemberDTO();
				obj.setProjectmember_idx(rs.getInt("projectmember_idx"));
				obj.setId(rs.getString("id"));
				obj.setPass(rs.getString("pass"));
				obj.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return obj;
	}

}
