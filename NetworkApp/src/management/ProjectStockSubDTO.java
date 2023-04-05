package management;

public class ProjectStockSubDTO {
	private int stocksub_idx; // PK
	private ProjectStockTopDTO projectStockTopDTO; // null
//	private int topcategory_idx;
	private String stocksub_name;
	
	public int getStocksub_idx() {
		return stocksub_idx;
	}
	public void setStocksub_idx(int stocksub_idx) {
		this.stocksub_idx = stocksub_idx;
	}
	public ProjectStockTopDTO getProjectStockTopDTO() {
		return projectStockTopDTO;
	}
	public void setProjectStockTopDTO(ProjectStockTopDTO projectStockTopDTO) {
		this.projectStockTopDTO = projectStockTopDTO;
	}
	public String getStocksub_name() {
		return stocksub_name;
	}
	public void setStocksub_name(String stocksub_name) {
		this.stocksub_name = stocksub_name;
	}
	
	
	
}
