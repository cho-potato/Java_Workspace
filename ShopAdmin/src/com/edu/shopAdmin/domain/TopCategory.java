package com.edu.shopAdmin.domain;

// 레코드 한 건을 담게될 데이터 전달 객체(DTO)
// 로직 X, 은닉화 O

public class TopCategory {
	private int topcategory_idx;
	private String topcategory_name;
	
	public int getTopcategory_idx() {
		return topcategory_idx;
	}
	public void setTopcategory_idx(int topcategory_idx) {
		this.topcategory_idx = topcategory_idx;
	}
	public String getTopcategory_name() {
		return topcategory_name;
	}
	public void setTopcategory_name(String topcategory_name) {
		this.topcategory_name = topcategory_name;
	}
}
