package management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import network.util.DBManager;
import util.ImageManager;

public class InnerStockPage extends ProjectInnerPage implements ActionListener {
	ProjectStockTopDAO projectStockTopDAO; // Has a 관계
	ProjectStockSubDAO projectStockSubDAO;
	public StockProductDAO stockProductDAO;
	DBManager dbManager = DBManager.getInstance();

	Border blackline;
	TitledBorder listBorder1;
	TitledBorder listBorder2;

	JPanel p_border;
	JPanel p_borderNorth;
	JPanel p_flowNorth;
	JLabel moveRight;
//	서쪽 영역
	JComboBox<String> box_top; // 상위 카테고리
	JComboBox<String> box_sub; // 하위 카테고리
	JLabel la_productName;
	JTextField t_name; // 상품명
	JLabel la_brand;
	JTextField t_brand; // 브랜드명
	JLabel la_price;
	JTextField t_price; // 가격
	JButton bt_regist; // 등록버튼
	JButton bt_edit; //
	JButton bt_del; //
	JComboBox<String> box_topCategory1; // 검색 구분
	JComboBox<String> box_subCategory2; // 검색 구분
	JTextField t_keyword;// 검색어
	JButton bt_search; // 검색 버튼

//	센터영역
	JPanel p_center;// 북쪽과 센터로 구분되어질 패널
	StockProductModel stockProductModel; // JTable이 표 구성에 참조할 객체
	JTable table;
	JScrollPane scroll;

//	하위 카테고리 선택시 담아놓을 PK(subcategory_idx)
	List<Integer> subIdxList1 = new ArrayList<Integer>();
	List<Integer> subIdxList2 = new ArrayList<Integer>();

	String dir = "C:/java_workspace2/data/shop/product/";
//	서쪽 영역에서 미리보기 될 이미지 명
	String filename;
//	현재 보고 있는 상품
	StockProductDTO currentProduct; // null

	public InnerStockPage(ProjectMainPage projectMainPage) {
		super(projectMainPage);
		projectStockTopDAO = new ProjectStockTopDAO();
		projectStockSubDAO = new ProjectStockSubDAO();
		stockProductDAO = new StockProductDAO();

		p_border = new JPanel();
		p_borderNorth = new JPanel();
		p_flowNorth = new JPanel();
		moveRight = new JLabel();
		moveRight.setPreferredSize(new Dimension(925, 0));
//		서쪽 영역
		box_top = new JComboBox<String>(); // 상위 카테고리
		box_sub = new JComboBox<String>(); // 하위 카테고리
		la_productName = new JLabel("자재명");
		t_name = new JTextField(); // 상품명
		la_brand = new JLabel("납품업체");
		t_brand = new JTextField(); // 브랜드명
		la_price = new JLabel("재고수량");
		t_price = new JTextField(); // 가격
		bt_regist = new JButton("추가"); // 등록버튼
		bt_edit = new JButton("수정"); //
		bt_del = new JButton("삭제"); //
		box_topCategory1 = new JComboBox<String>(); // 검색 구분
		box_subCategory2 = new JComboBox<String>(); // 검색 구분
		t_keyword = new JTextField();// 검색어
		bt_search = new JButton("조회"); // 검색 버튼

//		센터영역
		p_center = new JPanel();// 북쪽과 센터로 구분되어질 패널

		table = new JTable(stockProductModel = new StockProductModel(this));
//		table = new JTable(10, 7);
		scroll = new JScrollPane(table);

//		add + style
		blackline = BorderFactory.createLineBorder(Color.white);

		listBorder1 = BorderFactory.createTitledBorder(blackline, "재고변경");
		listBorder2 = BorderFactory.createTitledBorder(blackline, "재고현황");
		listBorder1.setTitleJustification(TitledBorder.CENTER);
		listBorder2.setTitleJustification(TitledBorder.CENTER);

		p_borderNorth.setBorder(listBorder1);
		p_center.setBorder(listBorder2);

		p_border.setLayout(new BorderLayout());
		add(p_border);
		p_border.setPreferredSize(new Dimension(1700, 685));
		p_border.setBackground(Color.BLACK);

		p_border.add(p_borderNorth, BorderLayout.NORTH);
		p_borderNorth.setPreferredSize(new Dimension(1500, 120));
		p_borderNorth.setBackground(Color.white);

		Dimension d = new Dimension(200, 30);
//		콤보박스
		box_top.setPreferredSize(d);
		box_sub.setPreferredSize(d);
		box_topCategory1.setPreferredSize(d);
		box_subCategory2.setPreferredSize(d);

//		라벨
		Dimension d1 = new Dimension(100, 30);
		la_productName.setFont(new Font("고딕", Font.BOLD, 15));
		la_productName.setPreferredSize(d1);
		la_productName.setHorizontalAlignment(SwingConstants.CENTER);
		la_brand.setFont(new Font("고딕", Font.BOLD, 15));
		la_brand.setPreferredSize(d1);
		la_brand.setHorizontalAlignment(SwingConstants.CENTER);
		la_price.setFont(new Font("고딕", Font.BOLD, 15));
		la_price.setPreferredSize(d1);
		la_price.setHorizontalAlignment(SwingConstants.CENTER);

//		텍스트필드
		t_name.setPreferredSize(d);
		t_brand.setPreferredSize(d);
		t_price.setPreferredSize(d);
		t_keyword.setPreferredSize(new Dimension(205, 30));

//		버튼
		Dimension d2 = new Dimension(100, 40);
		bt_regist.setPreferredSize(d2);
		bt_regist.setBackground(Color.LIGHT_GRAY);
		bt_regist.setForeground(Color.BLACK);
		bt_regist.setFont(new Font("고딕", Font.BOLD, 15));
		bt_regist.setBorderPainted(false);
		bt_regist.setFocusPainted(false);

		bt_edit.setPreferredSize(d2);
		bt_edit.setBackground(Color.LIGHT_GRAY);
		bt_edit.setForeground(Color.BLACK);
		bt_edit.setFont(new Font("고딕", Font.BOLD, 15));
		bt_edit.setBorderPainted(false);
		bt_edit.setFocusPainted(false);

		bt_del.setPreferredSize(d2);
		bt_del.setBackground(Color.RED);
		bt_del.setForeground(Color.WHITE);
		bt_del.setFont(new Font("고딕", Font.BOLD, 15));
		bt_del.setBorderPainted(false);
		bt_del.setFocusPainted(false);

		bt_search.setPreferredSize(d2);
		bt_search.setBackground(Color.LIGHT_GRAY);
		bt_search.setForeground(Color.BLACK);
		bt_search.setFont(new Font("고딕", Font.BOLD, 15));
		bt_search.setBorderPainted(false);
		bt_search.setFocusPainted(false);

		p_borderNorth.add(box_top);
		p_borderNorth.add(box_sub);
		p_borderNorth.add(la_productName);
		p_borderNorth.add(t_name);
		p_borderNorth.add(la_brand);
		p_borderNorth.add(t_brand);
		p_borderNorth.add(la_price);
		p_borderNorth.add(t_price);
		p_borderNorth.add(bt_regist);
		p_borderNorth.add(bt_edit);
		p_borderNorth.add(bt_del);
		p_borderNorth.add(moveRight);
		p_borderNorth.add(box_topCategory1);
		p_borderNorth.add(box_subCategory2);
		p_borderNorth.add(t_keyword);
		p_borderNorth.add(bt_search);

//		센터영역
		p_border.add(p_center);
		p_center.setPreferredSize(new Dimension(1500, 100));
		p_center.setBackground(Color.GRAY);
		p_center.add(scroll);
		scroll.setPreferredSize(new Dimension(1500, 500));

		getTopList1();
		getTopList2();

		box_top.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println(e.getItem());
					int stocktop_idx = projectStockTopDAO.getStocktop_idx((String) e.getItem());
					System.out.println(stocktop_idx);
//					getSubList(box_sub, box_subCategory2, subIdxList, stocktop_idx);
					getSubList1(box_sub, subIdxList1, stocktop_idx);
				}
			}
		});
		box_topCategory1.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println(e.getItem());
					int stocktop_idx = projectStockTopDAO.getStocktop_idx((String) e.getItem());
					System.out.println(stocktop_idx);
//					getSubList(box_sub, box_subCategory2, subIdxList, stocktop_idx);
					getSubList2(box_subCategory2, subIdxList2, stocktop_idx);
				}
			}
		});
//		버튼과 리스너 연결

		bt_regist.addActionListener(this);
		bt_search.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

//		마우스리스너 연결
		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
//				ArrayList의 Index에 접근할 수 있는 정보
				int row = table.getSelectedRow(); // 유저가 선택한 행
				int col = table.getSelectedColumn(); // 유저가 선택한 열
				System.out.println("선택한 로둥탁은" + row + ", 선택한 콜둥탁은" + col);
//				TopCategory를 가져올 수 없기 때문에 top-sub-product 세 개를 join해야 함
				String product_idx = (String) table.getValueAt(row, 2);
				System.out.println("product_idx=" + product_idx);

				getDetail(Integer.parseInt(product_idx));
			}
		});
//		this.setBackground(Color.BLUE);
	}

//	선택한 레코드 한 건 가져오기(결과를 우측에 반영)
	public void getDetail(int stockproduct_idx) {
		StockProductDTO stockProductDTO = stockProductDAO.select(stockproduct_idx);
		currentProduct = stockProductDTO; // 현재 보고있는 상품 대입
		System.out.println("getDetail = " + stockProductDTO);
////		우측 영역에 반영하기
////		1) TopCategory DTO에 들어있는 카테고리 이름을 추출
//		ProjectStockSubDTO projectStockSubDTO = stockProductDTO.getProjectStockSubDTO();
//		ProjectStockTopDTO projectStockTopDTO = projectStockSubDTO.getProjectStockTopDTO();
//		String topName = projectStockTopDTO.getStocktop_name();
	}

//		상위 카테고리 가져오기
	public void getTopList1() {
		List<ProjectStockTopDTO> topList = projectStockTopDAO.selectAll();
//			서쪽 영역
		box_top.addItem("상위 카테고리");
		for (ProjectStockTopDTO projectStockTopDTO : topList) {
			box_top.addItem(projectStockTopDTO.getStocktop_name());
		}
//		box_topCategory1.addItem("상위 카테고리");
//		for (ProjectStockTopDTO projectStockTopDTO : topList) {
//			box_topCategory1.addItem(projectStockTopDTO.getStocktop_name());
//		}

	}
	public void getTopList2() {
		List<ProjectStockTopDTO> topList2 = projectStockTopDAO.selectAll();
		box_topCategory1.addItem("상위 카테고리");
		for (ProjectStockTopDTO projectStockTopDTO : topList2) {
			box_topCategory1.addItem(projectStockTopDTO.getStocktop_name());
		}
		
	}

//	하위 카테고리 가져오기
	public void getSubList1(JComboBox box, List list, int stocktop_idx) {
//		하의 -> 청바지 반바지 면바지 ,,,
		List<ProjectStockSubDTO> subList1 = projectStockSubDAO.selectByTopCategory(stocktop_idx);
		System.out.println("getSubList.size는" + subList1.size());
//		기존 아이템 지우기
//		box_sub.removeAllItems();
		box.removeAllItems(); // 서쪽이 될지 동쪽이 될지 알 수 없다
//		box_subCategory2.removeAllItems();
//		subIdxList.removeAll(subIdxList);
		list.removeAll(list); // 서쪽이 될지 동쪽이 될지 알 수 없다
//		box_subCategory2.removeItem(list); // 서쪽이 될지 동쪽이 될지 알 수 없다

//		해당 Index에서 요소로 들어있는 DTO 꺼내기
//		box_sub.addItem("하위 카테고리");
		box.addItem("하위 카테고리");
//		box_subCategory2.addItem("하위 카테고리");

		for (int i = 0; i < subList1.size(); i++) {
			ProjectStockSubDTO projectStockSubDTO = subList1.get(i);
//			box_sub.addItem(subCategory.getSubcategory_name());
			box.addItem(projectStockSubDTO.getStocksub_name());
//			box_subCategory2.addItem(projectStockSubDTO.getStocksub_name());

//		한글로 된 아이템 이름뿐만 아니라 해당 PK도 보관하자
//			subIdxList.add(subCategory.getSubcategory_idx()); // PK
			list.add(projectStockSubDTO.getStocksub_idx()); // PK

		}
		System.out.println("보관된 subIdx 수는" + subIdxList1.size());
//		System.out.println("보관된 subIdx 수는" + subIdxList);
		System.out.println("보관된 subIdx 수는" + list);
	}
	public void getSubList2(JComboBox box_subCategory2, List list, int stocktop_idx) {
//		하의 -> 청바지 반바지 면바지 ,,,
		List<ProjectStockSubDTO> subList2 = projectStockSubDAO.selectByTopCategory(stocktop_idx);
		System.out.println("getSubList.size는" + subList2.size());
//		기존 아이템 지우기
		box_subCategory2.removeAllItems();

		box_subCategory2.removeItem(list); // 서쪽이 될지 동쪽이 될지 알 수 없다
		
//		해당 Index에서 요소로 들어있는 DTO 꺼내기
		box_subCategory2.addItem("하위 카테고리");
		
		for (int i = 0; i < subList2.size(); i++) {
			ProjectStockSubDTO projectStockSubDTO = subList2.get(i);
			box_subCategory2.addItem(projectStockSubDTO.getStocksub_name());
			
//		한글로 된 아이템 이름뿐만 아니라 해당 PK도 보관하자
//			subIdxList.add(subCategory.getSubcategory_idx()); // PK
			list.add(projectStockSubDTO.getStocksub_idx()); // PK
			
		}
		System.out.println("보관된 subIdx 수는" + subIdxList2.size());
//		System.out.println("보관된 subIdx 수는" + subIdxList);
		System.out.println("보관된 subIdx 수는" + list);
	}

//	모든 상품레코드 가져오기 (하위카테고리와 조인된 상태로)
	public void getProductList() {
//		List productList = productDAO.selectAll();
//		모델 업데이트, Jtable 업데이트
		stockProductModel.getProductList();
		table.updateUI();
	}

//	상품 등록
	public void regist() {
//		ProductDAO에게 insert 하기
//		유저가 선택한 하위 카테고리 콤보박스의 Index
		int index = box_sub.getSelectedIndex();

		int stocksub_idx = subIdxList1.get(index - 1);
		String stockproduct_name = t_name.getText();
		String brand = t_brand.getText();
		int price = Integer.parseInt(t_price.getText());

		StockProductDTO stockProductDTO = new StockProductDTO(); // empty

		ProjectStockSubDTO projectStockSubDTO = new ProjectStockSubDTO();
//		Product와 SubCategory는 전혀 다른 상태. subCategory를 product에 넣어주자
		stockProductDTO.setProjectStockSubDTO(projectStockSubDTO);

		projectStockSubDTO.setStocksub_idx(stocksub_idx);
		stockProductDTO.setStockproduct_name(stockproduct_name);
		stockProductDTO.setBrand(brand);
		stockProductDTO.setPrice(price);
		stockProductDTO.setFilename(filename);

		int n = stockProductDAO.insert(stockProductDTO);
		if (n > 0) {
			JOptionPane.showMessageDialog(this, "등록성공");
			getProductList();
		}
//		productDAO.insert(subcategory_idx, product_name, brand, price, filename);
//		productDAO.insert(채워진 DTO);
	}

//	선택한 상품 삭제하기
	public void del() {
		if (currentProduct == null) {
			JOptionPane.showMessageDialog(this, "제정신이냐?");
		} else {
			int op = JOptionPane.showConfirmDialog(this, "삭제할ㄲㅏ...");
			if (op == JOptionPane.OK_OPTION) { // 승인
				int n = stockProductDAO.delete(currentProduct.getStockproduct_idx());
				if (n > 0) {
					JOptionPane.showMessageDialog(this, "삭제완료");
//						JTable은 ProductModel이 보유한 productList만 참조하고 있으므로
//						갱신된 내용을 보여주려면 결국 productList가 변경되어야 한다
//						따라서, DB를 다시 조회한 후 productList를 재설정한다 (JTable.updateUI())
					getProductList();
					currentProduct = null; // 다시 아무 것도 선택하지 않은 상태
				}
			}
		}
	}

//		상품 수정
	public void update() {
			if (currentProduct == null) {
				JOptionPane.showMessageDialog(this, "수정하싈...?");
			} else {
				int op = JOptionPane.showConfirmDialog(this, "제정신이냐고");
				if (op == JOptionPane.OK_OPTION) { // 승인
//				DB Update
				StockProductDTO stockProductDTO = new StockProductDTO();
				ProjectStockSubDTO projectStockSubDTO = new ProjectStockSubDTO();

				stockProductDTO.setProjectStockSubDTO(projectStockSubDTO);  // 참조관계

				int stocksub_idx = subIdxList1.get(box_sub.getSelectedIndex() - 1);
				projectStockSubDTO.setStocksub_idx(stocksub_idx);
				
				stockProductDTO.setStockproduct_idx(currentProduct.getStockproduct_idx());
				stockProductDTO.setStockproduct_name(t_name.getText());
				stockProductDTO.setBrand(t_brand.getText());
				stockProductDTO.setPrice(Integer.parseInt(t_price.getText()));
				
				int n = stockProductDAO.update(stockProductDTO);
				if (n > 0) {
//					refresh
					JOptionPane.showMessageDialog(this, "수정완료");
					currentProduct = stockProductDTO;
					getProductList();
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj.equals(bt_regist)) {
			regist(); // 등록
		} else if (obj.equals(bt_search)) {

		} else if (obj.equals(bt_edit)) {
			if (currentProduct != null) { // 선택한 상품이 있다면
				update(); // 수정
			} else {
				JOptionPane.showMessageDialog(this, "선택안함?");
			}
		} else if (obj.equals(bt_del)) {
			del(); // 삭제
		}
	}

}
