package management;

import java.util.List;

public interface ProjectMemberDAO {
	public int insert(ProjectMemberDTO projectMemberDTO); // 삽입
	public int update(ProjectMemberDTO projectMemberDTO); // 수정
	public int delete(int projectmember_idx); // 삭제
	public List selectAll(); // 모든 레코드 가져오기
	public ProjectMemberDTO select(int projectmember_idx); // 한 건 가져오기 
	public ProjectMemberDTO select(ProjectMemberDTO projectMemberDTO); // 하위 객체에 구현강제
	
}
