package com.edu.shopclient.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.edu.shopclient.domain.Product;

// 하나의 상품을 표현할 패널 정의shopclient

public class ProductItem extends JPanel {
	String path = "C:/java_workspace2/data/shop/product/";
	Image image;
	
	Product product;
	
	
	public ProductItem(Product product) {
		this.product = product;
		this.setPreferredSize(new Dimension(750, 80));
//		화면이 그려지기 전 이미지를 얻어와야 하므로 생성자에서 이미지 생성 메서드를 호출한다
		createImage();
	}
	public void createImage() {
		File file = new File(path+product.getFilename());
		try {
			image = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void paintComponent(Graphics g) {

		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 850, 80);
		
//		그림그리기
		g.drawImage(image, 10, 10, 80, 60, null);
//		글씨쓰기(하위 카테고리, 상품명, 브랜드, 가격)
		g.setColor(Color.RED);
		g.drawString(product.getSubcategory().getSubcategory_name(), 100, 20);
		g.drawString(product.getProduct_name(), 100, 35);
		g.drawString(product.getBrand(), 100, 50);
		g.drawString(Integer.toString(product.getPrice()), 100, 65);
		
	}

}
