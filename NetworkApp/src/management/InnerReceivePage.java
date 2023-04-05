package management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class InnerReceivePage extends ProjectInnerPage implements ActionListener {
//	ProjectReceieveDAO projectReceieveDAO
//	ProjectSendDAO projectSendDAO
	
	
	Border blackline;
	TitledBorder listBorder1, listBorder2, listBorder3;
	
	JPanel panelBackGround, panelNorth, panelWest, panelCenter;
	JPanel panelWestNorth;
	String JComboBox[] = {"대분류", "중분류", "소분류", "선택", "선택", "30일"};
	JComboBox<String> box1, box2, box3, box4, box5, box6, box7, box8, box9;
	
	JButton buttonSelect, buttonInsert, buttonDelete, buttonSave, buttonCredit, button1, button2, button3;

	JLabel label1, label2, label3, label4, label5, label6, label7, label8;
	
	JLabel moveRight1, moveRight2, moveRight3, moveLeft1, moveLeft2;
	
	JTextField textField1, textField2, textField3, textField4, textField5;
	
	JTable tableWest, tableCenter;
	
	JScrollPane scrollWest, scrollCenter;
	public InnerReceivePage(ProjectMainPage projectMainPage) {
		super(projectMainPage);
		
		panelBackGround = new JPanel(); 
		panelNorth = new JPanel(); 
		panelWest = new JPanel(); 
		panelCenter = new JPanel();
		
		panelWestNorth = new JPanel();
		
//		for (int i; i<JComboBox.length; i++) {
//			box1
//		}
//		
		box1 = new JComboBox<String>();
		box2 = new JComboBox<String>();
		box3 = new JComboBox<String>();
		box4 = new JComboBox<String>();
		box5 = new JComboBox<String>();
		box6 = new JComboBox<String>();
		box7 = new JComboBox<String>();
		box8 = new JComboBox<String>();
		box9 = new JComboBox<String>();
		
		buttonSelect = new JButton("조 회");
		buttonInsert = new JButton("추 가");
		buttonDelete = new JButton("삭 제"); 
		buttonSave = new JButton("저 장");
		buttonCredit = new JButton("여신조회");
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();

		label1 = new JLabel("평균 발주 수량을 초과");
		label2 = new JLabel("최근 2개월 거래 없음");
		label3 = new JLabel("최근 2개월 반품 있음");
		label4 = new JLabel("상품대외");
		label5 = new JLabel("입금액");
		label6 = new JLabel("기등록청구금액");
		label7 = new JLabel("발주가능액");
		label8 = new JLabel("발주량추출일수", SwingConstants.RIGHT);
		
		moveRight1 = new JLabel();
		moveRight2 = new JLabel();
		moveRight3 = new JLabel();
		moveLeft1 = new JLabel();
		moveLeft2 = new JLabel();
		
		textField1 = new JTextField("770,357");
		textField2 = new JTextField("19,000,000");
		textField3 = new JTextField("1,725,105");
		textField4 = new JTextField("835,795");
		textField5 = new JTextField("원재료");
		
		tableWest = new JTable(20,5); 
		tableCenter = new JTable(30,5);
		
		scrollWest = new JScrollPane(tableWest);
		scrollCenter = new JScrollPane(tableCenter);
		
		blackline = BorderFactory.createLineBorder(Color.BLACK);
		listBorder1 = BorderFactory.createTitledBorder(blackline, "여신정보");
		listBorder2 = BorderFactory.createTitledBorder(blackline, "물류검색");
		listBorder3 = BorderFactory.createTitledBorder(blackline, "발주목록");
		listBorder1.setTitleJustification(TitledBorder.CENTER);
		listBorder2.setTitleJustification(TitledBorder.CENTER);
		listBorder3.setTitleJustification(TitledBorder.CENTER);
		
//		add + style		
		add(panelBackGround);
		panelBackGround.setLayout(new BorderLayout());
		panelBackGround.setPreferredSize(new Dimension(1900, 680));
		panelBackGround.setBackground(Color.LIGHT_GRAY);	
		
		panelBackGround.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setPreferredSize(new Dimension(1600, 100));
		panelNorth.setBackground(Color.white);	
		panelNorth.setBorder(listBorder1);
		
		panelBackGround.add(panelWest, BorderLayout.WEST);
		panelWest.setPreferredSize(new Dimension(650, 480));
		panelWest.setBackground(Color.GRAY);	
		panelWest.setBorder(listBorder2);
		
		panelBackGround.add(panelCenter, BorderLayout.CENTER);
//		panelCenter.setPreferredSize(new Dimension(100, 500));
		panelCenter.setBackground(Color.LIGHT_GRAY);	
		panelCenter.setBorder(listBorder3);
		
		
//		발주수량 디자인
		Dimension d1 = new Dimension(25, 25);
		
		panelNorth.add(moveRight1);
		moveRight1.setPreferredSize(new Dimension(1350, 0));
		
		panelNorth.add(button1);
		button1.setPreferredSize(d1);
		button1.setBackground(Color.YELLOW);
		button1.setBorderPainted(false);
		button1.setFocusPainted(false);
		panelNorth.add(label1);
		label1.setFont(new Font("고딕", Font.PLAIN, 15));
		
		panelNorth.add(button2);
		button2.setPreferredSize(d1);
		button2.setBackground(Color.CYAN);
		button2.setBorderPainted(false);
		button2.setFocusPainted(false);
		panelNorth.add(label2);
		label2.setFont(new Font("고딕", Font.PLAIN, 15));
		
		panelNorth.add(button3);
		button3.setPreferredSize(d1);
		button3.setBackground(Color.MAGENTA);
		button3.setBorderPainted(false);
		button3.setFocusPainted(false);
		panelNorth.add(label3);
		label3.setFont(new Font("고딕", Font.PLAIN, 15));

//		여신정보 디자인
		panelNorth.add(moveRight2);
		moveRight2.setPreferredSize(new Dimension(650, 0));
		
		Dimension d2 = new Dimension(180, 40);
		panelNorth.add(label4);
		label4.setFont(new Font("고딕", Font.BOLD, 15));
		panelNorth.add(textField1);
		textField1.setPreferredSize(d2);
		
		panelNorth.add(label5);
		label5.setFont(new Font("고딕", Font.BOLD, 15));
		panelNorth.add(textField2);
		textField2.setPreferredSize(d2);
		
		panelNorth.add(label6);
		label6.setFont(new Font("고딕", Font.BOLD, 15));
		panelNorth.add(textField3);
		textField3.setPreferredSize(d2);
		
		panelNorth.add(label7);
		label7.setFont(new Font("고딕", Font.BOLD, 15));
		panelNorth.add(textField4);
		textField4.setPreferredSize(d2);
		
		Dimension d4 = new Dimension(150, 40);
		panelNorth.add(buttonCredit);
		buttonCredit.setPreferredSize(d4);
		buttonCredit.setFont(new Font("고딕", Font.BOLD, 15));
		buttonCredit.setBackground(Color.LIGHT_GRAY);
		buttonCredit.setForeground(Color.BLACK);
		buttonCredit.setBorderPainted(false);
		buttonCredit.setFocusPainted(false);
		
		panelWest.setLayout(new BorderLayout());
		panelWest.add(panelWestNorth, BorderLayout.NORTH);
		panelWestNorth.setBackground(Color.WHITE);
		panelWestNorth.setPreferredSize(new Dimension(650, 180));
		
		Dimension d3 = new Dimension(200, 40);
		panelWestNorth.add(box1);
		box1.setPreferredSize(d3);
		panelWestNorth.add(box2);
		box2.setPreferredSize(d3);
		panelWestNorth.add(box3);
		box3.setPreferredSize(d3);
		
		panelWestNorth.add(box4);
		box4.setPreferredSize(d3);
		
		panelWestNorth.add(moveLeft1);
		moveLeft1.setPreferredSize(new Dimension(405,0));
		
		panelWestNorth.add(box5);
		box5.setPreferredSize(d3);
		panelWestNorth.add(label8);
		label8.setFont(new Font("고딕", Font.BOLD, 15));
		label8.setPreferredSize(new Dimension(200, 40));
		panelWestNorth.add(box6);
		box6.setPreferredSize(d3);

		panelWestNorth.add(textField5);
		textField5.setPreferredSize(d3);
		panelWestNorth.add(buttonSelect);
		buttonSelect.setPreferredSize(d3);
		buttonSelect.setFont(new Font("고딕", Font.BOLD, 15));
		buttonSelect.setBackground(Color.LIGHT_GRAY);
		buttonSelect.setForeground(Color.BLACK);
		buttonSelect.setBorderPainted(false);
		buttonSelect.setFocusPainted(false);
		
		panelWestNorth.add(moveLeft2);
		moveLeft2.setPreferredSize(new Dimension(200,0));
		
		panelWest.add(scrollWest);
//		scrollWest.setPreferredSize(new Dimension());
		
		panelCenter.add(moveRight3);
		moveRight3.setPreferredSize(new Dimension(745,0));
		
		panelCenter.add(buttonInsert);
		buttonInsert.setPreferredSize(d4);
		buttonInsert.setFont(new Font("고딕", Font.BOLD, 15));
		buttonInsert.setBackground(Color.DARK_GRAY);
		buttonInsert.setForeground(Color.WHITE);
		buttonInsert.setBorderPainted(false);
		buttonInsert.setFocusPainted(false);
		
		panelCenter.add(buttonDelete);
		buttonDelete.setPreferredSize(d4);
		buttonDelete.setFont(new Font("고딕", Font.BOLD, 15));
		buttonDelete.setBackground(Color.DARK_GRAY);
		buttonDelete.setForeground(Color.WHITE);
		buttonDelete.setBorderPainted(false);
		buttonDelete.setFocusPainted(false);
		
		panelCenter.add(buttonSave);
		buttonSave.setPreferredSize(d4);
		buttonSave.setFont(new Font("고딕", Font.BOLD, 15));
		buttonSave.setBackground(Color.DARK_GRAY);
		buttonSave.setForeground(Color.WHITE);
		buttonSave.setBorderPainted(false);
		buttonSave.setFocusPainted(false);
		
		panelCenter.add(scrollCenter);
		scrollCenter.setPreferredSize(new Dimension(1210, 500));
	}

	public void actionPerformed(ActionEvent e) {
		
	}

}
