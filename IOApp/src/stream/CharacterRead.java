package stream;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CharacterRead extends JFrame {
	JButton bt;
	JTextArea area;
	FileInputStream fis;

	public CharacterRead() {
		bt = new JButton("읽기");
		area = new JTextArea();
		area.setPreferredSize(new Dimension(480, 550));
		Font font = new Font("Verdana", Font.BOLD, 30);

		area.setFont(font);

		add(bt);
		add(area);

		setLayout(new FlowLayout());
		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

//		 버튼과 리스너와의 연결
//		 bt.addActionListener(new MyActionListener(area)); // 재사용성이 없다고 판다되니까 new //
//		 재사용성이 있다고 판단되면 변수로 빼고
//		 내부익명클래스
//		 -> 클래스 코드 안에 또 다른 이름 없는 클래스를 들 수 있는데, 이러한 용도의 클래스를 가리켜 내부 익명 클래스라 한다
//		 -> 내부 익명 클래스는 이벤스 구현시 특히, 재사용성이 없는 클래스를 물리적인.java 파일로까지 만들 필요가 없으므로 자주 사용된다
//		 -> point : new 뒤에 부모 자료형, 뒤에 오는 {}를 자식 클래스로 본다
//		 인터페이스는 new 할 수 없지만, 그 뒤에 ㄴ를 설정함으로써 부모와 자식관계가 됨
//		 내부 익명 클래스의 장점
//		 - 내부 익명 클래스 자신이 소속되어 있는 외부 클래스(CharacterRead)의 멤버변수를 내 것처럼 맘대로 접근할 수 있다
		bt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
//				System.out.println("ㅎㅎㅎㅎㅎㅎㅎㅎㅎ");
//				area.append("ㅎㅎㅎㅎㅎㅎㅎㅎㅎ");
				readFile();
			}
		});
	}

//	 메모장 파일 읽기(한글이 포함된)
	public void readFile() {
//		지금까지 써왔던 FileInputStream은 바이트기반 스트림이고, 1byte씩 데이터 처리한다.
//		때문에 처리대상 파일명에 영문자가 포함되어 있을 경우 문자화시켜도 아무런 문제가 없었다
//		하지만 한글의 경우 2byte로 구성되어 있으므로 문자화 시킬 경우 한글을 제대로 표현할 수 없다(깨진다)
//
//		주의) 읽어들이 1 byte의 데이터를 char형으로 변경할 때 깨지는 것일 뿐이지
//		파일 복사는 중간에 문자로 변환하여 확인하는 것이 아닌, 원본을 그대로 복원하는 경우기 때문에 꺠질 일이 없다
//
//		읽어들인 데이터를 한글로 변환하기 위해서는 문자기반 스트림을 이용한다

// 		FileInputStream fis = null; // 지역변수 초기화
//		System.out.println(fis);

		FileReader reader = null; // 전 세계 모든 문자가 깨지지 않는 문자인식 스트림. 영문이든 한글이든 문자를 기반으로 인식하기 때문에 java 오라클 라면 8번 인식한다
		String path = "C:/java_workspace2/IOApp/data/memo.txt";

//		스트림 생성
//		fis = new FileInputStream(path); 블럭지정하고 Shift + Alt + Z => try/catch 구문
		try {
			fis = new FileInputStream(path);
			reader = new FileReader(path);
			System.out.println("스트림 생성 성공");

			int data = -1;
//			배열로 돌릴 수 있지만 하나하나 해보자
//			 area.append(Integer.toString(data)); -> j에 해당하는 숫자 106 출력됨 area.append(Character.toString((char) data)); j가 출력됨
			data = reader.read(); // 1byte 읽기 data = fis.read();
			System.out.print(Character.toString((char) data)); // area.append(Character.toString((char) data));
			data = reader.read(); // 1byte 읽기 data = fis.read();
			System.out.print(Character.toString((char) data)); // area.append(Character.toString((char) data));
			data = reader.read(); // 1byte 읽기 data = fis.read();
			System.out.print(Character.toString((char) data)); // area.append(Character.toString((char) data));
			data = reader.read(); // 1byte 읽기 data = fis.read();
			System.out.print(Character.toString((char) data)); // area.append(Character.toString((char) data));
			data = reader.read(); // 1byte 읽기 data = fis.read();
			System.out.print(Character.toString((char) data)); // area.append(Character.toString((char) data));
			data = reader.read(); // 1byte 읽기 data = fis.read();
			System.out.print(Character.toString((char) data)); // area.append(Character.toString((char) data));
			data = reader.read(); // 1byte 읽기 data = fis.read();
			System.out.print(Character.toString((char) data)); // area.append(Character.toString((char) data));
			data = reader.read(); // 1byte 읽기 data = fis.read();
			System.out.println(Character.toString((char) data)); // area.append(Character.toString((char) data));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		new CharacterRead();
	}
}
