package border;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

public class LayoutApp extends JFrame{
	JPanel p_west;
	JPanel p_regist; // 제품정보입력영역 : 보더를 적용할 예정
	JPanel p_area; // 기타 영역 : 보더를 적용할 예정
	
	JPanel p_center;
	JPanel p_list; // 제품 목록 제목 보더를 붙일 영역
	JTable table;
	JScrollPane scroll;
	
	public LayoutApp() {
		p_west = new JPanel();
		p_regist = new JPanel(); // 제품정보입력영역 : 보더를 적용할 예정
		p_area = new JPanel(); // 기타 영역 : 보더를 적용할 예정
		
		p_center = new JPanel();
		p_list = new JPanel(); // 제품 목록 제목 보더를 붙일 영역
		table = new JTable(14, 8);
		scroll = new JScrollPane(table);
		
//		각각의 패널에 적절한 영역인 보더를 적용하되 제목이 있는 보더를 적용해보자
		Border registBorder = BorderFactory.createTitledBorder("제품정보");
		Border etcBorder = BorderFactory.createTitledBorder("기타정보");
		Border listBorder = BorderFactory.createTitledBorder("제품목록");
		
		p_regist.setBorder(registBorder); // 보더적용
		p_area.setBorder(etcBorder); // 보더적용
		p_list.setBorder(listBorder); // 보더적용
		
		
		p_west.setPreferredSize(new Dimension(200, 500));
		p_regist.setPreferredSize(new Dimension(180, 230));
		p_area.setPreferredSize(new Dimension(180, 200));
		
		p_list.setPreferredSize(new Dimension(680, 400));
		scroll.setPreferredSize(new Dimension(670, 390));
		
		p_west.add(p_regist);
		p_west.add(p_area);
		p_list.add(scroll);
		
		add(p_west, BorderLayout.WEST);
		add(p_list);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900, 500);
		setVisible(true);
	}
	public static void main(String[] args) {
		new LayoutApp();
	}
}
