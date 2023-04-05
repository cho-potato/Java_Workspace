package homework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShurekGallery extends JFrame {
	ArrayList<homework.CustomButton> btnList; // 컴파일 시점부터 사용할 자료형을 지정하는 것이 제네릭 방식
//	버튼 7개 생성예정(커스텀한 버튼)
	JPanel p;
	JPanel p_south; // 버튼 7개가 올려질 남쪽 패널
	ArrayList<Image> imageList = new ArrayList<Image>(); // size 0

//	슈랙그림이 그려질 위치 타겟지정
	int targetX; // 목표지점
	double a = 0.08;
	Thread thread; // 루프용 쓰레드 
	double x; // 그림의 x축 위치를 결정짓는 변수
	
	public ShurekGallery() {
		super("슈갤"); // 부모생성자는 자식보다 먼저 생성되어야 함
		btnList = new ArrayList<homework.CustomButton>(); // 제네릭을 포함한 것이 통째로 자료형임 // size 0
//		버튼 생성
		for (int i = 0; i < 7; i++) {
//		List는 Java Script의 배열과 거의 동일(크기 유동적 측면)
			btnList.add(new homework.CustomButton(this, i, 30, 30));
		}
		// 그림을 그리기 전에 미리 이미지를 준비
		createImage(); 
		
		p = new JPanel() {
			// paintComponent() 재정의 예정
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;	
				for(int i = 0; i<imageList.size(); i++) {
				g2.drawImage(imageList.get(i), (i*500)+(int)x, 0, 500, 340, ShurekGallery.this);
				}
			}
		};
		thread = new Thread() {
			public void run() {
				while(true) {
					gameLoop();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		
		p.setPreferredSize(new Dimension(500, 340));
		p.setBackground(Color.BLACK);

		p_south = new JPanel();
		p_south.setPreferredSize(new Dimension(500, 60));
		p_south.setBackground(Color.RED);

//		남쪽 패널에 버튼 7개 넣기
//		p_south는 default Layout이 FlowLayout으로 적용되어 있음
//		사용하는 모든 GUI 컴포넌트는 다음과 같이 두 가지 유형으로 분류
//		GUI Component? 사용자 입력을 받는 모든 컨트롤(버튼, 체크박스, 텍스트박스,,,)
//		1) 컨테이너류 : 상대를 포함할 수 있는 者(Frame, Panel ,,,)
//		컨테이너류는 상대를 포함해야 하기 때문에 배치관리자가 지원된다
//		-> Frame(BorderLayout), Panel(FlowLayout),,,
//		2) 비주얼컴포넌트 :  컨테이너류에 포함되는 者(Button, TextField ,,,)

//		for(int i = 0; i<btnList.size(); i++) {	
//		}
		// 개선된 for문은 Collection형에 최적화 되어 있기 때문에 내부적으로 반복 횟수도 알고 있다
		for (homework.CustomButton bt : btnList) {
			p_south.add(bt); // 패널에 부착
		}
		add(p);
		add(p_south, BorderLayout.SOUTH);

		setSize(500, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

// 클래스 패스에 존재하는 이미지들을 대상으로 이미지 객체 생성하기
	public void createImage() {
		Class myClass = this.getClass();
		for (int i = 0; i < btnList.size(); i++) {
			URL url = myClass.getClassLoader().getResource("res/shurek/img" + i + ".png");
			// BufferedImage도 Image의 자식이므로 Image형이다
			try {
				Image image = ImageIO.read(url);
				imageList.add(image); // 리스트에 추가
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("생성된 이미지는 총" + imageList.size() + "개");
	}
	public void tick() {
//		x축 방향으로 부드러운 감속도
		x = x + a*(targetX-x);
	}
	public void render() {
		p.repaint();
	}
	public void gameLoop() {
		tick();
		render();
	}
	public static void main(String[] args) {
		new ShurekGallery();
	}
}
