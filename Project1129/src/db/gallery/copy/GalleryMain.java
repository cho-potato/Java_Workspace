package db.gallery.copy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GalleryMain extends JFrame implements ActionListener {
//	서쪽 영역
	JPanel p_west;
	JTextField t_title;
	JTextField t_writer;
	JTextField t_content;
	JPanel p_preview; // 이미지 미리보기 채널
	JButton bt_regist;
	JButton bt_open; // 첨부 이미지 찾기 버튼

//	센터영역
	JTable table;
	JScrollPane scroll;

//	동쪽 영역
	JPanel p_east;
	JTextField t_title2;
	JTextField t_writer2;
	JTextField t_content2;
	JPanel p_preview2; // 이미지 미리보기 채널
	JButton bt_edit;
	JButton bt_del;

	JFileChooser chooser;
	Image image; // 패널이 그릴 수 있도록 멤버변수로 선언 
	
	public GalleryMain() {
		Dimension d1 = new Dimension(150, 500);
		Dimension d2 = new Dimension(145, 130);

//		서쪽 영역
		p_west = new JPanel();
		t_title = new JTextField(12);
		t_writer = new JTextField(12);
		t_content = new JTextField();
		p_preview = new JPanel() {

			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.YELLOW);
				g2.fillRect(0, 0, 145, 130);

				g2.setColor(Color.BLUE);

				
				if(image !=null) {
				g2.drawImage(image, 0, 0, 150, 130, GalleryMain.this);
				} else {
					g2.drawString("choose your file", 25, 70);
				}
			}
		};
		
		bt_open = new JButton("첨부");
		bt_regist = new JButton("등록");
		chooser = new JFileChooser();
		
		
		p_west.setPreferredSize(d1);
		t_content.setPreferredSize(d2);
		p_preview.setPreferredSize(d2);
		p_preview.setBackground(Color.BLACK);

		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(p_preview);
		p_west.add(bt_open);
		p_west.add(bt_regist);

		add(p_west, BorderLayout.WEST);

//		센터 영역
		table = new JTable();
		scroll = new JScrollPane(table);
		add(scroll);

//		동쪽 영역
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextField();
		p_preview2 = new JPanel();
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");
		
		p_east.setPreferredSize(d1);
		t_content2.setPreferredSize(d2);
		p_preview2.setPreferredSize(d2);
		p_preview2.setBackground(Color.BLACK);

		p_east.add(t_title2);
		p_east.add(t_writer2);
		p_east.add(t_content2);
		p_east.add(p_preview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);

		add(p_east, BorderLayout.EAST);

		setSize(900, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		bt_open.addActionListener(this);
		bt_regist.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

	}

	public void openFile() {
//		파일 탐색기를 띄우고 사용자가 선택한 이미지 파일을 미리보게 하자
		int result = chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION) {
//			유저가 선택한 파일반환
			File file = chooser.getSelectedFile();
			System.out.println(file.getName());
			try {
				image = ImageIO.read(file);
				p_preview.repaint();
				
//				이미지 명에 사용할 현재 시간(Millis까지)
				long time = System.currentTimeMillis();
				System.out.println(time);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_open) {
			openFile();
		} else if (obj == bt_regist) {

		} else if (obj == bt_edit) {

		} else if (obj == bt_del) {

		}
	}

	public static void main(String[] args) {
		new GalleryMain();
	}
}
