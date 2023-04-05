package management;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class StockProductModel extends AbstractTableModel{
	InnerStockPage innerStockPage;
	List<StockProductDTO> productList;
	Vector<String> column = new Vector<String>();

//	넘겨받는 이유는 AdminMain이 보유 중인 각종 객체들을 사용하기 위함(ProductDAO 등 ,,,)
	public StockProductModel(InnerStockPage innerStockPage) {
		this.innerStockPage = innerStockPage;
		
		column.add("중분류코드");
		column.add("소분류이름");
		column.add("자재코드");
		column.add("자재명");
		column.add("납품업체");
		column.add("재고수량");
		column.add("소분류코드");
		
		getProductList();
	}

	public void getProductList() {
		productList = innerStockPage.stockProductDAO.selectAll();
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
		StockProductDTO stockProductDTO = productList.get(row);
		
//		col변수는 Table에서 몇 번째 호수에 넣어야할지 결정짓는다
		String value = null;
		if (col == 0) {
			value = Integer.toString(stockProductDTO.getProjectStockSubDTO().getStocksub_idx());
		} else if(col == 1) {
			value = stockProductDTO.getProjectStockSubDTO().getStocksub_name();
		} else if(col == 2) {
			value = Integer.toString(stockProductDTO.getStockproduct_idx());
		} else if(col == 3) {
			value = stockProductDTO.getStockproduct_name();
		} else if(col == 4) {
			value = stockProductDTO.getBrand();
		} else if(col == 5) {
			value = Integer.toString(stockProductDTO.getPrice());
		} else if(col == 6) {
			value = stockProductDTO.getFilename();
		}
		return value;
	}

	public String getColumnName(int col) {
		
		return column.elementAt(col);
		
	}
}