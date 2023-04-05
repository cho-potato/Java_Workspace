package ani.qna;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import util.PagingManager;

public class QnAPage extends Page {
	JPanel p_list; // row 패널들이 붙여질 영역
	JPanel p_num; // 페이지 번호가 붙여질 영역
	JButton bt_regist; // 글등록 버튼

	ArrayList<ReBoard> boardList = new ArrayList<ReBoard>(); // ReBoard DTO가 들어있는 게시물 목록
	ArrayList<Row> rows = new ArrayList<Row>(); // size 0
	ArrayList<PageNum> pageNums = new ArrayList<PageNum>();

	PagingManager pagingManager = new PagingManager();

	public QnAPage(AppMain appMain) {
		super(appMain);

		p_list = new JPanel();
		p_num = new JPanel();
		bt_regist = new JButton("글등록");

		p_list.setPreferredSize(new Dimension(770, 455));
		p_num.setPreferredSize(new Dimension(770, 50));

		p_list.setBackground(Color.WHITE);
		p_num.setBackground(Color.WHITE);

		add(bt_regist);
		add(p_list);
		add(p_num);

		getList();
		
		bt_regist.addActionListener(new  ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appMain.showHide(AppMain.QNAREGIST);
			}
		});
	}

//	row 생성하기
	public void createRow() {
//		rows = new Row[10];
		int num = pagingManager.getNum();
		int curPos = pagingManager.getCurPos();
		
//		아래의 반복문을 돌리기 전에 커서를 페이지당 시작 위치값을 이용하려 올려놓자
		for (int i = 0; i < pagingManager.getPageSize(); i++) {
			if(num < 1) break;
//			if문을 만나지 않았다는 것은 보여질 레코드가 있다는 것이므로 if문 아래쪽에 커서를 이용하여 DTO를 리스트로부터 꺼내자
			ReBoard dto = boardList.get(curPos++); // 임시적으로 DTO 넘기기
			
			Row row = new Row(num--, dto, this);
			rows.add(row); // 리스트에 추가
			p_list.add(row); // 패널에 부착

		}
		this.updateUI(); // 현재 패널 갱신
	}

//	페이지 번호 생성
	public void createPageNum() {
		BlockNum prev = new BlockNum("◀", this);
		p_num.add(prev);
		prev.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				이전 블럭으로 가기 위한 currentPage값 계산
				pagingManager.setCurrentPage(pagingManager.getFirstPage() - 1);
				getList();
			}
		});

		for (int i = pagingManager.getFirstPage(); i <= pagingManager.getLastPage(); i++) { // 블럭 사이즈로 한정
			if(i > pagingManager.getTotalPage()) break; // 내가 가진 총 페이지 수를 넘어서면 반복문 탈출
			PageNum pageNum = new PageNum(Integer.toString(i), this);
			p_num.add(pageNum); // 하단 패널에 라벨 부착
			pageNums.add(pageNum); // 리스트에 담기
		}
		BlockNum next = new BlockNum("▶", this);
		p_num.add(next);
		next.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				이전 블럭으로 가기 위한 currentPage값 계산
				pagingManager.setCurrentPage(pagingManager.getLastPage() + 1);
				getList();
			}
		});

		p_num.updateUI(); // 새로고침
	}

	public void getList() {
//		기존에 붙어있던 컴포넌트들을 모두 제거 후 부착
		p_list.removeAll();
		p_num.removeAll();

//		쌓여있는 리스트 요소들도 제거
		rows.removeAll(rows); // ArrayList의 모든 요소 삭제
		pageNums.removeAll(pageNums); // ArrayList의 모든 요소 삭제

//		DB에서 레코드 가져오기
		boardList = (ArrayList)appMain.reBoardDAO.selectAll();
		
		pagingManager.init(boardList, pagingManager.getCurrentPage());
		createRow();
		createPageNum();
	}

//	row에 대한 하이라이트 효과
	public void showLight(int n) {
//		모든 rows를 대상으로
		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.get(i); // 리스트에서 요소 하나 꺼내기
			if (i == n) {
				row.setBackground(Color.RED); // 배경색 주기
			} else {
				row.setBackground(Color.WHITE); // 돌려놓기
			}
		}
	}

//	선택한 페이지에 대한 이펙트
	public void activePage(String n) {
		for (int i = 0; i < pageNums.size(); i++) {
			PageNum pageNum = pageNums.get(i);
			if (pageNum.getText().equals(n)) { // 넘겨받은 n과 같은 경우에만 대상 라벨의 텍스트 색상 변경
				pageNum.setForeground(Color.RED);
				pageNum.setFont(new Font("Arial black", Font.BOLD, 30));
			} else {
				pageNum.setForeground(Color.BLACK);
				pageNum.setFont(new Font("Arial black", Font.BOLD, 20));
			}
		}
	}
}
