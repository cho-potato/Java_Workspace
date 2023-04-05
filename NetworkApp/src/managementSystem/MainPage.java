package managementSystem;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPage extends Page{
	ClientMain clientMain;
	
	JPanel p;
	JPanel p_label, p_button1, p_button2;

	JLabel la_account; 
	JButton bt_logout;
	JButton bt_master, bt_receive, bt_sales, bt_stock, bt_work;
	JButton bt_select, bt_excel, bt_close;
	
	public MainPage(ClientMain clientMain) {
		super(clientMain);
		
		p = new JPanel();
		p_label = new JPanel();
		p_button1 = new JPanel();
		p_button2 = new JPanel();
		
		la_account = new JLabel("민진호와 아이들님"); 
		bt_logout = new JButton("LOGOUT");
		
		bt_master = new JButton("마스터");
		bt_receive = new JButton("수주관리"); 
		bt_sales = new JButton("매출관리"); 
		bt_stock = new JButton("재고관리"); 
		bt_work = new JButton("근태관리"); 
		
		bt_select = new JButton("조회"); 
		bt_excel = new JButton("엑셀"); 
		bt_close = new JButton("닫기"); 

		add(p);
		p.add(p_label);
		p.add(p_button1);
		p.add(p_button2);
		
		p_label.add(la_account);
		p_label.add(bt_logout);
		p_label.setAlignmentX(RIGHT_ALIGNMENT);
		p_label.setBackground(Color.GRAY);
		
		p_button1.add(bt_master);
		p_button1.add(bt_receive);
		p_button1.add(bt_sales);
		p_button1.add(bt_stock);
		p_button1.add(bt_work);
		p_button1.setAlignmentX(RIGHT_ALIGNMENT);
		
		p_button2.add(bt_select);
		p_button2.add(bt_excel);
		p_button2.add(bt_close);
		p_button1.setAlignmentX(RIGHT_ALIGNMENT);
				
		
	}
}

