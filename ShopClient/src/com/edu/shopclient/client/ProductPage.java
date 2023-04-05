package com.edu.shopclient.client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.edu.shopclient.domain.Product;
import com.edu.shopclient.model.repository.ProductDAO;

// 상품을 출력해주는 페이지

public class ProductPage extends Page {
//	scroll은 하나의 컴포넌트만 담을 수 있으므로 상품 패널들을 품을 수 있는 패널이 필요하다(=반복문 돌릴 수 있는 패널, wrapper패널)
	JPanel wrapper; // 상품 아이템들을 포함할 패널
	JScrollPane scroll;
	List<ProductItem> itemList;
	ProductDAO productDAO;

	public ProductPage() {
		wrapper = new JPanel();
//		wrapper.setLayout(new GridLayout(10, 1));
		wrapper.setPreferredSize(new Dimension(780, 1400));

		itemList = new ArrayList<ProductItem>();
		productDAO = new ProductDAO();

//		getProductList();

		scroll = new JScrollPane(wrapper);
		scroll.setPreferredSize(new Dimension(800, 400));
		add(scroll);

	}

//	상품데이터 불러오기
	public void getProductList(String topcategory_name) {
		List<Product> productList = productDAO.selectAll();

//		기존 ItemList 삭제
		itemList.removeAll(itemList);
		
//		wrapper에 붙은 아이템패널들 모두 삭제
		wrapper.removeAll();
		
		for (int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);

			ProductItem item = new ProductItem(product);
			wrapper.add(item);
//		내가 몇 건을 보고있는지 알 수 있는 
			itemList.add(item); // 게시물 수를 파악할 수 있도록 담아놓자

		}
	}
}
