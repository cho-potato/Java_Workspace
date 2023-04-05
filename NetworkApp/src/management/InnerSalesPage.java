package management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import network.util.DBManager;

public class InnerSalesPage extends ProjectInnerPage implements ActionListener {
	DBManager dbManager = DBManager.getInstance();
	ProjectSalesDAO projectSalesDAO = new ProjectSalesDAO();
	ProjectSalesDTO projectSalesDTO = new ProjectSalesDTO();
	String currentTitle;

	// 서쪽영역
	JPanel p_west;
	JComboBox<String> box_yy; // 연
	JComboBox<String> box_mm; // 월
	JComboBox<String> box_dd;// 일
	JTextArea area; // 텍스트
	JScrollPane scroll; // 스크롤
	JComboBox<String> box_icon; // 다이어리 아이콘
	JButton bt_regist; // 등록 및 수정 버튼
	JButton bt_update; // 등록 및 수정 버튼
	JButton bt_del; // 삭제 버튼
	JLabel emptyLabel1, emptyLabel2, emptyLabel3, emptyLabel4;

	// 센터영역
	JPanel p_center; // 플로우 적용 위한 센터의 최상위 컨테이너
	JPanel p_title; // 현재 연 월 및 이전 다음 버튼 영역
	JPanel p_dayOfWeek; // 요일 셀이 올 영역
	JPanel p_dayOfMonth; // 날짜 셀이 올 영역
	JButton bt_prev;
	JLabel la_title;
	JButton bt_next;

	Border blackline;
	TitledBorder title;

	// 요일처리
	ProjectSalesDayCell[] dayCells = new ProjectSalesDayCell[7]; // 요일 모두 담기
	String[] dayTitle = { "일", "월", "화", "수", "목", "금", "토" };

	// 날짜
	ProjectSalesDateCell[][] dateCells = new ProjectSalesDateCell[6][7];

	// 현재 사용자가 보게될 날짜 정보!!
	Calendar currentObj = Calendar.getInstance();// 현재날짜
	// ----------------------------------------------------------------------------------------------

	public InnerSalesPage(ProjectMainPage projectMainPage) {
		super(projectMainPage);

		// 서쪽영역
		p_west = new JPanel();
		box_yy = new JComboBox<String>();
		box_mm = new JComboBox<String>();
		box_dd = new JComboBox<String>();
		area = new JTextArea(5, 12); // 행,열
		scroll = new JScrollPane();
		box_icon = new JComboBox<String>();
		bt_regist = new JButton("등록");
		bt_update = new JButton("수정");
		bt_del = new JButton("삭제");

		emptyLabel1 = new JLabel("　　　　　");

		emptyLabel2 = new JLabel("　　　　　");
		emptyLabel3 = new JLabel("　　　　　");
		emptyLabel4 = new JLabel("　　　　　　　　　　　　　　　　　　　　");

		// 디자인
		p_west.setPreferredSize(new Dimension(300, 680));
		p_west.setBackground(Color.GRAY);

		Dimension d = new Dimension(250, 50);
		box_yy.setPreferredSize(d);
		box_mm.setPreferredSize(d);
		box_dd.setPreferredSize(d);
		area.setPreferredSize(new Dimension(270, 200));
		area.setLineWrap(true);
		box_icon.setPreferredSize(d);
		bt_regist = new JButton("등록");
		bt_regist.setPreferredSize(new Dimension(75, 40));
		bt_regist.setBackground(Color.WHITE);
		bt_regist.setForeground(Color.BLACK);
		bt_regist.setFont(new Font("고딕", Font.BOLD, 15));
		bt_regist.setBorderPainted(false);
		bt_regist.setFocusPainted(false);

		bt_update = new JButton("수정");
		bt_update.setPreferredSize(new Dimension(75, 40));
		bt_update.setBackground(Color.WHITE);
		bt_update.setForeground(Color.BLACK);
		bt_update.setFont(new Font("고딕", Font.BOLD, 15));
		bt_update.setBorderPainted(false);
		bt_update.setFocusPainted(false);

		bt_del = new JButton("삭제");
		bt_del.setPreferredSize(new Dimension(75, 40));
		bt_del.setBackground(Color.RED);
		bt_del.setForeground(Color.WHITE);
		bt_del.setFont(new Font("고딕", Font.BOLD, 15));
		bt_del.setBorderPainted(false);
		bt_del.setFocusPainted(false);

//		// 부착
//		p_west.add(box_yy);
//		p_west.add(emptyLabel1);
//		p_west.add(box_mm);
//		p_west.add(emptyLabel2);
//		p_west.add(box_dd);
//		p_west.add(emptyLabel3);
//		p_west.add(area);
//		p_west.add(emptyLabel4);
//		p_west.add(scroll);
//		p_west.add(box_icon);
//		p_west.add(bt_regist);
//
//		p_west.add(bt_update);
//		p_west.add(bt_del);
//
//		add(p_west, BorderLayout.WEST);

		// 센터영역
		p_center = new JPanel();
		p_title = new JPanel();
		bt_prev = new JButton("이전");
		bt_prev.setPreferredSize(new Dimension(70, 30));
		bt_prev.setBackground(Color.LIGHT_GRAY);
		bt_prev.setForeground(Color.BLACK);
		bt_prev.setFont(new Font("고딕", Font.BOLD, 15));
		bt_prev.setBorderPainted(false);
		bt_prev.setFocusPainted(false);

		la_title = new JLabel("2022-12", SwingConstants.CENTER);
		la_title.setPreferredSize(new Dimension(200, 30));
		la_title.setFont(new Font("고딕", Font.BOLD, 20));

		bt_next = new JButton("다음");
		bt_next.setPreferredSize(new Dimension(70, 30));
		bt_next.setBackground(Color.LIGHT_GRAY);
		bt_next.setForeground(Color.BLACK);
		bt_next.setFont(new Font("고딕", Font.BOLD, 15));
		bt_next.setBorderPainted(false);
		bt_next.setFocusPainted(false);

		p_dayOfWeek = new JPanel();
		p_dayOfMonth = new JPanel();

		// 디자인
		p_center.setPreferredSize(new Dimension(1400, 700));
		p_center.setBackground(Color.white);
		p_title.setPreferredSize(new Dimension(1400, 60));
		
		p_dayOfWeek.setLayout(new GridLayout(1, 7));
		p_dayOfMonth.setLayout(new GridLayout(6, 7));

		p_dayOfWeek.setPreferredSize(new Dimension(1400, 50));
		p_dayOfWeek.setBackground(Color.LIGHT_GRAY);
		p_dayOfMonth.setPreferredSize(new Dimension(1400, 550));
		p_dayOfMonth.setBackground(Color.LIGHT_GRAY);

		// 부착
		p_title.add(bt_prev);
		p_title.add(la_title);
		p_title.add(bt_next);
		title = BorderFactory.createTitledBorder("매출현황");
		p_title.setBorder(title);
	
		p_center.add(p_title);
		p_center.add(p_dayOfWeek);
		p_center.add(p_dayOfMonth);
		add(p_center, BorderLayout.EAST);

		createDayOfWeek(); // 요일 출력
		createDayOfMonth();// 날짜 출력

		calculate();// 날짜처리

		// 다음월
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 현재 날짜 객체정보를 먼저 얻자!!
				int mm = currentObj.get(Calendar.MONTH);

				// currentObj.set(월옵션, 현재월+1);
				currentObj.set(Calendar.MONTH, mm + 1);
				// System.out.println("당신이 보고 있는 현재 월은"+(mm+1));
				System.out.println(currentObj.get(Calendar.MONTH));
				calculate();
			}
		});
		// 이전월
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 현재 날짜 객체정보를 먼저 얻자!!
				int mm = currentObj.get(Calendar.MONTH);

				// currentObj.set(월옵션, 현재월+1);
				currentObj.set(Calendar.MONTH, mm - 1);
				// System.out.println("당신이 보고 있는 현재 월은"+(mm+1));
				System.out.println(currentObj.get(Calendar.MONTH));
				calculate();
			}
		});

//			bt_regist.addActionListener(this);
//			bt_update.addActionListener(this);
//			bt_del.addActionListener(this);

	}

	// ----------------------------------------------------------------------------------------------

	// 메소드 선언 : 요일 출력
	public void createDayOfWeek() {
		// 요일 모두 생성해서 패널에 부착
		for (int i = 0; i < dayCells.length; i++) {
//				DayCell dayCell = new DayCell();
//				dayCells[i]=dayCell;
			dayCells[i] = new ProjectSalesDayCell(dayTitle[i], "", 25, 20, 20); // 생성

			p_dayOfWeek.add(dayCells[i]); // 부착
		}
	}

	// 날짜 출력 생성
	public void createDayOfMonth() {

		for (int i = 0; i < dateCells.length; i++) {// 층
			for (int a = 0; a < dateCells[i].length; a++) {// 층
				dateCells[i][a] = new ProjectSalesDateCell(this, "", "", 20, 40, 10);

				// 패널에 부착
				p_dayOfMonth.add(dateCells[i][a]);
			}
		}
	}

	// 제목을 출력한다.
	public void printTitle() {
		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONTH);
		String str = yy + "년 " + (mm + 1) + "월";// 월은 0부터 시작하기 때문에 출력할때 1을 더해줘야 한다.

		la_title.setText(str);
	}

	/*
	 * 달력을 구현하기 위한 두가지 정보 1) 해당 월이 무슨 요일부터 시작하는가? -날짜 객체 생성하여, 조작(1일로 보낸다) -1일인
	 * 상태에서, 해당 날짜 객체에게 요일을 물어본다. ex)화요일 2) 해당 월이 며칠까지 인가 -날짜 객체 생성하여 조작
	 */
	public int getStartDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		// 년도
		int yy = currentObj.get(Calendar.YEAR);
		// 월
		int mm = currentObj.get(Calendar.MONTH);

		cal.set(yy, mm, 1);// 1일로 조작함 why? rs를 보내듯이 물어보려고 보냄
		// set(int year, int month, int date)
		// yy년 mm월의 1일로 보내서 1일에 해당하는 요일을 얻어내려고
		int day = cal.get(Calendar.DAY_OF_WEEK);// 요일 추출
		return day;

	}

	// 2) 해당 월이 며칠까지 인가
	// -날짜 객체 생성하여 조작
	public int getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();// 조작용 쓰고 버리는 용도
		// 년
		int yy = currentObj.get(Calendar.YEAR);
		// 월
		int mm = currentObj.get(Calendar.MONTH);

		cal.set(yy, mm + 1, 0);// 조작완료
		int date = cal.get(Calendar.DATE);

		return date;
	}

	// 날짜출력
	public void printDate() {
		// 만들어진 셀에 글씨만 바꾸기
		// 모든 셀에 접근하여, 알맞는 문자 출력
		int n = 0;// 시작 시점 지표
		int d = 0;// 날짜 출력 변수
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				ProjectSalesDateCell cell = dateCells[i][a];

				n++;
				if (n >= getStartDayOfWeek() && d < getLastDayOfMonth()) {
					d++;// 꾹 참았다가, n이 시작요일 이상일때부터 ++
					cell.title = Integer.toString(d);// int를 객체형으로 바꿔줌
				} else {
					cell.title = "";
				}
			}
		}
		p_dayOfMonth.repaint();// 패널 갱신
	}

	public void calculate() {
		printTitle();// 제목 출력
		// getStartDayOfWeek();
		// getLastDayOfMonth();
		printDate();// 날짜번호출력
		// 기록된 다이어리 출력
		printLog();
	}

	public void printLog() {
		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONTH);
		List<ProjectSalesDTO> diaryList = projectSalesDAO.selectAll(yy, mm);
		System.out.println("등록된 다이어리 수는 " + diaryList.size());

//			현재  월의 모든 날짜를 대상으로 반복문 수행
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				if (dateCells[i][a].title.equals("") == false) { // 숫자가 아닌 ""과 같은 문자열은 정수화할 수 없으므로 조건문으로 걸러내자
//						날짜 숫자 추출하기
					int date = Integer.parseInt(dateCells[i][a].title);
//						불러온 데이터 만큼 반복문
					for (int x = 0; x < diaryList.size(); x++) {
						ProjectSalesDTO obj = diaryList.get(x); // 다이어리 한 건 추출
						if (date == obj.getDd()) {
//								해당 셀에 데이터 표현
//								dateCells[i][a].color = Color.GREEN;
							dateCells[i][a].content = obj.getContent();
						}
					}
				}
			}
		}
		p_dayOfMonth.repaint();
	}

	// 데이터베이스와 관련된 쿼리 로직을 중복 정의하지 않기 위해 즉, 코드 재사용을 위해 쿼리만을 전담하는 객체를
	// 별도로 정의하여 유지보수성을 높이자 !
	// 이러한 목적으로 정의되는 객체를 어플리케이션 설계분야(디자인분야)에서는 DAO(data access object)라한다.
	// what to make? how to make? how to make!!! 설계를 생각해야함!
	// 디자인 분야에 쿼리 로직을 작성하는 순간 1회용이 됨
	public void regist() {
		// Diary DTO생성!!(empty상태=텅빈상태)
		ProjectSalesDTO d = new ProjectSalesDTO();
		System.out.println("호출전" + d);

		// UnBoxing(객체형이 기본자료형으로 변경되는 것)
		String yy = (String) box_yy.getSelectedItem();
		String mm = (String) box_mm.getSelectedItem();
		String dd = (String) box_dd.getSelectedItem();
		String content = area.getText();
//			String icon = (String) box_icon.getSelectedItem();

		// 레코드 한껀 채워넣기
		d.setYy(Integer.parseInt(yy));
		d.setMm(Integer.parseInt(mm));
		d.setDd(Integer.parseInt(dd));
		d.setContent(content);
//			d.setIcon(icon);

		// insert();
		int result = projectSalesDAO.insert(d);
		if (result > 0) {// 성공이라면
			JOptionPane.showMessageDialog(this, "등록완료");
			printLog();
		}
	}

	public void update() {
		// Diary DTO생성!!(empty상태=텅빈상태)
		ProjectSalesDTO d = new ProjectSalesDTO();
		System.out.println("호출전" + d);

		// UnBoxing(객체형이 기본자료형으로 변경되는 것)
		String yy = (String) box_yy.getSelectedItem();
		String mm = (String) box_mm.getSelectedItem();
		String dd = (String) box_dd.getSelectedItem();
		String content = area.getText();
//			String icon = (String) box_icon.getSelectedItem();

		// 레코드 한껀 채워넣기
		d.setYy(Integer.parseInt(yy));
		d.setMm(Integer.parseInt(mm));
		d.setDd(Integer.parseInt(dd));
		d.setContent(content);
//			d.setIcon(icon);

		// insert();
		int result = projectSalesDAO.insert(d);
		if (result > 0) {// 성공이라면
			JOptionPane.showMessageDialog(this, "수정완료");
			printLog();
		}
	}

	public void delete() {
		if (JOptionPane.showConfirmDialog(projectMainPage, "삭제하시겠습니까?") == JOptionPane.OK_OPTION) {
		}
		if (projectSalesDTO != null) {
			int result = projectSalesDAO.delete(projectSalesDTO.getProjectsalesdiary_idx());
			System.out.println("bt_del" + result);
			setDateInfo(currentTitle, projectSalesDTO);
			if (result > 0) { // 삭제 성공
				System.out.println("bt_del" + result);
				JOptionPane.showMessageDialog(projectMainPage, "삭제완료");
				printLog();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_regist) {
			regist();
		} else if (obj == bt_update) {
			update();
		} else if (obj == bt_del) {
			delete();
		}

	}

	// 셀을 선택할때 콤보박스의 값 변경
	public void setDateInfo(String title, ProjectSalesDTO projectSalesDTO) {
		this.projectSalesDTO = projectSalesDTO;
		this.currentTitle = title;

		// 콤보박스에 아이템을 누적하지 말고, 싹 지운상태에서 추가
		box_yy.removeAllItems();
		box_mm.removeAllItems();
		box_dd.removeAllItems();

		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONTH);
		box_yy.addItem(Integer.toString(yy));
		box_mm.addItem(Integer.toString(mm));
		box_dd.addItem(title);
		if (projectSalesDTO != null) {
			System.out.println("현재 pk : " + projectSalesDTO.getProjectsalesdiary_idx());
			area.setText(projectSalesDTO.getContent());
			p_west.updateUI();
		}
	}
}
