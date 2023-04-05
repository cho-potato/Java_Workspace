package excel;

// fileurl 추가한 DTO

public class Product {
	private String product_name;
	private String brand;
	private int price;
	private String fileurl; // 다운로드를 다 받은 후에는 더이상 웹상 주소는 필요없음
//	
	
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	
}
