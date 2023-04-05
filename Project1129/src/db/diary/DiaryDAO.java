package db.diary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*이 객체에는 db관련 CRUD이외의 코드는 두지 않는다
 insert, update, delete, select 실행에 대한 코드만 두어야 하는 쿼리전담객체이다. 
 이러한 목적의 클래스를 가리켜 DAO(Data Access Object)라 한다.
 ex) 대형어플리케이션 분야에서 테이블이 25개라면 DAO도 25개 존재해야 한다. 
 * */
public class DiaryDAO {
	DBManager dbManager=DBManager.getInstance();
	
	//CURD중 insert를 정의한다. 
	public int insert(Diary diary) {
		System.out.println("호출후"+diary);

		PreparedStatement pstmt=null;
		
		String sql="insert into diary(diary_idx, yy, mm, dd, content, icon)";
		sql+=" values(seq_diary.nextval, ?, ?, ?, ?, ?)";
		//바인드 변수의 순번은 1부터 시작
		
		Connection con=dbManager.getConnection();
		int result=0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, diary.getYy());
			pstmt.setInt(2, diary.getMm());
			pstmt.setInt(3, diary.getDd());
			pstmt.setString(4, diary.getContent());
			pstmt.setString(5, diary.getIcon());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	//해당월에 등록된 다이어리 가져오기
	//1부터 말일까지와 비교
	public List selectAll(int yy, int mm) {
		//1)반환값을 주면 디자인 쪽에서 처리해줘야함(디자인 영역에서 닫아야함)
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<Diary> list=new ArrayList<Diary>();//rs를 대신함
		
		String sql="select * from diary where yy=?";
		sql+=" and mm=? order by diary_idx asc";
		
		try {
			pstmt=dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, yy);
			pstmt.setInt(2, mm);
			rs=pstmt.executeQuery();//쿼리수행 및 테이블 반환
			
			//2)그래서 rs정보를 담아서 전달해야함.
			//rs가 없어지기 전에 빼먹고 있음
			while(rs.next()) {//총레코드 수를 모르기 때문에 for문은 사용 불가
				//비어있는 DTO 하나 생성하자 /이유? 레코드 한건을 담기 위함.
				Diary diary=new Diary();//DTO
				diary.setDiary_idx(rs.getInt("diary_idx"));
				diary.setYy(rs.getInt("yy"));
				diary.setMm(rs.getInt("mm"));
				diary.setDd(rs.getInt("dd"));
				diary.setContent(rs.getString("content"));
				diary.setIcon(rs.getString("icon"));
				
				//레코드 한줄이 채워진 DTO를 ArrayList에 추가하자
				list.add(diary);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
}