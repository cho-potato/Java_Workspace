package excel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

// 상품 1개를 그릴 커스텀 패널

public class ProductItem extends JPanel{
//	이미지와 상품명, 브랜드, 가격이 출력되어야 한다
	Image image;
	Product product;
	
//	위의 멤버변수들은 이 클래스의 인스턴스가 생성될 때 넘겨받는다(=생성자에서 매개변수로 처리)
	
//	생성자 하나 만들어서 일단 육안으로 확인할 수 있는 배경을 준다
	public ProductItem(Product product) {
		this.product = product;
		this.setBackground(Color.RED);
		this.setPreferredSize(new Dimension(700, 100));
//		product 안에 로컬 경로가 들어있는 정보가 있으므로 createImage()메서드에 전달
		createImage();
	}
//	이미지 생성하기(=엑셀에 명시된 URL을 이용하여 로컬PC로 이미지 가져온 후 그린다
//	그 후 가져오는 시점은 엑셀이 등록되고, 파싱이 완료된 후에 진행
//	일단 엑셀 업로드 및 파싱
	public void createImage() {
//		로컬파일 경로를 이용하여 이미지 객체 생성
		File file = new File(product.getFileurl());
//		file 인스턴스를 매개변수로 넣기
		try {
//			멤버변수 Image에 대입 
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	위의 멤버변수들을 활용하여 그림그리기
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
//			그리기 전에 기존 그림 지우기
			g2.clearRect(0, 0, 700, 100);
//			이미지 그리기
			g2.drawImage(image, 0, 0, 80, 60, null);
//			페인트 색상지정
			g2.setColor(Color.GRAY);
//			제품명 그리기 DTO에서 꺼내쓰기
			g2.drawString(product.getProduct_name(), 120, 15);
//			브랜드 그리기
			g2.drawString(product.getBrand(), 120, 50);
//			가격 그리기
			g2.drawString(Integer.toString(product.getPrice()), 120, 85);
		}

}
