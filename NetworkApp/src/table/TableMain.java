package table;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import network.util.DBManager;

// 편집 가능한 테이블 구현하기

public class TableMain extends JFrame{
	JTable table;
	JScrollPane scroll;

	DBManager dbManager = DBManager.getInstance();
	EmpModel model;
	EmpDAO dao = new EmpDAO();
	
	
	public TableMain() {
		table = new JTable(model = new EmpModel(this));
		scroll = new JScrollPane(table);
		
		add(scroll);
		
		setSize(800, 700);
		setVisible(true);
		setLocationRelativeTo(null);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);	
			}
		});
		getList();
	}
//	테이블 리스트 가져오기
	public void getList() {
		model.list = dao.selectAll();
		table.updateUI();
	}
	
	public static void main(String[] args) {
		new TableMain();
	}
}
