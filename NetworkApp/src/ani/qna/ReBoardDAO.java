package ani.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import network.util.DBManager;

// reboard 테이블에 대한 CRUD담당 객체

public class ReBoardDAO {
	DBManager dbManager = DBManager.getInstance();

	public int insert(ReBoard reboard) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();
//		Oracle
//		insert시 외부에 있는 시퀀스를 이용하므로 insert 하는 시점에 PK값 및 team이 결정될 수 있다
		String sql = "insert into reBoard(reBoard_idx, title, writer, content, team)";
		sql += " values(seq_reBoard.nextval, ?, ?, ?, seq_reBoard.nextval)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reboard.getTitle());
			pstmt.setString(2, reboard.getWriter());
			pstmt.setString(3, reboard.getContent());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	/*
	 * MySQL MySQL은 insert가 된 이후에나 PK값이 존재하므로 insert부터 완료한 후 가장 최근에 들어간 PK를 조사해야
	 * team값 설정 가능 String sql =
	 * "insert into reBoard(title, writer, content) values(?, ?, ?)"; int result =
	 * 0; Connection conn = null; PreparedStatement pstmt = null; ResultSet rs =
	 * null;
	 * 
	 * pstmt.setString if(result >0) { sql =
	 * "select last_insert_id() as reboard_idx"; pstmt2 = } if(rs.next()) {
	 * dbManager.release(pstmt); sql =
	 * "update reboard set team = ? where reBoard_idx=?"; pstmt =
	 * conn.prepareStatement(sql); pstmt.setInt(1, rs.getInt("reboard_idx"));
	 * pstmt.setInt(1, rs.getInt("reboard_idx")); } finally {
	 * 
	 * } return result; }
	 */

//	답변게시판의 모든 레코드 가져오기
//	동물 종류별로 뭉치기(order by category desc), 종류 내 정렬(rank asc)
	public List selectAll() {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = dbManager.getConnection();

		String sql = "select * from reboard order by team desc, step asc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReBoard reboard = new ReBoard();
				reboard.setReboard_idx(rs.getInt("reboard_idx"));
				reboard.setTitle(rs.getString("title"));
				reboard.setWriter(rs.getString("writer"));
				reboard.setContent(rs.getString("content"));
				reboard.setRegdate(rs.getString("regdate"));
				reboard.setHit(rs.getInt("hit"));
				reboard.setTeam(rs.getInt("team"));
				reboard.setStep(rs.getInt("step"));
				reboard.setDepth(rs.getInt("depth"));

				list.add(reboard); // 리스트에 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

//	레코드 한 건 가져오기
//	이 메서드를 호출하는 자는 반환값이 null인 경우 일치하는 데이터가 없음을 판단
//	반환값이 null이 아닌 경우 조건과 일치하는 레코드가 있음을 판단
	public ReBoard select(int reboard_idx) {
		ReBoard reboard = null; // 레코드가 있을 때만 new
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = dbManager.getConnection();

		String sql = "select * from reboard where reboard_idx=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reboard_idx); // PK

			rs = pstmt.executeQuery();

			if (rs.next()) { // 레코드가 있다면 DTO 생성
				reboard = new ReBoard();
				reboard.setReboard_idx(rs.getInt("reboard_idx"));
				reboard.setTitle(rs.getString("title"));
				reboard.setWriter(rs.getString("writer"));
				reboard.setContent(rs.getString("content"));
				reboard.setRegdate(rs.getString("regdate"));
				reboard.setHit(rs.getInt("hit"));
				reboard.setTeam(rs.getInt("team"));
				reboard.setStep(rs.getInt("step"));
				reboard.setDepth(rs.getInt("depth"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return reboard;
	}

//	답변이 들어갈 자리를 조사하기 위한 메서드
//	매개변수로 넘어오게 될 reboard에는 상세보기에 사용한 한 건의 레코드
	public int selectMaxStep(ReBoard reboard) {
		int step = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = dbManager.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("select max(step) as step from reboard");
		sb.append(" where team =? and depth=? and step > ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reboard.getTeam()); // 내가 본 글의 team
			pstmt.setInt(2, reboard.getDepth() + 1); // 내가 본 글의 depth보다 +1
			pstmt.setInt(3, reboard.getStep()); // 내가 본 글의 step
			rs = pstmt.executeQuery();

			if (rs.next()) { // 선행된 답변이 있다면
				step = rs.getInt("step");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return step;
	}

//	답변이 들어갈 자리 확보(답변이 들어갈 자리에 이미 선행된 step이 있다면 한 칸씩 밀리게 처리)
//	매개변수 reboard는 상세보기를 담고 있는 DTO
//	주의 : 기존 DTO step의 값에 위에서 구한 max(step)을 심어준 상태로 넘겨받자
	public int updateStep(ReBoard reboard) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = dbManager.getConnection();

		String sql = "update reboard set step=step+1";
		sql += " where team = ? and step > ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reboard.getTeam()); // 내가 본 글 team
			pstmt.setInt(2, reboard.getStep()); // selectMaxStep() 반환받은 step
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
//	답변 등록
	public int reply(ReBoard reboard) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		conn = dbManager.getConnection();
		
		String sql = "insert into reboard(reboard_idx, title, writer, content, team, step, depth)"; // 답변들은 값이 살아있기 때문에 default 값을 주면 안됨
		sql+= " values(seq_reboard.nextval, ?, ?, ?, ?, ?, ?)"; // 6개 물음표
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reboard.getTitle());
			pstmt.setString(2, reboard.getWriter());
			pstmt.setString(3, reboard.getContent());
			pstmt.setInt(4, reboard.getTeam()); // 내가 본 글의 team
			pstmt.setInt(5, reboard.getStep()+1); // selectMaxStep()에서 반환받은 값
			pstmt.setInt(6, reboard.getDepth()+1); // 내가 본 글의 depth +1
			pstmt.setInt(4, reboard.getTeam());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}
}
