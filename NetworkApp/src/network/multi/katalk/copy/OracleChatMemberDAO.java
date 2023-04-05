package network.multi.katalk.copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import network.domain.ChatMember;
import network.util.DBManager;

// Oracle의  테이블에 대한 CRUD를 수행하는 전담 객체 ChatMemberDAO

public class OracleChatMemberDAO implements ChatMemberDAO{
   DBManager dbManager = DBManager.getInstance();
   
   public int insert(ChatMember chatMember) {
      Connection con = null;
      PreparedStatement pstmt = null;
      con = dbManager.getConnection();
      
      String sql = "insert into chatmember(chatmember_idx, id, pass, name)";
      sql+= " values(seq_chatmember.nextval, ?, ?, ?)";
      
      int result = 0;
      
      try {
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, chatMember.getId());	//바인드 변수 
         pstmt.setString(2, chatMember.getPass());
         pstmt.setString(3, chatMember.getName());
         
         result = pstmt.executeUpdate(); // 쿼리실행
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return result;
   }

   public int update(ChatMember chatMember) {

      return 0;
   }

   public int delete(int chatmember_idx) {

      return 0;
   }

   public List selectAll() {

      return null;
   }

   public ChatMember select(int chatmember_idx) {
      return null;
   }

   public ChatMember select(ChatMember chatMember) {
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      ChatMember obj = null; // 로그인 성공시 회원정보를 담아둘 DTO
      con = dbManager.getConnection();
      
      String sql = "select * from chatmember where id=? and pass=?";
      try {
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, chatMember.getId());
         pstmt.setString(2, chatMember.getPass());
         
         rs = pstmt.executeQuery();
         if(rs.next()) { // 레코드가 있다면 조건에 일치하는 회원이 존재한다는 것이고 회원이 존재한다는 것은 로그인 성공이라는 의미 
//            로그인이 성공하면 로그인한 회원의 정보를 담아서 반환
            obj = new ChatMember(); // empty 상태의 DTO 생성
            obj.setChatmember_idx(rs.getInt("chatmember_idx"));
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