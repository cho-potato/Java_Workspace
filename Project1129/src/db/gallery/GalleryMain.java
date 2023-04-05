package db.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.StringUtil;

public class GalleryMain extends JFrame implements ActionListener {
//	서쪽 영역
	JPanel p_west;
	JTextField t_title;
	JTextField t_writer;
	JTextField t_content;
	JPanel p_sign; // 사인처리 할 패널
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
	Image detailImage; // 우측 패널에서 그려질 이미지
	File file; // 유저가 탐색기를 통해 선택한 파일
	String dir = "C:/java_workspace2/data/project1129/images";
	String fileName; // 개발자가 새롭게 정의한 파일명

	// DB 관련
	Connection conn;
	GalleryModel model;
	ArrayList<int[]> history = new ArrayList<int[]>();
//	int x, y;
	SignModel signModel;

	public GalleryMain() {
		connect();
		signModel = new SignModel(this);
		Dimension d1 = new Dimension(150, 500);
		Dimension d2 = new Dimension(145, 100);

//		서쪽 영역
		p_west = new JPanel();
		t_title = new JTextField(12);
		t_writer = new JTextField(12);
		t_content = new JTextField();
		p_sign = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, 145, 40);
				g2.setColor(Color.RED);
				for (int i = 0; i < history.size(); i++) {
					int[] dot = history.get(i); // 하나의 점을 거낸다
					g2.fillOval(dot[0], dot[1], 5, 5);
				}
			}
		};
		p_preview = new JPanel() {

			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.YELLOW);
				g2.fillRect(0, 0, 145, 130);
//				- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 순서대로 페인트 통 부어짐
				g2.setColor(Color.BLUE);

				if (image != null) {
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
		p_sign.setPreferredSize(d2);
		p_preview.setPreferredSize(d2);
		p_preview.setBackground(Color.BLACK);

		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(p_sign);
		p_west.add(p_preview);
		p_west.add(bt_open);
		p_west.add(bt_regist);

		add(p_west, BorderLayout.WEST);

//		센터 영역
		table = new JTable(model = new GalleryModel(this));
		scroll = new JScrollPane(table);
		add(scroll);

//		동쪽 영역
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextField();
		p_preview2 = new JPanel() {

			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, 145, 130); // 지우기
				g2.drawImage(detailImage, 0, 0, 145, 130, GalleryMain.this);
			}
		};

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
		setLocationRelativeTo(null);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt_open.addActionListener(this);
		bt_regist.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

//		윈도우와 리스너 연결
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				release(conn);
				System.exit(0);
			}
		});

//		테이블과 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				table.setSelectionBackground(Color.RED); // 하이라이트 효과
//				일차원 배열 추출
				int row = table.getSelectedRow(); // 유저가 선택한 row
				String[] record = model.data[row]; // 하나의 게시물 추출

				getDetail(record);
			}
		});
//		p_sign과 마우스 리스너 연결
		p_sign.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println();
				int x = e.getX();
				int y = e.getY();
				System.out.println(x + "," + y);
				int[] dot = { x, y }; // 한 점을 표현할 배열
				history.add(dot);

				p_sign.repaint();
			}
		});
	};

	public void openFile() {
//		파일 탐색기를 띄우고 사용자가 선택한 이미지 파일을 미리보게 하자
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
//			유저가 선택한 파일반환
			file = chooser.getSelectedFile();
			System.out.println(file.getName());
			try {
				image = ImageIO.read(file);
				p_preview.repaint();

//				이미지 명에 사용할 현재 시간(Millis까지)
				long time = System.currentTimeMillis();
				System.out.println(time);
				fileName = time + "." + StringUtil.getExtend(file.getName());
				System.out.println(fileName);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copy() {
//		이미지 복사 
		FileInputStream fis = null; // 파일을 대상으로 한 입력 스트림
		FileOutputStream fos = null; // 파일을 대상으로 한 출력 스트림
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(dir + "/" + fileName); // empty 상태의 bin 파일을 생성한다는 특징이 있음
			byte[] buff = new byte[1024]; // 메모리에 보관할 때는 int가 아니라 바이트와 쇼트가 의미있음
//			1024개가 모여야 read가 실행됨 (fis.read는 data에 들어가는 것이 아니라 buff에 드감)

			int data = -1; // 읽혀지지 않은 상태
			while (true) {
				data = fis.read(buff);
				if (data == -1)
					break; // break문 아래 영역은 break문을 만나지 않은 유효한 영역. 따라서 데이터가 있으므로 출력에 용이하다
				fos.write(buff); // fos.write(data); // 출력
				System.out.println(data); // 읽혀진 데이터
			}
			System.out.println("완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void regist() {
		copy(); // 이미지 복사
		int result = model.insert(t_title.getText(), t_writer.getText(), t_content.getText(), fileName); // 오라클 등록

		// 사인 정보 입력
		for (int i = 0; i < history.size(); i++) {
			int[] dot = history.get(i);
			signModel.insert(dot[0], dot[1], result);
		}
		if (result > 0) {
			model.selectAll();
			table.updateUI();
		}
	}

	public void getDetail(String[] record) {
//		동쪽 영역에 사용자가 선택한 레코드 1건을 출력
		t_title2.setText(record[1]);
		t_writer2.setText(record[2]);
		t_content2.setText(record[3]);
//		이미지처리
		try {
			File file = new File(dir + "/" + record[4]);
			System.out.println(file);
			detailImage = ImageIO.read(file);
			p_preview2.repaint();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_open) {
			openFile();
		} else if (obj == bt_regist) {
			regist();
		} else if (obj == bt_edit) {

		} else if (obj == bt_del) {

		}
	}

	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
//		- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 1) 드라이버 로드
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "javase";
			String pass = "1234";
			conn = DriverManager.getConnection(url, user, pass);
//		- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 2) 접속 
			if (conn != null) {
				this.setTitle("접속 성공");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void release(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void release(PreparedStatement pstmt, ResultSet rs) { // select의 경우
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new GalleryMain();
	}
}
