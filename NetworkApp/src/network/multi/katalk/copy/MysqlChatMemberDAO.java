package network.multi.katalk.copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import network.domain.ChatMember;
import network.util.DBManager;

public class MysqlChatMemberDAO implements ChatMemberDAO{
	
	DBManager dbManager=DBManager.getInstance();
	
	@Override
	public int insert(ChatMember chatMember) {
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="insert into chatmember(id, pass, name) values(?,?,?)";
		int result=0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, chatMember.getId());
			pstmt.setString(2, chatMember.getPass());
			pstmt.setString(3, chatMember.getName());
			result = pstmt.executeUpdate();//쿼리실행
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int update(ChatMember chatMember) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int chatmember_idx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatMember select(int chatmember_idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatMember select(ChatMember chatMember) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
