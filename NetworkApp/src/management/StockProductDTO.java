package management;


public class StockProductDTO {
	private int stockproduct_idx ;
	private ProjectStockSubDTO projectStockSubDTO;
	private String stockproduct_name;
	private String brand;
	private int price;
	private String filename;
	
	public int getStockproduct_idx() {
		return stockproduct_idx;
	}
	public void setStockproduct_idx(int stockproduct_idx) {
		this.stockproduct_idx = stockproduct_idx;
	}
	public ProjectStockSubDTO getProjectStockSubDTO() {
		return projectStockSubDTO;
	}
	public void setProjectStockSubDTO(ProjectStockSubDTO projectStockSubDTO) {
		this.projectStockSubDTO = projectStockSubDTO;
	}
	public String getStockproduct_name() {
		return stockproduct_name;
	}
	public void setStockproduct_name(String stockproduct_name) {
		this.stockproduct_name = stockproduct_name;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
