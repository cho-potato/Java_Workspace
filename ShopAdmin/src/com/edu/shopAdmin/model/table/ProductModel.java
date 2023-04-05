package com.edu.shopAdmin.model.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.edu.shopAdmin.admin.AdminMain;
import com.edu.shopAdmin.domain.Product;

public class ProductModel extends AbstractTableModel {
	AdminMain adminMain;
	List<Product> productList;
	Vector<String> column = new Vector<String>();
	
//	넘겨받는 이유는 AdminMain이 보유 중인 각종 객체들을 사용하기 위함(ProductDAO 등 ,,,)
	public ProductModel(AdminMain adminMain) {
		this.adminMain = adminMain;
		
		column.add("하위카테고리순번");
		column.add("하위카테고리이름");
		column.add("상품코드");
		column.add("상품명");
		column.add("브랜드");
		column.add("가격");
		column.add("이미지명");
		
		getProductList();
	}

	public void getProductList() {
		productList = adminMain.productDAO.selectAll();
	}

	public int getRowCount() {

		return productList.size();
	}

	public int getColumnCount() {

		return 7;
	}

	public Object getValueAt(int row, int col) {
//		보유한 List는 1차원이므로 row, col 형태로 데이터에 접근할 수 없다
//		객체지향적인 직관성을 살려 적용하기위해 col을 버리는 형태로 접근해본다
		Product product = productList.get(row);
		
//		col변수는 Table에서 몇 번째 호수에 넣어야할지 결정짓는다
		String value = null;
		if (col == 0) {
			value = Integer.toString(product.getSubcategory().getSubcategory_idx());
		} else if(col == 1) {
			value = product.getSubcategory().getSubcategory_name();
		} else if(col == 2) {
			value = Integer.toString(product.getProduct_idx());
		} else if(col == 3) {
			value = product.getProduct_name();
		} else if(col == 4) {
			value = product.getBrand();
		} else if(col == 5) {
			value = Integer.toString(product.getPrice());
		} else if(col == 6) {
			value = product.getFilename();
		}
		return value;
	}

	public String getColumnName(int col) {
		
		return column.elementAt(col);
		
	}
}
