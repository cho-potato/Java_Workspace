package management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import ani.qna.BlockNum;
import ani.qna.PageNum;
import ani.qna.Row;
import util.PagingManager;

public class InnerNoticePage extends ProjectInnerPage{
//	JPanel borderPanel;
//	JPanel eastPanel, centerPanel;
	JPanel noticePageList; // 각 게시글이 붙을 영역
	JPanel noticePageNum; // 각 게시글 넘버링
	JButton noticePageRegistBT; // 등록버튼
	
	Border blackline;
	TitledBorder listBorder;
	TitledBorder etcBorder;
	
	PagingManager pagingManager =  new PagingManager();
	ArrayList<ProjectNoticeBoardDTO> boardList = new ArrayList<ProjectNoticeBoardDTO>();
	ArrayList<NoticeRow> rows = new ArrayList<NoticeRow>();
	ArrayList<NoticePageNumber> pageNums = new ArrayList<NoticePageNumber>();
	
	public InnerNoticePage(ProjectMainPage projectMainPage) {
		super(projectMainPage);
		this.setBackground(Color.LIGHT_GRAY);
//		borderPanel = new JPanel();
//		eastPanel = new JPanel();
//		centerPanel = new JPanel();
		
		noticePageList = new JPanel();
		noticePageNum = new JPanel();
		noticePageRegistBT = new JButton("공지사항 등록");
		
		blackline = BorderFactory.createLineBorder(Color.black);
		
		listBorder = BorderFactory.createTitledBorder(blackline, "공지사항");
		listBorder.setTitleJustification(TitledBorder.CENTER);
		etcBorder = BorderFactory.createTitledBorder(blackline);
		etcBorder.setTitleJustification(TitledBorder.CENTER);

		
//		add(borderPanel);
//		borderPanel.setLayout(new BorderLayout());
//		borderPanel.setPreferredSize(new Dimension(1880, 680));
//		borderPanel.setBackground(Color.LIGHT_GRAY);
//		
//		borderPanel.add(centerPanel);
//		centerPanel.setPreferredSize(new Dimension(1300, 650));
//		//centerPanel.setBackground(Color.RED);
//		centerPanel.setBorder(listBorder);
//		
//		borderPanel.add(eastPanel, BorderLayout.EAST);
//		eastPanel.setPreferredSize(new Dimension(500, 650));
//		//eastPanel.setBackground(Color.BLUE);
//		eastPanel.setBorder(etcBorder);
		

//		centerPanel.add(noticePageRegistBT);
		add(noticePageRegistBT);
		noticePageRegistBT.setPreferredSize(new Dimension(150, 40));
//		noticePageRegistBT.setBackground(Color.RED);
		noticePageRegistBT.setBackground(Color.LIGHT_GRAY);
//		noticePageRegistBT.setForeground(Color.WHITE);
		noticePageRegistBT.setForeground(Color.LIGHT_GRAY);
		noticePageRegistBT.setFont(new Font("고딕", Font.BOLD, 15));
		noticePageRegistBT.setBorderPainted(false);
		noticePageRegistBT.setFocusPainted(false);
		
//		centerPanel.add(noticePageList);
		add(noticePageList);
		noticePageList.setPreferredSize(new Dimension(1900, 600));
		noticePageList.setBackground(Color.WHITE);
		noticePageList.setBorder(listBorder);
//		centerPanel.add(noticePageNum);
		add(noticePageNum);
		noticePageNum.setPreferredSize(new Dimension(1900, 40));
		noticePageNum.setBackground(Color.WHITE);
		noticePageNum.setBorder(etcBorder);
		
		getList();
		
		noticePageRegistBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectMainPage.mainPageShowHide(ProjectMainPage.NOTICEPAGEREGIST);
			}
		});
	}

	public void createRow() {
		int num = pagingManager.getNum();
		int curPos = pagingManager.getCurPos();
		
		for(int i = 0; i <pagingManager.getPageSize(); i++) {
			if(num < 1) break;
			ProjectNoticeBoardDTO dto = boardList.get(curPos++);
			
			NoticeRow NoticeRow = new NoticeRow(num--, dto, this);
			rows.add(NoticeRow);
			noticePageList.add(NoticeRow);
		}
		this.updateUI();
	}
//	페이지 번호 생성
	public void createPageNum() {
		NoticeBlockNumber prev = new NoticeBlockNumber("<", this);
		noticePageNum.add(prev);
		prev.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				이전 블럭으로 가기 위한 currentPage값 계산
				pagingManager.setCurrentPage(pagingManager.getFirstPage() - 1);
				getList();
			}
		});

		for (int i = pagingManager.getFirstPage(); i <= pagingManager.getLastPage(); i++) { // 블럭 사이즈로 한정
			if(i > pagingManager.getTotalPage()) break; // 내가 가진 총 페이지 수를 넘어서면 반복문 탈출
			NoticePageNumber noticePageNumber = new NoticePageNumber(Integer.toString(i), this);
			noticePageNum.add(noticePageNumber); // 하단 패널에 라벨 부착
			pageNums.add(noticePageNumber); // 리스트에 담기
		}
		NoticeBlockNumber next = new NoticeBlockNumber(">", this);
		noticePageNum.add(next);
		next.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				이전 블럭으로 가기 위한 currentPage값 계산
				pagingManager.setCurrentPage(pagingManager.getLastPage() + 1);
				getList();
			}
		});

		noticePageNum.updateUI(); // 새로고침
	}

	public void getList() {
//		기존에 붙어있던 컴포넌트들을 모두 제거 후 부착
		noticePageList.removeAll();
		noticePageNum.removeAll();

//		쌓여있는 리스트 요소들도 제거
		rows.removeAll(rows); // ArrayList의 모든 요소 삭제
		pageNums.removeAll(pageNums); // ArrayList의 모든 요소 삭제

//		DB에서 레코드 가져오기
		boardList = (ArrayList)projectMainPage.projectNoticeBoardDAO.selectAll();
		
		pagingManager.init(boardList, pagingManager.getCurrentPage());
		createRow();
		createPageNum();
	}

//	row에 대한 하이라이트 효과
	public void showLight(int n) {
//		모든 rows를 대상으로
		for (int i = 0; i < rows.size(); i++) {
			NoticeRow noticeRow = rows.get(i); // 리스트에서 요소 하나 꺼내기
			if (i == n) {
				noticeRow.setBackground(Color.RED); // 배경색 주기
			} else {
				noticeRow.setBackground(Color.WHITE); // 돌려놓기
			}
		}
	}

//	선택한 페이지에 대한 이펙트
	public void activePage(String n) {
		for (int i = 0; i < pageNums.size(); i++) {
			NoticePageNumber noticePageNumber = pageNums.get(i);
			if (noticePageNumber.getText().equals(n)) { // 넘겨받은 n과 같은 경우에만 대상 라벨의 텍스트 색상 변경
				noticePageNumber.setForeground(Color.RED);
				noticePageNumber.setFont(new Font("Arial black", Font.BOLD, 30));
			} else {
				noticePageNumber.setForeground(Color.BLACK);
				noticePageNumber.setFont(new Font("Arial black", Font.BOLD, 20));
			}
		}
	}
}
