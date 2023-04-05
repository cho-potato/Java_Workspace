package io.basic;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Stream을 볼 때는 크게 보고 접근
// 1. 입력인지 출력인지 접근
// 2. 문자기반인지 바이트 기반인지 

public class MemoApp extends JFrame {
	JButton bt;
	JButton bt_open; // 파일 탐색기 띄우는 버튼
	JButton bt_save; // area의 내용을 파일에 출력하기 위한 버튼
	JFileChooser chooser;

	JTextArea area;
	JScrollPane scroll;
	String path = "C:/java_workspace2/data/NetworkApp/res/memo.txt";

	File file; // 현재 열어놓은 바로 그 파일

	public MemoApp() {
		bt = new JButton("LOAD");
		bt_open = new JButton("OPEN");
		bt_save = new JButton("SAVE");
		chooser = new JFileChooser();
		area = new JTextArea();
		scroll = new JScrollPane(area);
		setLayout(new FlowLayout());

		scroll.setPreferredSize(new Dimension(570, 320));

		add(bt); // 프레임에 부착
		add(bt_open);
		add(bt_save);

		add(scroll); // 프레임에 부착

		setSize(600, 400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData();
				loadData2();
			}
		});
		bt_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		bt_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
	}

// 개발자가 문자기반 스트림의 존재를 모를 경우
	public void loadData() {
//		Reader 또는 Writer가 없으므로 바이트 기반으로 의심됨(문자이해못함)
		FileInputStream fis = null; // -> 바이트 기반이라 위대한 한글 읽는거 실패함
		try {
			fis = new FileInputStream(path);
			int data = -1;
			data = fis.read();
			System.out.println(data); // 아마도 바이트
			data = fis.read();
			System.out.println((char) data); // 아마도 J
			data = fis.read();
			System.out.println((char) data); // 아마도 a
			data = fis.read();
			System.out.println((char) data); // 아마도 v
			data = fis.read();
			System.out.println((char) data); // 아마도 a
			data = fis.read();
			System.out.println((char) data); // 아마도 공백
			data = fis.read();
			System.out.println((char) data); // 아마도 프 : 땡떙떙
//			read : Reads a byte of data from this input stream. "This method blocks if no input is yet available."
//			This method blocks if no input is yet available. : 입력될 때까지 대기 중인 상태(block)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

// 개발자가 문자 기반 스트림을 이용할 경우
	public void loadData2() {
		InputStreamReader reader = null;
		FileInputStream fis = null;

		try {
//			바이트 기반 스트림 생성
			fis = new FileInputStream(path);

//			기존 바이트 기반 스트림이 있어야 문자 스트림을 읽을 수 있다
//			바이트 기반 스트림을 문자기반 스트림으로 업그레이드 한 형태임(감싼 형태)
//			한글이 2바이트 기반이라 해도 바이트 기반 스트림(1바이트 기반) 빨대 안에서 2개가 하나인 형태로 입력되기 때문에(2알갱이씩 읽는게 아니라 해석하는 것)
//			java 프로그래머를 읽을 때 10번만 read하게 됨
			reader = new InputStreamReader(fis);
			int data = -1;
			while (true) {
				data = reader.read();
				if (data == -1)
					break;
				area.append(Character.toString((char) data));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
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
	}

	public void openFile() {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();

//			아래 객체의 정체는 InputStreamReader의 자식새끼이다
			FileReader reader = null;
			BufferedReader buffr = null;

			try {
				reader = new FileReader(file);
				buffr = new BufferedReader(reader);
				
				String data = null; // data = -1에서 바뀐 이유 : 문자가 모여 문자열이 되었기 때문
				int count = 0;

				while (true) {
					data = buffr.readLine();
					if (data == null)
						break;
//					data는 한 줄의 String이 들어있으며, 한 줄을 area의 출력시마다  area의 개행을 처리하자
					area.append(data + "\n");
					count++;
				}
				System.out.println("읽횟 = " + count);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (buffr != null) {
					try {
						buffr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void saveFile() {
		FileWriter writer = null; // 파일을 대상으로 한 문자기반 출력스트림
		BufferedWriter buffw = null; // Buffer 처리된 문자기반 스트림

		try {
			writer = new FileWriter(file);
			buffw = new BufferedWriter(writer);
			buffw.write(area.getText());
			JOptionPane.showMessageDialog(this, "저장완료");
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (buffw != null) {
				try {
					buffw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new MemoApp();

	}

}
