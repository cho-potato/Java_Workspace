package image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ImageManager;

// 자바에서 이미지를 얻는 방법은 번거로운 ToolKit만 있는 것이 아니다
// 사용 중인 OS의 디렉토리를 명시하는 것보다, 클래스 패스를 통한 자원접근이 더 자바스럽고, 플랫폼에 중립적이다
// 따라서 자원들도 package에 있다
public class HeroControl extends JFrame {
	JPanel p;
	Image image;
	ImageManager imageManager;
//	패키지에는 꼭 클래스만 넣을 수 있는게 아니다
//	패키지에 넣은 파일이 클래스인 경우 원래대로 패키지와 패키지를 . 찍어서 구분하고,
//	패키지에 넣은 파일이 클래스 외의  파일인 경우 디렉토리 취급하여 / 를 찍어 구분한다
	String[] imgName = { // 데이터는 규칙이 없다는 전제 하에 개발해야 한다(무조건 반복문 NONO)
			"res/hero/image1.png", "res/hero/image2.png", "res/hero/image3.png", "res/hero/image4.png",
			"res/hero/image5.png", "res/hero/image6.png", "res/hero/image7.png", "res/hero/image8.png",
			"res/hero/image9.png", "res/hero/image10.png", "res/hero/image11.png", "res/hero/image12.png",
			"res/hero/image13.png", "res/hero/image14.png", "res/hero/image15.png", "res/hero/image16.png",
			"res/hero/image17.png", "res/hero/image18.png"};
	Image[] images; // 이미지 배열 준비하기
	int index; // 이미지 배열의 몇 번째를 보여줄지를 결정짓는 index
	Thread thread; // 게임 Thread

	public HeroControl() {
//		클래스에 대한 정보를 가진 클래스 -> API 없이 .class 만으로도 상대의 class 내용을 알아낼 수 있다 		
//		Class myClass = this.getClass();
//		Method[] methods= myClass.getMethods();
//		for(Method m : methods) {
//			System.out.println(m.getName());			
//		}
		imageManager = new ImageManager();
		images = imageManager.createImages(imgName);
		p = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, 800, 600);
				g2.drawImage(images[index], 100, 50, 500, 500, HeroControl.this); // 내부 익명 클래스라 this 안먹힘
				if(index >= images.length-1) {
					index = 0;
				}
			}
		};
		thread = new Thread() {
			public void run() {
				while (true) {
					gameLoop();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start(); // Runnable
		
		add(p);
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 중앙에 프레임 놓기
	}

	public void tick() {
		index++;
	}

	public void render() {
		p.repaint();
	}

	public void gameLoop() {
		tick();
		render();
	}

	public static void main(String[] args) {
		new HeroControl();
	}
}
