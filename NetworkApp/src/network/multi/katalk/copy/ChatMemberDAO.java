package network.multi.katalk.copy;

import java.util.List;

import network.domain.ChatMember;

// 이 인터페이스를 구현하는 모든 객체들이 반드시 명시해야 할 메서드를 구현강제하기 위한 기준을 세움

public interface ChatMemberDAO {
    public int insert(ChatMember chatMember); // insert
    public int update(ChatMember chatMember); // update
    public int delete(int chatmember_idx); // update
    public List selectAll(); // 모든 레코드 가져오기
    public ChatMember select(int chatmember_idx); // 한 건 가져오기(select ~~ where ...idx)
    public ChatMember select(ChatMember chatMember); // 하위 객체들이 구현강제받음
}